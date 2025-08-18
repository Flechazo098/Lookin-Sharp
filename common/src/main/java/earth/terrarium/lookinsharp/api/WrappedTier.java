package earth.terrarium.lookinsharp.api;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class WrappedTier implements Tier {
    private final Tier tier;
    private final float maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final float enchantability;

    public WrappedTier(Tier tier, float maxUses, float efficiency, float attackDamage, float enchantability) {
        this.tier = tier;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    @Override
    public int getUses() {
        return (int) (tier.getUses() * maxUses);
    }

    @Override
    public float getSpeed() {
        return tier.getSpeed() * efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return tier.getAttackDamageBonus() * attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return tier.getIncorrectBlocksForDrops();
    }

    @Override
    public int getEnchantmentValue() {
        return (int) (tier.getEnchantmentValue() * enchantability);
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return tier.getRepairIngredient();
    }

    //builder

    public static class Builder {
        private final Tier tier;
        private float maxUses = 1;
        private float efficiency = 1;
        private float attackDamage = 1;
        private float enchantability = 1;

        public Builder(Tier tier) {
            this.tier = tier;
        }

        public Builder maxUses(float maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        public Builder efficiency(float efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public Builder attackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }

        public Builder enchantability(float enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public WrappedTier build() {
            return new WrappedTier(tier, maxUses, efficiency, attackDamage, enchantability);
        }
    }
}
