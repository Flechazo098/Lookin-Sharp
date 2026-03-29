package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public enum MaterialTypes {
    WOODEN(ToolMaterial.WOOD),
    STONE(ToolMaterial.STONE),
    IRON(ToolMaterial.IRON),
    GOLDEN(ToolMaterial.GOLD),
    DIAMOND(ToolMaterial.DIAMOND),
    NETHERITE(ToolMaterial.NETHERITE);

    public static final MaterialTypes[] VALUES = values();

    public final List<RegisterSupplier<Item>> items;
    public final ToolMaterial tier;

    MaterialTypes(ToolMaterial tier) {
        this.items = new ArrayList<>();
        this.tier = tier;
    }
}
