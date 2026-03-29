package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.types.BuiltInSwordTypes;
import earth.terrarium.lookinsharp.common.items.BaseSword;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, LookinSharp.MOD_ID);

    public static final List<RegisterSupplier<Item>> SWORDS = new ArrayList<>();
    public static final List<RegisterSupplier<Item>> CRAFTING_COMPONENTS = new ArrayList<>();

    public static final RegisterSupplier<Item> FORGING_ANVIL =
            ITEMS.register("forging_station", () -> new BlockItem(ModBlocks.FORGING_ANVIL.get(), itemProperties("forging_station")));

    //artifacts - withering, deadly, sticky, glacial, tonic, weakening, vampiric, battering
    public static final RegisterSupplier<Item> WITHERING_ARTIFACT = registerCraftingComponent("withering_artifact", () -> new Item(itemProperties("withering_artifact")));
    public static final RegisterSupplier<Item> DEADLY_ARTIFACT = registerCraftingComponent("deadly_artifact", () -> new Item(itemProperties("deadly_artifact")));
    public static final RegisterSupplier<Item> STICKY_ARTIFACT = registerCraftingComponent("sticky_artifact", () -> new Item(itemProperties("sticky_artifact")));
    public static final RegisterSupplier<Item> GLACIAL_ARTIFACT = registerCraftingComponent("glacial_artifact", () -> new Item(itemProperties("glacial_artifact")));
    public static final RegisterSupplier<Item> TONIC_ARTIFACT = registerCraftingComponent("tonic_artifact", () -> new Item(itemProperties("tonic_artifact")));
    public static final RegisterSupplier<Item> WEAKENING_ARTIFACT = registerCraftingComponent("weakening_artifact", () -> new Item(itemProperties("weakening_artifact")));
    public static final RegisterSupplier<Item> VAMPIRIC_ARTIFACT = registerCraftingComponent("vampiric_artifact", () -> new Item(itemProperties("vampiric_artifact")));
    public static final RegisterSupplier<Item> BATTERING_ARTIFACT = registerCraftingComponent("battering_artifact", () -> new Item(itemProperties("battering_artifact")));


    static {
        for (MaterialTypes value : MaterialTypes.VALUES) {
                var name = value.name().toLowerCase(Locale.ROOT);
            for (BuiltInSwordTypes type : BuiltInSwordTypes.VALUES) {
                var typeName = type.name().toLowerCase(Locale.ROOT);
                var swordName = name + "_" + typeName;
                RegisterSupplier<Item> sword = ITEMS.register(swordName, () -> new BaseSword(value.tier, type, 3, -2.4f, itemProperties(swordName)));
                SWORDS.add(sword);
                value.items.add(sword);
            }
        }
    }

    public static RegisterSupplier<Item> registerCraftingComponent(String name, Supplier<Item> supplier) {
        RegisterSupplier<Item> item = ITEMS.register(name, supplier);
        CRAFTING_COMPONENTS.add(item);
        return item;
    }

    private static Item.Properties itemProperties(String path) {
        return new Item.Properties().setId(
                ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, path))
        );
    }
}
