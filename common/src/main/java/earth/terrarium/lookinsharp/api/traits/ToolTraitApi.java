package earth.terrarium.lookinsharp.api.traits;

import cc.sighs.oelib.data.DataManager;
import earth.terrarium.lookinsharp.common.registry.ModDataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ToolTraitApi {
    private static WeightedList<Map.Entry<Identifier, ToolTraitData>> traitPool = WeightedList.of();
    private static boolean poolBuilt = false;

    public static void buildTraitPool() {
        Map<Identifier, ToolTraitData> traits = DataManager.getAllData(ToolTraitData.class);
        WeightedList.Builder<Map.Entry<Identifier, ToolTraitData>> builder = WeightedList.builder();

        for (Map.Entry<Identifier, ToolTraitData> entry : traits.entrySet()) {
            if (entry.getValue().getWeight() > 0) {
                builder.add(entry, entry.getValue().getWeight());
            }
        }

        traitPool = builder.build();
        poolBuilt = true;
    }

    public static ToolTrait getTrait(Identifier id) {
        return DataManager.getData(ToolTraitData.class, id);
    }

    public static List<? extends ToolTrait> getTraits() {
        return DataManager.getDataList(ToolTraitData.class);
    }

    public static ToolTrait rollTrait() {
        if (!poolBuilt) {
            buildTraitPool();
        }
        return traitPool.getRandom(RandomSource.create())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    public static Identifier getTraitId(ToolTrait trait) {
        Map<Identifier, ToolTraitData> traits = DataManager.getAllData(ToolTraitData.class);
        for (Map.Entry<Identifier, ToolTraitData> entry : traits.entrySet()) {
            if (entry.getValue().equals(trait)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Trait not found: " + trait);
    }

    @Nullable
    public static ToolTrait fromItem(ItemStack stack) {
        Identifier id = stack.get(ModDataComponents.TOOL_TRAIT_ID.get());
        if (id == null) return null;
        return getTrait(id);
    }

    public static void setTrait(ItemStack stack, ToolTrait trait) {
        Identifier id = getTraitId(trait);
        stack.set(ModDataComponents.TOOL_TRAIT_ID.get(), id);
    }

    public static void onDataReload() {
        poolBuilt = false;
    }
}
