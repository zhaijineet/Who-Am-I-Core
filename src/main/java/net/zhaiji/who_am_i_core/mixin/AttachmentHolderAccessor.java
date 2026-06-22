package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AttachmentHolder.class)
public interface AttachmentHolderAccessor {
    @Invoker("deserializeAttachments")
    void deserializeInternal(HolderLookup.Provider provider, CompoundTag tag);
}
