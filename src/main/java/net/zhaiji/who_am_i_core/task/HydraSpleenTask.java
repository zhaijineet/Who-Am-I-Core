package net.zhaiji.who_am_i_core.task;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.chestcavitybeyond.api.task.ISerializableTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

/**
 * 九头蛇脾脏任务
 * <p>
 * 唯一效果器官，将中毒效果转化为治疗
 * 当血量低于特定阈值时，每秒消耗中毒效果的时长来治疗自身
 * </p>
 */
public class HydraSpleenTask implements ISerializableTask {
    /**
     * 任务类型标识
     */
    public static final ResourceLocation TYPE = WhoAmICore.of("hydra_spleen");
    /**
     * 胸腔数据引用
     */
    private final ChestCavityData data;
    /**
     * 是否应该被移除
     */
    private boolean shouldRemove = false;

    public HydraSpleenTask(ChestCavityData data) {
        this.data = data;
    }

    /**
     * 反序列化构造函数
     * 用于从 NBT 数据恢复 HydraSpleenTask
     *
     * @param data     胸腔数据
     * @param provider HolderLookup.Provider
     * @param nbt      NBT 数据
     */
    public HydraSpleenTask(ChestCavityData data, HolderLookup.Provider provider, CompoundTag nbt) {
        this.data = data;
    }

    @Override
    public void tick(LivingEntity entity) {
        // 每20 tick（1秒）执行一次
        if (entity.tickCount % 20 != 0) {
            return;
        }

        // 检查胸腔是否还有九头蛇脾脏
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_SPLEEN.get())) {
            shouldRemove = true;
            return;
        }

        // 获取中毒效果
        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison == null || poison.getDuration() <= 0) {
            return;
        }

        // 检查血量阈值
        float healthRatio = entity.getHealth() / entity.getMaxHealth();
        if (healthRatio > 0.5) {
            return; // 血量高于50%时不触发
        }

        // 计算治疗系数
        int healMultiplier;
        if (healthRatio <= 0.1) {
            healMultiplier = 10; // ≤10%: 系数10
        } else if (healthRatio <= 0.2) {
            healMultiplier = 5; // ≤20%: 系数5
        } else {
            healMultiplier = 3; // ≤50%: 系数3
        }

        // 计算治疗量和消耗时长
        int amplifier = poison.getAmplifier() + 1; // 中毒等级（从0开始，所以+1）
        float healAmount = amplifier * healMultiplier;
        int consumeDuration = Math.min((int) Math.ceil(healAmount), poison.getDuration());

        // 执行治疗
        entity.heal(Math.min(healAmount, consumeDuration));

        // 移除旧的中毒效果并添加新的（时长减少）
        int newDuration = poison.getDuration() - consumeDuration;
        entity.removeEffect(MobEffects.POISON);
        if (newDuration > 0) {
            entity.addEffect(new MobEffectInstance(
                    MobEffects.POISON,
                    newDuration,
                    poison.getAmplifier(),
                    poison.isAmbient(),
                    poison.isVisible()
            ));
        }
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        return shouldRemove;
    }

    @Override
    public ResourceLocation getType() {
        return TYPE;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return new CompoundTag();
    }
}
