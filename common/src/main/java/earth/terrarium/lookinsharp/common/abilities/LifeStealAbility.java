package earth.terrarium.lookinsharp.common.abilities;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LifeStealAbility implements ToolAbility {
    @Override
    public boolean onHit(DamageSource source, LivingEntity victim, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker) {
            attacker.heal(amount / 4);
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LifeStealAbility;
    }

    @Override
    public int hashCode() {
        return LifeStealAbility.class.hashCode();
    }
}