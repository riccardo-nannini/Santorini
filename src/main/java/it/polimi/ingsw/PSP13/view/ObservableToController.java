package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

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
    public void notifyGodSelection(String gods)
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
     * @param builder
     */
    public void notifySetupBuilder(Coords builder)
    {
        controller.updateSetupBuilder(builder);
    }

    /**
     * the builder chosen by the user is sent to controller
     * @param builder
     */
    public void notifyBuilderChoice(Coords builder){controller.updateBuilderChoice(builder);}

    /**
     * the new position of the chosen builder is sent to controller
     * @param cellToMoveOn
     */
    public void notifyMoveInput(Coords cellToMoveOn){controller.updateMoveInput(cellToMoveOn);}

    /**
     * the cell to build on chosen by the user is sent to controller
     * @param cellToBuildOn
     */
    public void notifyBuildInput(Coords cellToBuildOn){controller.updateBuildInput(cellToBuildOn);}

    /**
     * the answer of the player is sent to controller
     * @param effect the answer (yes or no)
     */
    public void notifyEffect(String effect){controller.updateEffect(effect);}

    /**
     * the information of the cell to remove a block from is sent to controller
     * @param cellToRemoveBlock the coordinates of the cell
     */
    public void notifyRemoveInput(Coords cellToRemoveBlock){controller.updateRemoveInput(cellToRemoveBlock);}
}
