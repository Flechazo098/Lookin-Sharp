package earth.terrarium.lookinsharp.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.lookinsharp.common.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ForgingRecipe implements Recipe<RecipeInput> {
    private final ResourceLocation type;
    private final Ingredient input;
    private final List<ItemStack> results;

    public ForgingRecipe(ResourceLocation type, Ingredient input, List<ItemStack> results) {
        this.type = type;
        this.input = input;
        this.results = results;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return input.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return results.isEmpty() ? ItemStack.EMPTY : results.getFirst().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return results.isEmpty() ? ItemStack.EMPTY : results.getFirst();
    }

    @Override
    public String getGroup() {
        return Recipe.super.getGroup();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.FORGING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.FORGING.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public List<ItemStack> getResults() {
        return results;
    }

    public static class Serializer implements RecipeSerializer<ForgingRecipe> {
        public static final MapCodec<ForgingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(recipe -> recipe.type),
                Ingredient.CODEC.fieldOf("input").forGetter(ForgingRecipe::getInput),
                ItemStack.CODEC.listOf().fieldOf("results").forGetter(ForgingRecipe::getResults)
        ).apply(instance, ForgingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ForgingRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ForgingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ForgingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ForgingRecipe recipe) {
            buffer.writeResourceLocation(recipe.type);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeVarInt(recipe.results.size());
            for (ItemStack result : recipe.results) {
                ItemStack.STREAM_CODEC.encode(buffer, result);
            }
        }

        private static ForgingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ResourceLocation id = buffer.readResourceLocation();
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int size = buffer.readVarInt();
            List<ItemStack> results = NonNullList.withSize(size, ItemStack.EMPTY);
            for (int i = 0; i < size; i++) {
                results.set(i, ItemStack.STREAM_CODEC.decode(buffer));
            }
            return new ForgingRecipe(id, input, results);
        }
    }
}