package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;

//  TODO 有些属性不应该有负值？
public class WAICAttribute {
    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, WhoAmICore.MOD_ID);

    // 格挡 - 等值减少伤害
    public static final Holder<Attribute> BLOCK = registerRangedAttribute("block");

    // 反击 - 受到伤害时对攻击者造成等值伤害
    public static final Holder<Attribute> COUNTER_ATTACK = registerRangedAttribute("counter_attack");

    // 治疗 - 定期恢复生命值
    public static final Holder<Attribute> HEAL = registerRangedAttribute("heal");

    // 近战伤害
    public static final Holder<Attribute> MELEE_DAMAGE = registerRangedAttribute("melee_damage");

    // 远程伤害
    public static final Holder<Attribute> RANGED_DAMAGE = registerRangedAttribute("ranged_damage");

    // 魔法伤害
    public static final Holder<Attribute> MAGIC_DAMAGE = registerRangedAttribute("magic_damage");

    // 近战伤害百分比
    public static final Holder<Attribute> MELEE_DAMAGE_PERCENTAGE = registerPercentageAttribute("melee_damage_percentage");

    // 远程伤害百分比
    public static final Holder<Attribute> RANGED_DAMAGE_PERCENTAGE = registerPercentageAttribute("ranged_damage_percentage");

    // 魔法伤害百分比
    public static final Holder<Attribute> MAGIC_DAMAGE_PERCENTAGE = registerPercentageAttribute("magic_damage_percentage");

    // 抢夺
    public static final Holder<Attribute> LOOTING = registerRangedAttribute("looting");

    // 时运
    public static final Holder<Attribute> FORTUNE = registerRangedAttribute("fortune");


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

    public static Holder<Attribute> registerPercentageAttribute(String name) {
        return ATTRIBUTE.register(
            name,
            () -> new PercentageAttribute(
                "attribute." + WhoAmICore.MOD_ID + "." + name,
                1,        // 默认值
                Integer.MIN_VALUE,    // 最小值
                Integer.MAX_VALUE     // 最大值
            ).setSyncable(true)       // 自动同步到客户端
        );
    }
}
