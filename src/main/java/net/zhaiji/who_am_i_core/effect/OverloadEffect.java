package net.zhaiji.who_am_i_core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

/**
 * 超频效果 — 传导链节激活后施加，持续时间由传导链节传入
 * 每tick消耗3电荷，电荷耗尽时自动移除
 */
public class OverloadEffect extends MobEffect {
    public OverloadEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        float consumed = WAICOrganUtil.extractCharge(data, entity, 3, false);
        return !(WAICOrganUtil.getCharge(data) <= 0) || !(consumed < 3);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
