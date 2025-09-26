//package earth.terrarium.lookinsharp.compat.botarium.registry;
//
//import dev.architectury.registry.registries.RegistrySupplier;
//import earth.terrarium.lookinsharp.api.types.BuiltInSwordTypes;
//import earth.terrarium.lookinsharp.common.items.BaseSword;
//import earth.terrarium.lookinsharp.common.registry.ModItems;
//import earth.terrarium.lookinsharp.compat.botarium.items.VitaliumMaterial;
//import net.minecraft.world.item.Item;
//
//public class BotariumItems {
//    public static final RegistrySupplier<Item> VITALIUM_INGOT = ModItems.registerCraftingComponent("vitalium_ingot", () -> new Item(new Item.Properties()));
//
//    static {
//        for (BuiltInSwordTypes type : BuiltInSwordTypes.VALUES) {
//            var typeName = type.name().toLowerCase();
//            RegistrySupplier<Item> sword = ModItems.ITEMS.register("vitalium_" + typeName, () -> new BaseSword(VitaliumMaterial.INSTANCE, type, 3, -2.4f, new Item.Properties()));
//            ModItems.SWORDS.add(sword);
//        }
//    }
//}
