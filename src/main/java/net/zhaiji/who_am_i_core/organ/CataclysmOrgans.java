package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.manager.WAICTooltipManager;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.CataclysmOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICGoalSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICPlayerSkillUtil;

import java.util.function.Supplier;

public class CataclysmOrgans {
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

    // 魂尸脊柱
    public static final Supplier<Item> DRAUGR_SPINE = WAICItem.ITEM.register(
        "draugr_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 魂尸肋骨
    public static final Supplier<Item> DRAUGR_RIB = WAICItem.ITEM.register(
        "draugr_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .build()
    );

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
            .attack(CataclysmOrganUtil::phantomHeartAttack)
            .tooltip(WAICTooltipManager.PHANTOM_HEART_TOOLTIP)
            .build()
    );

    // 咒魂残片
    public static final Supplier<Item> PHANTOM_SHARD = WAICItem.ITEM.register(
        "phantom_shard",
        () -> Organ.builder()
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
            .skill(WAICPlayerSkillUtil::sealingStoneSlab)
            .goalSkill(WAICGoalSkillUtil.sealingStoneSlabGoalSkill())
            .cooldown(300)
            .tooltip(WAICTooltipManager.SEALING_STONE_SLAB_TOOLTIP)
            .build()
    );

    // 涛浪提灯
    public static final Supplier<Item> TIDAL_LANTERN = WAICItem.ITEM.register(
        "tidal_lantern",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .baseMultipliedAttribute(NeoForgeMod.SWIM_SPEED, 0.5)
            .attack(CataclysmOrganUtil::tidalLanternAttack)
            .tooltip(WAICTooltipManager.TIDAL_LANTERN_TOOLTIP)
            .build()
    );

    // 风暴脊柱 - 减伤效果在全局事件处理
    public static final Supplier<Item> STORM_SPINE = WAICItem.ITEM.register(
        "storm_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .tooltip(WAICTooltipManager.STORM_SPINE_TOOLTIP)
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

    // 不灭薪火
    public static final Supplier<Item> UNDYING_EMBER = WAICItem.ITEM.register(
        "undying_ember",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .modifier(CataclysmOrganUtil::undyingEmberModifier)
            .build()
    );

    // 焰魔肋甲
    public static final Supplier<Item> IGNITED_RIB_PLATING = WAICItem.ITEM.register(
        "ignited_rib_plating",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.5)
            .modifier(CataclysmOrganUtil::ignitedRibPlatingModifier)
            .build()
    );

    // 炽面甲
    public static final Supplier<Item> BLAZING_VISAGE = WAICItem.ITEM.register(
        "blazing_visage",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1)
            .attack(CataclysmOrganUtil::blazingVisageAttack)
            .tooltip(WAICTooltipManager.BLAZING_VISAGE_TOOLTIP)
            .build()
    );

    // 巨兽炉心
    public static final Supplier<Item> MONSTROSITY_CORE = WAICItem.ITEM.register(
        "monstrosity_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 5)
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.ARMOR_TOUGHNESS, 1)
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
            .skill(WAICPlayerSkillUtil::monstrosityCircuit)
            .goalSkill(WAICGoalSkillUtil.monstrosityCircuitGoalSkill())
            .cooldown(160)
            .tooltip(WAICTooltipManager.MONSTROSITY_CIRCUIT_TOOLTIP)
            .build()
    );

    // 巨兽熔炉
    public static final Supplier<Item> MONSTROSITY_FURNACE = WAICItem.ITEM.register(
        "monstrosity_furnace",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .addValueAttribute(InitAttribute.NUTRITION, 1)
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .tooltip(WAICTooltipManager.MONSTROSITY_FURNACE_TOOLTIP)
            .build()
    );

    // 战术磁盘
    public static final Supplier<Item> TACTICAL_DISK = WAICItem.ITEM.register(
        "tactical_disk",
        () -> Organ.builder()
            .modifier(CataclysmOrganUtil.ancientFactoryModifier(InitAttribute.HEALTH, 2))
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
            .tooltip(WAICTooltipManager.POWER_CELL_TOOLTIP)
            .build()
    );

    // 运算晶片
    public static final Supplier<Item> COMPUTE_CHIP = WAICItem.ITEM.register(
        "compute_chip",
        () -> Organ.builder()
            .modifier(CataclysmOrganUtil.ancientFactoryModifier(InitAttribute.NERVES, 1.5))
            .build()
    );

    // 机械之星
    public static final Supplier<Item> MECHANICAL_STAR = WAICItem.ITEM.register(
        "mechanical_star",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 5)
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(Attributes.ARMOR_TOUGHNESS, 2)
            .skill(WAICPlayerSkillUtil::mechanicalStar)
            .goalSkill(WAICGoalSkillUtil.mechanicalStarGoalSkill())
            .cooldown(160)
            .tooltip(WAICTooltipManager.MECHANICAL_STAR_TOOLTIP)
            .build()
    );

    // 死亡透镜
    public static final Supplier<Item> DEATH_LENS = WAICItem.ITEM.register(
        "death_lens",
        () -> Organ.builder()
            .skill(WAICPlayerSkillUtil::deathLens)
            .goalSkill(WAICGoalSkillUtil.deathLensGoalSkill())
            .cooldown(300)
            .tooltip(WAICTooltipManager.DEATH_LENS_TOOLTIP)
            .build()
    );

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
            .skill(WAICPlayerSkillUtil::voidCrystalSpine)
            .goalSkill(WAICGoalSkillUtil.voidCrystalSpineGoalSkill())
            .cooldown(300)
            .tooltip(WAICTooltipManager.VOID_CRYSTAL_SPINE_TOOLTIP)
            .build()
    );

    // 沙釉心脏
    public static final Supplier<Item> SAND_GLAZE_HEART = WAICItem.ITEM.register(
        "sand_glaze_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.NERVES, 1)
            .skill(WAICPlayerSkillUtil::sandGlazeHeart)
            .goalSkill(WAICGoalSkillUtil.sandGlazeHeartGoalSkill())
            .cooldown(400)
            .attack(CataclysmOrganUtil::sandGlazeHeartAttack)
            .tooltip(WAICTooltipManager.SAND_GLAZE_HEART_TOOLTIP)
            .build()
    );

    // 遗魂脊柱
    public static final Supplier<Item> REMNANT_SPINE = WAICItem.ITEM.register(
        "remnant_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 遗魂肋骨
    public static final Supplier<Item> REMNANT_RIB = WAICItem.ITEM.register(
        "remnant_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    public static void register() {
    }
}
