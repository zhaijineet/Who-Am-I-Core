package net.zhaiji.who_am_i_core.manager;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

public class MowziesMobChestCavityTypeManager {
    // 衰老胸腔
    public static final ChestCavityType AGED = register("aged")
        .setFirstRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setFirstRow(1, MowziesMobOrgans.AGED_RIB.get())
        .setFirstRow(2, MowziesMobOrgans.AGED_APPENDIX.get())
        .setFirstRow(3, MowziesMobOrgans.AGED_LUNG.get())
        .setFirstRow(4, MowziesMobOrgans.AGED_HEART.get())
        .setFirstRow(5, MowziesMobOrgans.AGED_LUNG.get())
        .setFirstRow(7, MowziesMobOrgans.AGED_RIB.get())
        .setFirstRow(8, MowziesMobOrgans.AGED_MUSCLE.get())
        .setSecondRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setSecondRow(1, MowziesMobOrgans.AGED_RIB.get())
        .setSecondRow(2, MowziesMobOrgans.AGED_SPLEEN.get())
        .setSecondRow(3, MowziesMobOrgans.AGED_KIDNEY.get())
        .setSecondRow(4, MowziesMobOrgans.AGED_SPINE.get())
        .setSecondRow(5, MowziesMobOrgans.AGED_KIDNEY.get())
        .setSecondRow(6, MowziesMobOrgans.AGED_LIVER.get())
        .setSecondRow(7, MowziesMobOrgans.AGED_RIB.get())
        .setSecondRow(8, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(1, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(2, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(3, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(4, MowziesMobOrgans.AGED_STOMACH.get())
        .setThirdRow(5, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(6, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(7, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(8, MowziesMobOrgans.AGED_MUSCLE.get());

    // 钢铁守护者
    public static final ChestCavityType WROUGHTNAUT = register("wroughtnaut")
        .copyWith(ChestCavityTypeManager.IRON_GOLEM)
        .setFirstRow(4, MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());

    // 太阳鸟
    public static final ChestCavityType UMVUTHI = register("umvuthi")
        .copyWith(AGED)
        .setFirstRow(2, MowziesMobOrgans.CONTROL_ROD.get())
        .setFirstRow(4, MowziesMobOrgans.CHEST_NOVA.get());

    // 泥峭胸腔
    public static final ChestCavityType BLUFF = register("bluff")
        .copyWith(AGED)
        .setFirstRow(2, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        .setFirstRow(4, MowziesMobOrgans.BLUFF_CORE.get())
        .setThirdRow(4, MowziesMobOrgans.BLUFF_TABLET.get());

    /**
     *注册 Mowzie's Mobs 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // TODO 巨噬叶
        event.registerEntity(EntityHandler.FOLIAATH.get(), AGED);
        // TODO 巨噬叶幼苗
        event.registerEntity(EntityHandler.BABY_FOLIAATH.get(), AGED);
        // 霜冻巨兽
        event.registerEntity(EntityHandler.FROSTMAW.get(), ChestCavityTypeManager.ANIMAL);
        // 荧光浮灯
        event.registerEntity(EntityHandler.LANTERN.get(), ChestCavityTypeManager.SMALL_ANIMAL);
        // 岩壳居蟹
        event.registerEntity(EntityHandler.GROTTOL.get(), ChestCavityTypeManager.SMALL_ANIMAL);
        // 飞蛇
        event.registerEntity(EntityHandler.NAGA.get(), ChestCavityTypeManager.CARNIVORE);
        // 雕刻家-通臂大师
        event.registerEntity(EntityHandler.SCULPTOR.get(), AGED);

        // 乌姆塔纳猛禽
        event.registerEntity(EntityHandler.UMVUTHANA_RAPTOR.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳食人鹤
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_MINION.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳猛禽追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳食人鹤追随者
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), ChestCavityTypeManager.ANIMAL);

        // TODO 钢铁守护者
        event.registerEntity(EntityHandler.WROUGHTNAUT.get(), WROUGHTNAUT);
        // TODO 太阳鸟-乌姆武提
        event.registerEntity(EntityHandler.UMVUTHI.get(), UMVUTHI);
        // TODO 泥峭人
        event.registerEntity(EntityHandler.BLUFF.get(), BLUFF);
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(ResourceLocation.fromNamespaceAndPath("mowziesmobs", path));
    }
}
