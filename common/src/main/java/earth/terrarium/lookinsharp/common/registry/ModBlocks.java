package earth.terrarium.lookinsharp.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.block.ForgingStationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(LookinSharp.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<ForgingStationBlock> FORGING_ANVIL = BLOCKS.register("forging_station", ForgingStationBlock::new);
}
