package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.CataclysmOrganUtil;

import java.util.function.Supplier;

public class CataclysmOrgans {
    // ==================== 利维坦器官 ====================

    // 利维坦心脏
    public static final Supplier<Item> LEVIATHAN_HEART = WAICItem.ITEM.register(
        "leviathan_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .build()
    );

    // 利维坦肌肉
    public static final Supplier<Item> LEVIATHAN_MUSCLE = WAICItem.ITEM.register(
        "leviathan_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2.25)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .baseMultipliedAttribute(NeoForgeMod.SWIM_SPEED, 0.3)
            .build()
    );

    // 利维坦肠子
    public static final Supplier<Item> LEVIATHAN_INTESTINE = WAICItem.ITEM.register(
        "leviathan_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 利维坦胃
    public static final Supplier<Item> LEVIATHAN_STOMACH = WAICItem.ITEM.register(
        "leviathan_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 利维坦鳃
    public static final Supplier<Item> LEVIATHAN_GILL = WAICItem.ITEM.register(
        "leviathan_gill",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 利维坦脊柱
    public static final Supplier<Item> LEVIATHAN_SPINE = WAICItem.ITEM.register(
        "leviathan_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .build()
    );

    // 利维坦鱼骨
    public static final Supplier<Item> LEVIATHAN_FISHBONE = WAICItem.ITEM.register(
        "leviathan_fishbone",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.25)
            .build()
    );

    // ==================== 冥行武弁器官 ====================

    // 冥行武弁脊柱
    public static final Supplier<Item> APTRGANGR_SPINE = WAICItem.ITEM.register(
        "aptrgangr_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 冥行武弁肋骨
    public static final Supplier<Item> APTRGANGR_RIB = WAICItem.ITEM.register(
        "aptrgangr_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .build()
    );

    // ==================== 咒翼灵骸器官 ====================

    // 咒翼灵骸脊柱
    public static final Supplier<Item> MALEDICTUS_SPINE = WAICItem.ITEM.register(
        "maledictus_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 咒翼灵骸肋骨
    public static final Supplier<Item> MALEDICTUS_RIB = WAICItem.ITEM.register(
        "maledictus_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    // 咒魂心脏
    public static final Supplier<Item> PHANTOM_HEART = WAICItem.ITEM.register(
        "phantom_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -2)
            .attack(CataclysmOrganUtil::phantomHeartAttack)
            .build()
    );

    // 咒魂残片
    public static final Supplier<Item> PHANTOM_SHARD = WAICItem.ITEM.register(
        "phantom_shard",
        () -> Organ.builder()
            .addValueAttribute(WAICAttribute.TEMPERATURE, -2)
            .addValueAttribute(InitAttribute.FROST_RESISTANCE, 3)
            .addValueAttribute(InitAttribute.LEAPING, 2)
            .build()
    );

    // 封印石板
    public static final Supplier<Item> SEALING_STONE_SLAB = WAICItem.ITEM.register(
        "sealing_stone_slab",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .skill(CataclysmOrganUtil::sealingStoneSlabSkill)
            .cooldown(300)
            .build()
    );

    // ==================== 斯库拉器官 ====================

    // 涛浪提灯
    public static final Supplier<Item> TIDAL_LANTERN = WAICItem.ITEM.register(
        "tidal_lantern",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .baseMultipliedAttribute(NeoForgeMod.SWIM_SPEED, 0.5)
            .attack(CataclysmOrganUtil::tidalLanternAttack)
            .build()
    );

    // 风暴脊柱 - 减伤效果在全局事件处理
    public static final Supplier<Item> STORM_SPINE = WAICItem.ITEM.register(
        "storm_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 风暴肋骨
    public static final Supplier<Item> STORM_RIB = WAICItem.ITEM.register(
        "storm_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .added(CataclysmOrganUtil::stormRibAdded)
            .removed(CataclysmOrganUtil::stormRibRemoved)
            .build()
    );

    // ==================== 焰魔器官 ====================

    // 不灭薪火
    public static final Supplier<Item> UNDYING_EMBER = WAICItem.ITEM.register(
        "undying_ember",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 9)
            .modifier(CataclysmOrganUtil::undyingEmberModifier)
            .build()
    );

    // 焰魔肋甲
    public static final Supplier<Item> IGNITED_RIB_PLATING = WAICItem.ITEM.register(
        "ignited_rib_plating",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .modifier(CataclysmOrganUtil::ignitedRibPlatingModifier)
            .build()
    );

    // 炽面甲
    public static final Supplier<Item> BLAZING_VISAGE = WAICItem.ITEM.register(
        "blazing_visage",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .attack(CataclysmOrganUtil::blazingVisageAttack)
            .build()
    );

    // ==================== 下界合金巨兽器官 ====================

    // 巨兽炉心
    public static final Supplier<Item> MONSTROSITY_CORE = WAICItem.ITEM.register(
        "monstrosity_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 5)
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.ARMOR_TOUGHNESS, 1)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2)
            .added(CataclysmOrganUtil::monstrosityCoreAdded)
            .removed(CataclysmOrganUtil::monstrosityCoreRemoved)
            .tick(CataclysmOrganUtil::monstrosityCoreTick)
            .build()
    );

    // 巨兽回路
    public static final Supplier<Item> MONSTROSITY_CIRCUIT = WAICItem.ITEM.register(
        "monstrosity_circuit",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2)
            .skill(CataclysmOrganUtil::monstrosityCircuitSkill)
            .cooldown(160)
            .build()
    );

    // 巨兽熔炉
    public static final Supplier<Item> MONSTROSITY_FURNACE = WAICItem.ITEM.register(
        "monstrosity_furnace",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .addValueAttribute(InitAttribute.NUTRITION, 1)
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2)
            .build()
    );

    // ==================== 远古工厂器官 ====================

    // 战术磁盘
    public static final Supplier<Item> TACTICAL_DISK = WAICItem.ITEM.register(
        "tactical_disk",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .modifier((context, modifiers) -> CataclysmOrganUtil.ancientFactoryModifier(context, modifiers, InitAttribute.HEALTH))
            .build()
    );

    // 强化构架
    public static final Supplier<Item> REINFORCED_FRAME = WAICItem.ITEM.register(
        "reinforced_frame",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.5)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .build()
    );

