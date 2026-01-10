package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LookinSharp.MOD_ID);

    public static final RegisterSupplier<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register("main",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.lookinsharp.main"))
                    .icon(() -> BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(LookinSharp.MOD_ID, "diamond_sword")).getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.FORGING_ANVIL.get());
                        ModItems.CRAFTING_COMPONENTS.forEach(component -> output.accept(component.get()));
                        ModItems.SWORDS.forEach(sword -> output.accept(sword.get()));
                    })
                    .build());

}