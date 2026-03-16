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
 * 吐息持续时间 = log10(中毒时长) 秒（转换为tick）
 * 每4 tick造成一次伤害
 * 单次伤害 = 中毒等级 + 1
 * 受影响的敌人施加原始中毒效果（时长 = 原始时长，等级 = 原始等级）
 * </p>
 */
public class HydraLungBreathTask implements IChestCavityTask {
    /**
     * 原始中毒等级（施加给敌人）
     */
    private final int poisonAmplifier;
    /**
     * 原始中毒时长（施加给敌人）
     */
    private final int poisonDuration;
    /**
     * 吐息持续时长（ticks）
     */
    private final int breathDurationTicks;
    /**
     * 当前tick计数器
     */
    private int tickTimer;
    /**
     * 毒物吐息投射物
     */
    private HydraVenomBreathProjectile projectile;
    /**
     * 构造函数
     *
     * @param poisonAmplifier 原始中毒等级
     * @param poisonDuration  原始中毒时长
     */
    public HydraLungBreathTask(int poisonAmplifier, int poisonDuration) {
        this.poisonAmplifier = poisonAmplifier;
        this.poisonDuration = poisonDuration;
        // 吐息持续时长 = log10(中毒时长) tick
        this.breathDurationTicks = (int) (Math.log10(poisonDuration));
        this.tickTimer = 0;
    }

    @Override
    public void onAdded(LivingEntity entity) {
        Level level = entity.level();
        if (level.isClientSide()) return;
        // 创建毒物吐息投射物
        projectile = new HydraVenomBreathProjectile(level, entity, poisonDuration, poisonAmplifier);

        // 设置位置（在玩家前方）
        projectile.setPos(entity.position().add(0, entity.getEyeHeight() * 0.7, 0));

        // 添加到世界
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
        // 清理投射物
        if (projectile != null && !projectile.isRemoved()) {
            projectile.discard();
        }
        projectile = null;
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        // 任务持续时间结束时移除
        return tickTimer >= breathDurationTicks;
    }
}
