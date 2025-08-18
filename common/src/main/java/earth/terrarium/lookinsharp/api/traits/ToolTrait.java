package earth.terrarium.lookinsharp.api.traits;

import earth.terrarium.lookinsharp.api.rarities.ToolRarity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface ToolTrait {
    int getWeight();
    void modifyAttributes(ItemStack stack, EquipmentSlot slot, AttributeModificationFunction function, ToolRarity rarity);
}
