package net.zhaiji.who_am_i_core;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.zhaiji.who_am_i_core.client.event.ClientEventManager;

@Mod(value = WhoAmICore.MOD_ID, dist = Dist.CLIENT)
public class WhoAmICoreClient {
    public WhoAmICoreClient(IEventBus modEventBus, ModContainer modContainer) {
        // 初始化客户端事件监听器
        ClientEventManager.init(modEventBus, NeoForge.EVENT_BUS);
    }
}
