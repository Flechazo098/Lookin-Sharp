//package earth.terrarium.lookinsharp.compat.botarium;
//
//import earth.terrarium.lookinsharp.compat.botarium.items.BotariumBaseSword;
//
//import earth.terrarium.lookinsharp.compat.botarium.registry.BotariumItems;
//
//public class BotariumCompat {
//    public static void init() {
//        BotariumItems.VITALIUM_INGOT.getId();
//    }
//
//    public static void extractEnergy(ItemStack stack, int i) {
//        if (stack.getItem() instanceof BotariumBaseSword baseSword) {
//            baseSword.getEnergyStorage(stack).internalExtract(i * 40L, false);
//        }
//    }
//
//    public static boolean canAttack(ItemStack stack) {
//        if (stack.getItem() instanceof BotariumBaseSword baseSword) {
//            return baseSword.getEnergyStorage(stack).getStoredEnergy() > 40;
//        }
//        return true;
//    }
//}
