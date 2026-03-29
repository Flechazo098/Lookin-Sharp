package earth.terrarium.lookinsharp.mixin;

import earth.terrarium.lookinsharp.common.recipe.ForgingRecipeAccess;
import net.minecraft.world.item.crafting.RecipeAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeAccess.class)
public interface RecipeAccessMixin extends ForgingRecipeAccess {
}
