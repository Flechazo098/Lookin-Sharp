package earth.terrarium.lookinsharp.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.api.abilities.ToolAbilityManager;
import earth.terrarium.lookinsharp.common.items.BaseSword;
import earth.terrarium.lookinsharp.common.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ArtifactAttachmentRecipe implements SmithingRecipe {
    private final ResourceLocation type;
    private final Ingredient artifactIngredient;
    private final Ingredient addonIngredient;
    private final ToolAbility result;

    public ArtifactAttachmentRecipe(ResourceLocation type, Ingredient artifactIngredient, Ingredient addonIngredient, ToolAbility result) {
        this.type = type;
        this.artifactIngredient = artifactIngredient;
        this.addonIngredient = addonIngredient;
        this.result = result;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack itemStack) {
        return artifactIngredient.test(itemStack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack itemStack) {
        return itemStack.getItem() instanceof BaseSword;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack itemStack) {
        return addonIngredient.test(itemStack);
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return artifactIngredient.test(input.getItem(0)) &&
                input.getItem(1).getItem() instanceof BaseSword &&
                addonIngredient.test(input.getItem(2));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SmithingRecipeInput input, @NotNull HolderLookup.Provider provider) {
        ItemStack sword = input.getItem(1);
        ItemStack result = sword.copy();
        if (result.getItem() instanceof BaseSword swordItem) {
            swordItem.setAbility(result, this.result);
        }
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARTIFACT_ATTACHMENT_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 3 && pHeight >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(LookinSharp.MOD_ID, "iron_sword")));
        if (stack.getItem() instanceof BaseSword sword) {
            sword.setAbility(stack, result);
        }
        return stack;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    public Ingredient getArtifactIngredient() {
        return artifactIngredient;
    }

    public Ingredient getAddonIngredient() {
        return addonIngredient;
    }

    public ToolAbility getResult() {
        return result;
    }

    public static class Serializer implements RecipeSerializer<ArtifactAttachmentRecipe> {
        public static final MapCodec<ArtifactAttachmentRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(recipe -> recipe.type),
                Ingredient.CODEC.fieldOf("artifact").forGetter(ArtifactAttachmentRecipe::getArtifactIngredient),
                Ingredient.CODEC.fieldOf("addon").forGetter(ArtifactAttachmentRecipe::getAddonIngredient),
                ToolAbilityManager.CODEC.fieldOf("result").forGetter(ArtifactAttachmentRecipe::getResult)
        ).apply(instance, ArtifactAttachmentRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ArtifactAttachmentRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::encode,
                Serializer::decode
        );

        @Override
        public @NotNull MapCodec<ArtifactAttachmentRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ArtifactAttachmentRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void encode(RegistryFriendlyByteBuf buffer, ArtifactAttachmentRecipe recipe) {
            buffer.writeResourceLocation(recipe.type);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.artifactIngredient);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addonIngredient);
            ToolAbilityManager.STREAM_CODEC.encode(buffer, recipe.result);
        }

        private static ArtifactAttachmentRecipe decode(RegistryFriendlyByteBuf buffer) {
            ResourceLocation id = buffer.readResourceLocation();
            Ingredient artifactIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addonIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ToolAbility result = ToolAbilityManager.STREAM_CODEC.decode(buffer);
            return new ArtifactAttachmentRecipe(id, artifactIngredient, addonIngredient, result);
        }
    }
}