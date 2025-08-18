package earth.terrarium.lookinsharp.common.abilities;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class GolemAbility implements ToolAbility {
    @Override
    public boolean onHit(DamageSource source, LivingEntity victim, float amount) {
        victim.setDeltaMovement(victim.getDeltaMovement().x, Math.max(victim.getDeltaMovement().y, 0) + .4, victim.getDeltaMovement().z);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GolemAbility;
    }

    @Override
    public int hashCode() {
        return GolemAbility.class.hashCode();
    }
}