package earth.terrarium.lookinsharp.common.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.ArrayList;
import java.util.List;

public enum MaterialTypes {
    WOODEN(Tiers.WOOD),
    STONE(Tiers.STONE),
    IRON(Tiers.IRON),
    GOLDEN(Tiers.GOLD),
    DIAMOND(Tiers.DIAMOND),
    NETHERITE(Tiers.NETHERITE);

    public static final MaterialTypes[] VALUES = values();

    public final List<RegistrySupplier<Item>> items;
    public final Tier tier;

    MaterialTypes(Tier tier) {
        this.items = new ArrayList<>();
        this.tier = tier;
    }
}