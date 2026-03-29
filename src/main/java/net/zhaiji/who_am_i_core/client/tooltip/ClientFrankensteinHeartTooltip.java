package net.zhaiji.who_am_i_core.client.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * 弗兰肯斯坦心脏的自定义 Tooltip 渲染器。
 * <p>
 * 固定 2 个槽位的横向布局，不显示被叉掉的空槽位。
 * </p>
 */
@OnlyIn(Dist.CLIENT)
public class ClientFrankensteinHeartTooltip implements ClientTooltipComponent {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/background");
    private static final ResourceLocation SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/slot");
    private static final int GRID_SIZE_X = 2;
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 20;
    private final BundleContents contents;

    public ClientFrankensteinHeartTooltip(BundleContents contents) {
        this.contents = contents;
    }

    @Override
    public int getHeight() {
        return SLOT_SIZE_Y + 2 + 4;
    }

    @Override
    public int getWidth(Font font) {
        return GRID_SIZE_X * SLOT_SIZE_X + 2;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        guiGraphics.blitSprite(BACKGROUND_SPRITE, x, y, GRID_SIZE_X * SLOT_SIZE_X + 2, SLOT_SIZE_Y + 2);

        for (int i = 0; i < GRID_SIZE_X; i++) {
            int slotX = x + i * SLOT_SIZE_X + 1;
            int slotY = y + 1;

            if (i < contents.size()) {
                ItemStack itemStack = contents.getItemUnsafe(i);
                guiGraphics.blitSprite(SLOT_SPRITE, slotX, slotY, SLOT_SIZE_X, SLOT_SIZE_Y);
                guiGraphics.renderItem(itemStack, slotX + 1, slotY + 1, i);
                guiGraphics.renderItemDecorations(font, itemStack, slotX + 1, slotY + 1);
                if (i == 0) {
                    AbstractContainerScreen.renderSlotHighlight(guiGraphics, slotX + 1, slotY + 1, 0);
                }
            } else {
                guiGraphics.blitSprite(SLOT_SPRITE, slotX, slotY, SLOT_SIZE_X, SLOT_SIZE_Y);
            }
        }
    }
}
