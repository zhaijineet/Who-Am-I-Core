package net.zhaiji.who_am_i_core.register;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.attachment.HumoursData;

import java.util.function.Supplier;

public class WAICAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, WhoAmICore.MOD_ID);

    // 四体液数据
    public static final Supplier<AttachmentType<HumoursData>> HUMOURS = ATTACHMENT_TYPE.register(
        "humours",
        () -> AttachmentType.serializable(HumoursData::new)
            .sync(HumoursData.STREAM_CODEC)
            .copyOnDeath()
            .build()
    );
}
