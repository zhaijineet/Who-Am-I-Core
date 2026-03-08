package net.zhaiji.who_am_i_core.event;

import net.neoforged.bus.api.IEventBus;
import net.zhaiji.chestcavitybeyond.api.event.RegisterChestCavityEvent;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

public class CommonEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        modBusListener(modBus);
        gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(CommonEventHandler::handlerRegisterChestCavityEvent);
    }

    public static void gameBusListener(IEventBus gameBus) {
        gameBus.addListener(CommonEventHandler::handlerOrganChangeEvent);
    }
}
