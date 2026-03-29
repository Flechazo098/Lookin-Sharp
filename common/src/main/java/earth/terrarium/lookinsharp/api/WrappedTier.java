package earth.terrarium.lookinsharp.api;

import net.minecraft.world.item.ToolMaterial;

public class WrappedTier {
    private final ToolMaterial material;
    private final float maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final float enchantability;

    public WrappedTier(ToolMaterial material, float maxUses, float efficiency, float attackDamage, float enchantability) {
        this.material = material;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    public ToolMaterial asToolMaterial() {
        return new ToolMaterial(
                material.incorrectBlocksForDrops(),
                (int) (material.durability() * maxUses),
                material.speed() * efficiency,
                material.attackDamageBonus() * attackDamage,
                (int) (material.enchantmentValue() * enchantability),
                material.repairItems()
        );
    }

    //builder

    public static class Builder {
        private final ToolMaterial material;
        private float maxUses = 1;
        private float efficiency = 1;
        private float attackDamage = 1;
        private float enchantability = 1;

        public Builder(ToolMaterial material) {
            this.material = material;
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
            return new WrappedTier(material, maxUses, efficiency, attackDamage, enchantability);
        }
    }
}
