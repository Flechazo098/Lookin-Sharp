package earth.terrarium.lookinsharp.fabric.client;

import com.mafuyu404.oelib.fabric.event.DataReloadEvent;
import com.mafuyu404.oelib.fabric.event.impl.Events;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityApi;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitApi;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.client.LookinSharpClient;
import net.fabricmc.api.ClientModInitializer;

public final class LookinSharpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LookinSharpClient.init();
        Events.on(DataReloadEvent.EVENT)
                .normal()
                .register(this::onDataReload);
    }


    public void onDataReload(Class<?> dataClass, int loadedCount, int invalidCount) {
        if (dataClass == ToolRarityData.class) {
            ToolRarityApi.onDataReload();
            LookinSharp.LOGGER.info("Reloaded {} ToolRarity entries", loadedCount);
        } else if (dataClass == ToolTraitData.class) {
            ToolTraitApi.onDataReload();
            LookinSharp.LOGGER.info("Reloaded {} ToolTrait entries", loadedCount);
        }
    }
}
