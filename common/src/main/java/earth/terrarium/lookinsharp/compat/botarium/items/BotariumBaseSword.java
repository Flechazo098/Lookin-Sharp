//package earth.terrarium.lookinsharp.compat.botarium.items;
//
//import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
//import earth.terrarium.botarium.common.energy.base.EnergyContainer;
//import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
//import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
//import earth.terrarium.botarium.common.energy.util.EnergyHooks;
//import earth.terrarium.lookinsharp.api.types.SwordType;
//import earth.terrarium.lookinsharp.common.items.BaseSword;
//import net.minecraft.ChatFormatting;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Tier;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class BotariumBaseSword extends BaseSword implements BotariumEnergyItem<WrappedItemEnergyContainer> {
//    public BotariumBaseSword(Tier tier, SwordType type, int i, float f, Properties properties) {
//        super(tier, type, i, f, properties.durability(0));
//    }
//
//    @Override
//    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
//        EnergyContainer energyStorage = this.getEnergyStorage(itemStack);
//        return energyStorage.getStoredEnergy() > 40 ? super.getDestroySpeed(itemStack, blockState) : 0;
//    }
//
//    @Override
//    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
//        EnergyContainer energyStorage = this.getEnergyStorage(itemStack);
//        list.add(Component.translatable("tooltip.lookinsharp.energy", energyStorage.getStoredEnergy(), energyStorage.getMaxCapacity()).withStyle(ChatFormatting.GRAY));
//        super.appendHoverText(itemStack, level, list, tooltipFlag);
//    }
//
//    @Override
//    public WrappedItemEnergyContainer getEnergyStorage(ItemStack holder) {
//        return new WrappedItemEnergyContainer(holder, new SimpleEnergyContainer(20000));
//    }
//
//    @Override
//    public boolean isBarVisible(ItemStack itemStack) {
//        return true;
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public int getBarWidth(ItemStack itemStack) {
//        return EnergyHooks.toDurabilityBar(this, itemStack);
//    }
//
//    @Override
//    public int getBarColor(ItemStack itemStack) {
//        return 0xFF80B6;
//    }
//}
