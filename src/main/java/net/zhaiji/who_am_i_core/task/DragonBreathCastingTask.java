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
 */
public class DragonBreathCastingTask implements IChestCavityTask {
    private final BreathType breathType;
    private final ChestCavityData data;
    private final SoundEvent soundEvent;
    private final float damage;
    private int durationTicks = 100;
    private AbstractConeProjectile projectile;

    /**
     * 构造函数 - 用于创建施法任务
     *
     * @param data       胸腔数据
     * @param breathType 吐息类型
     * @param spellLevel 法术等级
     */
    public DragonBreathCastingTask(ChestCavityData data, BreathType breathType, int spellLevel) {
        this.data = data;
        this.breathType = breathType;
        damage = getDamage(breathType, spellLevel, data.getOwner());
        soundEvent = switch (breathType) {
            case FIRE_BREATH -> SoundRegistry.FIRE_BREATH_LOOP.get();
            case ICE_BREATH -> SoundRegistry.CONE_OF_COLD_LOOP.get();
            case LIGHTNING_BREATH -> SoundRegistry.ELECTROCUTE_LOOP.get();
        };
    }

    /**
     * 获取指定吐息类型在给定法术等级和施法者下的实际伤害值
     */
    public static float getDamage(BreathType breathType, int spellLevel, LivingEntity caster) {
        return switch (breathType) {
            case FIRE_BREATH -> ((FireBreathSpell) SpellRegistry.FIRE_BREATH_SPELL.get()).getDamage(spellLevel, caster);
            case ICE_BREATH -> ((ConeOfColdSpell) SpellRegistry.CONE_OF_COLD_SPELL.get()).getDamage(spellLevel, caster);
            case LIGHTNING_BREATH -> ((ElectrocuteSpell) SpellRegistry.ELECTROCUTE_SPELL.get()).getDamage(spellLevel, caster);
        };
    }

    @Override
    public void onAdded(LivingEntity entity) {
        Level level = entity.level();
        projectile = createProjectile(level, entity);
        projectile.setDamage(damage);
        projectile.setPos(entity.position().add(0, entity.getEyeHeight() * 0.7, 0));
        level.addFreshEntity(projectile);
    }

    @Override
    public void tick(LivingEntity entity) {
        Level level = entity.level();
        // 每 10tick 循环播放声音
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
        // 每tick激活伤害检测
        if (projectile != null && !projectile.isRemoved()) {
            projectile.setDealDamageActive();
        }
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
        // 持续时间结束、实体死亡、投射物失效
        if (durationTicks <= 0 || !entity.isAlive() || projectile == null || projectile.isRemoved()) return true;
        // 器官被移除
        return !switch (breathType) {
            case FIRE_BREATH -> data.hasOrgan(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get());
            case ICE_BREATH -> data.hasOrgan(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get());
            case LIGHTNING_BREATH -> data.hasOrgan(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get());
        };
    }

    /**
     * 创建投射物
     *
     * @param level  世界
     * @param entity 施法实体
     * @return 投射物实例
     */
    private AbstractConeProjectile createProjectile(Level level, LivingEntity entity) {
        return switch (breathType) {
            case FIRE_BREATH -> new FireBreathProjectile(level, entity);
            case ICE_BREATH -> new ConeOfColdProjectile(level, entity);
            case LIGHTNING_BREATH -> new ElectrocuteProjectile(level, entity);
        };
    }

    /**
     * 获取吐息类型
     */
    public BreathType getBreathType() {
        return breathType;
    }

    /**
     * 吐息类型枚举
     */
    public enum BreathType {
        FIRE_BREATH,
        ICE_BREATH,
        LIGHTNING_BREATH;
    }
}
