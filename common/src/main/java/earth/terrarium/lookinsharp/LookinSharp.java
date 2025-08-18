package earth.terrarium.lookinsharp;

import com.mojang.logging.LogUtils;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityApi;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitApi;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.common.registry.*;
import earth.terrarium.lookinsharp.compat.botarium.BotariumCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LookinSharp {
    public static final String MOD_ID = "lookinsharp";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation BASE_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_range");
    public static final ResourceLocation BASE_REACH = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_reach");
    public static final ResourceLocation BASE_KNOCKBACK = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_knockback");
    public static final ResourceLocation BASE_MOVEMENT = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_movement");
    public static final ResourceLocation BASE_TOUGHNESS = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_toughness");

    public static final Map<Supplier<Item>, DropChance> DROPS = new HashMap<>();

    public static void init() {

        ModDataComponents.DATA_COMPONENTS.register();
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModCreativeTabs.CREATIVE_TABS.register();
        ModRecipes.RECIPE_SERIALIZERS.register();
        ModRecipes.RECIPE_TYPES.register();
        ModMenus.MENU_TYPES.register();
        ModAbilities.init();

        DROPS.put(ModItems.BATTERING_ARTIFACT, new DropChance(EntityType.IRON_GOLEM, 0.35F));
        DROPS.put(ModItems.DEADLY_ARTIFACT, new DropChance(EntityType.CAVE_SPIDER, 0.05F));
        DROPS.put(ModItems.GLACIAL_ARTIFACT, new DropChance(EntityType.SNOW_GOLEM, 0.35F));
        DROPS.put(ModItems.STICKY_ARTIFACT, new DropChance(EntityType.SLIME, 0.05F));
        DROPS.put(ModItems.TONIC_ARTIFACT, new DropChance(EntityType.WITCH, 0.25F));
        DROPS.put(ModItems.WEAKENING_ARTIFACT, new DropChance(EntityType.HUSK, 0.10F));
        DROPS.put(ModItems.WITHERING_ARTIFACT, new DropChance(EntityType.WITHER_SKELETON, 0.10F));
        DROPS.put(ModItems.VAMPIRIC_ARTIFACT, new DropChance(EntityType.GHAST, 0.25F));

        BotariumCompat.init();
    }

    public static void postInit() {
        // 数据重载后重建池
        ToolRarityApi.onDataReload();
        ToolTraitApi.onDataReload();
    }

    public record DropChance(EntityType<?> entityType, float chance) {
    }
}