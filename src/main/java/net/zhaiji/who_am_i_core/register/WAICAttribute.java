package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICAttribute {
    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, WhoAmICore.MOD_ID);

    public static final Holder<Attribute> BLOCK = registerRangedAttribute("block");
    public static final Holder<Attribute> COUNTER_ATTACK = registerRangedAttribute("counter_attack");
    public static final Holder<Attribute> HEAL = registerRangedAttribute("heal");
    public static final Holder<Attribute> MELEE_DAMAGE = registerRangedAttribute("melee_damage");
    public static final Holder<Attribute> RANGED_DAMAGE = registerRangedAttribute("ranged_damage");
    public static final Holder<Attribute> MAGIC_DAMAGE = registerRangedAttribute("magic_damage");
    public static final Holder<Attribute> MELEE_DAMAGE_PERCENTAGE = registerPercentageAttribute("melee_damage_percentage");
    public static final Holder<Attribute> RANGED_DAMAGE_PERCENTAGE = registerPercentageAttribute("ranged_damage_percentage");
    public static final Holder<Attribute> MAGIC_DAMAGE_PERCENTAGE = registerPercentageAttribute("magic_damage_percentage");
    public static final Holder<Attribute> LOOTING = registerRangedAttribute("looting");
    public static final Holder<Attribute> FORTUNE = registerRangedAttribute("fortune");
    public static final Holder<Attribute> MAX_BLOOD = registerRangedAttribute("max_blood", 100, 0, Integer.MAX_VALUE);
    public static final Holder<Attribute> MAX_YELLOW_BILE = registerRangedAttribute("max_yellow_bile", 100, 0, Integer.MAX_VALUE);
    public static final Holder<Attribute> MAX_BLACK_BILE = registerRangedAttribute("max_black_bile", 100, 0, Integer.MAX_VALUE);
    public static final Holder<Attribute> MAX_PHLEGM = registerRangedAttribute("max_phlegm", 100, 0, Integer.MAX_VALUE);

    public static Holder<Attribute> registerRangedAttribute(String name) {
        return registerRangedAttribute(name, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static Holder<Attribute> registerRangedAttribute(String name, double defaultValue, double minimumValue, double maximumValue) {
        return ATTRIBUTE.register(
            name,
            () -> new RangedAttribute(
                "attribute." + WhoAmICore.MOD_ID + "." + name,
                defaultValue,
                minimumValue,
                maximumValue
            ).setSyncable(true)
        );
    }

    public static Holder<Attribute> registerPercentageAttribute(String name) {
        return ATTRIBUTE.register(
            name,
            () -> new PercentageAttribute(
                "attribute." + WhoAmICore.MOD_ID + "." + name,
                1,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE
            ).setSyncable(true)
        );
    }
}
