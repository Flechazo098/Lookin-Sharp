package earth.terrarium.lookinsharp.fabric;

import com.mafuyu404.oelib.fabric.data.DataRegistry;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class LookinSharpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DataRegistry.register(ToolRarityData.class);
        DataRegistry.register(ToolTraitData.class);

        LookinSharp.init();
        ModEventHandler.register();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(Utils::onEntityHit);
    }
}