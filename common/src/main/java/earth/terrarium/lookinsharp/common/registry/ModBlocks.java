package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.block.ForgingStationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, LookinSharp.MOD_ID);

    public static final RegisterSupplier<ForgingStationBlock> FORGING_ANVIL = BLOCKS.register("forging_station", ForgingStationBlock::new);
}
