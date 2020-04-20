package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.ArrayList;
import java.util.List;

public class TurnHandler {

    //TODO volendo si può togliere il riferimento circolare a MatchHandler con qualche accorgimento

    private MatchHandler matchHandler;
    private String useEffect;
    private Coords builderPos;
    private Coords moveCoords;
    private Coords buildCoords;
    private Coords removeCoords;
    private final Input input;

    public TurnHandler(Input input) {
        this.input = input;
    }

    public void getInputBuilder(Player player) {
        boolean valid;
        Builder builder;
        do {
            input.chooseBuilder(player.getUsername());
            try {
                builderPos = matchHandler.getCoords();
                builder = matchHandler.getMatch().getBuilderByCoords(builderPos);
                valid = player == matchHandler.getMatch().getPlayerByBuilder(builder);
                if (player.getCellMoves(builder).isEmpty()) valid = false;
            } catch (IllegalArgumentException e) {
                valid = false;
            }
        } while(!valid);
    }

    public Coords getInputMove(Builder builder, List<Coords> legalMoves) {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            input.moveInput(username, legalMoves, error);
            error = !legalMoves.contains(moveCoords);
        } while(error);
        Player player = matchHandler.getMatch().getPlayerByBuilder(builder);
        if (player.win(builder, builder.getCoords(), moveCoords)) matchHandler.setEndGame(true);
        return moveCoords;
    }

    public Coords getInputBuild(Builder builder, List<Coords> legalBuilds) {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            input.buildInput(username, legalBuilds, error);
            error = !legalBuilds.contains(buildCoords);
        } while(error);
        return buildCoords;
    }

    public boolean getInputUseEffect(String god) {
        boolean valid = false;
        do {
            input.effectInput(god);
            if (useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y")
                    || useEffect.toLowerCase().equals("no") || useEffect.toLowerCase().equals("n")) valid = true;
        } while (!valid);

        return useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y");
    }

    public Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) {
        boolean error;
        do {
            error = false;
            input.removeBlock(legalRemoves, error);
            if (!legalRemoves.contains(removeCoords)) error = true;
        } while(error);
        return buildCoords;
    }

    public void setUseEffect(String useEffect) {
        this.useEffect = useEffect;
    }

    public void setBuilderPos(Coords builderPos) {
        this.builderPos = builderPos;
    }

    public void setMatchHandler(MatchHandler match) {
        this.matchHandler = match;
    }

    public void setMoveCoords(Coords moveCoords) {
        this.moveCoords = moveCoords;
    }

    public void setBuildCoords(Coords buildCoords) {
        this.buildCoords = buildCoords;
    }

    public void setRemoveCoords(Coords removeCoords) {
        this.removeCoords = removeCoords;
    }

}
