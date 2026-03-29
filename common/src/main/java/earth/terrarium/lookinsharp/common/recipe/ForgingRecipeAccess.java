package earth.terrarium.lookinsharp.common.recipe;

import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public interface ForgingRecipeAccess {
    default SelectableRecipe.SingleInputSet<StonecutterRecipe> lookinsharp$forgingRecipes() {
        return SelectableRecipe.SingleInputSet.empty();
    }

    default void lookinsharp$setForgingRecipes(SelectableRecipe.SingleInputSet<StonecutterRecipe> recipes) {
    }
}
