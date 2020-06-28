package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class GodsSelectionDispatch extends ServerDispatchBehavior {
    public GodsSelectionDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * behavior related to the setup phase. the challenger chose a set of god
     * and it has been communicated
     * @param messageVC
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getString() != null) viewObserver.updateGodSelection(messageVC.getString());

    }
}
