package earth.terrarium.lookinsharp.api.abilities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface ToolAbility {
    default boolean onHit(DamageSource source, LivingEntity victim, float amount) {
        return true;
    }
}
