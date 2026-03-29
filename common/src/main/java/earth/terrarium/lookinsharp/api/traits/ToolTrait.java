package earth.terrarium.lookinsharp.api.traits;

import earth.terrarium.lookinsharp.api.rarities.ToolRarity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ToolTrait {
    int getWeight();

    @Nullable
    default Integer getColor() {
        return null;
    }

    void modifyAttributes(ItemStack stack, EquipmentSlot slot, AttributeModificationFunction function, ToolRarity rarity);
}
