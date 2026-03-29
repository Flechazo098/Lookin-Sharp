package earth.terrarium.lookinsharp.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.api.abilities.ToolAbilityManager;
import earth.terrarium.lookinsharp.common.items.BaseSword;
import earth.terrarium.lookinsharp.common.registry.ModItems;
import earth.terrarium.lookinsharp.common.registry.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleSmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ArtifactAttachmentRecipe extends SimpleSmithingRecipe {
    public static final MapCodec<ArtifactAttachmentRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            Identifier.CODEC.fieldOf("type").forGetter(recipe -> recipe.type),
            Ingredient.CODEC.fieldOf("artifact").forGetter(ArtifactAttachmentRecipe::getArtifactIngredient),
            Ingredient.CODEC.fieldOf("addon").forGetter(ArtifactAttachmentRecipe::getAddonIngredient),
            ToolAbilityManager.CODEC.fieldOf("result").forGetter(ArtifactAttachmentRecipe::getResult)
    ).apply(instance, ArtifactAttachmentRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArtifactAttachmentRecipe> STREAM_CODEC = StreamCodec.of(
            ArtifactAttachmentRecipe::encode,
            ArtifactAttachmentRecipe::decode
    );

    private final Identifier type;
    private final Ingredient artifactIngredient;
    private final Ingredient addonIngredient;
    private final ToolAbility result;

    public ArtifactAttachmentRecipe(
            Recipe.CommonInfo commonInfo,
            Identifier type,
            Ingredient artifactIngredient,
            Ingredient addonIngredient,
            ToolAbility result
    ) {
        super(commonInfo);
        this.type = type;
        this.artifactIngredient = artifactIngredient;
        this.addonIngredient = addonIngredient;
        this.result = result;
    }

    public ArtifactAttachmentRecipe(Identifier type, Ingredient artifactIngredient, Ingredient addonIngredient, ToolAbility result) {
        this(new Recipe.CommonInfo(true), type, artifactIngredient, addonIngredient, result);
    }

    @Override
    public boolean matches(SmithingRecipeInput input, net.minecraft.world.level.Level level) {
        return this.artifactIngredient.test(input.template())
                && input.base().getItem() instanceof BaseSword
                && this.addonIngredient.test(input.addition());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SmithingRecipeInput input) {
        ItemStack sword = input.base();
        ItemStack result = sword.copy();
        if (result.getItem() instanceof BaseSword swordItem) {
            swordItem.setAbility(result, this.result);
        }
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<ArtifactAttachmentRecipe> getSerializer() {
        return ModRecipes.ARTIFACT_ATTACHMENT_SERIALIZER.get();
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.of(this.artifactIngredient);
    }

    @Override
    public Ingredient baseIngredient() {
        return Ingredient.of(ModItems.SWORDS.stream().map(supplier -> supplier.get().asItem()));
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(this.addonIngredient);
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

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(List.of(
                Optional.of(this.artifactIngredient),
                Optional.of(this.baseIngredient()),
                Optional.of(this.addonIngredient)
        ));
    }

    private static void encode(RegistryFriendlyByteBuf buffer, ArtifactAttachmentRecipe recipe) {
        Recipe.CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        buffer.writeIdentifier(recipe.type);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.artifactIngredient);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addonIngredient);
        ToolAbilityManager.STREAM_CODEC.encode(buffer, recipe.result);
    }

    private static ArtifactAttachmentRecipe decode(RegistryFriendlyByteBuf buffer) {
        Recipe.CommonInfo commonInfo = Recipe.CommonInfo.STREAM_CODEC.decode(buffer);
        Identifier id = buffer.readIdentifier();
        Ingredient artifactIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient addonIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ToolAbility result = ToolAbilityManager.STREAM_CODEC.decode(buffer);
        return new ArtifactAttachmentRecipe(commonInfo, id, artifactIngredient, addonIngredient, result);
    }
}
