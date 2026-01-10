package earth.terrarium.lookinsharp;

import cc.sighs.oelib.data.DataRegistry;
import com.mojang.logging.LogUtils;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.common.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class LookinSharp {
    public static final String MOD_ID = "lookinsharp";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation BASE_RANGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_range");
    public static final ResourceLocation BASE_REACH = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_reach");
    public static final ResourceLocation BASE_KNOCKBACK = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_knockback");
    public static final ResourceLocation BASE_MOVEMENT = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_movement");
    public static final ResourceLocation BASE_TOUGHNESS = ResourceLocation.fromNamespaceAndPath(MOD_ID, "base_toughness");


    public static void init() {
        DataRegistry.register(ToolRarityData.class, ToolRarityData.CODEC);
        DataRegistry.register(ToolTraitData.class, ToolTraitData.CODEC);
        ModDataComponents.DATA_COMPONENTS.register();
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModCreativeTabs.CREATIVE_TABS.register();
        ModRecipes.RECIPE_SERIALIZERS.register();
        ModRecipes.RECIPE_TYPES.register();
        ModMenus.MENU_TYPES.register();
        ModAbilities.init();

//        BotariumCompat.init();
    }
}