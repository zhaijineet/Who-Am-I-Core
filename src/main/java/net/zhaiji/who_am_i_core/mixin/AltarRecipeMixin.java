package net.zhaiji.who_am_i_core.mixin;

import com.github.tartaricacid.touhoulittlemaid.crafting.AltarRecipe;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 祭坛复活女仆时恢复attachment
 */
@Mixin(AltarRecipe.class)
public abstract class AltarRecipeMixin {
    @WrapOperation(
        method = "rebornMaid",
        at = @At(
            value = "INVOKE",
            target = "Lcom/github/tartaricacid/touhoulittlemaid/entity/passive/EntityMaid;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"
        )
    )
    public void whoAmICore$rebornMaid(EntityMaid maid, CompoundTag maidCompound, Operation<Void> original) {
        original.call(maid, maidCompound);
        if (!maidCompound.contains("neoforge:attachments", Tag.TAG_COMPOUND)) return;
        CompoundTag attachments = maidCompound.getCompound("neoforge:attachments");
        ((AttachmentHolderAccessor) maid).deserializeInternal(maid.registryAccess(), attachments);
    }
}
