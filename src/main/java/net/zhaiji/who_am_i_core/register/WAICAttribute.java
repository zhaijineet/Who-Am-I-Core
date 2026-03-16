package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICAttribute {
    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, WhoAmICore.MOD_ID);

    // 温度
    public static final Holder<Attribute> TEMPERATURE = registerRangedAttribute("temperature");

    // 格挡 - 等值减少伤害
    public static final Holder<Attribute> BLOCK = registerRangedAttribute("block");

    public static Holder<Attribute> registerRangedAttribute(String name) {
        return ATTRIBUTE.register(
            name,
            () -> new RangedAttribute(
                "attribute." + WhoAmICore.MOD_ID + "." + name,
                0,         // 默认值
                Integer.MIN_VALUE,    // 最小值
                Integer.MAX_VALUE     // 最大值
            ).setSyncable(true)       // 自动同步到客户端
        );
    }
}
