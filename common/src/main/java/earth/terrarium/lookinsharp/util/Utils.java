package earth.terrarium.lookinsharp.util;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.common.items.BaseSword;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Utils {
    public static Holder<Attribute> getReachAttribute() {
        return Attributes.BLOCK_INTERACTION_RANGE;
    }

    public static Holder<Attribute> getAttackRangeAttribute() {
        return Attributes.ENTITY_INTERACTION_RANGE;
    }

    public static boolean onEntityHit(LivingEntity victim, DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof BaseSword tool) {
                ToolAbility ability = tool.getAbility(stack);
                boolean canAttack = true;
                if (ability != null) {
                    return ability.onHit(source, victim, amount);
                } else {
                    return canAttack;
                }
            }
        }
        return true;
    }
}
