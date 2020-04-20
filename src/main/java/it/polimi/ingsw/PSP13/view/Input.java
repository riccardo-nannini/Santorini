package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public abstract class Input {

    protected ObservableToController controller;

    public Input(ObservableToController controller)
    {
        this.controller = controller;
    }

    /**
     * asks the player to choose a builder to move
     * @param player the player's nickname
     * @param checkMoveCells a list of cell the builder can move on
     * @param error true if the previous input generated an error
     */
    public abstract void moveInput(String player, List<Coords> checkMoveCells, boolean error);

    /**
     * asks the player to choose a builder and to build a structure
     * @param player the player's nickname
     * @param checkBuildCells a list of cell the builder can build on
     */
    public abstract void buildInput(String player, List<Coords> checkBuildCells, boolean error);

    /**
     * asks the player to insert a nickname for this match
     * @param error true if the previous input generated an error
     * @return the player's nickname
     */
    public abstract void nicknameInput(boolean error);

    /**
     * asks the player which god he wants to play with
     * @param player the player who has to choose
     * @param chosenGods the gods available to be chosen
     */
    public abstract void godInput(String player, List<String> chosenGods, boolean error);

    /**
     * asks the player the starting position of his builder
     * @param player the player who has to choose
     */
    public abstract void builderSetUpInput(String player, boolean callNumber, boolean error);

    /**
     * asks the challenger to choose a set of gods for this match
     * @param challenger the player who has to choose
     * @param godsList contains the name of all the gods available to choose from
     * @param godsNumber the number of gods the challenger has to choose
     */
    public abstract void godSelectionInput(String challenger, List<String> godsList, int godsNumber, boolean error);

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
     * @param username Name of the winner
     */
    public void notifyWin(String username) {}

}
