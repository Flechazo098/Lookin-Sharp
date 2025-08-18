package earth.terrarium.lookinsharp.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Map;

public class PlatformHelper {

    @ExpectPlatform
    public static <T> Map<ResourceLocation, T> getAllData(Class<T> dataClass) {
        throw new UnsupportedOperationException("Must be implemented by platform");
    }


    @ExpectPlatform
    public static  <T> T getData(Class<T> dataClass, ResourceLocation location) {
        throw new UnsupportedOperationException("Must be implemented by platform");
    }

    @ExpectPlatform
    public static <T> List<T> getDataList(Class<T> dataClass) {
        throw new UnsupportedOperationException("Must be implemented by platform");
    }

}