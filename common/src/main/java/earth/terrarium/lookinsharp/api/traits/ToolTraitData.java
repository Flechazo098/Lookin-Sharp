package earth.terrarium.lookinsharp.api.traits;

import com.mafuyu404.oelib.api.data.DataDriven;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import earth.terrarium.lookinsharp.api.rarities.ToolRarity;

import java.util.List;
import java.util.Map;

@DataDriven(
    modid = "lookinsharp",
    folder = "tool_traits",
    syncToClient = true
)
public record ToolTraitData(
    int weight,
    Map<String, List<AttributeModifierEntry>> attributeModifiers
) implements ToolTrait {
    
    public static final Codec<ToolTraitData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("weight").forGetter(ToolTraitData::weight),
            Codec.unboundedMap(
                Codec.STRING,
                AttributeModifierEntry.CODEC.listOf()
            ).fieldOf("attributeModifiers").forGetter(ToolTraitData::attributeModifiers)
        ).apply(instance, ToolTraitData::new)
    );

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void modifyAttributes(ItemStack stack, EquipmentSlot slot, AttributeModificationFunction function, ToolRarity rarity) {
        String slotName = slot.getName();
        List<AttributeModifierEntry> modifiers = attributeModifiers.get(slotName);
        if (modifiers != null) {
            for (AttributeModifierEntry entry : modifiers) {
                Holder<Attribute> attribute = BuiltInRegistries.ATTRIBUTE.getHolder(entry.attribute()).orElse(null);
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
        ResourceLocation attribute,
        AttributeModifier modifier
    ) {
        public static final Codec<AttributeModifierEntry> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("attribute").forGetter(AttributeModifierEntry::attribute),
                AttributeModifier.CODEC.fieldOf("modifier").forGetter(AttributeModifierEntry::modifier)
            ).apply(instance, AttributeModifierEntry::new)
        );
    }
}