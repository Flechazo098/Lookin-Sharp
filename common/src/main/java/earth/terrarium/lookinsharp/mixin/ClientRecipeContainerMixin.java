package earth.terrarium.lookinsharp.mixin;

import earth.terrarium.lookinsharp.common.recipe.ForgingRecipeAccess;
import net.minecraft.client.multiplayer.ClientRecipeContainer;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientRecipeContainer.class)
public abstract class ClientRecipeContainerMixin implements ForgingRecipeAccess {
    @Unique
    private SelectableRecipe.SingleInputSet<StonecutterRecipe> lookinsharp$forgingRecipes = SelectableRecipe.SingleInputSet.empty();

    @Override
    public SelectableRecipe.SingleInputSet<StonecutterRecipe> lookinsharp$forgingRecipes() {
        return this.lookinsharp$forgingRecipes;
    }

    @Override
    public void lookinsharp$setForgingRecipes(SelectableRecipe.SingleInputSet<StonecutterRecipe> recipes) {
        this.lookinsharp$forgingRecipes = recipes;
    }
}
