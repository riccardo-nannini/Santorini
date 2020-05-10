package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;

import java.util.List;

public abstract class Input {

    protected ObservableToController controller;

    public Input(ObservableToController controller)
    {
        this.controller = controller;
    }

    /**
     * asks the player to choose a builder to move
     * @param checkMoveCells a list of cell the builder can move on
     * @param error true if the previous input generated an error
     */
    public abstract void moveInput(List<Coords> checkMoveCells, boolean error);

    /**
     * asks the player to choose a builder and to build a structure
     * @param checkBuildCells a list of cell the builder can build on
     */
    public abstract void buildInput(List<Coords> checkBuildCells, boolean error);

    /**
     * asks the player to insert a nickname for this match
     * @param error true if the previous input generated an error
     * @return the player's nickname
     */
    public abstract void nicknameInput(boolean error);

    /**
     * asks the player which god he wants to play with
     * @param chosenGods the gods available to be chosen
     */
    public abstract void godInput(List<String> chosenGods, boolean error);

    /**
     * asks the player the starting position of his builder
     */
    public abstract void builderSetUpInput(boolean callNumber, boolean error);

    /**
     * asks the challenger to choose a set of gods for this match
     * @param godsList contains the name of all the gods available to choose from
     * @param godsNumber the number of gods the challenger has to choose
     */
    public abstract void godSelectionInput(List<String> godsList, int godsNumber, boolean error);

    /**
     * asks the player if he wants to activate the effect of his god
     * @param god the name of the god related to the effect
     */
    public abstract void effectInput(String god);

    /**
     * asks the player to select the builder he has to move
     * @param player the player who has to choose
     */
    public abstract void chooseBuilder(String player);

    /**
     * asks the player to remove a block on a certain cell
     */
    public abstract void removeBlock(List<Coords> removableBlocks, boolean error);


    // ------------- UPDATES THE VIEW VIA PARAMETERS OBJECT -------------------

    /**
     * Update view's map
     * @param mapVM Immutable map sent from the model
     */
    public void updateMap(MapVM mapVM) {}

    /**
     * Update view's builders of the color of BuilerVM
     * @param builderVM Immutables couple of builders sent from the model
     */
    public void updateBuilders(BuilderVM builderVM){}

    /**
     * Notifies the view that "username" won
     */
    public void notifyWin() {}

    public void notifyLose() {}

    /**
     * Updates cliet's CLI setting clients information (usernames, builders colors and gods)
     * @param messageClientsInfoCV contains clients information
     */
    public void setupClientsInfo(MessageClientsInfoCV messageClientsInfoCV) {}

    /**
     * Informs clients that the challenger is choosing gods
     * for the match
     * @param challenger username of the challenger
     */
    public void printWaitGodsSelection(String challenger) {}

    /**
     * Informs clients that player is choosing his god
     * @param player player's username
     */
    public void printWaitGodSelection(String player) {}

    /**
     * Inform client which god the server assigned him
     * @param assignedGod name of the assigned god
     */
    public void printAssignedGod(String assignedGod) {}

    public abstract void disconnectionMessage();

    /**
     * asks the player to choose the number of players
     * for this current match
     */
    public abstract void choosePlayerNum(boolean error);

    /**
     * Saves in MapPrinter the effect description of player's god
     * @param effect description of the effect
     */
    public void getEffectDescription(String effect) {}

    public abstract void playAgain();

    /**
     * Asks the challenger to choose the starter player
     * @param error true if the previous input generated an error
     * @param usernames players' usernames
     */
    public void starterInput(boolean error, List<String> usernames) {}

    /**
     * Informs clients that the challenger is choosing the starter player
     * @param challenger challenger's username
     */
    public void printWaitStarterSelection(String challenger) {}

}
