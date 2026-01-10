package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.api.abilities.ToolAbilityManager;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, LookinSharp.MOD_ID);

    public static final RegisterSupplier<DataComponentType<ToolAbility>> TOOL_ABILITY = DATA_COMPONENTS.register("tool_ability",
            () -> DataComponentType.<ToolAbility>builder()
                    .persistent(ToolAbilityManager.CODEC)
                    .networkSynchronized(ToolAbilityManager.STREAM_CODEC)
                    .build());

    public static final RegisterSupplier<DataComponentType<ResourceLocation>> TOOL_TRAIT_ID = DATA_COMPONENTS.register("tool_trait",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build());

    public static final RegisterSupplier<DataComponentType<ResourceLocation>> TOOL_RARITY_ID = DATA_COMPONENTS.register("tool_rarity",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .build());
}