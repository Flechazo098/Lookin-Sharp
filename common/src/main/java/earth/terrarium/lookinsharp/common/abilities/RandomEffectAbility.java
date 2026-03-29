package earth.terrarium.lookinsharp.common.abilities;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RandomEffectAbility implements ToolAbility {
    public static final WeightedList<AttackFunction> EFFECTS = WeightedList.<AttackFunction>builder()
            .add(((attacker, victim) -> victim.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1))), 2)
            .add(((attacker, victim) -> victim.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1))), 2)
            .add(((attacker, victim) -> victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1))), 2)
            .add(((attacker, victim) -> victim.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1))), 2)
            .add(((attacker, victim) -> victim.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 30, 4))), 2)
            .add(((attacker, victim) -> {
            }), 10)
            .add(((attacker, victim) -> attacker.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2))), 1)
            .add(((attacker, victim) -> attacker.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 60, 1))), 1)
            .add(((attacker, victim) -> attacker.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1))), 1)
            .add(((attacker, victim) -> attacker.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 1))), 1)
            .add(((attacker, victim) -> attacker.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 60, 1))), 1)
            .build();

    @Override
    public boolean onHit(DamageSource source, LivingEntity victim, float amount) {
        if (source.getEntity() instanceof LivingEntity attacker) {
            EFFECTS.getRandom(RandomSource.create()).ifPresent(effect -> effect.apply(attacker, victim));
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RandomEffectAbility;
    }

    @Override
    public int hashCode() {
        return RandomEffectAbility.class.hashCode();
    }

    public interface AttackFunction {
        void apply(LivingEntity attacker, LivingEntity victim);
    }
}
