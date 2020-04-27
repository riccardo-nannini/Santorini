package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.List;

public class TurnHandler {

    //TODO volendo si può togliere il riferimento circolare a MatchHandler con qualche accorgimento

    private MatchHandler matchHandler;
    private String useEffect = null;
    private Coords builderPos = null;
    private Coords moveCoords = null;
    private Coords buildCoords = null;
    private Coords removeCoords = null;
    private final VirtualView virtualView;

    public TurnHandler(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public synchronized Coords getInputBuilder(Player player) throws IOException {
        boolean valid;
        Builder builder;
        do {
            virtualView.chooseBuilder(player.getUsername());
            try {
                while (builderPos == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //TODO
                    }
                }
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

    public synchronized Coords getInputMove(Builder builder, List<Coords> legalMoves) throws IOException {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.moveInput(username, legalMoves, error);
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

    public synchronized Coords getInputBuild(Builder builder, List<Coords> legalBuilds) throws IOException {
        boolean error = false;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.buildInput(username, legalBuilds, error);
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

    public synchronized boolean getInputUseEffect(String player, String god) throws IOException {
        boolean valid = false;
        do {
            virtualView.effectInput(player, god);
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

    public synchronized Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) throws IOException {
        boolean error;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        System.out.println("\n OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        do {
            error = false;
            virtualView.removeBlock(username, legalRemoves, error);
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
