package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Coords;

/**
 * This is the controller observer class which is subscribed to the view
 */
public class ViewObserver {

    private final MatchHandler handler;

    public ViewObserver(MatchHandler matchHandler)
    {
        this.handler = matchHandler;
    }

    /**
     * Sets the value of the god to the player instance and updates the list of gods left
     * @param god the god chosen by user
     */
    public void updateGod(String god)
    {
        handler.setSelectedGod(god);
    }

    /**
     * Sets the gods available for this match
     * @param gods
     */
    public void updateGodSelection(String gods)
    {
        handler.setGodsReceived(gods);
    }

    /**
     * Sets the initial coordinates of player builders
     * @param builder
     */
    public void updateSetupBuilder(Coords builder)
    {
        handler.setCoords(builder);
    }

    /**
     * Selects the builder chosen by the user
     * @param builder
     */
    public void updateBuilderChoice(Coords builder){ handler.getTurnHandler().setBuilderPos(builder);}

    /**
     * Sets the new position of a given builder
     * @param cellToMoveOn
     */
    public void updateMoveInput(Coords cellToMoveOn){
        handler.getTurnHandler().setMoveCoords(cellToMoveOn);
    }

    /**
     * Sets the new structure level of the chosen position
     * @param cellToBuildOn
     */
    public void updateBuildInput(Coords cellToBuildOn){
        handler.getTurnHandler().setBuildCoords(cellToBuildOn);
    }

    /**
     * Selects the answer of the player in using the effect (yes or no)
     * @param effect
     */
    public void updateEffect(String effect)
    {
        handler.getTurnHandler().setUseEffect(effect);
    }

    /**
     * Selects the cell to remove a block from
     * @param cellToRemoveBlock
     */
    public void updateRemoveInput(Coords cellToRemoveBlock)
    {
        handler.getTurnHandler().setRemoveCoords(cellToRemoveBlock);
    }

    public void updateDisconnection(String player) {
        handler.getTurnHandler().addDisconnectedPlayer(player);
        handler.addDisconnectedPlayer(player);
    }

    public void updateStarter(String starter) {
        handler.setSelectedStarter(starter);
    }
}
