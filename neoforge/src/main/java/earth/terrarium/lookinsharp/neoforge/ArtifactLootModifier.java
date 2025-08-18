package earth.terrarium.lookinsharp.neoforge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ArtifactLootModifier extends LootModifier {
    // 修改为MapCodec
    public static final Supplier<MapCodec<ArtifactLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> inst.group(
                    LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter((lm) -> lm.conditions),
                    ItemStack.CODEC.fieldOf("result").forGetter((lm) -> lm.result)
            ).apply(inst, ArtifactLootModifier::new))
    );

    public final ItemStack result;

    protected ArtifactLootModifier(LootItemCondition[] conditionsIn, ItemStack result) {
        super(conditionsIn);
        this.result = result;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext arg) {
        objectArrayList.add(result.copy());
        return objectArrayList;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return LookinSharpNeoForge.ARTIFACT_LOOT_MODIFIER.get();
    }
}