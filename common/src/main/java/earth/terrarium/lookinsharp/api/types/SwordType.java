package earth.terrarium.lookinsharp.api.types;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public interface SwordType {
    default void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
    }
}
