package earth.terrarium.lookinsharp.api.rarities;

import earth.terrarium.lookinsharp.common.registry.ModDataComponents;
import earth.terrarium.lookinsharp.platform.PlatformHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ToolRarityApi {
    private static SimpleWeightedRandomList<Map.Entry<ResourceLocation, ToolRarityData>> rarityPool = SimpleWeightedRandomList.empty();
    private static boolean poolBuilt = false;

    public static void buildRarityPool() {
        Map<ResourceLocation, ToolRarityData> rarities = PlatformHelper.getAllData(ToolRarityData.class);
        SimpleWeightedRandomList.Builder<Map.Entry<ResourceLocation, ToolRarityData>> builder = SimpleWeightedRandomList.builder();

        for (Map.Entry<ResourceLocation, ToolRarityData> entry : rarities.entrySet()) {
            if (entry.getValue().getWeight() > 0) {
                builder.add(entry, entry.getValue().getWeight());
            }
        }

        rarityPool = builder.build();
        poolBuilt = true;
    }

    public static ToolRarity getRarity(ResourceLocation id) {
        return PlatformHelper.getData(ToolRarityData.class, id);
    }

    public static ToolRarity rollRarity() {
        if (!poolBuilt) {
            buildRarityPool();
        }
        return rarityPool.getRandomValue(RandomSource.create())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    public static ResourceLocation getRarityId(ToolRarity rarity) {
        Map<ResourceLocation, ToolRarityData> rarities = PlatformHelper.getAllData(ToolRarityData.class);
        for (Map.Entry<ResourceLocation, ToolRarityData> entry : rarities.entrySet()) {
            if (entry.getValue().equals(rarity)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Rarity not found: " + rarity);
    }

    @Nullable
    public static ToolRarity fromItem(ItemStack stack) {
        ResourceLocation id = stack.get(ModDataComponents.TOOL_RARITY_ID.get());
        if (id == null) return null;
        return getRarity(id);
    }

    public static void setRarity(ItemStack stack, ToolRarity rarity) {
        ResourceLocation id = getRarityId(rarity);
        stack.set(ModDataComponents.TOOL_RARITY_ID.get(), id);
        if (rarity.getVanillaRarity() != null) {
            stack.set(DataComponents.RARITY, rarity.getVanillaRarity());
        }
    }

    public static void onDataReload() {
        poolBuilt = false;
    }
}