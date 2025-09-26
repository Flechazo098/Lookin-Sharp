//package earth.terrarium.lookinsharp.compat.botarium.items;
//
//import net.minecraft.tags.BlockTags;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.Tier;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.level.block.Block;
//import org.jetbrains.annotations.NotNull;
//
//public class VitaliumMaterial implements Tier {
//    public static final VitaliumMaterial INSTANCE = new VitaliumMaterial();
//
//    @Override
//    public int getUses() {
//        return 1000;
//    }
//
//    @Override
//    public float getSpeed() {
//        return 8.0F;
//    }
//
//    @Override
//    public float getAttackDamageBonus() {
//        return 3.5F;
//    }
//
//    @Override
//    public TagKey<Block> getIncorrectBlocksForDrops() {
//        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
//    }
//
//    @Override
//    public int getEnchantmentValue() {
//        return 15;
//    }
//
//    @Override
//    public @NotNull Ingredient getRepairIngredient() {
//        return Ingredient.EMPTY;
//    }
//}
