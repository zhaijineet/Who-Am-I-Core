package net.zhaiji.who_am_i_core.organ;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.manager.WAICTooltipManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class IronSpellOrgans {
    // 亡灵术士脊柱
    public static final Supplier<Item> NECROMANCER_SPINE = WAICItem.ITEM.register(
        "necromancer_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .addValueAttribute(AttributeRegistry.SPELL_POWER, 0.2)
            .build()
    );

    // 亡灵术士肋骨
    public static final Supplier<Item> NECROMANCER_RIB = WAICItem.ITEM.register(
        "necromancer_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .addValueAttribute(AttributeRegistry.MAX_MANA, 20)
            .build()
    );

    // 原初之火
    public static final Supplier<Item> PRIMORDIAL_FLAME = WAICItem.ITEM.register(
        "primordial_flame",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 10)
            .addValueAttribute(InitAttribute.FIRE_RESISTANCE, 10)
            .addValueAttribute(AttributeRegistry.SPELL_POWER, 0.2)
            .addValueAttribute(AttributeRegistry.FIRE_SPELL_POWER, 0.5)
            .addValueAttribute(AttributeRegistry.MAX_MANA, 500)
            .build()
    );

    // 绿宝石头骨
    public static final Supplier<Item> EMERALD_SKULL = WAICItem.ITEM.register(
        "emerald_skull",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 3)
            .addValueAttribute(InitAttribute.DEFENSE, 3)
            .build()
    );

    // 腐败魂灯 - 灵魂收割被动在 CommonEventHandler 的 LivingDeathEvent 中处理
    public static final Supplier<Item> CORRUPTED_SOUL_LANTERN = WAICItem.ITEM.register(
        "corrupted_soul_lantern",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 4)
            .build()
    );

    // 尸王脊柱 - 减伤效果在全局事件处理
    public static final Supplier<Item> DEAD_KING_SPINE = WAICItem.ITEM.register(
        "dead_king_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 4)
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .tooltip(WAICTooltipManager.DEAD_KING_SPINE_TOOLTIP)
            .build()
    );

    // 尸王肋骨 - 添加黑胆汁上限修改
    public static final Supplier<Item> DEAD_KING_RIB = WAICItem.ITEM.register(
        "dead_king_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 4)
            .addValueAttribute(WAICAttribute.MAX_BLACK_BILE, 50)
            .build()
    );

    public static void register() {
    }
}
