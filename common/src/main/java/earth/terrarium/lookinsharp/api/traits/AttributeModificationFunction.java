package earth.terrarium.lookinsharp.api.traits;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

@FunctionalInterface
public interface AttributeModificationFunction {
    void modifyAttribute(Holder<Attribute> attribute, AttributeModifier modifier);
}
