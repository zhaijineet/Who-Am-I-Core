package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class MowziesMobOrgans {
    /**
     * 钢铁守护者的护心镜
     *
     * <pre>
     * #金属 #传说 #唯一被动
     *
     * -80%最终移动速度
     * 移除FOV的修改
     * 抵挡来自正面的实体的攻击
     * 攻击后的3秒内不能移动
     * </pre>
     */
    public static final Supplier<Item> FERROUS_WROUGHTNAUT_HEART_MIRROR = WAICItem.ITEM.register(
        "ferrous_wroughtnaut_heart_mirror",
        () -> Organ.builder()
            // -80%最终移动速度
            .totalMultipliedAttribute(Attributes.MOVEMENT_SPEED, -0.8)
            .incomingDamage(MowziesMobOrganUtil::ferrousWroughtnautHeartMirrorIncomingDamage)
            .attack(MowziesMobOrganUtil::ferrousWroughtnautHeartMirrorAttack)
            .build()
    );

    /**
     * 胸中新星
     * <pre>
     * #魔法 #召唤 #传说 #唯一被动
     * 当胸腔关闭时，烧毁周围3x3范围内的器官（机械系和魔法系器官除外）
     * 周围3x3范围内的乌姆塔纳面具会召唤对应的追随者
     * 面具会为主人提供其药水效果
     * </pre>
     */
    public static final Supplier<Item> CHEST_NOVA = WAICItem.ITEM.register(
        "chest_nova",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            // 胸腔关闭时烧毁周围器官以及尝试创建任务
            .chestCavityClose(MowziesMobOrganUtil::chestNovaChestCavityClose)
            // 器官移除时清理任务
            .removed(MowziesMobOrganUtil::chestNovaRemoved)
            .build()
    );

    /**
     * 制御棒
     *
     * <pre>
     * #机械 #传说 #唯一被动
     *
     * 当制御棒在胸中新星的3x3范围内时：
     * - 乌姆塔纳追随者重新召唤冷却时间从30秒缩短为10秒
     * - 面具也会给追随者提供效果
     * </pre>
     */
    public static final Supplier<Item> CONTROL_ROD = WAICItem.ITEM.register(
        "control_rod",
        () -> Organ.builder()
            .tooltip(WAICTooltipUtil.CONTROL_ROD_TOOLTIP)
            .build()
    );

    // 衰老心脏
    public static final Supplier<Item> AGED_HEART = WAICItem.ITEM.register(
        "aged_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 0.5)
            .build()
    );

    // 衰老肺脏
    public static final Supplier<Item> AGED_LUNG = WAICItem.ITEM.register(
        "aged_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 0.5)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 0.5)
            .addValueAttribute(InitAttribute.ENDURANCE, 0.5)
            .build()
    );

    // 衰老脊柱
    public static final Supplier<Item> AGED_SPINE = WAICItem.ITEM.register(
        "aged_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 0.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .build()
    );

    // 衰老胃
    public static final Supplier<Item> AGED_STOMACH = WAICItem.ITEM.register(
        "aged_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 0.5)
            .build()
    );

    // 衰老肠子
    public static final Supplier<Item> AGED_INTESTINE = WAICItem.ITEM.register(
        "aged_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 0.5)
            .build()
    );

    // 衰老肾脏
    public static final Supplier<Item> AGED_KIDNEY = WAICItem.ITEM.register(
        "aged_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 0.5)
            .build()
    );

    //衰老脾脏
    public static final Supplier<Item> AGED_SPLEEN = WAICItem.ITEM.register(
        "aged_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 0.5)
            .build()
    );

    // 衰老肝脏
    public static final Supplier<Item> AGED_LIVER = WAICItem.ITEM.register(
        "aged_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 0.5)
            .build()
    );

    // 衰老阑尾
    public static final Supplier<Item> AGED_APPENDIX = WAICItem.ITEM.register(
        "aged_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 0.5)
            .build()
    );

    // 衰老肋骨
    public static final Supplier<Item> AGED_RIB = WAICItem.ITEM.register(
        "aged_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 0.5)
            .build()
    );

    // 衰老肌肉
    public static final Supplier<Item> AGED_MUSCLE = WAICItem.ITEM.register(
        "aged_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 0.5)
            .addValueAttribute(InitAttribute.SPEED, 0.5)
            .build()
    );

    /**
     * 禅心
     * <pre>
     * 生命值+3，防御+1.5，击退抗性+100%，跳跃力+2
     * 磐石之躯：免疫摔落伤害
     * 地卜亲和：拥有地卜术效果时，伤害减免50%
     * </pre>
     */
    public static final Supplier<Item> ZEN_HEART = WAICItem.ITEM.register(
        "zen_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .addValueAttribute(InitAttribute.LEAPING, 2)
            .build()
    );

    /**
     * 泥峭铭文板
     * <pre>
     * #魔法
     * 右键长按食用泥土物品，恢复饥饿值并获得吸收效果
     * </pre>
     */
    public static final Supplier<Item> BLUFF_TABLET = WAICItem.ITEM.register(
        "bluff_tablet",
        () -> Organ.builder()
            .build()
    );

    /**
     * 活性泥峭棒
     * <pre>
     * #魔法
     * 右键长按食用泥土物品，恢复饥饿值并增加饱和度
     * </pre>
     */
    public static final Supplier<Item> ACTIVE_BLUFF_ROD = WAICItem.ITEM.register(
        "active_bluff_rod",
        () -> Organ.builder()
            .build()
    );

    public static void register() {
    }    /**
     * 泥峭核心
     * <pre>
     * #魔法
     * 食用视线方向的泥土方块
     * </pre>
     */
    public static final Supplier<Item> BLUFF_CORE = WAICItem.ITEM.register(
        "bluff_core",
        () -> Organ.builder()
            .skill(MowziesMobOrganUtil::bluffCoreSkill)
            .build()
    );
}
