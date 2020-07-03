package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class EffectDispatch extends ServerDispatchBehavior {
    public EffectDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Behavior related the user's choice to use its god effect
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getString() != null) viewObserver.updateEffect(messageVC.getString());

    }
}
