package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class ObservableToController {

    /**
     * this is the observer from the controller who needs to be notified
     */
    private ViewObserver controller;

    public void addObserver(ViewObserver controller)
    {
        this.controller = controller;
    }

    /**
     * the name chosen by the user is sent to controller
     * @param nickname
     */
    public void notifyNickname(String nickname)
    {
        controller.updateNickname(nickname);
    }

    /**
     * the selection of gods selected by the challenger is sent to controller
     * @param gods
     */
    public void notifyGodSelection(String[] gods)
    {
        controller.updateGodSelection(gods);
    }

    /**
     * the god chosen by the user is sent to the controller
     * @param god
     */
    public void notifyGod(String god)
    {
        controller.updateGod(god);
    }

    /**
     * the initial position of the users'builder is sent to controller
     * @param builder1
     * @param builder2
     */
    public void notifySetupBuilder(Coords builder1, Coords builder2)
    {
        controller.updateSetupBuilder(builder1, builder2);
    }
}
