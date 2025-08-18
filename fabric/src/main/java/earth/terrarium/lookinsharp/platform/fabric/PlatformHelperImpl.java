package earth.terrarium.lookinsharp.platform.fabric;

import com.mafuyu404.oelib.fabric.data.DataManager;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class PlatformHelperImpl {

    public static <T> Map<ResourceLocation, T> getAllData(Class<T> dataClass) {
        DataManager<T> manager = DataManager.get(dataClass);
        return manager != null ? manager.getAllData() : Map.of();
    }

    public static <T> T getData(Class<T> dataClass, ResourceLocation location) {
        DataManager<T> manager = DataManager.get(dataClass);
        return manager != null ? manager.getData(location) : null;
    }

    public static <T> List<T> getDataList(Class<T> dataClass) {
        DataManager<T> manager = DataManager.get(dataClass);
        return manager != null ? manager.getDataList() : List.of();
    }
}