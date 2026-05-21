package net.zhaiji.who_am_i_core.manager;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.world.item.Items;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;

import java.util.Map;

public class CataclysmChestCavityTypeManager {
    // ==================== 利维坦胸腔 ====================
    // 海洋巨兽，保留鱼鳃/鱼骨/脊柱的核心结构
    public static final ChestCavityType LEVIATHAN = ChestCavityTypeManager.register("leviathan")
        .copyWith(ChestCavityTypeManager.AQUATIC)
        .setFirstRow(0, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setFirstRow(1, CataclysmOrgans.LEVIATHAN_FISHBONE.get())
        .setFirstRow(3, CataclysmOrgans.LEVIATHAN_GILL.get())
        .setFirstRow(4, CataclysmOrgans.LEVIATHAN_HEART.get())
        .setFirstRow(5, CataclysmOrgans.LEVIATHAN_GILL.get())
        .setFirstRow(7, CataclysmOrgans.LEVIATHAN_FISHBONE.get())
        .setFirstRow(8, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setSecondRow(0, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setSecondRow(1, CataclysmOrgans.LEVIATHAN_FISHBONE.get())
        .setSecondRow(4, CataclysmOrgans.LEVIATHAN_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.LEVIATHAN_FISHBONE.get())
        .setSecondRow(8, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setThirdRow(0, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setThirdRow(1, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setThirdRow(2, CataclysmOrgans.LEVIATHAN_INTESTINE.get())
        .setThirdRow(3, CataclysmOrgans.LEVIATHAN_INTESTINE.get())
        .setThirdRow(4, CataclysmOrgans.LEVIATHAN_STOMACH.get())
        .setThirdRow(5, CataclysmOrgans.LEVIATHAN_INTESTINE.get())
        .setThirdRow(6, CataclysmOrgans.LEVIATHAN_INTESTINE.get())
        .setThirdRow(7, CataclysmOrgans.LEVIATHAN_MUSCLE.get())
        .setThirdRow(8, CataclysmOrgans.LEVIATHAN_MUSCLE.get());

    // ==================== 下界合金巨兽胸腔 ====================
    // 机械巨兽，使用铁傀儡底座承载炉心/回路/熔炉
    public static final ChestCavityType NETHERITE_MONSTROSITY = ChestCavityTypeManager.register("netherite_monstrosity")
        .copyWith(ChestCavityTypeManager.IRON_GOLEM)
        .setFirstRow(4, CataclysmOrgans.MONSTROSITY_CORE.get())
        .setSecondRow(4, CataclysmOrgans.MONSTROSITY_CIRCUIT.get())
        .setThirdRow(4, CataclysmOrgans.MONSTROSITY_FURNACE.get());

    // ==================== 焰魔胸腔 ====================
    // 火焰构装，使用烈焰底座承载不灭薪火与焰魔甲胄
    public static final ChestCavityType IGNIS = ChestCavityTypeManager.register("ignis")
        .copyWith(ChestCavityTypeManager.BLAZE)
        .setFirstRow(1, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setFirstRow(2, CataclysmOrgans.BLAZING_VISAGE.get())
        .setFirstRow(4, CataclysmOrgans.UNDYING_EMBER.get())
        .setFirstRow(6, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setFirstRow(7, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setSecondRow(1, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setSecondRow(7, CataclysmOrgans.IGNITED_RIB_PLATING.get());

    // ==================== 斯库拉胸腔 ====================
    // 两栖海怪，保留潮汐灯/风暴脊柱/风暴肋骨
    public static final ChestCavityType SCYLLA = ChestCavityTypeManager.register("scylla")
        .copyWith(ChestCavityTypeManager.AQUATIC)
        .setFirstRow(1, CataclysmOrgans.STORM_RIB.get())
        .setFirstRow(4, CataclysmOrgans.TIDAL_LANTERN.get())
        .setFirstRow(7, CataclysmOrgans.STORM_RIB.get())
        .setSecondRow(1, CataclysmOrgans.STORM_RIB.get())
        .setSecondRow(4, CataclysmOrgans.STORM_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.STORM_RIB.get());

    // 末影守卫
    public static final ChestCavityType ENDER_GUARDIAN = ChestCavityTypeManager.register("cataclysm_ender_guardian")
        .setFirstRow(1, CataclysmOrgans.GUARDIAN_STONE.get())
        .setFirstRow(2, Items.PURPUR_BLOCK)
        .setFirstRow(3, Items.PURPUR_BLOCK)
        .setFirstRow(4, ModItems.VOID_CORE.get())
        .setFirstRow(5, Items.PURPUR_BLOCK)
        .setFirstRow(6, Items.PURPUR_BLOCK)
        .setFirstRow(7, CataclysmOrgans.GUARDIAN_STONE.get())

        .setSecondRow(1, CataclysmOrgans.GUARDIAN_STONE.get())
        .setSecondRow(2, Items.PURPUR_BLOCK)
        .setSecondRow(3, ModItems.VOID_STONE.get())
        .setSecondRow(4, CataclysmOrgans.VOID_CRYSTAL_SPINE.get())
        .setSecondRow(5, ModItems.VOID_STONE.get())
        .setSecondRow(6, Items.PURPUR_BLOCK)
        .setSecondRow(7, CataclysmOrgans.GUARDIAN_STONE.get())

        .setThirdRow(2, Items.END_STONE_BRICKS)
        .setThirdRow(3, Items.END_STONE_BRICKS)
        .setThirdRow(4, ModItems.VOID_STONE.get())
        .setThirdRow(5, Items.END_STONE_BRICKS)
        .setThirdRow(6, Items.END_STONE_BRICKS)

        .addValueBonuses(ModItems.VOID_CORE.get(), Map.of(
            InitAttribute.HEALTH, 2.0,
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 末影傀儡
    public static final ChestCavityType ENDER_GOLEM = ChestCavityTypeManager.register("cataclysm_ender_golem")
        .setFirstRow(3, Items.CRYING_OBSIDIAN)
        .setFirstRow(4, ModItems.VOID_CORE.get())
        .setFirstRow(5, Items.CRYING_OBSIDIAN)

        .setSecondRow(2, Items.CRYING_OBSIDIAN)
        .setSecondRow(3, Items.OBSIDIAN)
        .setSecondRow(4, ModItems.VOID_STONE.get())
        .setSecondRow(5, Items.OBSIDIAN)
        .setSecondRow(6, Items.CRYING_OBSIDIAN)

        .setThirdRow(2, Items.CRYING_OBSIDIAN)
        .setThirdRow(3, Items.OBSIDIAN)
        .setThirdRow(4, ModItems.VOID_STONE.get())
        .setThirdRow(5, Items.OBSIDIAN)
        .setThirdRow(6, Items.CRYING_OBSIDIAN)

        .addValueBonuses(ModItems.VOID_CORE.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // ==================== 咒骸胸腔 ====================
    // 骸骨系巨大亡灵
    public static final ChestCavityType APTRGANGR = ChestCavityTypeManager.register("aptrgangr")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, CataclysmOrgans.APTRGANGR_RIB.get())
        .setFirstRow(7, CataclysmOrgans.APTRGANGR_RIB.get())
        .setSecondRow(1, CataclysmOrgans.APTRGANGR_RIB.get())
        .setSecondRow(4, CataclysmOrgans.APTRGANGR_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.APTRGANGR_RIB.get());

    // ==================== 咒翼灵骸胸腔 ====================
    // 以凋零骷髅骨架为底，承载咒翼灵骸的魂心与封印石板
    public static final ChestCavityType MALEDICTUS = ChestCavityTypeManager.register("maledictus")
        .copyWith(ChestCavityTypeManager.WITHER_SKELETON)
        .setFirstRow(1, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setFirstRow(2, CataclysmOrgans.PHANTOM_SHARD.get())
        .setFirstRow(4, CataclysmOrgans.PHANTOM_HEART.get())
        .setFirstRow(7, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setSecondRow(1, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setSecondRow(2, CataclysmOrgans.SEALING_STONE_SLAB.get())
        .setSecondRow(4, CataclysmOrgans.MALEDICTUS_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.MALEDICTUS_RIB.get());

    // ==================== 先驱者胸腔 ====================
    // 远古工厂系机械体，使用铁傀儡底座承载核心部件
    public static final ChestCavityType HARBINGER = ChestCavityTypeManager.register("harbinger")
        .copyWith(ChestCavityTypeManager.IRON_GOLEM)
        .setFirstRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setFirstRow(2, CataclysmOrgans.TACTICAL_DISK.get())
        .setFirstRow(4, CataclysmOrgans.MECHANICAL_STAR.get())
        .setFirstRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(2, CataclysmOrgans.DEATH_LENS.get())
        .setSecondRow(4, CataclysmOrgans.COMPUTE_CHIP.get())
        .setSecondRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(4, CataclysmOrgans.POWER_CELL.get());

    // ==================== 燃烧亡魂胸腔 ====================
    // 亡灵火焰体，使用亡灵底座承载咒魂心脏
    public static final ChestCavityType IGNITED_REVENANT = ChestCavityTypeManager.register("ignited_revenant")
        .copyWith(ChestCavityTypeManager.UNDEAD)
        .setFirstRow(2, CataclysmOrgans.PHANTOM_SHARD.get())
        .setFirstRow(4, CataclysmOrgans.PHANTOM_HEART.get());

    // ==================== 远古遗骸胸腔 ====================
    // 沙漠巨兽残骸，使用食肉动物底座承载封印石板
    public static final ChestCavityType ANCIENT_REMNANT = ChestCavityTypeManager.register("ancient_remnant")
        .copyWith(ChestCavityTypeManager.CARNIVORE)
        .setFirstRow(4, CataclysmOrgans.SEALING_STONE_SLAB.get());

    /**
     * 注册灾变 Mod 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // --- Boss / 旗舰实体 ---
        // 利维坦
        event.registerEntity(ModEntities.THE_LEVIATHAN.get(), LEVIATHAN);
        // 下界合金巨兽
        event.registerEntity(ModEntities.NETHERITE_MONSTROSITY.get(), NETHERITE_MONSTROSITY);
        // 焰魔
        event.registerEntity(ModEntities.IGNIS.get(), IGNIS);
        // 斯库拉
        event.registerEntity(ModEntities.SCYLLA.get(), SCYLLA);
        // 末影守卫
        event.registerEntity(ModEntities.ENDER_GUARDIAN.get(), ENDER_GUARDIAN);
        // 末影傀儡
        event.registerEntity(ModEntities.ENDER_GOLEM.get(), ENDER_GOLEM);
        // 咒翼灵骸
        event.registerEntity(ModEntities.MALEDICTUS.get(), MALEDICTUS);
        // 先驱者
        event.registerEntity(ModEntities.THE_HARBINGER.get(), HARBINGER);
        // 炽燃遗魂
        event.registerEntity(ModEntities.IGNITED_REVENANT.get(), IGNITED_REVENANT);
        // 远古遗魂
        event.registerEntity(ModEntities.ANCIENT_REMNANT.get(), ANCIENT_REMNANT);

        // --- 非 Boss / 变体实体 ---
        // 炽燃狂魂
        event.registerEntity(ModEntities.IGNITED_BERSERKER.get(), ChestCavityTypeManager.UNDEAD);
        // 紫水晶巨蟹
        event.registerEntity(ModEntities.AMETHYST_CRAB.get(), ChestCavityTypeManager.ARTHROPOD);
        // 幼年利维坦
        event.registerEntity(ModEntities.THE_BABY_LEVIATHAN.get(), ChestCavityTypeManager.AQUATIC);
        // 下界合金幼兽
        event.registerEntity(ModEntities.NETHERITE_MINISTROSITY.get(), ChestCavityTypeManager.IRON_GOLEM);
        // 现世遗魂
        event.registerEntity(ModEntities.MODERN_REMNANT.get(), ChestCavityTypeManager.UNDEAD);
        // 冥行武弁
        event.registerEntity(ModEntities.APTRGANGR.get(), APTRGANGR);
        // 骸龙
        event.registerEntity(ModEntities.KOBOLETON.get(), ChestCavityTypeManager.SKELETON);
        // 骸龙斗士
        event.registerEntity(ModEntities.KOBOLEDIATOR.get(), ChestCavityTypeManager.SKELETON);
        // 瓦吉特
        event.registerEntity(ModEntities.WADJET.get(), ChestCavityTypeManager.SKELETON);
        // 再行魂尸
        event.registerEntity(ModEntities.DRAUGR.get(), ChestCavityTypeManager.UNDEAD);
        // 皇家魂尸
        event.registerEntity(ModEntities.ROYAL_DRAUGR.get(), ChestCavityTypeManager.UNDEAD);
        // 精英魂尸
        event.registerEntity(ModEntities.ELITE_DRAUGR.get(), ChestCavityTypeManager.UNDEAD);
        // 渊灵
        event.registerEntity(ModEntities.DEEPLING.get(), ChestCavityTypeManager.SALTWATER);
        // 渊灵蛮兵
        event.registerEntity(ModEntities.DEEPLING_BRUTE.get(), ChestCavityTypeManager.SALTWATER);
        // 渊灵垂钓者
        event.registerEntity(ModEntities.DEEPLING_ANGLER.get(), ChestCavityTypeManager.SALTWATER);
        // 渊灵祭司
        event.registerEntity(ModEntities.DEEPLING_PRIEST.get(), ChestCavityTypeManager.SALTWATER);
        // 渊灵术士
        event.registerEntity(ModEntities.DEEPLING_WARLOCK.get(), ChestCavityTypeManager.SALTWATER);
        // 蓑鲉
        event.registerEntity(ModEntities.LIONFISH.get(), ChestCavityTypeManager.FISH);
        // 珊瑚傀儡
        event.registerEntity(ModEntities.CORAL_GOLEM.get(), ChestCavityTypeManager.IRON_GOLEM);
        // 珊瑚巨像
        event.registerEntity(ModEntities.CORALSSUS.get(), ChestCavityTypeManager.AQUATIC);
        // 沧溟巡守
        event.registerEntity(ModEntities.HIPPOCAMTUS.get(), ChestCavityTypeManager.AQUATIC);
        // 水母莉亚
        event.registerEntity(ModEntities.CINDARIA.get(), ChestCavityTypeManager.AQUATIC);
        // 巨钳守卫
        event.registerEntity(ModEntities.CLAWDIAN.get(), ChestCavityTypeManager.AQUATIC);
        // 小海胆
        event.registerEntity(ModEntities.URCHINKIN.get(), ChestCavityTypeManager.AQUATIC);
        // 章鱼共生体
        event.registerEntity(ModEntities.SYMBIOCTO.get(), ChestCavityTypeManager.AQUATIC);
        // 溺尸宿主
        event.registerEntity(ModEntities.DROWNED_HOST.get(), ChestCavityTypeManager.UNDEAD);
        // 末影甲虫
        event.registerEntity(ModEntities.ENDERMAPTERA.get(), ChestCavityTypeManager.ARTHROPOD);
        // 观测者
        event.registerEntity(ModEntities.THE_WATCHER.get(), ChestCavityTypeManager.IRON_GOLEM);
        // 徘徊者
        event.registerEntity(ModEntities.THE_PROWLER.get(), ChestCavityTypeManager.IRON_GOLEM);
    }
}
