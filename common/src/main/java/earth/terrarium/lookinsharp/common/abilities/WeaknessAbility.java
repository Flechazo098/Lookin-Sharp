package earth.terrarium.lookinsharp.common.abilities;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class WeaknessAbility implements ToolAbility {
    @Override
    public boolean onHit(DamageSource source, LivingEntity victim, float amount) {
        victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WeaknessAbility;
    }

    @Override
    public int hashCode() {
        return WeaknessAbility.class.hashCode();
    }
}