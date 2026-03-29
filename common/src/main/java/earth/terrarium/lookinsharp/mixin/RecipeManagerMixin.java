package earth.terrarium.lookinsharp.mixin;

import earth.terrarium.lookinsharp.common.recipe.ForgingRecipeAccess;
import earth.terrarium.lookinsharp.common.recipe.ForgingRecipe;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin implements ForgingRecipeAccess {
    @Shadow
    private RecipeMap recipes;

    @Unique
    private SelectableRecipe.SingleInputSet<StonecutterRecipe> lookinsharp$forgingRecipes = SelectableRecipe.SingleInputSet.empty();

    @Inject(method = "finalizeRecipeLoading", at = @At("TAIL"))
    private void lookinsharp$appendForgingRecipes(FeatureFlagSet enabledFlags, CallbackInfo ci) {
        ArrayList<SelectableRecipe.SingleInputEntry<StonecutterRecipe>> forging = new ArrayList<>();

        for (RecipeHolder<?> recipeHolder : this.recipes.values()) {
            if (!(recipeHolder.value() instanceof ForgingRecipe forgingRecipe)) {
                continue;
            }

            if (!forgingRecipe.getInput().items().allMatch(i -> i.value().isEnabled(enabledFlags))) {
                continue;
            }

            for (ItemStackTemplate result : forgingRecipe.getResultTemplates()) {
                if (!result.item().value().isEnabled(enabledFlags)) {
                    continue;
                }

                SelectableRecipe.SingleInputEntry<StonecutterRecipe> entry =
                        new SelectableRecipe.SingleInputEntry<>(
                                forgingRecipe.getInput(),
                                new SelectableRecipe<>(new SlotDisplay.ItemStackSlotDisplay(result), Optional.empty())
                        );

                forging.add(entry);
            }
        }

        this.lookinsharp$forgingRecipes = new SelectableRecipe.SingleInputSet<>(forging);
    }

    @Override
    public SelectableRecipe.SingleInputSet<StonecutterRecipe> lookinsharp$forgingRecipes() {
        return this.lookinsharp$forgingRecipes;
    }

    @Override
    public void lookinsharp$setForgingRecipes(SelectableRecipe.SingleInputSet<StonecutterRecipe> recipes) {
        this.lookinsharp$forgingRecipes = recipes;
    }
}
