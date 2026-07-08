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

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder()
        .comment(
            "Who Am I Core 客户端配置",
            "Client config for Who Am I Core"
        )
        .push("Config");

    private static final ModConfigSpec.IntValue HUD_X = BUILDER
        .comment(
            "四体液 HUD 的 X 轴偏移",
            "Humours HUD X offset"
        )
        .defineInRange(
            "hudX",
            10,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    private static final ModConfigSpec.IntValue HUD_Y = BUILDER
        .comment(
            "四体液 HUD 的 Y 轴偏移",
            "Humours HUD Y offset"
        )
        .defineInRange(
            "hudY",
            -5,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
        );

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudAnchor> HUD_ANCHOR = BUILDER
        .comment(
            "HUD 的屏幕角落锚点",
            "HUD corner position anchor"
        )
        .defineEnum(
            "hudAnchor",
            HumoursOverlay.HudAnchor.BOTTOM_LEFT
        );

    private static final ModConfigSpec.EnumValue<HumoursOverlay.HudVisibility> HUD_VISIBILITY = BUILDER
        .comment(
            "HUD 显示条件：ALWAYS = 始终显示，HAS_HUMOURS = 仅当玩家有任意体液时显示",
            "HUD display condition: ALWAYS = always show, HAS_HUMOURS = only show when player has any humour"
        )
        .defineEnum(
            "hudVisibility",
            HumoursOverlay.HudVisibility.HAS_HUMOURS
        );

    private static final ModConfigSpec.IntValue EXISTENCE_DISPLACER_PARTICLE_COUNT = BUILDER
        .comment(
            "存在置换器每个使用 tick 生成的附魔粒子数量（0 为关闭）",
            "Existence Displacer enchantment particle count spawned per use tick (0 to disable)"
        )
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
