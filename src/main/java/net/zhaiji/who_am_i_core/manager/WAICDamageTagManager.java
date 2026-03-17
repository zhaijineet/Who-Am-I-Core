package net.zhaiji.who_am_i_core.manager;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICDamageTagManager {
    /**
     * 近战伤害标签
     * 包含所有属于近战攻击类型的伤害，用于统一判断是否为近战伤害
     */
    public static final TagKey<DamageType> IS_MELEE = create("is_melee");

    public static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, WhoAmICore.of(name));
    }
}
