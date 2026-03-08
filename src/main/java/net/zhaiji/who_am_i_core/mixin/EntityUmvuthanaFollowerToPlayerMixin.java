package net.zhaiji.who_am_i_core.mixin;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollower;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.mixinapi.IEntityUmvuthanaFollowerToPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityUmvuthanaFollowerToPlayer.class)
public abstract class EntityUmvuthanaFollowerToPlayerMixin extends EntityUmvuthanaFollower<Player>
    implements IEntityUmvuthanaFollowerToPlayer {
    @Unique
    private boolean organSummon = false;

    public EntityUmvuthanaFollowerToPlayerMixin(
        EntityType<? extends EntityUmvuthanaFollower> type,
        Level world,
        Class<Player> leaderClass
    ) {
        super(type, world, leaderClass);
    }

    @Shadow
    protected abstract void deactivate();

    @Override
    public void setOrganSummon() {
        organSummon = true;
    }

    @Override
    public boolean isOrganSummon() {
        return organSummon;
    }

    @Override
    public void setDeactivate() {
        // 先强制中断当前活跃的能力（绕过 canUse 检查）
        Ability<?> activeAbility = getActiveAbility();
        if (activeAbility != null && activeAbility.isUsing()) {
            activeAbility.interrupt();
        }
        // 然后执行原有的 deactivate 逻辑
        this.deactivate();
    }

    @Inject(
        method = "mobInteract",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$mobInteract(Player playerIn, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (organSummon) cir.setReturnValue(super.mobInteract(playerIn, hand));
    }

    @Inject(
        method = "getDeactivatedMask",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$getDeactivatedMask(ItemUmvuthanaMask mask, CallbackInfoReturnable<ItemStack> cir) {
        if (organSummon) cir.setReturnValue(ItemStack.EMPTY);
    }

    @Inject(
        method = "readAdditionalSaveData",
        at = @At("HEAD")
    )
    public void whoAmICore$readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        organSummon = compound.getBoolean("organSummon");
    }

    @Inject(
        method = "addAdditionalSaveData",
        at = @At("HEAD")
    )
    public void whoAmICore$addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("organSummon", organSummon);
    }
}
