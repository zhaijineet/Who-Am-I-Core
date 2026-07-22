package net.zhaiji.who_am_i_core.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zhaiji.who_am_i_core.client.overlay.HumoursOverlay;

public class WhoAmIClientConfig {
    public static int hudX;
    public static int hudY;
    public static HumoursOverlay.HudAnchor hudAnchor;
    public static HumoursOverlay.HudVisibility hudVisibility;
    public static int existenceDisplacerParticleCount;

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue HUD_X = BUILDER
        .comment("X offset of the Humours HUD relative to the anchor corner")
        .defineInRange("hudX", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue HUD_Y = BUILDER
        .comment("Y offset of the Humours HUD relative to the anchor corner")
        .defineInRange("hudY", -5, Integer.MIN_VALUE, Integer.MAX_VALUE);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudAnchor> HUD_ANCHOR = BUILDER
        .comment("Screen corner where the Humours HUD is anchored")
        .defineEnum("hudAnchor", HumoursOverlay.HudAnchor.BOTTOM_LEFT);

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudVisibility> HUD_VISIBILITY = BUILDER
        .comment("When to display the Humours HUD. ALWAYS = always show, HAS_HUMOURS = only show when player has any humour, NEVER = never show")
        .defineEnum("hudVisibility", HumoursOverlay.HudVisibility.HAS_HUMOURS);

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
            existenceDisplacerParticleCount = EXISTENCE_DISPLACER_PARTICLE_COUNT.get();
        }
    }
}
