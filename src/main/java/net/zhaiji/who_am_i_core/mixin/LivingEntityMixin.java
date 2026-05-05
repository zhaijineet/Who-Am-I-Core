package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    @Nullable
    public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean addEffect(MobEffectInstance effectInstance);

    @Unique
    public LivingEntity whoAmICore$self() {
        return (LivingEntity) (Object) this;
    }

    /**
     * 九头蛇胃将食物负面效果转化为中毒
     * <p>
     * 九头蛇肠子提高获得的负面效果时长为1.5倍
     * </p>
     */
    @Inject(
        method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
        at = @At("HEAD")
    )
    public void whoAmICore$eat(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        if (level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(whoAmICore$self());

        int hydraStomachCount = data.getOrganCount(IceAndFireOrgans.HYDRA_STOMACH.get());
        if (hydraStomachCount <= 0) return;
        int duration = 0;
        int amplifier = 0;
        for (FoodProperties.PossibleEffect effect : foodProperties.effects()) {
            MobEffectInstance instance = effect.effect();
            if (!((IMobEffectInstance) instance).isHarmful()) {
                if (instance.isInfiniteDuration()) continue;
                duration += instance.getDuration();
                if (amplifier < instance.getAmplifier()) {
                    amplifier = instance.getAmplifier();
                }
            }
        }
        int hydraIntestineCount = data.getOrganCount(IceAndFireOrgans.HYDRA_INTESTINE.get());
        if (hydraIntestineCount > 0) {
            duration = (int) (duration * (1 + (0.5 * hydraIntestineCount)));
        }
        duration *= hydraStomachCount;
        MobEffectInstance poison = getEffect(MobEffects.POISON);
        if (poison != null && !poison.isInfiniteDuration()) {
            duration += poison.getDuration();
            if (amplifier < poison.getAmplifier()) {
                amplifier = poison.getAmplifier();
            }
        }
        addEffect(new MobEffectInstance(MobEffects.POISON, duration, amplifier));
    }

    /**
     * 九头蛇肠子提高获得的效果时长为1.5倍
     */
    @ModifyArg(
        method = "addEatEffect",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"
        )
    )
    public MobEffectInstance whoAmICore$addEatEffect(MobEffectInstance effectInstance) {
        ChestCavityData data = ChestCavityUtil.getData(whoAmICore$self());
        int hydraIntestineCount = data.getOrganCount(IceAndFireOrgans.HYDRA_INTESTINE.get());
        if (hydraIntestineCount > 0) {
            ((IMobEffectInstance) effectInstance).setDuration(duration -> (int) (duration * (1 + (0.5 * hydraIntestineCount))));
        }
        return effectInstance;
    }
}
