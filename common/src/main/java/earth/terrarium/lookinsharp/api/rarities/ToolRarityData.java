package earth.terrarium.lookinsharp.api.rarities;

import com.mafuyu404.oelib.api.data.DataDriven;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@DataDriven(
        modid = "lookinsharp",
        folder = "tool_rarities",
        syncToClient = true
)
public record ToolRarityData(
        int weight,
        double multiplier,
        int color,
        @Nullable String vanillaRarity
) implements ToolRarity {

    public static final Codec<ToolRarityData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("weight").forGetter(ToolRarityData::weight),
                    Codec.DOUBLE.fieldOf("multiplier").forGetter(ToolRarityData::multiplier),
                    Codec.INT.fieldOf("color").forGetter(ToolRarityData::color),
                    Codec.STRING.optionalFieldOf("vanillaRarity").forGetter(data ->
                            data.vanillaRarity != null ? Optional.of(data.vanillaRarity) : Optional.empty())
            ).apply(instance, (weight, multiplier, color, vanillaRarity) ->
                    new ToolRarityData(weight, multiplier, color, vanillaRarity.orElse(null)))
    );

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    @Nullable
    public Rarity getVanillaRarity() {
        if (vanillaRarity == null) return null;
        return switch (vanillaRarity.toLowerCase()) {
            case "common" -> Rarity.COMMON;
            case "uncommon" -> Rarity.UNCOMMON;
            case "rare" -> Rarity.RARE;
            case "epic" -> Rarity.EPIC;
            default -> null;
        };
    }
}