package net.zhaiji.who_am_i_core.manager;

import dev.xylonity.companions.registry.CompanionsEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;

import java.util.Map;

public class CompanionsChestCavityTypeManager {
    // 教宗胸腔
    public static final ChestCavityType PONTIFF = register("pontiff")
        .setFirstRow(0, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setFirstRow(1, InitItem.RIB.get())
        .setFirstRow(2, CompanionsOrgans.PONTIFF_APPENDIX.get())
        .setFirstRow(3, CompanionsOrgans.PONTIFF_LUNG.get())
        .setFirstRow(4, CompanionsOrgans.PONTIFF_HEART.get())
        .setFirstRow(5, CompanionsOrgans.PONTIFF_LUNG.get())
        .setFirstRow(7, InitItem.RIB.get())
        .setFirstRow(8, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setSecondRow(0, CompanionsOrgans.PONTIFF_MUSCLE.get())
        .setSecondRow(1, InitItem.RIB.get())
        .setSecondRow(2, CompanionsOrgans.PONTIFF_SPLEEN.get())
        .setSecondRow(3, CompanionsOrgans.PONTIFF_KIDNEY.get())
        .setSecondRow(4, InitItem.SPINE.get())
        .setSecondRow(5, CompanionsOrgans.PONTIFF_KIDNEY.get())
        .setSecondRow(6, CompanionsOrgans.PONTIFF_LIVER.get())
        .setSecondRow(7, InitItem.RIB.get())
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

    // 布制胸腔
    public static final ChestCavityType CLOTH = register("cloth")
        .setFirstRow(0, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setFirstRow(1, CompanionsOrgans.CLOTH_RIB.get())
        .setFirstRow(2, CompanionsOrgans.CLOTH_APPENDIX.get())
        .setFirstRow(3, CompanionsOrgans.CLOTH_LUNG.get())
        .setFirstRow(4, CompanionsOrgans.CLOTH_HEART.get())
        .setFirstRow(5, CompanionsOrgans.CLOTH_LUNG.get())
        .setFirstRow(7, CompanionsOrgans.CLOTH_RIB.get())
        .setFirstRow(8, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setSecondRow(0, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setSecondRow(1, CompanionsOrgans.CLOTH_RIB.get())
        .setSecondRow(2, CompanionsOrgans.CLOTH_SPLEEN.get())
        .setSecondRow(3, CompanionsOrgans.CLOTH_KIDNEY.get())
        .setSecondRow(4, CompanionsOrgans.CLOTH_SPINE.get())
        .setSecondRow(5, CompanionsOrgans.CLOTH_KIDNEY.get())
        .setSecondRow(6, CompanionsOrgans.CLOTH_LIVER.get())
        .setSecondRow(7, CompanionsOrgans.CLOTH_RIB.get())
        .setSecondRow(8, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setThirdRow(0, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setThirdRow(1, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setThirdRow(2, CompanionsOrgans.CLOTH_INTESTINE.get())
        .setThirdRow(3, CompanionsOrgans.CLOTH_INTESTINE.get())
        .setThirdRow(4, CompanionsOrgans.CLOTH_STOMACH.get())
        .setThirdRow(5, CompanionsOrgans.CLOTH_INTESTINE.get())
        .setThirdRow(6, CompanionsOrgans.CLOTH_INTESTINE.get())
        .setThirdRow(7, CompanionsOrgans.CLOTH_MUSCLE.get())
        .setThirdRow(8, CompanionsOrgans.CLOTH_MUSCLE.get());

    // 蛋糕胸腔
    public static final ChestCavityType CAKE = register("cake")
        .setFirstRow(0, Items.BREAD)
        .setFirstRow(1, Items.BREAD)
        .setFirstRow(2, Items.BREAD)
        .setFirstRow(3, CompanionsOrgans.CAKE_LUNG.get())
        .setFirstRow(4, CompanionsOrgans.CAKE_HEART.get())
        .setFirstRow(5, CompanionsOrgans.CAKE_LUNG.get())
        .setFirstRow(6, Items.BREAD)
        .setFirstRow(7, Items.BREAD)
        .setFirstRow(8, Items.BREAD)
        .setSecondRow(0, Items.BREAD)
        .setSecondRow(1, Items.BREAD)
        .setSecondRow(2, Items.BREAD)
        .setSecondRow(3, Items.BREAD)
        .setSecondRow(4, Items.BREAD)
        .setSecondRow(5, Items.BREAD)
        .setSecondRow(6, CompanionsOrgans.CAKE_LIVER.get())
        .setSecondRow(7, Items.BREAD)
        .setSecondRow(8, Items.BREAD)
        .setThirdRow(0, Items.BREAD)
        .setThirdRow(1, Items.BREAD)
        .setThirdRow(2, Items.BREAD)
        .setThirdRow(3, Items.BREAD)
        .setThirdRow(4, CompanionsOrgans.CAKE_STOMACH.get())
        .setThirdRow(5, Items.BREAD)
        .setThirdRow(6, Items.BREAD)
        .setThirdRow(7, Items.BREAD)
        .setThirdRow(8, Items.BREAD)
        .addValueBonuses(CompanionsOrgans.CAKE_HEART.get(), Map.of(
            InitAttribute.NERVES, 1.0
        ));

    /**
     * 注册 Companions 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // --- 有专属器官的实体 ---
        // 神圣教宗
        event.registerEntity(CompanionsEntities.SACRED_PONTIFF.get(), PONTIFF);
        // 泰迪
        event.registerEntity(CompanionsEntities.TEDDY.get(), CLOTH);

        // 科尼利尔斯·呱克
        event.registerEntity(CompanionsEntities.CORNELIUS.get(), ChestCavityTypeManager.FROG);
        // 蚁狮
        event.registerEntity(CompanionsEntities.ANTLION.get(), ChestCavityTypeManager.ARTHROPOD);
        // TODO 电纳魔
        event.registerEntity(CompanionsEntities.DINAMO.get(), ChestCavityTypeManager.IRON_GOLEM);
        // TODO 爪牙
        event.registerEntity(CompanionsEntities.MINION.get(), ChestCavityTypeManager.HUMAN);
        // 金色悦灵
        event.registerEntity(CompanionsEntities.GOLDEN_ALLAY.get(), WAICChestCavityTypeManager.FANTASTICAL);
        // 灵魂魔导士
        event.registerEntity(CompanionsEntities.SOUL_MAGE.get(), WAICChestCavityTypeManager.FANTASTICAL);
        // 可颂龙
        event.registerEntity(CompanionsEntities.CROISSANT_DRAGON.get(), CAKE);
        // TODO 木偶手套
        event.registerEntity(CompanionsEntities.PUPPET_GLOVE.get(), CLOTH);
        // TODO 木偶
        event.registerEntity(CompanionsEntities.PUPPET.get(), WAICChestCavityTypeManager.WOODEN);
        // TODO 暗影利剑
        event.registerEntity(CompanionsEntities.SHADE_SWORD.get(), ChestCavityTypeManager.ARMOR_STAND);
        // TODO 暗影魔颚
        event.registerEntity(CompanionsEntities.SHADE_MAW.get(), ChestCavityTypeManager.ARMOR_STAND);
        // TODO 猕安卡
        event.registerEntity(CompanionsEntities.MANKH.get(), ChestCavityTypeManager.HUMAN);
        // TODO 斗篷客
        event.registerEntity(CompanionsEntities.CLOAK.get(), CLOTH);

        // --- 召唤生物 ---
        // TODO 活体蜡烛
        event.registerEntity(CompanionsEntities.LIVING_CANDLE.get(), ChestCavityTypeManager.BLAZE);
        // 焰火蟾蜍
        event.registerEntity(CompanionsEntities.FIREWORK_TOAD.get(), ChestCavityTypeManager.FROG);
        // 泡泡蛙
        event.registerEntity(CompanionsEntities.BUBBLE_FROG.get(), ChestCavityTypeManager.FROG);
        // TODO 灰烬蝌蚪
        event.registerEntity(CompanionsEntities.EMBER_POLE.get(), ChestCavityTypeManager.BLAZE);
        // 下界牛蛙
        event.registerEntity(CompanionsEntities.NETHER_BULLFROG.get(), ChestCavityTypeManager.FROG);
        // 末影蛙
        event.registerEntity(CompanionsEntities.ENDER_FROG.get(), ChestCavityTypeManager.FROG);


        // TODO 敌意小鬼
        event.registerEntity(CompanionsEntities.HOSTILE_IMP.get(), ChestCavityTypeManager.BLAZE);
        // TODO 灾厄电磁傀儡
        event.registerEntity(CompanionsEntities.ILLAGER_GOLEM.get(), ChestCavityTypeManager.IRON_GOLEM);
        // TODO 魔法手套
        event.registerEntity(CompanionsEntities.HOSTILE_PUPPET_GLOVE.get(), CLOTH);
        // 野生蚁狮
        event.registerEntity(CompanionsEntities.WILD_ANTLION.get(), ChestCavityTypeManager.ARTHROPOD);
        // TODO 损坏的电纳魔
        event.registerEntity(CompanionsEntities.BROKEN_DINAMO.get(), ChestCavityTypeManager.IRON_GOLEM);
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(ResourceLocation.fromNamespaceAndPath("companions", path));
    }
}
