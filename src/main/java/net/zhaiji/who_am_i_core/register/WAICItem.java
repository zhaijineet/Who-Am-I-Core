package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

public class WAICItem {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, WhoAmICore.MOD_ID);

    static {
        MowziesMobOrgans.register();
        IceAndFireOrgans.register();
    }
}
