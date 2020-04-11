package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.util.List;

public class TurnHandler {

    Player player;

    public Coords getInputMove(Builder builder, List<Coords> legalBuilds) {
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
