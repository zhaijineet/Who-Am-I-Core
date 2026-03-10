package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganSkillUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract SoundEvent getEatingSound();

    @Shadow
    public abstract void consume(int amount, @Nullable LivingEntity entity);

    @Shadow
    public abstract Item getItem();

    @Unique
    private ItemStack self() {
        return (ItemStack) (Object) this;
    }

    @Unique
    private boolean isDirtItem() {
        return MowziesMobOrganSkillUtil.isDirtItem(self());
    }

    @Unique
    private boolean hasBluffOrgan(LivingEntity entity) {
        return ChestCavityUtil.getData(entity).hasOrgan(
            organ -> organ.is(MowziesMobOrgans.BLUFF_CORE.get()) ||
                     organ.is(MowziesMobOrgans.BLUFF_TABLET.get()) ||
                     organ.is(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        );
    }

    @Inject(
        method = "use",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$use(
        Level level,
        Player player,
        InteractionHand usedHand,
        CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir
    ) {
        if (isDirtItem() && hasBluffOrgan(player)) {
            player.startUsingItem(usedHand);
            cir.setReturnValue(InteractionResultHolder.consume(self()));
        }
    }

    @Inject(
        method = "finishUsingItem",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$finishUsingItem(Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (isDirtItem() && hasBluffOrgan(livingEntity)) {
            level.playSound(
                null,
                livingEntity.getOnPos(),
                getEatingSound(),
                SoundSource.NEUTRAL,
                1.0F,
                1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F
            );
            MowziesMobOrganSkillUtil.eatDirt(livingEntity, getItem());
            consume(1, livingEntity);
            livingEntity.gameEvent(GameEvent.EAT);
            cir.setReturnValue(self());
        }
    }

    @Inject(
        method = "getUseAnimation",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$getUseAnimation(CallbackInfoReturnable<UseAnim> cir) {
        if (isDirtItem()) {
            cir.setReturnValue(UseAnim.EAT);
        }
    }

    @Inject(
        method = "getUseDuration",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$getUseDuration(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (isDirtItem()) {
            cir.setReturnValue(36);
        }
    }
}
