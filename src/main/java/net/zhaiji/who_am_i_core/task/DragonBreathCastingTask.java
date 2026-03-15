package net.zhaiji.who_am_i_core.task;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import io.redspace.ironsspellbooks.entity.spells.cone_of_cold.ConeOfColdProjectile;
import io.redspace.ironsspellbooks.entity.spells.electrocute.ElectrocuteProjectile;
import io.redspace.ironsspellbooks.entity.spells.fire_breath.FireBreathProjectile;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.fire.FireBreathSpell;
import io.redspace.ironsspellbooks.spells.ice.ConeOfColdSpell;
import io.redspace.ironsspellbooks.spells.lightning.ElectrocuteSpell;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

/**
 * 龙吐息持续性施法任务
 * <p>
 * 管理龙类吐息袋器官的持续性施法，使用 ISNB 的投射物系统
 * 不触发施法动作和音效，不影响移动速度
 * </p>
 */
public class DragonBreathCastingTask implements IChestCavityTask {
    private final BreathType breathType;
    private final int spellLevel;
    private final ChestCavityData data;
    private int durationTicks;
    private AbstractConeProjectile projectile;

    /**
     * 构造函数 - 用于创建施法任务
     *
     * @param data          胸腔数据
     * @param breathType    吐息类型
     * @param spellLevel    法术等级
     * @param durationTicks 持续时间（ticks）
     */
    public DragonBreathCastingTask(ChestCavityData data, BreathType breathType, int spellLevel, int durationTicks) {
        this.data = data;
        this.breathType = breathType;
        this.spellLevel = spellLevel;
        this.durationTicks = durationTicks;
    }

    @Override
    public void onAdded(LivingEntity entity) {
        // 创建并添加投射物实体到世界
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        projectile = createProjectile(breathType, level, entity);
        // 使用 ISNB 的完整伤害计算系统
        // 考虑法术强度属性、法术学派加成等
        float damage = switch (breathType) {
            case FIRE_BREATH -> ((FireBreathSpell) SpellRegistry.FIRE_BREATH_SPELL.get()).getDamage(spellLevel, entity);
            case ICE_BREATH -> ((ConeOfColdSpell) SpellRegistry.CONE_OF_COLD_SPELL.get()).getDamage(spellLevel, entity);
            case LIGHTNING_BREATH -> ((ElectrocuteSpell) SpellRegistry.ELECTROCUTE_SPELL.get()).getDamage(spellLevel, entity);
        };
        projectile.setDamage(damage);

        // 设置位置
        projectile.setPos(entity.position().add(0, entity.getEyeHeight() * 0.7, 0));

        // 添加到世界
        level.addFreshEntity(projectile);
    }

    @Override
    public void tick(LivingEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide()) {
            SoundEvent soundEvent = switch (breathType) {
                case FIRE_BREATH -> SoundRegistry.FIRE_BREATH_LOOP.get();
                case ICE_BREATH -> SoundRegistry.CONE_OF_COLD_LOOP.get();
                case LIGHTNING_BREATH -> SoundRegistry.ELECTROCUTE_LOOP.get();
            };
            // 每 20 ticks（1秒）重复播放声音
            if (durationTicks % 10 == 0 && projectile != null && !projectile.isRemoved()) {
                level.playSound(
                    null,
                    entity.getOnPos(),
                    soundEvent,
                    SoundSource.PLAYERS,
                    2.0f,
                    0.9f + level.random.nextFloat() * 0.2f
                );
            }

            // 每帧激活伤害检测
            if (projectile != null && !projectile.isRemoved()) {
                projectile.setDealDamageActive();
            }
        }

        // 减少持续时间
        durationTicks--;
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
        // 取消条件：
        // 1. 持续时间结束
        if (durationTicks <= 0) {
            return true;
        }

        // 2. 实体死亡
        if (!entity.isAlive()) {
            return true;
        }

        // 3. 投射物失效
        if (projectile != null && projectile.isRemoved()) {
            return true;
        }

        // 4. 器官被移除（检查胸腔中是否还有对应的吐息袋）
        boolean hasBreathSac = switch (breathType) {
            case FIRE_BREATH -> data.hasOrgan(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get());
            case ICE_BREATH -> data.hasOrgan(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get());
            case LIGHTNING_BREATH -> data.hasOrgan(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get());
        };

        return !hasBreathSac;
    }

    /**
     * 创建投射物
     *
     * @param breathType 吐息类型
     * @param level      世界
     * @param entity     施法实体
     * @return 投射物实例
     */
    private AbstractConeProjectile createProjectile(BreathType breathType, Level level, LivingEntity entity) {
        return switch (breathType) {
            case FIRE_BREATH -> new FireBreathProjectile(level, entity);
            case ICE_BREATH -> new ConeOfColdProjectile(level, entity);
            case LIGHTNING_BREATH -> new ElectrocuteProjectile(level, entity);
        };
    }

    /**
     * 吐息类型枚举
     * 包含对应的 ISNB 投射物类和声音事件
     */
    public enum BreathType {
        FIRE_BREATH,
        ICE_BREATH,
        LIGHTNING_BREATH;
    }
}
