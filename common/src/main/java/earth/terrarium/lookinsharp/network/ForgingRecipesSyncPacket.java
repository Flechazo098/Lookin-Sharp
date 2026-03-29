package earth.terrarium.lookinsharp.network;

import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.Side;
import cc.sighs.oelib.network.serialization.NetFieldCodec;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.recipe.ForgingRecipeAccess;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NetworkPacket(modId = LookinSharp.MOD_ID, id = "forging_recipes_sync", side = Side.CLIENT)
public record ForgingRecipesSyncPacket(List<Entry> entries) implements INetworkPacket<ForgingRecipesSyncPacket> {

    public static ForgingRecipesSyncPacket from(SelectableRecipe.SingleInputSet<StonecutterRecipe> recipes) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (SelectableRecipe.SingleInputEntry<StonecutterRecipe> entry : recipes.entries()) {
            if (entry.recipe().optionDisplay() instanceof SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate stack)) {
                entries.add(new Entry(entry.input(), stack));
            }
        }
        return new ForgingRecipesSyncPacket(entries);
    }

    public SelectableRecipe.SingleInputSet<StonecutterRecipe> asRecipeSet() {
        ArrayList<SelectableRecipe.SingleInputEntry<StonecutterRecipe>> rebuilt = new ArrayList<>(this.entries.size());
        for (Entry entry : this.entries) {
            rebuilt.add(new SelectableRecipe.SingleInputEntry<>(
                    entry.input(),
                    new SelectableRecipe<>(new SlotDisplay.ItemStackSlotDisplay(entry.result()), Optional.empty())
            ));
        }
        return new SelectableRecipe.SingleInputSet<>(rebuilt);
    }

    @Override
    public void handle(INetworkContext context) {
        context.enqueueWork(() -> {
            if (!context.isClientSide() || context.client() == null) {
                return;
            }

            ClientPacketListener connection = context.client().getConnection();
            if (connection == null) {
                return;
            }

            if (connection.recipes() instanceof ForgingRecipeAccess access) {
                access.lookinsharp$setForgingRecipes(asRecipeSet());
            }
        });
    }

    public record Entry(
            @NetFieldCodec(holder = Ingredient.class, field = "CONTENTS_STREAM_CODEC")
            Ingredient input,
            @NetFieldCodec(holder = ItemStackTemplate.class)
            ItemStackTemplate result
    ) {
    }
}
