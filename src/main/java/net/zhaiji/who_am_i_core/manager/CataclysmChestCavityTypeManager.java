package net.zhaiji.who_am_i_core.manager;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;

import java.util.Map;

public class CataclysmChestCavityTypeManager {
    // 利维坦胸腔
    public static final ChestCavityType LEVIATHAN = register("leviathan")
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

    // 下界合金巨兽胸腔
    public static final ChestCavityType NETHERITE_MONSTROSITY = register("netherite_monstrosity")
        .setFirstRow(2, Items.POLISHED_BLACKSTONE_BRICKS)
        .setFirstRow(3, Items.REDSTONE_BLOCK)
        .setFirstRow(4, CataclysmOrgans.MONSTROSITY_CORE.get())
        .setFirstRow(5, Items.REDSTONE_BLOCK)
        .setFirstRow(6, Items.POLISHED_BLACKSTONE_BRICKS)
        .setSecondRow(2, Items.POLISHED_BLACKSTONE_BRICKS)
        .setSecondRow(3, Items.REDSTONE_BLOCK)
        .setSecondRow(4, CataclysmOrgans.MONSTROSITY_CIRCUIT.get())
        .setSecondRow(5, Items.REDSTONE_BLOCK)
        .setSecondRow(6, Items.POLISHED_BLACKSTONE_BRICKS)
        .setThirdRow(2, Items.POLISHED_BLACKSTONE_BRICKS)
        .setThirdRow(3, Items.REDSTONE_BLOCK)
        .setThirdRow(4, CataclysmOrgans.MONSTROSITY_FURNACE.get())
        .setThirdRow(5, Items.REDSTONE_BLOCK)
        .setThirdRow(6, Items.POLISHED_BLACKSTONE_BRICKS)
        .addValueBonuses(CataclysmOrgans.MONSTROSITY_CORE.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 下界合金幼兽胸腔
    public static final ChestCavityType NETHERITE_MINISTROSITY = register("netherite_ministrosity")
        .setFirstRow(4, Items.POLISHED_BLACKSTONE_BRICKS)
        .setSecondRow(3, Items.REDSTONE_BLOCK)
        .setSecondRow(4, CataclysmOrgans.MONSTROSITY_CORE.get())
        .setSecondRow(5, Items.REDSTONE_BLOCK)
        .setThirdRow(4, Items.POLISHED_BLACKSTONE_BRICKS)
        .addValueBonuses(CataclysmOrgans.MONSTROSITY_CORE.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 焰魔胸腔
    public static final ChestCavityType IGNIS = register("ignis")
        .setFirstRow(1, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setFirstRow(4, CataclysmOrgans.UNDYING_EMBER.get())
        .setFirstRow(7, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setSecondRow(1, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .setSecondRow(4, CataclysmOrgans.BLAZING_VISAGE.get())
        .setSecondRow(7, CataclysmOrgans.IGNITED_RIB_PLATING.get())
        .addValueBonuses(CataclysmOrgans.UNDYING_EMBER.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 珊瑚傀儡胸腔
    public static final ChestCavityType CORAL_GOLEM = register("coral_golem")
        .copyWith(ChestCavityTypeManager.IRON_GOLEM)
        .setNeedBreath(true)
        .setFirstRow(4, ModItems.CRYSTALLIZED_CORAL.get())
        .addValueBonuses(ModItems.CRYSTALLIZED_CORAL.get(), Map.of(
            InitAttribute.HEALTH, 2.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0,
            InitAttribute.WATER_BREATH, 1.0
        ));

    // 珊瑚巨像胸腔
    public static final ChestCavityType CORALSSUS = register("coralssus")
        .setNeedBreath(false)
        .setFirstRow(3, ModItems.CORAL_CHUNK.get())
        .setFirstRow(4, Items.TUBE_CORAL_BLOCK)
        .setFirstRow(5, ModItems.CORAL_CHUNK.get())
        .setSecondRow(3, Items.BRAIN_CORAL_BLOCK)
        .setSecondRow(4, Items.BUBBLE_CORAL_BLOCK)
        .setSecondRow(5, Items.FIRE_CORAL_BLOCK)
        .setThirdRow(4, Items.HORN_CORAL_BLOCK)
        .addValueBonuses(Items.BUBBLE_CORAL_BLOCK, Map.of(
            InitAttribute.HEALTH, 2.0,
            InitAttribute.NERVES, 1.0
        ));

    // 斯库拉胸腔
    public static final ChestCavityType SCYLLA = register("scylla")
        .copyWith(ChestCavityTypeManager.SALTWATER)
        .setFirstRow(1, CataclysmOrgans.STORM_RIB.get())
        .setFirstRow(4, CataclysmOrgans.TIDAL_LANTERN.get())
        .setFirstRow(7, CataclysmOrgans.STORM_RIB.get())
        .setSecondRow(1, CataclysmOrgans.STORM_RIB.get())
        .setSecondRow(4, CataclysmOrgans.STORM_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.STORM_RIB.get());

    // 末影守卫
    public static final ChestCavityType ENDER_GUARDIAN = register("ender_guardian")
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
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 末影傀儡
    public static final ChestCavityType ENDER_GOLEM = register("ender_golem")
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

    // 魂尸胸腔
    public static final ChestCavityType DRAUGR = register("draugr")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, CataclysmOrgans.DRAUGR_RIB.get())
        .setFirstRow(7, CataclysmOrgans.DRAUGR_RIB.get())
        .setSecondRow(1, CataclysmOrgans.DRAUGR_RIB.get())
        .setSecondRow(4, CataclysmOrgans.DRAUGR_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.DRAUGR_RIB.get())
        .addValueBonuses(CataclysmOrgans.DRAUGR_SPINE.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0,
            InitAttribute.WATER_BREATH, 1.0
        ));

    // 咒翼灵骸胸腔
    public static final ChestCavityType MALEDICTUS = register("maledictus")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setFirstRow(3, CataclysmOrgans.PHANTOM_SHARD.get())
        .setFirstRow(4, CataclysmOrgans.PHANTOM_HEART.get())
        .setFirstRow(7, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setSecondRow(1, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setSecondRow(4, CataclysmOrgans.MALEDICTUS_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.MALEDICTUS_RIB.get())
        .setThirdRow(4, CataclysmOrgans.SEALING_STONE_SLAB.get())
        .addValueBonuses(CataclysmOrgans.MALEDICTUS_SPINE.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 先驱者胸腔
    public static final ChestCavityType HARBINGER = register("harbinger")
        .setFirstRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setFirstRow(4, CataclysmOrgans.MECHANICAL_STAR.get())
        .setFirstRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(3, CataclysmOrgans.TACTICAL_DISK.get())
        .setSecondRow(4, CataclysmOrgans.COMPUTE_CHIP.get())
        .setSecondRow(5, CataclysmOrgans.TACTICAL_DISK.get())
        .setSecondRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(3, CataclysmOrgans.POWER_CELL.get())
        .setThirdRow(4, CataclysmOrgans.DEATH_LENS.get())
        .setThirdRow(5, CataclysmOrgans.POWER_CELL.get())
        .setThirdRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .addValueBonuses(CataclysmOrgans.MECHANICAL_STAR.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 徘徊者胸腔
    public static final ChestCavityType PROWLER = register("prowler")
        .setFirstRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setFirstRow(4, CataclysmOrgans.TACTICAL_DISK.get())
        .setFirstRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(4, CataclysmOrgans.COMPUTE_CHIP.get())
        .setSecondRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(1, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(3, CataclysmOrgans.POWER_CELL.get())
        .setThirdRow(4, CataclysmOrgans.DEATH_LENS.get())
        .setThirdRow(5, CataclysmOrgans.POWER_CELL.get())
        .setThirdRow(7, CataclysmOrgans.REINFORCED_FRAME.get())
        .addValueBonuses(CataclysmOrgans.COMPUTE_CHIP.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 观测者胸腔
    public static final ChestCavityType WATCHER = register("watcher")
        .setFirstRow(4, CataclysmOrgans.TACTICAL_DISK.get())
        .setSecondRow(3, CataclysmOrgans.REINFORCED_FRAME.get())
        .setSecondRow(4, CataclysmOrgans.COMPUTE_CHIP.get())
        .setSecondRow(5, CataclysmOrgans.REINFORCED_FRAME.get())
        .setThirdRow(4, CataclysmOrgans.POWER_CELL.get())
        .addValueBonuses(CataclysmOrgans.COMPUTE_CHIP.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 炽燃遗魂胸腔
    public static final ChestCavityType IGNITED_REVENANT = register("ignited_revenant")
        .copyWith(ChestCavityTypeManager.BLAZE)
        .setFirstRow(0, ModItems.BURNING_ASHES.get())
        .setFirstRow(7, ModItems.BURNING_ASHES.get())
        .setFirstRow(8, ModItems.BURNING_ASHES.get())
        .setSecondRow(0, ModItems.BURNING_ASHES.get())
        .setSecondRow(1, ModItems.BURNING_ASHES.get())
        .setSecondRow(8, ModItems.BURNING_ASHES.get())
        .setThirdRow(1, ModItems.BURNING_ASHES.get())
        .setThirdRow(7, ModItems.BURNING_ASHES.get())
        .setThirdRow(8, ModItems.BURNING_ASHES.get());

    // 炽燃狂魂胸腔
    public static final ChestCavityType IGNITED_BERSERKER = register("ignited_berserker")
        .copyWith(ChestCavityTypeManager.BLAZE)
        .setFirstRow(0, ModItems.DYING_EMBER.get())
        .setFirstRow(7, ModItems.DYING_EMBER.get())
        .setFirstRow(8, ModItems.DYING_EMBER.get())
        .setSecondRow(0, ModItems.DYING_EMBER.get())
        .setSecondRow(1, ModItems.DYING_EMBER.get())
        .setSecondRow(8, ModItems.DYING_EMBER.get())
        .setThirdRow(1, ModItems.DYING_EMBER.get())
        .setThirdRow(7, ModItems.DYING_EMBER.get())
        .setThirdRow(8, ModItems.DYING_EMBER.get());

    // 灾变两栖胸腔（小海胆、章鱼共生体）
    public static final ChestCavityType CATACLYSM_AMPHIBIOUS = register("cataclysm_amphibious")
        .copyWith(ChestCavityTypeManager.AQUATIC)
        .setNeedBreath(true)
        .addValueBonuses(InitItem.GILL.get(), Map.of(
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 遗魂胸腔（远古遗魂、现世遗魂）
    public static final ChestCavityType REMNANT = register("remnant")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, CataclysmOrgans.REMNANT_RIB.get())
        .setFirstRow(4, CataclysmOrgans.SAND_GLAZE_HEART.get())
        .setFirstRow(7, CataclysmOrgans.REMNANT_RIB.get())
        .setSecondRow(1, CataclysmOrgans.REMNANT_RIB.get())
        .setSecondRow(4, CataclysmOrgans.REMNANT_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.REMNANT_RIB.get())
        .addValueBonuses(CataclysmOrgans.REMNANT_SPINE.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0,
            InitAttribute.WATER_BREATH, 1.0
        ));

    // 骸龙胸腔（瓦吉特、骸龙、骸龙斗士）
    public static final ChestCavityType KOBOLETON = register("koboleton")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, CataclysmOrgans.REMNANT_RIB.get())
        .setFirstRow(7, CataclysmOrgans.REMNANT_RIB.get())
        .setSecondRow(1, CataclysmOrgans.REMNANT_RIB.get())
        .setSecondRow(4, CataclysmOrgans.REMNANT_SPINE.get())
        .setSecondRow(7, CataclysmOrgans.REMNANT_RIB.get())
        .addValueBonuses(CataclysmOrgans.REMNANT_SPINE.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0,
            InitAttribute.WATER_BREATH, 1.0
        ));

    /**
     * 注册灾变 Mod 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // 利维坦
        event.registerEntity(ModEntities.THE_LEVIATHAN.get(), LEVIATHAN);
        // 幼年利维坦
        event.registerEntity(ModEntities.THE_BABY_LEVIATHAN.get(), LEVIATHAN);
        // 紫水晶巨蟹
        event.registerEntity(ModEntities.AMETHYST_CRAB.get(), ChestCavityTypeManager.ANIMAL);
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
        event.registerEntity(ModEntities.CORAL_GOLEM.get(), CORAL_GOLEM);
        // 珊瑚巨像
        event.registerEntity(ModEntities.CORALSSUS.get(), CORALSSUS);

        // 下界合金巨兽
        event.registerEntity(ModEntities.NETHERITE_MONSTROSITY.get(), NETHERITE_MONSTROSITY);
        // 下界合金幼兽
        event.registerEntity(ModEntities.NETHERITE_MINISTROSITY.get(), NETHERITE_MINISTROSITY);

        // 焰魔
        event.registerEntity(ModEntities.IGNIS.get(), IGNIS);
        // 炽燃遗魂
        event.registerEntity(ModEntities.IGNITED_REVENANT.get(), IGNITED_REVENANT);
        // 炽燃狂魂
        event.registerEntity(ModEntities.IGNITED_BERSERKER.get(), IGNITED_BERSERKER);

        // 斯库拉
        event.registerEntity(ModEntities.SCYLLA.get(), SCYLLA);
        // 沧溟巡守
        event.registerEntity(ModEntities.HIPPOCAMTUS.get(), ChestCavityTypeManager.SALTWATER);
        // 水母莉亚
        event.registerEntity(ModEntities.CINDARIA.get(), ChestCavityTypeManager.SALTWATER);
        // 巨钳守卫
        event.registerEntity(ModEntities.CLAWDIAN.get(), ChestCavityTypeManager.SALTWATER);
        // 小海胆
        event.registerEntity(ModEntities.URCHINKIN.get(), CATACLYSM_AMPHIBIOUS);
        // 章鱼共生体
        event.registerEntity(ModEntities.SYMBIOCTO.get(), CATACLYSM_AMPHIBIOUS);
        // 溺尸宿主
        event.registerEntity(ModEntities.DROWNED_HOST.get(), ChestCavityTypeManager.UNDEAD);

        // 末影守卫
        event.registerEntity(ModEntities.ENDER_GUARDIAN.get(), ENDER_GUARDIAN);
        // 末影傀儡
        event.registerEntity(ModEntities.ENDER_GOLEM.get(), ENDER_GOLEM);
        // 末影甲虫
        event.registerEntity(ModEntities.ENDERMAPTERA.get(), ChestCavityTypeManager.ARTHROPOD);

        // 咒翼灵骸
        event.registerEntity(ModEntities.MALEDICTUS.get(), MALEDICTUS);
        // 冥行武弁
        event.registerEntity(ModEntities.APTRGANGR.get(), DRAUGR);
        // 再行魂尸
        event.registerEntity(ModEntities.DRAUGR.get(), DRAUGR);
        // 皇家魂尸
        event.registerEntity(ModEntities.ROYAL_DRAUGR.get(), DRAUGR);
        // 精英魂尸
        event.registerEntity(ModEntities.ELITE_DRAUGR.get(), DRAUGR);

        // 先驱者
        event.registerEntity(ModEntities.THE_HARBINGER.get(), HARBINGER);
        // 观测者
        event.registerEntity(ModEntities.THE_WATCHER.get(), WATCHER);
        // 徘徊者
        event.registerEntity(ModEntities.THE_PROWLER.get(), PROWLER);

        // 远古遗魂
        event.registerEntity(ModEntities.ANCIENT_REMNANT.get(), REMNANT);
        // 现世遗魂
        event.registerEntity(ModEntities.MODERN_REMNANT.get(), REMNANT);
        // 骸龙
        event.registerEntity(ModEntities.KOBOLETON.get(), KOBOLETON);
        // 骸龙斗士
        event.registerEntity(ModEntities.KOBOLEDIATOR.get(), KOBOLETON);
        // 瓦吉特
        event.registerEntity(ModEntities.WADJET.get(), KOBOLETON);
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(ResourceLocation.fromNamespaceAndPath("cataclysm", path));
    }
}
