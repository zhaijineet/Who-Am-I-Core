package net.zhaiji.who_am_i_core.event;

import net.neoforged.bus.api.IEventBus;

public class CommonEventManager {
    public static void init(IEventBus modBus, IEventBus gameBus) {
        modBusListener(modBus);
        gameBusListener(gameBus);
    }

    public static void modBusListener(IEventBus modBus) {
        modBus.addListener(CommonEventHandler::handlerEntityAttributeModificationEvent);
        modBus.addListener(CommonEventHandler::handlerOrganRegisterEvent);
        modBus.addListener(CommonEventHandler::handlerChestCavityRegisterEvent);
    }

    public static void gameBusListener(IEventBus gameBus) {
        gameBus.addListener(CommonEventHandler::handlerOrganChangeEvent);
        gameBus.addListener(CommonEventHandler::handlerLivingDeathEvent);
        gameBus.addListener(CommonEventHandler::handlerLivingIncomingDamageEvent);
        gameBus.addListener(CommonEventHandler::handlerLivingDamageEvent$Pre);
        gameBus.addListener(CommonEventHandler::handlerSpellOnCastEvent);
        gameBus.addListener(CommonEventHandler::handlerMobEffectEvent$Added);
        gameBus.addListener(CommonEventHandler::handlerMobEffectEvent$Remove);
        gameBus.addListener(CommonEventHandler::handlerMobEffectEvent$Expired);
        gameBus.addListener(CommonEventHandler::handlerPlayerXpPickup);
        gameBus.addListener(CommonEventHandler::handlerPlayerLevelChange);
        gameBus.addListener(CommonEventHandler::handlerLivingEntityUseItemEvent$Finish);
    }
}
