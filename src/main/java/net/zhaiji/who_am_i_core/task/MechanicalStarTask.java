package net.zhaiji.who_am_i_core.task;

import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import org.jetbrains.annotations.Nullable;

/**
 * 机械之星追踪导弹连发任务
 * <p>
 * 以固定间隔连续发射追踪导弹，导弹数随机械器官数量动态变化。
 * 不持久化：重载游戏时直接结束（参考 HydraLungBreathTask / DragonBreathCastingTask）。
 * </p>
 */
public class MechanicalStarTask implements IChestCavityTask {
    /**
     * 追踪目标（可为 null，此时沿视线方向发射）
     */
    @Nullable
    private final LivingEntity target;
    /**
     * 每枚导弹的伤害
     */
    private final float damagePerMissile;
    /**
     * 剩余待发射的导弹数
     */
    private int remainingMissiles;
    /**
     * 下次发射倒计时（0 表示本 tick 立即发射）
     */
    private int cooldown;

    /**
     * 构造函数
     *
     * @param missileCount     导弹总数
     * @param damagePerMissile 每枚导弹伤害
     * @param target           追踪目标（可为 null）
     */
    public MechanicalStarTask(int missileCount, float damagePerMissile, @Nullable LivingEntity target) {
        this.remainingMissiles = missileCount;
        this.damagePerMissile = damagePerMissile;
        this.target = target;
        this.cooldown = 0;
    }

    @Override
    public void tick(LivingEntity entity) {
        if (cooldown <= 0 && remainingMissiles > 0) {
            spawnMissile(entity);
            remainingMissiles--;
            cooldown = 10;
        }
        cooldown--;
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        return remainingMissiles <= 0 || !entity.isAlive();
    }

    /**
     * 发射一枚追踪导弹
     */
    private void spawnMissile(LivingEntity entity) {
        Level level = entity.level();

        Vec3 direction;
        if (target != null && target.isAlive() && !target.isRemoved()) {
            Vec3 diff = target.getEyePosition().subtract(entity.getEyePosition());
            // 两实体眼睛位置重合时回退到视线方向，避免零向量 normalize 产生 NaN
            direction = diff.lengthSqr() < 1e-8 ? entity.getLookAngle().normalize() : diff.normalize();
        } else {
            direction = entity.getLookAngle().normalize();
        }

        Wither_Homing_Missile_Entity missile = new Wither_Homing_Missile_Entity(
            entity, direction, level, damagePerMissile, target);
        missile.setPos(
            entity.getX(),
            entity.getY() + OrganSkillUtil.effectiveEyeHeight(entity) * 0.69F,
            entity.getZ()
        );
        level.addFreshEntity(missile);

        level.playSound(
            null,
            entity,
            ModSounds.ROCKET_LAUNCH.get(),
            SoundSource.PLAYERS,
            1.0F,
            1.0F
        );
    }
}
