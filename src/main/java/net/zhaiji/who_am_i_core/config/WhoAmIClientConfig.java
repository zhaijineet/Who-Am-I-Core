package net.zhaiji.who_am_i_core.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zhaiji.who_am_i_core.client.overlay.HumoursOverlay;

public class WhoAmIClientConfig {
    public static int hudX;
    public static int hudY;
    public static HumoursOverlay.HudAnchor hudAnchor;
    public static HumoursOverlay.HudVisibility hudVisibility;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder()
        .comment("Client config for Who Am I Core")
        .push("Config");

    private static final ModConfigSpec.IntValue HUD_X = BUILDER
        .comment("Humours HUD X offset")
        .defineInRange(
            "hudX",
            10,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    private static final ModConfigSpec.IntValue HUD_Y = BUILDER
        .comment("Humours HUD Y offset")
        .defineInRange(
            "hudY",
            -5,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudAnchor> HUD_ANCHOR = BUILDER
        .comment("HUD corner position anchor")
        .defineEnum(
            "hudAnchor",
            HumoursOverlay.HudAnchor.BOTTOM_LEFT
        );

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudVisibility> HUD_VISIBILITY = BUILDER
        .comment("HUD display condition: ALWAYS = always show, HAS_HUMOURS = only show when player has any humour")
        .defineEnum(
            "hudVisibility",
            HumoursOverlay.HudVisibility.HAS_HUMOURS
        );

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static void handlerModConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            hudX = HUD_X.get();
            hudY = HUD_Y.get();
            hudAnchor = HUD_ANCHOR.get();
            hudVisibility = HUD_VISIBILITY.get();
        }
    }
}
