package net.zhaiji.who_am_i_core.register;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

import java.util.function.Supplier;

public class WAICCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        WhoAmICore.MOD_ID
    );

    public static final String WHO_AM_I_CORE_TAB_TRANSLATABLE = "itemGroup.who_am_i_core";

    public static final Supplier<CreativeModeTab> WHO_AM_I_CORE_TAB = CREATIVE_MODE_TAB.register(
        "who_am_i_core_tab",
        () -> CreativeModeTab.builder()
            .icon(() -> InitItem.CHEST_OPENER.get().getDefaultInstance())
            .title(Component.translatable(WHO_AM_I_CORE_TAB_TRANSLATABLE))
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> entry : WAICItem.ITEM.getEntries()) {
                    Item item = entry.get();
                    output.accept(item);
                    if (item == IceAndFireOrgans.FIRE_DRAGON_GEM.get()) {
                        output.accept(IafItems.FIRE_DRAGON_HEART.get());
                    }
                    if (item == IceAndFireOrgans.ICE_DRAGON_GEM.get()) {
                        output.accept(IafItems.ICE_DRAGON_HEART.get());
                    }
                    if (item == IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get()) {
                        output.accept(IafItems.LIGHTNING_DRAGON_HEART.get());
                    }
                    if (item == IceAndFireOrgans.HYDRA_SPINE.get()) {
                        output.accept(IafItems.HYDRA_HEART.get());
                    }
                }
            })
            .build()
    );
}
