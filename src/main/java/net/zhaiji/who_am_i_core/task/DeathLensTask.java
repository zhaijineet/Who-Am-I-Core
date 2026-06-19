package net.zhaiji.who_am_i_core.task;

import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;

/**
 * 死亡透镜激光跟随任务。
 */
public class DeathLensTask implements IChestCavityTask {
    private Death_Laser_Beam_Entity laser;

    public DeathLensTask(Death_Laser_Beam_Entity laser) {
        this.laser = laser;
    }

    @Override
    public void tick(LivingEntity entity) {
        if (laser != null && !laser.isRemoved()) {
            laser.setPos(entity.getX(), entity.getY() + OrganSkillUtil.effectiveEyeHeight(entity) * 0.69F, entity.getZ());
            laser.setYaw((float) ((entity.yHeadRot + 90) * Math.PI / 180.0D));
            laser.setPitch((float) (-entity.getXRot() * Math.PI / 180.0D));
        }
    }

    @Override
    public void onRemoved(LivingEntity entity) {
        laser = null;
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        // 激光实体失效或施法者死亡 → 结束任务
        return laser == null || laser.isRemoved() || !entity.isAlive();
    }
}
