package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_callback.ControllerCallback;
import it.polimi.ingsw.PSP13.network.client_callback.MessageVC;

import java.util.List;

public class ObservableToController {

    /**
     * this is the observer from the controller who needs to be notified
     */
    private ControllerCallback callback;

    public ObservableToController(ControllerCallback callback)
    {
        this.callback = callback;
    }

    /**
     * the name chosen by the user is sent to controller
     * @param nickname
     */
    public void notifyNickname(String nickname)
    {
        MessageVC msg = new MessageVC(2, nickname, null,0);
        callback.send(msg);
    }

    /**
     * the selection of gods selected by the challenger is sent to controller
     * @param gods
     */
    public void notifyGodSelection(String gods)
    {
        MessageVC msg = new MessageVC(5, gods, null,0);
        callback.send(msg);
    }

    /**
     * the god chosen by the user is sent to the controller
     * @param god
     */
    public void notifyGod(String god)
    {
        MessageVC msg = new MessageVC(3, god, null,0);
        callback.send(msg);
    }

    /**
     * the initial position of the users'builder is sent to controller
     * @param builder
     */
    public void notifySetupBuilder(Coords builder)
    {

        MessageVC msg = new MessageVC(4, null, builder,0);
        callback.send(msg);
    }

    /**
     * the builder chosen by the user is sent to controller
     * @param builder
     */
    public void notifyBuilderChoice(Coords builder)
    {
        MessageVC msg = new MessageVC(7, null, builder,0);
        callback.send(msg);
    }

    /**
     * the new position of the chosen builder is sent to controller
     * @param cellToMoveOn
     */
    public void notifyMoveInput(Coords cellToMoveOn)
    {
        MessageVC msg = new MessageVC(0, null, cellToMoveOn,0);
        callback.send(msg);
    }

    /**
     * the cell to build on chosen by the user is sent to controller
     * @param cellToBuildOn
     */
    public void notifyBuildInput(Coords cellToBuildOn)
    {
        MessageVC msg = new MessageVC(1, null, cellToBuildOn,0);
        callback.send(msg);
    }

    /**
     * the answer of the player is sent to controller
     * @param effect the answer (yes or no)
     */
    public void notifyEffect(String effect)
    {
        MessageVC msg = new MessageVC(6, effect, null,0);
        callback.send(msg);
    }

    /**
     * the information of the cell to remove a block from is sent to controller
     * @param cellToRemoveBlock the coordinates of the cell
     */
    public void notifyRemoveInput(Coords cellToRemoveBlock)
    {
        MessageVC msg = new MessageVC(8, null, cellToRemoveBlock, 0);
        callback.send(msg);
    }

    public void notifyPlayersNumber(int number)
    {
        MessageVC msg = new MessageVC(13,null,null, number);
        callback.send(msg);
    }

    /**
     * the selection of the starter player is sent to the controller
     * @param starter username of the selected starter player
     */
    public void notifyStarterSelection(String starter) {
        MessageVC msg = new MessageVC(15, starter, null, 0);
        callback.send(msg);
    }


    public void notifyPlayAgain(String playAgain)
    {
        System.out.println(playAgain);
        MessageVC msg = null;
        if(playAgain.equals("yes") || playAgain.equals("y")){
            msg = new MessageVC(16,"yes",null,0);
        }
        else if(playAgain.equals("no") || playAgain.equals("n")){
            msg = new MessageVC(16,"no",null,0);
        }

        callback.send(msg);

    }

}