    // 蓄能电芯
    public static final Supplier<Item> POWER_CELL = WAICItem.ITEM.register(
        "power_cell",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .tick(CataclysmOrganUtil::powerCellTick)
            .build()
    );

    // 运算晶片
    public static final Supplier<Item> COMPUTE_CHIP = WAICItem.ITEM.register(
        "compute_chip",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .modifier((context, modifiers) -> CataclysmOrganUtil.ancientFactoryModifier(context, modifiers, InitAttribute.NERVES))
            .build()
    );

    // 机械之星
    public static final Supplier<Item> MECHANICAL_STAR = WAICItem.ITEM.register(
        "mechanical_star",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 5)
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.ARMOR_TOUGHNESS, 2)
            .skill(CataclysmOrganUtil::mechanicalStarSkill)
            .cooldown(160)
            .build()
    );

    // 死亡透镜
    public static final Supplier<Item> DEATH_LENS = WAICItem.ITEM.register(
        "death_lens",
        () -> Organ.builder()
            .skill(CataclysmOrganUtil::deathLensSkill)
            .cooldown(300)
            .build()
    );

    // ==================== 末影守卫器官 ====================

    // 守卫石块
    public static final Supplier<Item> GUARDIAN_STONE = WAICItem.ITEM.register(
        "guardian_stone",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 3)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.1) // +10%
            .build()
    );

    // 虚空晶脊
    public static final Supplier<Item> VOID_CRYSTAL_SPINE = WAICItem.ITEM.register(
        "void_crystal_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2.5)
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .skill(CataclysmOrganUtil::voidCrystalSpineSkill)
            .cooldown(300)
            .build()
    );

    public static void register() {
    }
}
