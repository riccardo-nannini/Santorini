package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_callback.ControllerCallback;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

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
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.processNickname, nickname, null,0);
        callback.send(msg);
    }

    /**
     * the selection of gods selected by the challenger is sent to controller
     * @param gods
     */
    public void notifyGodSelection(String gods)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.processGodsSelection, gods, null,0);
        callback.send(msg);
    }

    /**
     * the god chosen by the user is sent to the controller
     * @param god
     */
    public void notifyGod(String god)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.processGodChoice, god, null,0);
        callback.send(msg);
    }

    /**
     * the initial position of the users'builder is sent to controller
     * @param builder
     */
    public void notifySetupBuilder(Coords builder)
    {

        MessageFromViewToController msg = new MessageFromViewToController(MessageID.builderSetupPhase, null, builder,0);
        callback.send(msg);
    }

    /**
     * the builder chosen by the user is sent to controller
     * @param builder
     */
    public void notifyBuilderChoice(Coords builder)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.selectBuilder, null, builder,0);
        callback.send(msg);
    }

    /**
     * the new position of the chosen builder is sent to controller
     * @param cellToMoveOn
     */
    public void notifyMoveInput(Coords cellToMoveOn)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.move, null, cellToMoveOn,0);
        callback.send(msg);
    }

    /**
     * the cell to build on chosen by the user is sent to controller
     * @param cellToBuildOn
     */
    public void notifyBuildInput(Coords cellToBuildOn)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.build, null, cellToBuildOn,0);
        callback.send(msg);
    }

    /**
     * the answer of the player is sent to controller
     * @param effect the answer (yes or no)
     */
    public void notifyEffect(String effect)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.useEffect, effect, null,0);
        callback.send(msg);
    }

    /**
     * the information of the cell to remove a block from is sent to controller
     * @param cellToRemoveBlock the coordinates of the cell
     */
    public void notifyRemoveInput(Coords cellToRemoveBlock)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.removeBlock, null, cellToRemoveBlock, 0);
        callback.send(msg);
    }

    public void notifyPlayersNumber(int number)
    {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.processPlayersNumber,null,null, number);
        callback.send(msg);
    }

    /**
     * the selection of the starter player is sent to the controller
     * @param starter username of the selected starter player
     */
    public void notifyStarterSelection(String starter) {
        MessageFromViewToController msg = new MessageFromViewToController(MessageID.updateStarter, starter, null, 0);
        callback.send(msg);
    }


    public void notifyPlayAgain(String playAgain)
    {
        System.out.println(playAgain);
        MessageFromViewToController msg = null;
        if(playAgain.equals("yes") || playAgain.equals("y")){
            msg = new MessageFromViewToController(MessageID.rematch,"yes",null,0);
        }
        else if(playAgain.equals("no") || playAgain.equals("n")){
            msg = new MessageFromViewToController(MessageID.rematch,"no",null,0);
        }

        callback.send(msg);

    }

}
