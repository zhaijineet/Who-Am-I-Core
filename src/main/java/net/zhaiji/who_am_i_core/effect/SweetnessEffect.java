package net.zhaiji.who_am_i_core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.register.WAICEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * 甜蜜效果 — 持续为周围 16 格范围内的生物回复生命值，玩家额外回复饱食度和饱和度
 * <p>
 * 蛋糕肝脏增强：拥有蛋糕肝脏且等级 ≥ 2 时，每秒消耗 1 级甜蜜清除 1 个随机负面效果
 */
public class SweetnessEffect extends MobEffect {
    public SweetnessEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) return true;

        // 甜蜜效果的核心：为周围 16 格范围内的生物回复，排除敌对生物
        List<LivingEntity> nearbyEntities = entity.level().getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(16),
            livingEntity -> !(livingEntity instanceof Enemy)
        );

        for (LivingEntity nearby : nearbyEntities) {
            nearby.heal(1.0F);
            // 玩家额外回复饱食度和饱和度
            if (nearby instanceof Player player) {
                player.getFoodData().eat(1, 0.5F);
            }
        }

        // 蛋糕肝脏增强：等级 ≥ 2（amplifier ≥ 1）时，消耗 1 级清除 1 个随机负面效果
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (data.hasOrgan(CompanionsOrgans.CAKE_LIVER.get()) && amplifier >= 1) {
            // 收集所有负面效果
            List<MobEffectInstance> harmfulEffects = new ArrayList<>();
            for (MobEffectInstance instance : entity.getActiveEffects()) {
                if (!instance.getEffect().value().isBeneficial()) {
                    harmfulEffects.add(instance);
                }
            }
            if (!harmfulEffects.isEmpty()) {
                // 随机清除 1 个负面效果
                MobEffectInstance toRemove = harmfulEffects.get(entity.getRandom().nextInt(harmfulEffects.size()));
                entity.removeEffect(toRemove.getEffect());

                // 消耗 1 级甜蜜（amplifier - 1），重新施加更低等级
                int newAmplifier = amplifier - 1;
                MobEffectInstance currentSweetness = entity.getEffect(WAICEffect.SWEETNESS);
                int remainingDuration = currentSweetness != null ? currentSweetness.getDuration() : 600;
                entity.removeEffect(WAICEffect.SWEETNESS);
                entity.addEffect(new MobEffectInstance(WAICEffect.SWEETNESS, remainingDuration, newAmplifier));
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // 每秒（20 tick）触发一次
        return duration % 20 == 0;
    }
}
