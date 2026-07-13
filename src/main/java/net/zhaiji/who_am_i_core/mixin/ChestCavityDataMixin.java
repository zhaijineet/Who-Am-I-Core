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
import net.zhaiji.chestcavitybeyond.api.ChestCavitySize;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.util.AnvilCraftOrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChestCavityData.class)
public abstract class ChestCavityDataMixin implements IChestCavityData {
    @Unique
    private int dragonBloodFlags = 0;
    @Shadow
    private LivingEntity owner;

    @Shadow
    public abstract double getCurrentValue(Holder<Attribute> attribute);

    @Shadow
    public abstract int getOrganCount(TagKey<Item> tag);

    @Shadow
    public abstract ChestCavitySize getSize();

    /**
     * 任意来源调整胸腔容量前（道具、指令等），按目标等级随机补齐或退移龙血 bit，
     * 使扩容等级标记始终与实际 size 一致。
     */
    @Inject(
        method = "resize(Lnet/zhaiji/chestcavitybeyond/api/ChestCavitySize;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/zhaiji/chestcavitybeyond/attachment/ChestCavityData;updateSize(Lnet/zhaiji/chestcavitybeyond/api/ChestCavitySize;)V"
        )
    )
    public void whoAmICore$resize(ChestCavitySize newSize, CallbackInfo ci) {
        int currentLevel = getExpansionLevel();
        int targetLevel = newSize.getId();
        if (currentLevel == targetLevel) return;
        List<Integer> candidates = new ArrayList<>();
        if (currentLevel < targetLevel) {
            if ((dragonBloodFlags & IChestCavityData.BIT_FIRE_DRAGON) == 0) candidates.add(IChestCavityData.BIT_FIRE_DRAGON);
            if ((dragonBloodFlags & IChestCavityData.BIT_ICE_DRAGON) == 0) candidates.add(IChestCavityData.BIT_ICE_DRAGON);
            if ((dragonBloodFlags & IChestCavityData.BIT_LIGHTNING_DRAGON) == 0) candidates.add(IChestCavityData.BIT_LIGHTNING_DRAGON);
            for (int i = currentLevel; i < targetLevel && !candidates.isEmpty(); i++) {
                int pickIndex = owner.level().getRandom().nextInt(candidates.size());
                dragonBloodFlags |= candidates.remove(pickIndex);
            }
        } else {
            if ((dragonBloodFlags & IChestCavityData.BIT_FIRE_DRAGON) != 0) candidates.add(IChestCavityData.BIT_FIRE_DRAGON);
            if ((dragonBloodFlags & IChestCavityData.BIT_ICE_DRAGON) != 0) candidates.add(IChestCavityData.BIT_ICE_DRAGON);
            if ((dragonBloodFlags & IChestCavityData.BIT_LIGHTNING_DRAGON) != 0) candidates.add(IChestCavityData.BIT_LIGHTNING_DRAGON);
            for (int i = targetLevel; i < currentLevel && !candidates.isEmpty(); i++) {
                int pickIndex = owner.level().getRandom().nextInt(candidates.size());
                dragonBloodFlags &= ~candidates.remove(pickIndex);
            }
        }
    }

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    public void whoAmICore$tick(CallbackInfo ci) {
        if (owner == null) return;
        int tickCount = owner.tickCount;
        if (owner.level().isClientSide() || tickCount % 20 != 0) return;
        // 应用治愈属性效果，每秒回复治愈等量的生命值
        double heal = getCurrentValue(WAICAttribute.HEAL);
        if (heal > 0) {
            owner.heal((float) heal);
        }
        // 余烬金属器官：身处火源环境，每个余烬器官每秒恢复0.5生命值
        int emberCount = getOrganCount(WAICItemTagManager.EMBER);
        if (emberCount > 0 && AnvilCraftOrganUtil.isInFireSource(owner)) {
            owner.heal(0.5F * emberCount);
        }
        // 诅咒器官诅咒效果：每 40 tick 根据诅咒器官数量施加负面效果
        // 参考 AnvilCraft 原版 ICursed：虚弱(始终) / 缓慢(>8) / 饥饿(>64)
        if (tickCount % 40 == 0) {
            int cursedCount = getOrganCount(WAICItemTagManager.CURSED);
            if (cursedCount >= 1) {
                // 虚弱：每5个器官等级+1
                int weaknessAmp = (cursedCount - 1) / 5;
                owner.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, weaknessAmp));
            }
            if (cursedCount >= 3) {
                // 缓慢：每6个器官等级+1
                int slownessAmp = (cursedCount - 3) / 6;
                owner.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, slownessAmp));
            }
            if (cursedCount >= 5) {
                // 饥饿：每9个器官等级+1
                int hungerAmp = (cursedCount - 5) / 9;
                owner.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, hungerAmp));
            }
        }
    }

    /**
     * 新生成的实体初始化器官后，若其胸腔类型默认 size 大于 ROW_3，按差值随机补齐等量的龙血 bit，使扩容等级与 size 等级一致
     */
    @Inject(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/zhaiji/chestcavitybeyond/attachment/ChestCavityData;sync()V"
        )
    )
    public void whoAmICore$init(CallbackInfo ci) {
        int targetLevel = getSize().getId();
        int currentLevel = getExpansionLevel();
        if (currentLevel >= targetLevel) return;
        List<Integer> candidates = new ArrayList<>();
        if ((dragonBloodFlags & IChestCavityData.BIT_FIRE_DRAGON) == 0) candidates.add(IChestCavityData.BIT_FIRE_DRAGON);
        if ((dragonBloodFlags & IChestCavityData.BIT_ICE_DRAGON) == 0) candidates.add(IChestCavityData.BIT_ICE_DRAGON);
        if ((dragonBloodFlags & IChestCavityData.BIT_LIGHTNING_DRAGON) == 0) candidates.add(IChestCavityData.BIT_LIGHTNING_DRAGON);
        for (int i = currentLevel; i < targetLevel && !candidates.isEmpty(); i++) {
            int pickIndex = owner.level().getRandom().nextInt(candidates.size());
            dragonBloodFlags |= candidates.remove(pickIndex);
        }
    }

    @Override
    public boolean isDragonBloodUsed(int flag) {
        return (dragonBloodFlags & flag) != 0;
    }

    @Override
    public int getDragonBloodFlags() {
        return dragonBloodFlags;
    }

    @Override
    public void setDragonBloodFlags(int flags) {
        dragonBloodFlags = flags;
    }

    @Override
    public int getExpansionLevel() {
        return Integer.bitCount(dragonBloodFlags);
    }

    @Inject(
        method = "serializeNBT(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/nbt/CompoundTag;",
        at = @At("RETURN")
    )
    public void whoAmICore$serializeNBT(HolderLookup.Provider provider, CallbackInfoReturnable<CompoundTag> cir) {
        cir.getReturnValue().putInt("dragonBloodFlags", dragonBloodFlags);
    }

    @Inject(
        method = "deserializeNBT(Lnet/minecraft/core/HolderLookup$Provider;Lnet/minecraft/nbt/CompoundTag;)V",
        at = @At("RETURN")
    )
    public void whoAmICore$deserializeNBT(HolderLookup.Provider provider, CompoundTag tag, CallbackInfo ci) {
        // 优先读取新字段
        if (tag.contains("dragonBloodFlags")) {
            dragonBloodFlags = tag.getInt("dragonBloodFlags");
            return;
        }
        // TODO 1.2.0 删除：旧存档以 trophyFlags 存储 boss 奖杯扩容标记，位值复用映射如下
        //   旧 BIT_CHESED(1)    → 新 BIT_FIRE_DRAGON(1)
        //   旧 BIT_GEBURAH(2)   → 新 BIT_ICE_DRAGON(2)
        //   旧 BIT_MALKUTH(4)   → 新 BIT_LIGHTNING_DRAGON(4)
        // 仅保留扩容等级，具体来源语义不迁移
        if (tag.contains("trophyFlags")) {
            dragonBloodFlags = tag.getInt("trophyFlags");
        }
    }
}
