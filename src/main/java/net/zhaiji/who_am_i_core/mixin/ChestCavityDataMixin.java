package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestCavityData.class)
public abstract class ChestCavityDataMixin {
    @Shadow
    @Nullable
    public abstract LivingEntity getOwner();

    @Shadow
    public abstract double getCurrentValue(Holder<Attribute> attribute);

    @Shadow
    public abstract int getOrganCount(TagKey<Item> tag);

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    public void whoAmICore$tick(CallbackInfo ci) {
        LivingEntity owner = getOwner();
        int tickCount = owner.tickCount;
        if (owner.level().isClientSide() || tickCount % 20 != 0) return;
        // 应用治愈属性效果，每秒回复治愈等量的生命值
        double heal = getCurrentValue(WAICAttribute.HEAL);
        if (heal > 0) {
            owner.heal((float) heal);
        }
        // 诅咒金器官诅咒效果：每 40 tick 根据诅咒金器官数量施加负面效果
        // 参考 AnvilCraft 原版 ICursed：虚弱(始终) / 缓慢(>8) / 饥饿(>64)
        if (tickCount % 40 == 0) {
            int cursedCount = getOrganCount(WAICItemTagManager.CURSED_GOLD);
            if (cursedCount >= 1) {
                // 虚弱：每5个器官等级+1
                int weaknessAmp = (cursedCount - 1) / 5;
                owner.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, weaknessAmp));
            }
            if (cursedCount >= 3) {
                // 缓慢：每6个器官等级+1
                int slownessAmp = (cursedCount - 3) / 6;
                owner.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, slownessAmp));
            }
            if (cursedCount >= 5) {
                // 饥饿：每9个器官等级+1
                int hungerAmp = (cursedCount - 5) / 9;
                owner.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, hungerAmp));
            }
        }
    }
}
