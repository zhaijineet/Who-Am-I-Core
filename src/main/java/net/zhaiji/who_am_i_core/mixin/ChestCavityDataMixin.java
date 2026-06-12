package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestCavityData.class)
public abstract class ChestCavityDataMixin implements IChestCavityData {
    @Unique
    private int trophyFlags = 0;

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
        // 诅咒器官诅咒效果：每 40 tick 根据诅咒器官数量施加负面效果
        // 参考 AnvilCraft 原版 ICursed：虚弱(始终) / 缓慢(>8) / 饥饿(>64)
        if (tickCount % 40 == 0) {
            int cursedCount = getOrganCount(WAICItemTagManager.CURSED);
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

    @Override
    public boolean isTrophyUsed(int flag) {
        return (trophyFlags & flag) != 0;
    }

    @Override
    public void setTrophyUsed(int flag, boolean used) {
        if (used) {
            trophyFlags |= flag;
        } else {
            trophyFlags &= ~flag;
        }
    }

    @Override
    public int getExpansionLevel() {
        return Integer.bitCount(trophyFlags);
    }

    @Inject(
        method = "serializeNBT(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/nbt/CompoundTag;",
        at = @At("RETURN")
    )
    private void who_am_i_core$serializeNBT(HolderLookup.Provider provider, CallbackInfoReturnable<CompoundTag> cir) {
        cir.getReturnValue().putInt("trophyFlags", trophyFlags);
    }

    @Inject(
        method = "deserializeNBT(Lnet/minecraft/core/HolderLookup$Provider;Lnet/minecraft/nbt/CompoundTag;)V",
        at = @At("RETURN")
    )
    private void who_am_i_core$deserializeNBT(HolderLookup.Provider provider, CompoundTag tag, CallbackInfo ci) {
        // 优先读取新格式
        if (tag.contains("trophyFlags")) {
            trophyFlags = tag.getInt("trophyFlags");
        }
    }
}
