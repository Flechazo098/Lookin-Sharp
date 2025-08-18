package earth.terrarium.lookinsharp.common.menu;

import earth.terrarium.lookinsharp.common.registry.ModBlocks;
import earth.terrarium.lookinsharp.common.registry.ModMenus;
import earth.terrarium.lookinsharp.common.registry.ModRecipes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ForgingStationContainer extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int RESULT_SLOT = 1;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int USE_ROW_SLOT_START = 29;
    private static final int USE_ROW_SLOT_END = 38;

    private final ContainerLevelAccess access;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<ItemStack> outputs = new ArrayList<>();
    private ItemStack input = ItemStack.EMPTY;
    long lastSoundTime;

    final Slot inputSlot;
    final Slot resultSlot;

    Runnable slotUpdateListener = () -> {
    };

    public final Container container = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            super.setChanged();
            ForgingStationContainer.this.slotsChanged(this);
            ForgingStationContainer.this.slotUpdateListener.run();
        }
    };

    final ResultContainer resultContainer = new ResultContainer();

    public ForgingStationContainer(int i, Inventory arg) {
        this(i, arg, ContainerLevelAccess.NULL);
    }

    public ForgingStationContainer(
            int i,
            Inventory arg,
            final ContainerLevelAccess arg2
    ) {
        super(ModMenus.FORGING_ANVIL.get(), i);
        this.access = arg2;
        this.level = arg.player.level();

        this.inputSlot = this.addSlot(new Slot(this.container, INPUT_SLOT, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, RESULT_SLOT, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack arg) {
                return false;
            }

            @Override
            public void onTake(Player arg3, ItemStack arg22) {
                applyOldComponents(arg22);
                arg22.onCraftedBy(arg3.level(), arg3, arg22.getCount());

                ForgingStationContainer.this.resultContainer.awardUsedRecipes(
                        arg3, this.getRelevantItems()
                );

                ItemStack itemStack = ForgingStationContainer.this.inputSlot.remove(RESULT_SLOT);
                if (!itemStack.isEmpty()) {
                    ForgingStationContainer.this.setupResultSlot();
                }

                arg2.execute((arg, arg2) -> {
                    long l = arg.getGameTime();
                    if (ForgingStationContainer.this.lastSoundTime != l) {
                        arg.playSound(
                                null,
                                arg2,
                                SoundEvents.UI_STONECUTTER_TAKE_RESULT,
                                SoundSource.BLOCKS,
                                1.0f,
                                1.0f
                        );
                        ForgingStationContainer.this.lastSoundTime = l;
                    }
                });

                super.onTake(arg3, arg22);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(ForgingStationContainer.this.inputSlot.getItem());
            }
        });

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(arg, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(arg, j, 8 + j * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<ItemStack> getOutputs() {
        return this.outputs;
    }

    public int getNumOutputs() {
        return this.outputs.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.outputs.isEmpty();
    }

    @Override
    public boolean stillValid(Player arg) {
        return stillValid(this.access, arg, ModBlocks.FORGING_ANVIL.get());
    }

    @Override
    public boolean clickMenuButton(Player arg, int i) {
        if (this.isValidRecipeIndex(i)) {
            this.selectedRecipeIndex.set(i);
            this.setupResultSlot();
        }
        return true;
    }

    private boolean isValidRecipeIndex(int i) {
        return i >= 0 && i < this.outputs.size();
    }

    @Override
    public void slotsChanged(Container arg) {
        ItemStack itemStack = this.inputSlot.getItem();
        if (!itemStack.is(this.input.getItem())) {
            this.input = itemStack.copy();
            this.setupRecipeList(arg, itemStack);
        }
    }

    private void setupRecipeList(Container arg, ItemStack arg2) {
        this.outputs.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);

        if (!arg2.isEmpty()) {
            var input = new SingleRecipeInput(arg2);
            this.outputs = this.level.getRecipeManager()
                    .getRecipesFor(ModRecipes.FORGING.get(), input, this.level)
                    .stream()
                    .map(RecipeHolder::value)
                    .flatMap(recipe -> recipe.getResults().stream())
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    void setupResultSlot() {
        if (!this.outputs.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            ItemStack itemStack = this.outputs.get(this.selectedRecipeIndex.get()).copy();

            if (itemStack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultSlot.set(itemStack);
            } else {
                this.resultSlot.set(ItemStack.EMPTY);
            }
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }
        this.broadcastChanges();
    }

    @Override
    public @NotNull MenuType<?> getType() {
        return ModMenus.FORGING_ANVIL.get();
    }

    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack arg, Slot arg2) {
        return arg2.container != this.resultContainer && super.canTakeItemForPickAll(arg, arg2);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player arg, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();

            if (i == 1) {
                applyOldComponents(itemStack2);
                item.onCraftedBy(itemStack2, arg.level(), arg);
                if (!this.moveItemStackTo(itemStack2, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (i == 0
                    ? !this.moveItemStackTo(itemStack2, INV_SLOT_START, USE_ROW_SLOT_END, false)
                    : (this.level.getRecipeManager()
                    .getRecipeFor(ModRecipes.FORGING.get(),
                            new SingleRecipeInput(itemStack2),
                            this.level)
                    .isPresent()
                    ? !this.moveItemStackTo(itemStack2, 0, 1, false)
                    : (i >= INV_SLOT_START && i < INV_SLOT_END
                    ? !this.moveItemStackTo(itemStack2, 29, 38, false)
                    : i >= USE_ROW_SLOT_START && i < USE_ROW_SLOT_END
                    && !this.moveItemStackTo(itemStack2, 2, 29, false)))) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(arg, itemStack2);
            this.broadcastChanges();
        }

        return itemStack;
    }

    @Override
    public void removed(Player arg) {
        super.removed(arg);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((arg2, arg3) -> this.clearContainer(arg, this.container));
    }

    public void applyOldComponents(ItemStack result) {
        ItemStack sourceItem = this.slots.getFirst().getItem();
        if (!sourceItem.isEmpty()) {
            result.applyComponents(sourceItem.getComponents());
        }
    }
}
