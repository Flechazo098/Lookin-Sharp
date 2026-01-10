package earth.terrarium.lookinsharp.fabric;

import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class LookinSharpFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        LookinSharp.init();
        ModEventHandler.register();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(Utils::onEntityHit);
    }
}