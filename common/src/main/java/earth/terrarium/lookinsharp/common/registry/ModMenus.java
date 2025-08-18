package earth.terrarium.lookinsharp.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.menu.ForgingStationContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(LookinSharp.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<ForgingStationContainer>> FORGING_ANVIL = MENU_TYPES.register("forging_anvil", () -> new MenuType<>(ForgingStationContainer::new, FeatureFlags.VANILLA_SET));
}
