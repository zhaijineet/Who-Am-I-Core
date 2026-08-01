package net.zhaiji.who_am_i_core.client.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.TranslatableEnum;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.config.WhoAmIClientConfig;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

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
    // 各体液对应纹理主色
    private static final int COLOR_BLOOD = 0xC02734;
    private static final int COLOR_YELLOW_BILE = 0xFAD609;
    private static final int COLOR_BLACK_BILE = 0x39323D;
    private static final int COLOR_PHLEGM = 0x5399EA;

    /**
     * 渲染四体液 HUD
     * 位置由 HudAnchor 配置决定，显示条件由 HudVisibility 配置决定
     */
    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui) return;

        Player player = minecraft.player;
        HumoursData data = HumoursData.get(player);

        HudVisibility hudVisibility = WhoAmIClientConfig.HUD_VISIBILITY.get();
        if (hudVisibility == HudVisibility.NEVER) return;
        if (hudVisibility == HudVisibility.HAS_HUMOURS && data.isAllHumourEmpty()) return;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = WhoAmIClientConfig.HUD_X.get();
        int y = WhoAmIClientConfig.HUD_Y.get();
        int baseX = x;
        int baseY = screenHeight - BG_SIZE + y;

        switch (WhoAmIClientConfig.HUD_ANCHOR.get()) {
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

        float blood = data.getBlood();
        float yellowBile = data.getYellowBile();
        float blackBile = data.getBlackBile();
        float phlegm = data.getPhlegm();
        float maxBlood = (float) player.getAttributeValue(WAICAttribute.MAX_BLOOD);
        float maxYellowBile = (float) player.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE);
        float maxBlackBile = (float) player.getAttributeValue(WAICAttribute.MAX_BLACK_BILE);
        float maxPhlegm = (float) player.getAttributeValue(WAICAttribute.MAX_PHLEGM);

        // 绘制底层背景菱形
        guiGraphics.blit(TEXTURE, baseX, baseY, BG_U, BG_V, BG_SIZE, BG_SIZE);

        // 绘制四个进度条（位置：血液左/黄胆汁上/黑胆汁右/粘液下）
        renderBar(guiGraphics, baseX + 3, baseY + 19, safeRatio(blood, maxBlood), BAR_V_BLOOD);
        renderBar(guiGraphics, baseX + 19, baseY + 3, safeRatio(yellowBile, maxYellowBile), BAR_V_YELLOW_BILE);
        renderBar(guiGraphics, baseX + 35, baseY + 19, safeRatio(blackBile, maxBlackBile), BAR_V_BLACK_BILE);
        renderBar(guiGraphics, baseX + 19, baseY + 35, safeRatio(phlegm, maxPhlegm), BAR_V_PHLEGM);

        // 绘制数值
        renderValues(
            guiGraphics,
            minecraft.font,
            baseX,
            baseY,
            blood,
            maxBlood,
            yellowBile,
            maxYellowBile,
            blackBile,
            maxBlackBile,
            phlegm,
            maxPhlegm
        );
    }

    /**
     * 将面积比例转换为菱形的可见高度比例
     * 菱形底部是尖的，线性裁剪在低值时几乎不可见，需要用面积反算高度
     */
    private static float areaRatioToHeightRatio(float ratio) {
        if (ratio <= 0) return 0;
        if (ratio >= 1) return 1;
        if (ratio <= 0.5F) {
            return (float) Math.sqrt(ratio / 2);
        } else {
            return 1 - (float) Math.sqrt((1 - ratio) / 2);
        }
    }

    private static float safeRatio(float current, float maximum) {
        return maximum <= 0 ? 0 : current / maximum;
    }

    /**
     * 渲染单个进度条菱形，根据面积比例从底部向上裁剪显示
     */
    private static void renderBar(GuiGraphics guiGraphics, int barX, int barY, float ratio, int v) {
        if (ratio <= 0) return;
        float heightRatio = areaRatioToHeightRatio(ratio);
        int visibleHeight = (int) Math.ceil(heightRatio * BAR_SIZE);

        int clipTop = barY + (BAR_SIZE - visibleHeight);
        int clipBottom = barY + BAR_SIZE;

        guiGraphics.enableScissor(barX, clipTop, barX + BAR_SIZE, clipBottom);
        guiGraphics.blit(TEXTURE, barX, barY, BAR_U, v, BAR_SIZE, BAR_SIZE);
        guiGraphics.disableScissor();
    }

    private static void renderValues(
        GuiGraphics guiGraphics,
        Font font,
        int baseX,
        int baseY,
        float blood,
        float maxBlood,
        float yellowBile,
        float maxYellowBile,
        float blackBile,
        float maxBlackBile,
        float phlegm,
        float maxPhlegm
    ) {
        if (WhoAmIClientConfig.HUD_VALUE_VISIBILITY.get() == HudValueVisibility.NEVER) return;

        boolean showBlood = shouldShow(blood);
        boolean showYellowBile = shouldShow(yellowBile);
        boolean showBlackBile = shouldShow(blackBile);
        boolean showPhlegm = shouldShow(phlegm);

        String bloodText = formatValue(blood, maxBlood);
        String yellowBileText = formatValue(yellowBile, maxYellowBile);
        String blackBileText = formatValue(blackBile, maxBlackBile);
        String phlegmText = formatValue(phlegm, maxPhlegm);

        switch (WhoAmIClientConfig.HUD_VALUE_POSITION.get()) {
            case CENTER -> {
                if (WhoAmIClientConfig.HUD_VALUE_FORMAT.get() == HudValueFormat.CURRENT_MAX) {
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().scale(0.75F, 0.75F, 1F);
                    float inv = 1F / 0.75F;
                    drawCentered(guiGraphics, font, Math.round((baseX + 17) * inv), Math.round((baseY + 33) * inv), bloodText, showBlood);
                    drawCentered(
                        guiGraphics,
                        font,
                        Math.round((baseX + 33) * inv),
                        Math.round((baseY + 17) * inv),
                        yellowBileText,
                        showYellowBile
                    );
                    drawCentered(
                        guiGraphics,
                        font,
                        Math.round((baseX + 49) * inv),
                        Math.round((baseY + 33) * inv),
                        blackBileText,
                        showBlackBile
                    );
                    drawCentered(guiGraphics, font, Math.round((baseX + 33) * inv), Math.round((baseY + 49) * inv), phlegmText, showPhlegm);
                    guiGraphics.pose().popPose();
                } else {
                    drawCentered(guiGraphics, font, baseX + 17, baseY + 33, bloodText, showBlood);
                    drawCentered(guiGraphics, font, baseX + 33, baseY + 17, yellowBileText, showYellowBile);
                    drawCentered(guiGraphics, font, baseX + 49, baseY + 33, blackBileText, showBlackBile);
                    drawCentered(guiGraphics, font, baseX + 33, baseY + 49, phlegmText, showPhlegm);
                }
            }
            case OUTSIDE -> {
                drawRight(guiGraphics, font, baseX - 2, baseY + 33, bloodText, showBlood);
                drawCentered(guiGraphics, font, baseX + 33, baseY - 2 - font.lineHeight / 2, yellowBileText, showYellowBile);
                drawLeft(guiGraphics, font, baseX + BG_SIZE + 2, baseY + 33, blackBileText, showBlackBile);
                drawCentered(guiGraphics, font, baseX + 33, baseY + BG_SIZE + 2 + font.lineHeight / 2, phlegmText, showPhlegm);
            }
            case LIST_BELOW -> {
                int drawY = baseY + BG_SIZE + 2;
                int centerX = baseX + BG_SIZE / 2;
                int leftGroupWidth = calcGroupWidth(font, bloodText, showBlood, "B", yellowBileText, showYellowBile, "Y");
                int rightGroupWidth = calcGroupWidth(font, blackBileText, showBlackBile, "K", phlegmText, showPhlegm, "P");
                boolean hasLeft = showBlood || showYellowBile;
                boolean hasRight = showBlackBile || showPhlegm;

                int drawX;
                if (hasLeft && hasRight) {
                    drawX = centerX - leftGroupWidth - 2;
                } else {
                    drawX = centerX - (leftGroupWidth + rightGroupWidth) / 2;
                }

                drawX = drawHorizontalValue(guiGraphics, font, drawX, drawY, bloodText, showBlood, COLOR_BLOOD, "B");
                drawX = drawHorizontalValue(guiGraphics, font, drawX, drawY, yellowBileText, showYellowBile, COLOR_YELLOW_BILE, "Y");
                drawX = drawHorizontalValue(guiGraphics, font, drawX, drawY, blackBileText, showBlackBile, COLOR_BLACK_BILE, "K");
                drawHorizontalValue(guiGraphics, font, drawX, drawY, phlegmText, showPhlegm, COLOR_PHLEGM, "P");
            }
        }
    }

    private static boolean shouldShow(float current) {
        return WhoAmIClientConfig.HUD_VALUE_VISIBILITY.get() != HudValueVisibility.HAS_VALUE || current > 0;
    }

    private static String formatValue(float current, float maximum) {
        return switch (WhoAmIClientConfig.HUD_VALUE_FORMAT.get()) {
            case CURRENT_MAX -> Math.round(current) + "/" + Math.round(maximum);
            case CURRENT_ONLY -> String.valueOf(Math.round(current));
            case PERCENTAGE -> maximum <= 0 ? "0%" : Math.round(current / maximum * 100) + "%";
        };
    }

    private static void drawCentered(
        GuiGraphics guiGraphics,
        Font font,
        int centerX,
        int centerY,
        String text,
        boolean show
    ) {
        if (!show) return;
        int textWidth = font.width(text);
        guiGraphics.drawString(font, text, centerX - textWidth / 2, centerY - font.lineHeight / 2, 0xFFFFFF);
    }

    private static void drawRight(
        GuiGraphics guiGraphics,
        Font font,
        int rightEdgeX,
        int centerY,
        String text,
        boolean show
    ) {
        if (!show) return;
        int textWidth = font.width(text);
        guiGraphics.drawString(font, text, rightEdgeX - textWidth, centerY - font.lineHeight / 2, 0xFFFFFF);
    }

    private static void drawLeft(
        GuiGraphics guiGraphics,
        Font font,
        int leftX,
        int centerY,
        String text,
        boolean show
    ) {
        if (!show) return;
        guiGraphics.drawString(font, text, leftX, centerY - font.lineHeight / 2, 0xFFFFFF);
    }

    private static int drawHorizontalValue(
        GuiGraphics guiGraphics,
        Font font,
        int x,
        int y,
        String text,
        boolean show,
        int color,
        String label
    ) {
        if (!show) return x;
        if (WhoAmIClientConfig.HUD_VALUE_COLOR_LABEL.get()) {
            guiGraphics.drawString(font, text, x, y, color);
            return x + font.width(text) + 4;
        } else {
            String full = label + ": " + text;
            guiGraphics.drawString(font, full, x, y, 0xFFFFFF);
            return x + font.width(full) + 4;
        }
    }

    private static int calcGroupWidth(
        Font font,
        String text1,
        boolean show1,
        String label1,
        String text2,
        boolean show2,
        String label2
    ) {
        int width = 0;
        int count = 0;
        if (show1) {
            width += entryWidth(font, text1, label1);
            count++;
        }
        if (show2) {
            width += entryWidth(font, text2, label2);
            count++;
        }
        if (count == 2) {
            width += 4;
        }
        return width;
    }

    private static int entryWidth(Font font, String text, String label) {
        if (WhoAmIClientConfig.HUD_VALUE_COLOR_LABEL.get()) {
            return font.width(text);
        } else {
            return font.width(label + ": " + text);
        }
    }

    public enum HudAnchor implements TranslatableEnum {
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        TOP_RIGHT;

        @Override
        public Component getTranslatedName() {
            return WAICTooltipUtil.configEnumTranslation("hudAnchor", this);
        }
    }

    public enum HudVisibility implements TranslatableEnum {
        ALWAYS,
        HAS_HUMOURS,
        NEVER;

        @Override
        public Component getTranslatedName() {
            return WAICTooltipUtil.configEnumTranslation("hudVisibility", this);
        }
    }

    public enum HudValueFormat implements TranslatableEnum {
        CURRENT_MAX,
        CURRENT_ONLY,
        PERCENTAGE;

        @Override
        public Component getTranslatedName() {
            return WAICTooltipUtil.configEnumTranslation("hudValueFormat", this);
        }
    }

    public enum HudValuePosition implements TranslatableEnum {
        CENTER,
        OUTSIDE,
        LIST_BELOW;

        @Override
        public Component getTranslatedName() {
            return WAICTooltipUtil.configEnumTranslation("hudValuePosition", this);
        }
    }

    public enum HudValueVisibility implements TranslatableEnum {
        ALWAYS,
        HAS_VALUE,
        NEVER;

        @Override
        public Component getTranslatedName() {
            return WAICTooltipUtil.configEnumTranslation("hudValueVisibility", this);
        }
    }
}
