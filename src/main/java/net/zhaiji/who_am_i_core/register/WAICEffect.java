package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.effect.DragonPowerEffect;
import net.zhaiji.who_am_i_core.effect.SweetnessEffect;

public class WAICEffect {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, WhoAmICore.MOD_ID);

    public static final Holder<MobEffect> FIRE_DRAGON_POWER = EFFECT.register(
        "fire_dragon_power",
        () -> new DragonPowerEffect(MobEffectCategory.BENEFICIAL, 0xFF4500)
    );

    public static final Holder<MobEffect> ICE_DRAGON_POWER = EFFECT.register(
        "ice_dragon_power",
        () -> new DragonPowerEffect(MobEffectCategory.BENEFICIAL, 0x00BFFF)
    );

    public static final Holder<MobEffect> LIGHTNING_DRAGON_POWER = EFFECT.register(
        "lightning_dragon_power",
        () -> new DragonPowerEffect(MobEffectCategory.BENEFICIAL, 0xFFFF00)
    );

    public static final Holder<MobEffect> DRAGON_POWER = EFFECT.register(
        "dragon_power",
        () -> new DragonPowerEffect(MobEffectCategory.BENEFICIAL, 0x800080)
    );

    public static final Holder<MobEffect> SWEETNESS = EFFECT.register(
        "sweetness",
        () -> new SweetnessEffect(MobEffectCategory.BENEFICIAL, 0xFFB6C1)
    );
}
