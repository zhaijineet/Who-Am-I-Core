package net.zhaiji.who_am_i_core.client.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.config.WhoAmIClientConfig;

public class HumoursOverlay {
    public static final ResourceLocation HUMOURS_HUD = WhoAmICore.of("humours_hud");
    public static final ResourceLocation TEXTURE = WhoAmICore.of("textures/gui/humours_gui.png");
    // 底层背景菱形
    private static final int BG_U = 0;
    private static final int BG_V = 0;
    private static final int BG_SIZE = 66;
    // 四个进度条菱形（纹理中的 UV 偏移，从上到下排列）
    private static final int BAR_U = 66;
    private static final int BAR_V_BLOOD = 0;
    private static final int BAR_V_PHLEGM = 28;
    private static final int BAR_V_YELLOW_BILE = 56;
    private static final int BAR_V_BLACK_BILE = 84;
    private static final int BAR_SIZE = 28;

    /**
     * 渲染四体液 HUD
     * 位置由 HudAnchor 配置决定，显示条件由 HudVisibility 配置决定
     */
    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui) return;

        HumoursData data = HumoursData.get(minecraft.player);

        // 如果设置为"永不显示"，直接跳过渲染
        if (WhoAmIClientConfig.hudVisibility == HudVisibility.NEVER) return;

        // 如果设置为"有体液时显示"，检查玩家当前是否持有任何体液
        if (WhoAmIClientConfig.hudVisibility == HudVisibility.HAS_HUMOURS) {
            if (data.getBlood() <= 0 && data.getYellowBile() <= 0
                && data.getBlackBile() <= 0 && data.getPhlegm() <= 0) {
                return;
            }
        }

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = WhoAmIClientConfig.hudX;
        int y = WhoAmIClientConfig.hudY;
        int baseX = x;
        int baseY = screenHeight - BG_SIZE + y;

        switch (WhoAmIClientConfig.hudAnchor) {
            case TOP_LEFT -> {
                baseY = y;
            }
            case TOP_RIGHT -> {
                baseX = screenWidth - BG_SIZE + x;
                baseY = y;
            }
            case BOTTOM_LEFT -> {
                baseY = screenHeight - BG_SIZE + y;
            }
            case BOTTOM_RIGHT -> {
                baseX = screenWidth - BG_SIZE + x;
                baseY = screenHeight - BG_SIZE + y;
            }
        }

        // 绘制底层背景菱形
        guiGraphics.blit(TEXTURE, baseX, baseY, BG_U, BG_V, BG_SIZE, BG_SIZE);

        // 绘制四个进度条（按比例裁剪，位置：血液左/黄胆汁上/黑胆汁右/粘液下）
        renderBar(guiGraphics, baseX + 3, baseY + 19, data.getBloodRatio(), BAR_U, BAR_V_BLOOD);
        renderBar(guiGraphics, baseX + 19, baseY + 3, data.getYellowBileRatio(), BAR_U, BAR_V_YELLOW_BILE);
        renderBar(guiGraphics, baseX + 35, baseY + 19, data.getBlackBileRatio(), BAR_U, BAR_V_BLACK_BILE);
        renderBar(guiGraphics, baseX + 19, baseY + 35, data.getPhlegmRatio(), BAR_U, BAR_V_PHLEGM);
    }

    /**
     * 将面积比例转换为菱形的可见高度比例
     * 菱形底部是尖的，线性裁剪在低值时几乎不可见，需要用面积反算高度
     */
    private static float areaRatioToHeightRatio(float ratio) {
        if (ratio <= 0) return 0;
        if (ratio >= 1) return 1;
        if (ratio <= 0.5F) {
            // h² = ratio * S²/2  →  h/S = sqrt(ratio / 2)
            return (float) Math.sqrt(ratio / 2);
        } else {
            // S²/2 - (S-h)² = ratio * S²/2  →  (S-h)/S = sqrt((1-ratio) / 2)
            return 1 - (float) Math.sqrt((1 - ratio) / 2);
        }
    }

    /**
     * 渲染单个进度条菱形，根据面积比例从底部向上裁剪显示
     */
    private static void renderBar(GuiGraphics guiGraphics, int baseX, int baseY, float ratio, int u, int v) {
        if (ratio <= 0) return;
        float heightRatio = areaRatioToHeightRatio(ratio);
        int visibleHeight = (int) Math.ceil(heightRatio * BAR_SIZE);

        int clipTop = baseY + (BAR_SIZE - visibleHeight);
        int clipBottom = baseY + BAR_SIZE;

        guiGraphics.enableScissor(baseX, clipTop, baseX + BAR_SIZE, clipBottom);
        guiGraphics.blit(TEXTURE, baseX, baseY, u, v, BAR_SIZE, BAR_SIZE);
        guiGraphics.disableScissor();
    }

    public enum HudAnchor {
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        TOP_RIGHT
    }

    public enum HudVisibility {
        ALWAYS,
        HAS_HUMOURS,
        NEVER
    }
}
