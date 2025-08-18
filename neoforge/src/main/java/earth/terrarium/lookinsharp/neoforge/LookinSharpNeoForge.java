package earth.terrarium.lookinsharp.neoforge;

import com.mafuyu404.oelib.neoforge.data.DataRegistry;
import com.mojang.serialization.MapCodec;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.client.screen.ForgingAnvilScreen;
import earth.terrarium.lookinsharp.common.registry.ModMenus;
import earth.terrarium.lookinsharp.util.Utils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(LookinSharp.MOD_ID)
public class LookinSharpNeoForge {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LookinSharp.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ArtifactLootModifier>> ARTIFACT_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("artifact_drop", ArtifactLootModifier.CODEC);

    public LookinSharpNeoForge(IEventBus modBus) {
        DataRegistry.register(ToolRarityData.class);
        DataRegistry.register(ToolTraitData.class);

        LookinSharp.init();

        LOOT_MODIFIERS.register(modBus);

        modBus.addListener(this::commonSetup);

        if (FMLEnvironment.dist.isClient()) {
            modBus.addListener(this::clientSetup);
        }

        NeoForge.EVENT_BUS.addListener((LivingDamageEvent.Pre event) -> Utils.onEntityHit(
                event.getEntity(),
                event.getSource(),
                event.getOriginalDamage()
        ));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LookinSharp.postInit();
    }

    private void clientSetup(RegisterMenuScreensEvent event) {
        event.register(ModMenus.FORGING_ANVIL.get(), ForgingAnvilScreen::new);
    }
}