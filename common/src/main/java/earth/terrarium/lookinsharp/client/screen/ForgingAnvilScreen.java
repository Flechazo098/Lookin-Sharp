package earth.terrarium.lookinsharp.client.screen;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import earth.terrarium.lookinsharp.common.menu.ForgingStationContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ForgingAnvilScreen extends AbstractContainerScreen<ForgingStationContainer> {
    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/scroller_disabled");
    private static final Identifier RECIPE_SELECTED_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/recipe_selected");
    private static final Identifier RECIPE_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/recipe_highlighted");
    private static final Identifier RECIPE_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/recipe");
    private static final Identifier BG_LOCATION = Identifier.withDefaultNamespace("textures/gui/container/stonecutter.png");
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public ForgingAnvilScreen(ForgingStationContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY--;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int left = this.leftPos;
        int top = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, left, top, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        int scrollerOffset = (int) (41.0F * this.scrollOffs);
        Identifier scroller = this.isScrollBarActive() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        int scrollerX = left + 119;
        int scrollerY = top + 15;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, scroller, scrollerX, scrollerY + scrollerOffset, 12, 15);
        if (mouseX >= scrollerX && mouseY >= scrollerY && mouseX < scrollerX + 12 && mouseY < scrollerY + 54) {
            if (this.isScrollBarActive()) {
                graphics.requestCursor(this.scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
            } else {
                graphics.requestCursor(CursorTypes.NOT_ALLOWED);
            }
        }

        int recipeLeft = this.leftPos + 52;
        int recipeTop = this.topPos + 14;
        int endIndex = this.startIndex + 12;
        this.extractButtons(graphics, mouseX, mouseY, recipeLeft, recipeTop, endIndex);
        this.extractRecipes(graphics, recipeLeft, recipeTop, endIndex);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        if (!this.displayRecipes) {
            return;
        }

        int left = this.leftPos + 52;
        int top = this.topPos + 14;
        int end = this.startIndex + 12;
        List<ItemStack> outputs = this.menu.getOutputs();
        for (int index = this.startIndex; index < end && index < this.menu.getNumOutputs(); index++) {
            int posIndex = index - this.startIndex;
            int x = left + posIndex % 4 * 16;
            int y = top + posIndex / 4 * 18 + 2;
            if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 18) {
                graphics.setTooltipForNextFrame(this.font, outputs.get(index), mouseX, mouseY);
            }
        }
    }

    private void extractButtons(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int x, int y, int endIndex) {
        for (int index = this.startIndex; index < endIndex && index < this.menu.getNumOutputs(); index++) {
            int posIndex = index - this.startIndex;
            int posX = x + posIndex % 4 * 16;
            int posY = y + posIndex / 4 * 18 + 2;
            Identifier sprite;
            if (index == this.menu.getSelectedRecipeIndex()) {
                sprite = RECIPE_SELECTED_SPRITE;
            } else if (mouseX >= posX && mouseY >= posY && mouseX < posX + 16 && mouseY < posY + 18) {
                sprite = RECIPE_HIGHLIGHTED_SPRITE;
            } else {
                sprite = RECIPE_SPRITE;
            }

            int textureY = posY - 1;
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, posX, textureY, 16, 18);
            if (mouseX >= posX && mouseY >= textureY && mouseX < posX + 16 && mouseY < textureY + 18) {
                graphics.requestCursor(CursorTypes.POINTING_HAND);
            }
        }
    }

    private void extractRecipes(GuiGraphicsExtractor graphics, int x, int y, int endIndex) {
        List<ItemStack> outputs = this.menu.getOutputs();
        for (int index = this.startIndex; index < endIndex && index < this.menu.getNumOutputs(); index++) {
            int posIndex = index - this.startIndex;
            int posX = x + posIndex % 4 * 16;
            int posY = y + posIndex / 4 * 18 + 2;
            graphics.item(outputs.get(index), posX, posY);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int left = this.leftPos + 52;
            int top = this.topPos + 14;
            int end = this.startIndex + 12;
            for (int index = this.startIndex; index < end; index++) {
                int posIndex = index - this.startIndex;
                double relX = event.x() - (left + posIndex % 4 * 16);
                double relY = event.y() - (top + posIndex / 4 * 18);
                if (relX >= 0.0 && relY >= 0.0 && relX < 16.0 && relY < 18.0 && this.menu.clickMenuButton(this.minecraft.player, index)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, index);
                    return true;
                }
            }

            left = this.leftPos + 119;
            top = this.topPos + 9;
            if (event.x() >= left && event.x() < left + 12 && event.y() >= top && event.y() < top + 54) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (this.scrolling && this.isScrollBarActive()) {
            int top = this.topPos + 14;
            int bottom = top + 54;
            this.scrollOffs = ((float) event.y() - top - 7.5F) / (bottom - top - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int) (this.scrollOffs * this.getOffscreenRows() + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        this.scrolling = false;
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
        if (super.mouseScrolled(x, y, scrollX, scrollY)) {
            return true;
        }

        if (this.isScrollBarActive()) {
            int rows = this.getOffscreenRows();
            float delta = (float) scrollY / rows;
            this.scrollOffs = Mth.clamp(this.scrollOffs - delta, 0.0F, 1.0F);
            this.startIndex = (int) (this.scrollOffs * rows + 0.5) * 4;
        }

        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumOutputs() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumOutputs() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        this.scrollOffs = 0.0F;
        this.startIndex = 0;
    }
}
