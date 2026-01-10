package earth.terrarium.lookinsharp.neoforge;

import cc.sighs.oelib.neoforge.event.DataReloadEvent;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityApi;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitApi;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = LookinSharp.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onDataReload(DataReloadEvent event) {
        if (event.isDataType(ToolRarityData.class)) {
            ToolRarityApi.onDataReload();
        }
        if (event.isDataType(ToolTraitData.class)) {
            ToolTraitApi.onDataReload();
            LookinSharp.LOGGER.info("Reloaded {} ToolTrait entries", event.getLoadedCount());
        }
    }
}
