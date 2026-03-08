package net.zhaiji.who_am_i_core.client.event;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;

public class ClientEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        modBusListener(modBus);
        gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
    }


    public static void gameBusListener(IEventBus gameBus) {
        gameBus.addListener(ClientEventHandler::handlerComputeFovModifierEvent);
        gameBus.addListener(EventPriority.LOW, ClientEventHandler::handlerMovementInputUpdateEvent);
    }
}
