package net.zhaiji.who_am_i_core.manager;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

public class IceAndFireChestCavityTypeManager {
    // 火龙胸腔
    public static final ChestCavityType FIRE_DRAGON = ChestCavityTypeManager.register("fire_dragon")
        .setFirstRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.FIRE_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.FIRE_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.FIRE_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.FIRE_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.FIRE_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.FIRE_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.FIRE_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 冰龙胸腔
    public static final ChestCavityType ICE_DRAGON = ChestCavityTypeManager.register("ice_dragon")
        .setFirstRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.ICE_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.ICE_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.ICE_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.ICE_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.ICE_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.ICE_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.ICE_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.ICE_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.ICE_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.ICE_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 电龙胸腔
    public static final ChestCavityType LIGHTNING_DRAGON = ChestCavityTypeManager.register("lightning_dragon")
        .setFirstRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 九头蛇胸腔（缺少 kidney, liver, appendix）
    public static final ChestCavityType HYDRA = ChestCavityTypeManager.register("hydra")
        .setFirstRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.HYDRA_RIB.get())
        .setFirstRow(3, IceAndFireOrgans.HYDRA_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.HYDRA_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.HYDRA_LUNG.get())
        .setFirstRow(7, IceAndFireOrgans.HYDRA_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.HYDRA_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.HYDRA_SPLEEN.get())
        .setSecondRow(4, IceAndFireOrgans.HYDRA_SPINE.get())
        .setSecondRow(7, IceAndFireOrgans.HYDRA_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.HYDRA_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get());

    // 判断是否可以开启龙胸腔（活体龙和尸体可开胸，骨架不可开胸）
    private static boolean canOpenDragonRibcage(Player player, LivingEntity entity) {
        if (entity instanceof DragonBaseEntity dragon) {
            if (!dragon.isModelDead()) return true;
            // 骨架状态（isSkeletal()=true）不可开胸
            return !dragon.isSkeletal();
        }
        return false;
    }
}
