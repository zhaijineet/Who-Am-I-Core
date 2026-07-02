package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityDataSyncHandler;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 向 CCB 的 ChestCavityDataSyncHandler 追加龙血标记位的网络同步，使客户端能读取正确的扩容等级
 */
@Mixin(ChestCavityDataSyncHandler.class)
public abstract class ChestCavityDataSyncHandlerMixin {
    @Inject(
        method = "write(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/zhaiji/chestcavitybeyond/attachment/ChestCavityData;Z)V",
        at = @At("RETURN")
    )
    public void whoAmICore$appendDragonBloodFlags(
        RegistryFriendlyByteBuf buf, ChestCavityData attachment, boolean initialSync, CallbackInfo ci
    ) {
        buf.writeInt(((IChestCavityData) attachment).getDragonBloodFlags());
    }

    @Inject(
        method = "read(Lnet/neoforged/neoforge/attachment/IAttachmentHolder;Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/zhaiji/chestcavitybeyond/attachment/ChestCavityData;)Lnet/zhaiji/chestcavitybeyond/attachment/ChestCavityData;",
        at = @At("RETURN")
    )
    public void whoAmICore$applyDragonBloodFlags(
        IAttachmentHolder holder, RegistryFriendlyByteBuf buf,
        @Nullable ChestCavityData previousValue, CallbackInfoReturnable<ChestCavityData> cir
    ) {
        ((IChestCavityData) cir.getReturnValue()).setDragonBloodFlags(buf.readInt());
    }
}
