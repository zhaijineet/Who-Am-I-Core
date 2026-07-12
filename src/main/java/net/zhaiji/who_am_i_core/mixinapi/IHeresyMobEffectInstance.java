package net.zhaiji.who_am_i_core.mixinapi;

import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;

public interface IHeresyMobEffectInstance extends IMobEffectInstance {
    boolean isHeresyEnhanced();

    void setHeresyEnhanced(boolean heresyEnhanced);
}
