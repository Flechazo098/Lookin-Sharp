package earth.terrarium.lookinsharp.client;

import cc.sighs.oelib.registry.extra.MenuRegister;
import earth.terrarium.lookinsharp.client.screen.ForgingAnvilScreen;
import earth.terrarium.lookinsharp.common.registry.ModMenus;

public class LookinSharpClient {
    public static void init() {
        MenuRegister.registerScreenFactory(ModMenus.FORGING_ANVIL, ForgingAnvilScreen::new);
    }
}
