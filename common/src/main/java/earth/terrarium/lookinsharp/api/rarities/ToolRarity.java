package earth.terrarium.lookinsharp.api.rarities;

import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

public interface ToolRarity {
    int getWeight();

    double getMultiplier();

    int getColor();

    @Nullable
    Rarity getVanillaRarity();
}