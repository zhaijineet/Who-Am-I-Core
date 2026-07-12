package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.api.UseCondition;
import net.zhaiji.who_am_i_core.manager.UseConditionManager;
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
        Optional<UseCondition> condition = UseConditionManager.getMatchingCondition(player, whoAmICore$self());
        if (condition.isEmpty()) return;

        UseCondition useCondition = condition.get();
        if (useCondition.shouldForceStartUsingItem(player, whoAmICore$self())) {
            // TODO forceStartUsingItem 绕过整个 Item.use 而非仅 canEat，对模组食物启动逻辑兼容性有限，后续重构 API
            player.startUsingItem(usedHand);
            cir.setReturnValue(InteractionResultHolder.consume(whoAmICore$self()));
        } else if (useCondition.hasOnUse()) {
            // 瞬发使用路径：立即调用 onUse
            cir.setReturnValue(useCondition.onUse(player, whoAmICore$self()));
        }
        // 都没有 → 不拦截，让原版 use() 正常执行
    }

    @Inject(
        method = "finishUsingItem",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$finishUsingItem(Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        Optional<UseCondition> condition = UseConditionManager.getMatchingCondition(livingEntity, whoAmICore$self());
        condition.filter(UseCondition::hasOnFinishUsingItem)
            .ifPresent(useCondition -> cir.setReturnValue(useCondition.onFinishUsingItem(livingEntity, whoAmICore$self())));
    }

    @Inject(
        method = "getUseAnimation",
        at = @At("RETURN"),
        cancellable = true
    )
    public void whoAmICore$getUseAnimation(CallbackInfoReturnable<UseAnim> cir) {
        if (cir.getReturnValue() != UseAnim.NONE) return;
        UseConditionManager.getUseAnimation(whoAmICore$self()).ifPresent(cir::setReturnValue);
    }

    @Inject(
        method = "getUseDuration",
        at = @At("RETURN"),
        cancellable = true
    )
    public void whoAmICore$getUseDuration(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        Optional<UseCondition> condition = UseConditionManager.getMatchingCondition(entity, whoAmICore$self());
        condition.ifPresent(useCondition -> {
            int duration = useCondition.getUseDuration(entity, whoAmICore$self());
            if (duration != cir.getReturnValue()) {
                cir.setReturnValue(duration);
            }
        });
    }
}
