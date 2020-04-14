package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class TurnHandler {

    private Input input;

    public Builder getInputBuilder(Player player) {
        return null;
    }

    public Coords getInputMoveCoords(Player player, List<Coords> legalMoves) {
        return null;
    }

    public Coords getInputBuildCoords(Player player, List<Coords> legalBuilds) {
        return null;
    }

    public Coords getInputMove(Builder builder, List<Coords> legalMoves) {
        return null;
    }

    public Coords getInputBuild(Builder builder, List<Coords> legalBuilds) {
        return null;
    }

    public boolean getInputUseEffect() {
        return true;
    }

    public Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) {return null;};

}
