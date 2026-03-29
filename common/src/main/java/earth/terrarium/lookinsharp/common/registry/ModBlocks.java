package earth.terrarium.lookinsharp.common.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.common.block.ForgingStationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, LookinSharp.MOD_ID);

    public static final RegisterSupplier<ForgingStationBlock> FORGING_ANVIL =
            BLOCKS.register("forging_station", () -> new ForgingStationBlock(blockProperties("forging_station")));

    private static BlockBehaviour.Properties blockProperties(String path) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL).setId(
                ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, path))
        );
    }
}
