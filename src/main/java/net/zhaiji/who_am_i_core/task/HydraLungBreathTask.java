package net.zhaiji.who_am_i_core.task;

import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.who_am_i_core.entity.HydraVenomBreathProjectile;

/**
 * 九头蛇肺毒物吐息任务
 * <p>
 * 消耗玩家身上的中毒效果，释放九头蛇毒物吐息
 * 吐息持续时间 = √(中毒时长) tick
 * 每4 tick造成一次伤害，单次伤害 = 中毒等级 + 1
 * 受影响的敌人施加原始中毒效果（时长 = 原始时长，等级 = 原始等级）
 * </p>
 */
public class HydraLungBreathTask implements IChestCavityTask {
    private final int poisonAmplifier;
    private final int poisonDuration;
    private final int breathDurationTicks;
    private int tickTimer;
    private HydraVenomBreathProjectile projectile;

    public HydraLungBreathTask(int poisonAmplifier, int poisonDuration) {
        this.poisonAmplifier = poisonAmplifier;
        this.poisonDuration = poisonDuration;
        this.breathDurationTicks = (int) Math.sqrt(poisonDuration);
        this.tickTimer = 0;
    }

    @Override
    public void onAdded(LivingEntity entity) {
        Level level = entity.level();
        if (level.isClientSide()) return;
        projectile = new HydraVenomBreathProjectile(level, entity, poisonDuration, poisonAmplifier);
        projectile.setPos(entity.position().add(0, entity.getEyeHeight() * 0.7, 0));
        level.addFreshEntity(projectile);
        level.playSound(
            null,
            entity.getOnPos(),
            SoundRegistry.POISON_BREATH_LOOP.get(),
            SoundSource.PLAYERS,
            2.0f,
            0.9f + level.random.nextFloat() * 0.2f
        );
    }

    @Override
    public void tick(LivingEntity entity) {
        Level level = entity.level();

        if (!level.isClientSide()) {
            tickTimer++;

            if (tickTimer % 4 == 0 && projectile != null && !projectile.isRemoved()) {
                projectile.setDealDamageActive();
            }

            if (tickTimer % 10 == 0 && projectile != null && !projectile.isRemoved()) {
                level.playSound(
                    null,
                    entity.getOnPos(),
                    SoundRegistry.POISON_BREATH_LOOP.get(),
                    SoundSource.PLAYERS,
                    2.0f,
                    0.9f + level.random.nextFloat() * 0.2f
                );
            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity) {
        if (projectile != null && !projectile.isRemoved()) {
            projectile.discard();
        }
        projectile = null;
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        return tickTimer >= breathDurationTicks || !entity.isAlive() || projectile == null || projectile.isRemoved();
    }
}
