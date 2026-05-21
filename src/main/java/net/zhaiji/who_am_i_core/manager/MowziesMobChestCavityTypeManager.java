package net.zhaiji.who_am_i_core.manager;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

public class MowziesMobChestCavityTypeManager {
    // ==================== 衰老胸腔 ====================
    // 衰老系通用模板，属性较弱但结构完整
    public static final ChestCavityType AGED = ChestCavityTypeManager.register("aged")
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

    // ==================== 铁甲巨兽胸腔 ====================
    // 机械巨兽，使用铁傀儡底座承载护心镜
    public static final ChestCavityType WROUGHTNAUT = ChestCavityTypeManager.register("wroughtnaut")
        .copyWith(ChestCavityTypeManager.IRON_GOLEM)
        .setFirstRow(4, MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());

    // ==================== 乌姆塔纳祭司胸腔 ====================
    // 胸中新星(心) / 制御棒(阑尾)，其余衰老器官
    public static final ChestCavityType UMVUTHI = ChestCavityTypeManager.register("umvuthi")
        .copyWith(AGED)
        .setFirstRow(2, MowziesMobOrgans.CONTROL_ROD.get())
        .setFirstRow(4, MowziesMobOrgans.CHEST_NOVA.get());

    // ==================== 泥峭胸腔 ====================
    // 禅心(心) / 泥峭铭文板(胃) / 活性泥峭棒(阑尾) / 泥峭核心(特殊位)，其余衰老器官
    public static final ChestCavityType BLUFF = ChestCavityTypeManager.register("bluff")
        .copyWith(AGED)
        .setFirstRow(2, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        .setFirstRow(4, MowziesMobOrgans.ZEN_HEART.get())
        .setFirstRow(6, MowziesMobOrgans.BLUFF_CORE.get())
        .setThirdRow(4, MowziesMobOrgans.BLUFF_TABLET.get());

    /**
     *注册 Mowzie's Mobs 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // --- 衰老系（通用） ---
        // 巨噬叶
        event.registerEntity(EntityHandler.FOLIAATH.get(), AGED);
        // 巨噬叶幼苗
        event.registerEntity(EntityHandler.BABY_FOLIAATH.get(), AGED);
        // 霜冻巨兽
        event.registerEntity(EntityHandler.FROSTMAW.get(), AGED);
        // 荧光浮灯
        event.registerEntity(EntityHandler.LANTERN.get(), AGED);
        // 岩壳居蟹
        event.registerEntity(EntityHandler.GROTTOL.get(), AGED);
        // 飞蛇
        event.registerEntity(EntityHandler.NAGA.get(), AGED);
        // 雕刻家-通臂大师
        event.registerEntity(EntityHandler.SCULPTOR.get(), AGED);

        // --- 乌姆塔纳随从 ---
        // 乌姆塔纳猛禽
        event.registerEntity(EntityHandler.UMVUTHANA_RAPTOR.get(), AGED);
        // 乌姆塔纳食人鹤
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE.get(), AGED);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_MINION.get(), AGED);
        // 乌姆塔纳猛禽追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), AGED);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), AGED);
        // 乌姆塔纳食人鹤追随者
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), AGED);

        // --- 有专属器官的特殊实体 ---
        // 钢铁守护者
        event.registerEntity(EntityHandler.WROUGHTNAUT.get(), WROUGHTNAUT);
        // 太阳鸟-乌姆武提
        event.registerEntity(EntityHandler.UMVUTHI.get(), UMVUTHI);
        // 泥峭人
        event.registerEntity(EntityHandler.BLUFF.get(), BLUFF);
    }
}
