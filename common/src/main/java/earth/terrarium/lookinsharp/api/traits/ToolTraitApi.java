package earth.terrarium.lookinsharp.api.traits;

import earth.terrarium.lookinsharp.common.registry.ModDataComponents;
import earth.terrarium.lookinsharp.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ToolTraitApi {
    private static SimpleWeightedRandomList<Map.Entry<ResourceLocation, ToolTraitData>> traitPool = SimpleWeightedRandomList.empty();
    private static boolean poolBuilt = false;

    public static void buildTraitPool() {
        Map<ResourceLocation, ToolTraitData> traits = PlatformHelper.getAllData(ToolTraitData.class);
        SimpleWeightedRandomList.Builder<Map.Entry<ResourceLocation, ToolTraitData>> builder = SimpleWeightedRandomList.builder();

        for (Map.Entry<ResourceLocation, ToolTraitData> entry : traits.entrySet()) {
            if (entry.getValue().getWeight() > 0) {
                builder.add(entry, entry.getValue().getWeight());
            }
        }

        traitPool = builder.build();
        poolBuilt = true;
    }

    public static ToolTrait getTrait(ResourceLocation id) {
        return PlatformHelper.getData(ToolTraitData.class, id);
    }

    public static List<? extends ToolTrait> getTraits() {
        return PlatformHelper.getDataList(ToolTraitData.class);
    }

    public static ToolTrait rollTrait() {
        if (!poolBuilt) {
            buildTraitPool();
        }
        return traitPool.getRandomValue(RandomSource.create())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    public static ResourceLocation getTraitId(ToolTrait trait) {
        Map<ResourceLocation, ToolTraitData> traits = PlatformHelper.getAllData(ToolTraitData.class);
        for (Map.Entry<ResourceLocation, ToolTraitData> entry : traits.entrySet()) {
            if (entry.getValue().equals(trait)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Trait not found: " + trait);
    }

    @Nullable
    public static ToolTrait fromItem(ItemStack stack) {
        ResourceLocation id = stack.get(ModDataComponents.TOOL_TRAIT_ID.get());
        if (id == null) return null;
        return getTrait(id);
    }

    public static void setTrait(ItemStack stack, ToolTrait trait) {
        ResourceLocation id = getTraitId(trait);
        stack.set(ModDataComponents.TOOL_TRAIT_ID.get(), id);
    }

    public static void onDataReload() {
        poolBuilt = false;
    }
}