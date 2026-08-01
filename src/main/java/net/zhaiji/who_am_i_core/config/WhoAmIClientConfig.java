package net.zhaiji.who_am_i_core.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zhaiji.who_am_i_core.client.overlay.HumoursOverlay;

public class WhoAmIClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue HUD_X = BUILDER
        .comment("X offset of the Humours HUD relative to the anchor corner")
        .defineInRange(
            "hudX",
            22,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    public static final ModConfigSpec.IntValue HUD_Y = BUILDER
        .comment("Y offset of the Humours HUD relative to the anchor corner")
        .defineInRange(
            "hudY",
            -15,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    public static final ModConfigSpec.EnumValue<HumoursOverlay.HudAnchor> HUD_ANCHOR = BUILDER
        .comment("Screen corner where the Humours HUD is anchored")
        .defineEnum(
            "hudAnchor",
            HumoursOverlay.HudAnchor.BOTTOM_LEFT
        );

    public static final ModConfigSpec.EnumValue<HumoursOverlay.HudVisibility> HUD_VISIBILITY = BUILDER
        .comment("When to display the Humours HUD. Always = always show, Has Humours = only show when player has any humour, Never = never show")
        .defineEnum(
            "hudVisibility",
            HumoursOverlay.HudVisibility.HAS_HUMOURS
        );

    public static final ModConfigSpec.EnumValue<HumoursOverlay.HudValueFormat> HUD_VALUE_FORMAT = BUILDER
        .comment("Value display format. Current / Max = 50/100, Current Only = 50, Percentage = 50%")
        .defineEnum(
            "hudValueFormat",
            HumoursOverlay.HudValueFormat.CURRENT_ONLY
        );

    public static final ModConfigSpec.EnumValue<HumoursOverlay.HudValuePosition> HUD_VALUE_POSITION = BUILDER
        .comment("Where to display humour values. Center = on top of each bar, Outside = around HUD edges, List Below = listed below HUD")
        .defineEnum(
            "hudValuePosition",
            HumoursOverlay.HudValuePosition.CENTER
        );

    public static final ModConfigSpec.EnumValue<HumoursOverlay.HudValueVisibility> HUD_VALUE_VISIBILITY = BUILDER
        .comment("When to display humour values. Always = always show, Has Value = only show when that humour > 0, Never = never show values")
        .defineEnum(
            "hudValueVisibility",
            HumoursOverlay.HudValueVisibility.HAS_VALUE
        );

    public static final ModConfigSpec.BooleanValue HUD_VALUE_COLOR_LABEL = BUILDER
        .comment("Use humour colors instead of letter labels in List Below mode. true = colored values, false = letter prefix (B:/Y:/K:/P:)")
        .define(
            "hudValueColorLabel",
            true
        );

    public static final ModConfigSpec.IntValue EXISTENCE_DISPLACER_PARTICLE_COUNT = BUILDER
        .comment("Enchantment particles spawned per use tick by the Existence Displacer (0 to disable)")
        .defineInRange(
            "existenceDisplacerParticleCount",
            2887,
            0,
            16384
        );

    public static final ModConfigSpec SPEC = BUILDER.build();
}
