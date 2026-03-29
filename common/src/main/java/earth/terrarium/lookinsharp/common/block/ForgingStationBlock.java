package earth.terrarium.lookinsharp.common.block;

import earth.terrarium.lookinsharp.common.menu.ForgingStationContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForgingStationBlock extends Block {
    public static VoxelShape box = Shapes.or(
            Block.box(0, 0, 0, 16, 4, 16),
            Block.box(3, 4, 3, 13, 6, 13),
            Block.box(5, 6, 5, 11, 9, 11),
            Block.box(4, 9, 3, 12, 16, 13),
            Block.box(0, 12, 5, 4, 16, 11),
            Block.box(12, 10, 4, 16, 16, 12)
    );

    public ForgingStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos,
                                               Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        player.openMenu(blockState.getMenuProvider(level, blockPos));
        player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
        return InteractionResult.CONSUME;
    }


    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((i, inv, player) -> new ForgingStationContainer(i, inv, ContainerLevelAccess.create(level, blockPos)), Component.translatable(this.getDescriptionId()));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return box;
    }
}
