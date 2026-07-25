package net.zhaiji.who_am_i_core.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zhaiji.who_am_i_core.client.overlay.HumoursOverlay;

public class WhoAmIClientConfig {
    public static int hudX;
    public static int hudY;
    public static HumoursOverlay.HudAnchor hudAnchor;
    public static HumoursOverlay.HudVisibility hudVisibility;
    public static HumoursOverlay.HudValueFormat hudValueFormat;
    public static HumoursOverlay.HudValuePosition hudValuePosition;
    public static HumoursOverlay.HudValueVisibility hudValueVisibility;
    public static boolean hudValueColorLabel;
    public static int existenceDisplacerParticleCount;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue HUD_X = BUILDER
        .comment("X offset of the Humours HUD relative to the anchor corner")
        .defineInRange("hudX", 22, Integer.MIN_VALUE, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue HUD_Y = BUILDER
        .comment("Y offset of the Humours HUD relative to the anchor corner")
        .defineInRange("hudY", -15, Integer.MIN_VALUE, Integer.MAX_VALUE);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudAnchor> HUD_ANCHOR = BUILDER
        .comment("Screen corner where the Humours HUD is anchored")
        .defineEnum("hudAnchor", HumoursOverlay.HudAnchor.BOTTOM_LEFT);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudVisibility> HUD_VISIBILITY = BUILDER
        .comment("When to display the Humours HUD. ALWAYS = always show, HAS_HUMOURS = only show when player has any humour, NEVER = never show")
        .defineEnum("hudVisibility", HumoursOverlay.HudVisibility.HAS_HUMOURS);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudValueFormat> HUD_VALUE_FORMAT = BUILDER
        .comment("Value display format. CURRENT_MAX = 50/100, CURRENT_ONLY = 50, PERCENTAGE = 50%")
        .defineEnum("hudValueFormat", HumoursOverlay.HudValueFormat.CURRENT_ONLY);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudValuePosition> HUD_VALUE_POSITION = BUILDER
        .comment("Where to display humour values. CENTER = on top of each bar, OUTSIDE = around HUD edges, LIST_BELOW = listed below HUD")
        .defineEnum("hudValuePosition", HumoursOverlay.HudValuePosition.CENTER);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudValueVisibility> HUD_VALUE_VISIBILITY = BUILDER
        .comment("When to display humour values. ALWAYS = always show, HAS_VALUE = only show when that humour > 0, NEVER = never show values")
        .defineEnum("hudValueVisibility", HumoursOverlay.HudValueVisibility.HAS_VALUE);

    private static final ModConfigSpec.BooleanValue HUD_VALUE_COLOR_LABEL = BUILDER
        .comment("Use humour colors instead of letter labels in LIST_BELOW mode. true = colored values, false = letter prefix (B:/Y:/K:/P:)")
        .define("hudValueColorLabel", true);

    private static final ModConfigSpec.IntValue EXISTENCE_DISPLACER_PARTICLE_COUNT = BUILDER
        .comment("Enchantment particles spawned per use tick by the Existence Displacer (0 to disable)")
        .defineInRange("existenceDisplacerParticleCount", 2887, 0, 16384);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static void handlerModConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            hudX = HUD_X.get();
            hudY = HUD_Y.get();
            hudAnchor = HUD_ANCHOR.get();
            hudVisibility = HUD_VISIBILITY.get();
            hudValueFormat = HUD_VALUE_FORMAT.get();
            hudValuePosition = HUD_VALUE_POSITION.get();
            hudValueVisibility = HUD_VALUE_VISIBILITY.get();
            hudValueColorLabel = HUD_VALUE_COLOR_LABEL.get();
            existenceDisplacerParticleCount = EXISTENCE_DISPLACER_PARTICLE_COUNT.get();
        }
    }
}
