package net.zhaiji.who_am_i_core.client.event;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.zhaiji.who_am_i_core.config.WhoAmIClientConfig;

public class ClientEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        modBusListener(modBus);
        gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(ClientEventHandler::handlerEntityRenderersEvent$RegisterRenderers);
        modBus.addListener(ClientEventHandler::handlerRegisterClientTooltipComponentFactoriesEvent);
        modBus.addListener(ClientEventHandler::handlerFMLClientSetupEvent);
        modBus.addListener(ClientEventHandler::handlerRegisterGuiLayersEvent);
        modBus.addListener(WhoAmIClientConfig::handlerModConfigEvent);
    }


    public static void gameBusListener(IEventBus gameBus) {
        gameBus.addListener(ClientEventHandler::handlerComputeFovModifierEvent);
        gameBus.addListener(EventPriority.LOW, ClientEventHandler::handlerMovementInputUpdateEvent);
        gameBus.addListener(ClientEventHandler::handlerItemTooltipEvent);
    }
}
