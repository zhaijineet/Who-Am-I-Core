package net.zhaiji.who_am_i_core.manager;

import dev.xylonity.companions.registry.CompanionsEntities;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;

public class CompanionsChestCavityTypeManager {
    // ==================== 教宗胸腔 ====================
    // 拟人教宗，直接使用人类底座承载专属器官
    public static final ChestCavityType PONTIFF = ChestCavityTypeManager.register("pontiff")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setFirstRow(0, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setFirstRow(2, CompanionsOrgans.PONTIFF_APPENDIX.get())
        .setFirstRow(3, CompanionsOrgans.PONTIFF_LUNG.get())
        .setFirstRow(4, CompanionsOrgans.PONTIFF_HEART.get())
        .setFirstRow(5, CompanionsOrgans.PONTIFF_LUNG.get())
        .setFirstRow(8, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setSecondRow(0, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setSecondRow(2, CompanionsOrgans.PONTIFF_SPLEEN.get())
        .setSecondRow(3, CompanionsOrgans.PONTIFF_KIDNEY.get())
        .setSecondRow(5, CompanionsOrgans.PONTIFF_KIDNEY.get())
        .setSecondRow(6, CompanionsOrgans.PONTIFF_LIVER.get())
        .setSecondRow(8, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setThirdRow(0, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setThirdRow(1, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setThirdRow(2, CompanionsOrgans.PONTIFF_INTESTINE.get())
        .setThirdRow(3, CompanionsOrgans.PONTIFF_INTESTINE.get())
        .setThirdRow(4, CompanionsOrgans.PONTIFF_STOMACH.get())
        .setThirdRow(5, CompanionsOrgans.PONTIFF_INTESTINE.get())
        .setThirdRow(6, CompanionsOrgans.PONTIFF_INTESTINE.get())
        .setThirdRow(7, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setThirdRow(8, CompanionsOrgans.PONTIFF_MUSCLE.get());

    // ==================== 蛋糕胸腔 ====================
    // 布制/玩偶类胸腔，适合泰迪与类似实体
    public static final ChestCavityType CAKE = ChestCavityTypeManager.register("cake")
        .copyWith(WAICChestCavityTypeManager.CLOTH)
        .setFirstRow(3, CompanionsOrgans.CAKE_LUNG.get())
        .setFirstRow(4, CompanionsOrgans.CAKE_HEART.get())
        .setFirstRow(5, CompanionsOrgans.CAKE_LUNG.get())
        .setSecondRow(6, CompanionsOrgans.CAKE_LIVER.get())
        .setThirdRow(4, CompanionsOrgans.CAKE_STOMACH.get());

    /**
     * 注册 Companions 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // --- 有专属器官的实体 ---
        // 神圣教宗
        event.registerEntity(CompanionsEntities.SACRED_PONTIFF.get(), PONTIFF);
        // 泰迪
        event.registerEntity(CompanionsEntities.TEDDY.get(), CAKE);

        // --- 无专属器官的伴侣生物 ---
        // 科尼利尔斯·呱克
        event.registerEntity(CompanionsEntities.CORNELIUS.get(), ChestCavityTypeManager.FROG);
        // 蚁狮
        event.registerEntity(CompanionsEntities.ANTLION.get(), ChestCavityTypeManager.ARTHROPOD);
        // 电纳魔
        event.registerEntity(CompanionsEntities.DINAMO.get(), ChestCavityTypeManager.IRON_GOLEM);
        // 爪牙
        event.registerEntity(CompanionsEntities.MINION.get(), ChestCavityTypeManager.HUMAN);
        // 金色悦灵
        event.registerEntity(CompanionsEntities.GOLDEN_ALLAY.get(), WAICChestCavityTypeManager.FANTASTICAL);
        // 灵魂魔导士
        event.registerEntity(CompanionsEntities.SOUL_MAGE.get(), ChestCavityTypeManager.HUMAN);
        // 可颂龙
        event.registerEntity(CompanionsEntities.CROISSANT_DRAGON.get(), ChestCavityTypeManager.ENDER_DRAGON);
        // 木偶手套
        event.registerEntity(CompanionsEntities.PUPPET_GLOVE.get(), WAICChestCavityTypeManager.CLOTH);
        // 木偶
        event.registerEntity(CompanionsEntities.PUPPET.get(), WAICChestCavityTypeManager.WOODEN);
        // 暗影利剑
        event.registerEntity(CompanionsEntities.SHADE_SWORD.get(), ChestCavityTypeManager.ARMOR_STAND);
        // 暗影魔颚
        event.registerEntity(CompanionsEntities.SHADE_MAW.get(), ChestCavityTypeManager.ARMOR_STAND);
        // 猕安卡
        event.registerEntity(CompanionsEntities.MANKH.get(), ChestCavityTypeManager.HUMAN);
        // 斗篷客
        event.registerEntity(CompanionsEntities.CLOAK.get(), WAICChestCavityTypeManager.CLOTH);

        // --- 召唤生物 ---
        // 活体蜡烛
        event.registerEntity(CompanionsEntities.LIVING_CANDLE.get(), ChestCavityTypeManager.BLAZE);
        // 焰火蟾蜍
        event.registerEntity(CompanionsEntities.FIREWORK_TOAD.get(), ChestCavityTypeManager.FROG);
        // 泡泡蛙
        event.registerEntity(CompanionsEntities.BUBBLE_FROG.get(), ChestCavityTypeManager.FROG);
        // 灰烬蝌蚪
        event.registerEntity(CompanionsEntities.EMBER_POLE.get(), ChestCavityTypeManager.BLAZE);
        // 下界牛蛙
        event.registerEntity(CompanionsEntities.NETHER_BULLFROG.get(), ChestCavityTypeManager.FROG);
        // 末影蛙
        event.registerEntity(CompanionsEntities.ENDER_FROG.get(), ChestCavityTypeManager.FROG);

        // --- 敌对生物 ---
        // 敌意小鬼
        event.registerEntity(CompanionsEntities.HOSTILE_IMP.get(), ChestCavityTypeManager.BLAZE);
        // 灾厄电磁傀儡
        event.registerEntity(CompanionsEntities.ILLAGER_GOLEM.get(), ChestCavityTypeManager.IRON_GOLEM);
        // 魔法手套
        event.registerEntity(CompanionsEntities.HOSTILE_PUPPET_GLOVE.get(), WAICChestCavityTypeManager.CLOTH);
        // 野生蚁狮
        event.registerEntity(CompanionsEntities.WILD_ANTLION.get(), ChestCavityTypeManager.ARTHROPOD);
        // 损坏的电纳魔
        event.registerEntity(CompanionsEntities.BROKEN_DINAMO.get(), ChestCavityTypeManager.IRON_GOLEM);
    }
}
