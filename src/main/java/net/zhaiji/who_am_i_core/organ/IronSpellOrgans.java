package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.IronSpellOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class IronSpellOrgans {
    // ==================== 死灵法师器官 ====================

    // 死灵法师脊柱
    public static final Supplier<Item> NECROMANCER_SPINE = WAICItem.ITEM.register(
        "necromancer_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 死灵法师肋骨
    public static final Supplier<Item> NECROMANCER_RIB = WAICItem.ITEM.register(
        "necromancer_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .build()
    );

    // ==================== 死者之王器官 ====================

    // 腐败魂灯 - 灵魂收割被动在 CommonEventHandler 的 LivingDeathEvent 中处理
    public static final Supplier<Item> CORRUPTED_SOUL_LANTERN = WAICItem.ITEM.register(
        "corrupted_soul_lantern",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 尸王脊柱 - 减伤效果在全局事件处理
    public static final Supplier<Item> DEAD_KING_SPINE = WAICItem.ITEM.register(
        "dead_king_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 尸王肋骨 - 添加黑胆汁上限修改
    public static final Supplier<Item> DEAD_KING_RIB = WAICItem.ITEM.register(
        "dead_king_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .added(IronSpellOrganUtil::deadKingRibAdded)
            .removed(IronSpellOrganUtil::deadKingRibRemoved)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    public static void register() {
    }
}
