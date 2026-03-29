package earth.terrarium.lookinsharp.api.rarities;

import cc.sighs.oelib.data.DataManager;
import earth.terrarium.lookinsharp.common.registry.ModDataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ToolRarityApi {
    private static WeightedList<Map.Entry<Identifier, ToolRarityData>> rarityPool = WeightedList.of();
    private static boolean poolBuilt = false;

    public static void buildRarityPool() {
        Map<Identifier, ToolRarityData> rarities = DataManager.getAllData(ToolRarityData.class);
        WeightedList.Builder<Map.Entry<Identifier, ToolRarityData>> builder = WeightedList.builder();

        for (Map.Entry<Identifier, ToolRarityData> entry : rarities.entrySet()) {
            if (entry.getValue().getWeight() > 0) {
                builder.add(entry, entry.getValue().getWeight());
            }
        }

        rarityPool = builder.build();
        poolBuilt = true;
    }

    public static ToolRarity getRarity(Identifier id) {
        return DataManager.getData(ToolRarityData.class, id);
    }

    public static List<? extends ToolRarity> getRarities() {
        return DataManager.getDataList(ToolRarityData.class);
    }

    public static ToolRarity rollRarity() {
        if (!poolBuilt) {
            buildRarityPool();
        }
        return rarityPool.getRandom(RandomSource.create())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    public static Identifier getRarityId(ToolRarity rarity) {
        Map<Identifier, ToolRarityData> rarities = DataManager.getAllData(ToolRarityData.class);
        for (Map.Entry<Identifier, ToolRarityData> entry : rarities.entrySet()) {
            if (entry.getValue().equals(rarity)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Rarity not found: " + rarity);
    }

    @Nullable
    public static ToolRarity fromItem(ItemStack stack) {
        Identifier id = stack.get(ModDataComponents.TOOL_RARITY_ID.get());
        if (id == null) return null;
        return getRarity(id);
    }

    public static void setRarity(ItemStack stack, ToolRarity rarity) {
        Identifier id = getRarityId(rarity);
        stack.set(ModDataComponents.TOOL_RARITY_ID.get(), id);
    }

    public static void onDataReload() {
        poolBuilt = false;
    }
}
