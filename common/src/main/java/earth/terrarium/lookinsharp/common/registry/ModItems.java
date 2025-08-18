package earth.terrarium.lookinsharp.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.types.BuiltInSwordTypes;
import earth.terrarium.lookinsharp.common.items.BaseSword;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(LookinSharp.MOD_ID, Registries.ITEM);

    public static final List<RegistrySupplier<Item>> SWORDS = new ArrayList<>();
    public static final List<RegistrySupplier<Item>> CRAFTING_COMPONENTS = new ArrayList<>();

    public static final RegistrySupplier<Item> FORGING_ANVIL = ITEMS.register("forging_station", () -> new BlockItem(ModBlocks.FORGING_ANVIL.get(), new Item.Properties()));

    //artifacts - withering, deadly, sticky, glacial, tonic, weakening, vampiric, battering
    public static final RegistrySupplier<Item> WITHERING_ARTIFACT = registerCraftingComponent("withering_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> DEADLY_ARTIFACT = registerCraftingComponent("deadly_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> STICKY_ARTIFACT = registerCraftingComponent("sticky_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> GLACIAL_ARTIFACT = registerCraftingComponent("glacial_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> TONIC_ARTIFACT = registerCraftingComponent("tonic_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> WEAKENING_ARTIFACT = registerCraftingComponent("weakening_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> VAMPIRIC_ARTIFACT = registerCraftingComponent("vampiric_artifact", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> BATTERING_ARTIFACT = registerCraftingComponent("battering_artifact", () -> new Item(new Item.Properties()));


    static {
        for (MaterialTypes value : MaterialTypes.VALUES) {
            var name = value.name().toLowerCase(Locale.ROOT);
            for (BuiltInSwordTypes type : BuiltInSwordTypes.VALUES) {
                var typeName = type.name().toLowerCase(Locale.ROOT);
                var swordName = name + "_" + typeName;
                RegistrySupplier<Item> sword = ITEMS.register(swordName, () -> new BaseSword(value.tier, type, 3, -2.4f, new Item.Properties()));
                SWORDS.add(sword);
                value.items.add(sword);
            }
        }
    }

    public static RegistrySupplier<Item> registerCraftingComponent(String name, Supplier<Item> supplier) {
        RegistrySupplier<Item> item = ITEMS.register(name, supplier);
        CRAFTING_COMPONENTS.add(item);
        return item;
    }
}