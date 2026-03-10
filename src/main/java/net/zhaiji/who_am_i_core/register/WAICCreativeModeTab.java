package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.WhoAmICore;

import java.util.function.Supplier;

public class WAICCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, WhoAmICore.MOD_ID);

    public static final String WHO_AM_I_CORE_TAB_TRANSLATABLE = "itemGroup.who_am_i_core";

    public static final Supplier<CreativeModeTab> CHEST_CAVITY_BEYOND_TAB = CREATIVE_MODE_TAB.register(
            "who_am_i_core_tab",
            () -> CreativeModeTab.builder()
                                 .icon(() -> InitItem.CHEST_OPENER.get().getDefaultInstance())
                                 .title(Component.translatable(WHO_AM_I_CORE_TAB_TRANSLATABLE))
                                 .displayItems((parameters, output) -> {
                                     WAICItem.ITEM.getEntries()
                                                  .stream()
                                                  .map(Supplier::get)
                                                  .forEach(output::accept);
                                 })
                                 .build()
    );
}
