package earth.terrarium.lookinsharp.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.recipe.ArtifactAttachmentRecipe;
import earth.terrarium.lookinsharp.common.recipe.ForgingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(LookinSharp.MOD_ID, Registries.RECIPE_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(LookinSharp.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeType<ForgingRecipe>> FORGING =
            RECIPE_TYPES.register("forging", () -> RecipeType.register("forging"));

    public static final RegistrySupplier<RecipeSerializer<ForgingRecipe>> FORGING_SERIALIZER = RECIPE_SERIALIZERS.register("forging", ForgingRecipe.Serializer::new);

    public static final RegistrySupplier<RecipeSerializer<ArtifactAttachmentRecipe>> ARTIFACT_ATTACHMENT_SERIALIZER = RECIPE_SERIALIZERS.register("artifact_attachment", ArtifactAttachmentRecipe.Serializer::new);

}