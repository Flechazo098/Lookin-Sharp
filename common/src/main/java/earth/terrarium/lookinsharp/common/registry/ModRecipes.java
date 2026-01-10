package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.recipe.ArtifactAttachmentRecipe;
import earth.terrarium.lookinsharp.common.recipe.ForgingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create( Registries.RECIPE_TYPE, LookinSharp.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LookinSharp.MOD_ID);

    public static final RegisterSupplier<RecipeType<ForgingRecipe>> FORGING =
            RECIPE_TYPES.register("forging", () -> RecipeType.register("forging"));

    public static final RegisterSupplier<RecipeSerializer<ForgingRecipe>> FORGING_SERIALIZER = RECIPE_SERIALIZERS.register("forging", ForgingRecipe.Serializer::new);

    public static final RegisterSupplier<RecipeSerializer<ArtifactAttachmentRecipe>> ARTIFACT_ATTACHMENT_SERIALIZER = RECIPE_SERIALIZERS.register("artifact_attachment", ArtifactAttachmentRecipe.Serializer::new);

}