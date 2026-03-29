package net.zhaiji.who_am_i_core;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.zhaiji.who_am_i_core.event.CommonEventManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICCreativeModeTab;
import net.zhaiji.who_am_i_core.register.WAICEffect;
import net.zhaiji.who_am_i_core.register.WAICEntity;
import net.zhaiji.who_am_i_core.register.WAICItem;

@Mod(WhoAmICore.MOD_ID)
public class WhoAmICore {
    public static final String MOD_ID = "who_am_i_core";

    public WhoAmICore(IEventBus modEventBus, ModContainer modContainer) {
        // 注册
        WAICItem.ITEM.register(modEventBus);
        WAICCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        WAICAttribute.ATTRIBUTE.register(modEventBus);
        WAICEntity.ENTITY.register(modEventBus);
        WAICEffect.EFFECT.register(modEventBus);

        // 注册游戏事件处理器到 NeoForge 事件总线
        CommonEventManager.init(modEventBus, NeoForge.EVENT_BUS);
    }

    /**
     * 创建资源位置的工具方法
     *
     * @param path 路径
     * @return 资源位置
     */
    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
