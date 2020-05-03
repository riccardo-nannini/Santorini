package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * this is the controller observer class which is subscribed to the view
 */
public class ViewObserver {

    private final MatchHandler handler;

    public ViewObserver(MatchHandler matchHandler)
    {
        this.handler = matchHandler;
    }

    /**
     * sets the value of the god to the player instance and updates the list of gods left
     * @param god the god chosen by user
     */
    public void updateGod(String god)
    {
        handler.setSelectedGod(god);
    }

    /**
     * sets the gods available for this match
     * @param gods
     */
    public void updateGodSelection(String gods)
    {
        handler.setGodsReceived(gods);
    }

    /**
     * sets the initial coordinates of player builders
     * @param builder
     */
    public void updateSetupBuilder(Coords builder)
    {
        handler.setCoords(builder);
    }

    /**
     * selects the builder chosen by the user
     * @param builder
     */
    public void updateBuilderChoice(Coords builder){ handler.getTurnHandler().setBuilderPos(builder);}

    /**
     * sets the new position of a given builder
     * @param cellToMoveOn
     */
    public void updateMoveInput(Coords cellToMoveOn){
        handler.getTurnHandler().setMoveCoords(cellToMoveOn);
    }

    /**
     * sets the new structure level of the chosen position
     * @param cellToBuildOn
     */
    public void updateBuildInput(Coords cellToBuildOn){
        handler.getTurnHandler().setBuildCoords(cellToBuildOn);
    }

    /**
     * selects the answer of the player in using the effect (yes or no)
     * @param effect
     */
    public void updateEffect(String effect)
    {
        handler.getTurnHandler().setUseEffect(effect);
    }

    /**
     * selects the cell to remove a block from
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
}
