package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.api.EdibleCondition;
import net.zhaiji.who_am_i_core.manager.EdibleConditionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Unique
    public ItemStack whoAmICore$self() {
        return (ItemStack) (Object) this;
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
        if (EdibleConditionManager.canEat(player, whoAmICore$self())) {
            player.startUsingItem(usedHand);
            cir.setReturnValue(InteractionResultHolder.consume(whoAmICore$self()));
        }
    }

    @Inject(
        method = "finishUsingItem",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$finishUsingItem(Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        Optional<EdibleCondition> condition = EdibleConditionManager.getMatchingCondition(livingEntity, whoAmICore$self());
        condition.ifPresent(edibleCondition -> cir.setReturnValue(edibleCondition.onEat(livingEntity, whoAmICore$self())));
    }

    @Inject(
        method = "getUseAnimation",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$getUseAnimation(CallbackInfoReturnable<UseAnim> cir) {
        if (getItem().getUseAnimation(whoAmICore$self()) == UseAnim.NONE) {
            EdibleConditionManager.getUseAnimation(whoAmICore$self()).ifPresent(cir::setReturnValue);
        }
    }

    @Inject(
        method = "getUseDuration",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$getUseDuration(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        Optional<EdibleCondition> condition = EdibleConditionManager.getMatchingCondition(entity, whoAmICore$self());
        condition.ifPresent(edibleCondition -> cir.setReturnValue(edibleCondition.getUseDuration(entity, whoAmICore$self())));
    }
}
