package earth.terrarium.lookinsharp.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.lookinsharp.common.registry.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ForgingRecipe implements Recipe<RecipeInput> {
    public static final MapCodec<ForgingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            Identifier.CODEC.fieldOf("type").forGetter(recipe -> recipe.type),
            Ingredient.CODEC.fieldOf("input").forGetter(ForgingRecipe::getInput),
            ItemStackTemplate.CODEC.listOf().fieldOf("results").forGetter(ForgingRecipe::getResultTemplates)
    ).apply(instance, ForgingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ForgingRecipe> STREAM_CODEC = StreamCodec.of(
            ForgingRecipe::encode,
            ForgingRecipe::decode
    );

    private final Recipe.CommonInfo commonInfo;
    private final Identifier type;
    private final Ingredient input;
    private final List<ItemStackTemplate> results;
    private @Nullable PlacementInfo placementInfo;

    public ForgingRecipe(Recipe.CommonInfo commonInfo, Identifier type, Ingredient input, List<ItemStackTemplate> results) {
        this.commonInfo = commonInfo;
        this.type = type;
        this.input = input;
        this.results = List.copyOf(results);
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return this.input.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput) {
        return this.results.isEmpty() ? ItemStack.EMPTY : this.results.getFirst().create();
    }

    @Override
    public boolean showNotification() {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public @NotNull RecipeSerializer<ForgingRecipe> getSerializer() {
        return ModRecipes.FORGING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<ForgingRecipe> getType() {
        return ModRecipes.FORGING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.input);
        }
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public List<ItemStack> getResults() {
        return this.results.stream().map(ItemStackTemplate::create).toList();
    }

    public List<ItemStackTemplate> getResultTemplates() {
        return this.results;
    }

    private static void encode(RegistryFriendlyByteBuf buffer, ForgingRecipe recipe) {
        Recipe.CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        buffer.writeIdentifier(recipe.type);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
        buffer.writeVarInt(recipe.results.size());
        for (ItemStackTemplate result : recipe.results) {
            ItemStackTemplate.STREAM_CODEC.encode(buffer, result);
        }
    }

    private static ForgingRecipe decode(RegistryFriendlyByteBuf buffer) {
        Recipe.CommonInfo commonInfo = Recipe.CommonInfo.STREAM_CODEC.decode(buffer);
        Identifier id = buffer.readIdentifier();
        Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        int size = buffer.readVarInt();
        List<ItemStackTemplate> results = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            results.add(ItemStackTemplate.STREAM_CODEC.decode(buffer));
        }
        return new ForgingRecipe(commonInfo, id, input, results);
    }
}
