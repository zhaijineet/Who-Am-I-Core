package net.zhaiji.who_am_i_core.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICTooltipUtil {
    public static Component organSkill(Item item) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".skill");
    }

    public static Component organSkill(Item item, int index) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".skill." + index);
    }

    public static Component organDescription(Item item) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".description");
    }

    public static Component organDescription(Item item, int index) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".description." + index);
    }
}
