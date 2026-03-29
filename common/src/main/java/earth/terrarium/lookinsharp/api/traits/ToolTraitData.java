package earth.terrarium.lookinsharp.api.traits;

import cc.sighs.oelib.data.api.DataDriven;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.lookinsharp.api.rarities.ToolRarity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@DataDriven(
        modid = "lookinsharp",
        folder = "tool_traits",
        syncToClient = true
)
public record ToolTraitData(
        int weight,
        @Nullable Integer color,
        Map<String, List<AttributeModifierEntry>> attributeModifiers
) implements ToolTrait {

    public static final Codec<ToolTraitData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("weight").forGetter(ToolTraitData::weight),
                    Codec.INT.optionalFieldOf("color").forGetter(data ->
                            data.color != null ? java.util.Optional.of(data.color) : java.util.Optional.empty()),
                    Codec.unboundedMap(
                            Codec.STRING,
                            AttributeModifierEntry.CODEC.listOf()
                    ).fieldOf("attributeModifiers").forGetter(ToolTraitData::attributeModifiers)
            ).apply(instance, (weight, color, attributeModifiers) ->
                    new ToolTraitData(weight, color.orElse(null), attributeModifiers))
    );

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    @Nullable
    public Integer getColor() {
        return color;
    }

    @Override
    public void modifyAttributes(ItemStack stack, EquipmentSlot slot, AttributeModificationFunction function, ToolRarity rarity) {
        String slotName = slot.getName();
        List<AttributeModifierEntry> modifiers = attributeModifiers.get(slotName);
        if (modifiers != null) {
            for (AttributeModifierEntry entry : modifiers) {
                Holder<Attribute> attribute = BuiltInRegistries.ATTRIBUTE.get(entry.attribute()).orElse(null);
                if (attribute != null) {
                    double scaledAmount = entry.modifier().amount() * rarity.getMultiplier();
                    AttributeModifier scaledModifier = new AttributeModifier(
                            entry.modifier().id(),
                            scaledAmount,
                            entry.modifier().operation()
                    );
                    function.modifyAttribute(attribute, scaledModifier);
                }
            }
        }
    }

    public record AttributeModifierEntry(
            Identifier attribute,
            AttributeModifier modifier
    ) {
        public static final Codec<AttributeModifierEntry> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Identifier.CODEC.fieldOf("attribute").forGetter(AttributeModifierEntry::attribute),
                        AttributeModifier.CODEC.fieldOf("modifier").forGetter(AttributeModifierEntry::modifier)
                ).apply(instance, AttributeModifierEntry::new)
        );
    }
}
