package earth.terrarium.lookinsharp.client;

import dev.architectury.registry.menu.MenuRegistry;
import earth.terrarium.lookinsharp.client.screen.ForgingAnvilScreen;
import earth.terrarium.lookinsharp.common.registry.ModMenus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class LookinSharpClient {
    public static void init() {
        MenuRegistry.registerScreenFactory(ModMenus.FORGING_ANVIL.get(), ForgingAnvilScreen::new);
    }
}
