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
    private String useEffect = null;
    private Coords builderPos = null;
    private Coords moveCoords = null;
    private Coords buildCoords = null;
    private Coords removeCoords = null;
    private final Input input;

    public TurnHandler(Input input) {
        this.input = input;
    }

    public synchronized Coords getInputBuilder(Player player) {
        boolean valid;
        Builder builder;
        do {
            input.chooseBuilder(player.getUsername());
            try {
                while (builderPos == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //TODO
                    }
                }
                builderPos = matchHandler.getCoords();
                builder = matchHandler.getMatch().getBuilderByCoords(builderPos);
                valid = player == matchHandler.getMatch().getPlayerByBuilder(builder);
                if (player.getCellMoves(builder).isEmpty()) valid = false;
            } catch (IllegalArgumentException e) {
                valid = false;
            }
        } while(!valid);
        Coords returnCoords = builderPos;
        builderPos = null;
        return returnCoords;
    }

    public synchronized Coords getInputMove(Builder builder, List<Coords> legalMoves) {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            input.moveInput(username, legalMoves, error);
            while (moveCoords == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            error = !legalMoves.contains(moveCoords);
        } while(error);
        Player player = matchHandler.getMatch().getPlayerByBuilder(builder);
        if (player.win(builder, builder.getCoords(), moveCoords)) matchHandler.setEndGame(true);
        Coords returnCoords = moveCoords;
        moveCoords = null;
        return returnCoords;
    }

    public synchronized Coords getInputBuild(Builder builder, List<Coords> legalBuilds) {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            input.buildInput(username, legalBuilds, error);
            while (buildCoords == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            error = !legalBuilds.contains(buildCoords);
        } while(error);
        Coords returnCoords = buildCoords;
        buildCoords = null;
        return returnCoords;
    }

    public synchronized boolean getInputUseEffect(String god) {
        boolean valid = false;
        do {
            input.effectInput(god);
            while (useEffect == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y")
                    || useEffect.toLowerCase().equals("no") || useEffect.toLowerCase().equals("n")) valid = true;
        } while (!valid);
        boolean returnValue = useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y");
        useEffect = null;
        return returnValue;
    }

    public synchronized Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) {
        boolean error;
        do {
            error = false;
            input.removeBlock(legalRemoves, error);
            while (removeCoords == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!legalRemoves.contains(removeCoords)) error = true;
        } while(error);
        Coords returnCoords = removeCoords;
        removeCoords = null;
        return returnCoords;
    }

    public synchronized void setUseEffect(String useEffect) {
        this.useEffect = useEffect;
        notifyAll();
    }

    public synchronized void setBuilderPos(Coords builderPos) {
        this.builderPos = builderPos;
        notifyAll();
    }

    public synchronized void setMoveCoords(Coords moveCoords) {
        this.moveCoords = moveCoords;
        notifyAll();
    }

    public synchronized void setBuildCoords(Coords buildCoords) {
        this.buildCoords = buildCoords;
        notifyAll();
    }

    public synchronized void setRemoveCoords(Coords removeCoords) {
        this.removeCoords = removeCoords;
        notifyAll();
    }

    public void setMatchHandler(MatchHandler match) {
        this.matchHandler = match;
    }

}
