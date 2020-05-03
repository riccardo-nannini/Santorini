package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurnHandler {

    //TODO volendo si può togliere il riferimento circolare a MatchHandler con qualche accorgimento

    private MatchHandler matchHandler;
    private List<String> disconnectedPlayers = new ArrayList<>();
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
        Coords returnCoords;
        do {
            virtualView.chooseBuilder(player.getUsername());
            try {
                while (builderPos == null && disconnectedPlayers.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //TODO
                    }
                }
                if (!disconnectedPlayers.isEmpty()) {
                    if (!disconnectedPlayers.contains(player.getUsername())) {
                        while (builderPos == null) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                //TODO
                            }
                        }
                    }
                    virtualView.notifyDisconnection();
                }
                builder = matchHandler.getMatch().getBuilderByCoords(builderPos);
                valid = player == matchHandler.getMatch().getPlayerByBuilder(builder);
                if (valid && player.getCellMoves(builder).isEmpty()) {
                    Builder otherBuilder = player.getBuilders()[0] == builder ? player.getBuilders()[1] : player.getBuilders()[0];
                    builderPos = otherBuilder.getCoords();
                }
            } catch (IllegalArgumentException e) {
                valid = false;
            } finally {
                returnCoords = builderPos;
                builderPos = null;
            }
        } while(!valid);
        return returnCoords;
    }

    public synchronized Coords getInputMove(Builder builder, List<Coords> legalMoves) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.moveInput(username, legalMoves, error);
            while (moveCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (moveCoords == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                }
                virtualView.notifyDisconnection();
            }
            error = !legalMoves.contains(moveCoords);
            returnCoords = moveCoords;
            moveCoords = null;
        } while(error);
        Player player = matchHandler.getMatch().getPlayerByBuilder(builder);
        if (player.win(builder, builder.getCoords(), returnCoords)) matchHandler.setEndGame(true);
        return returnCoords;
    }

    public synchronized Coords getInputBuild(Builder builder, List<Coords> legalBuilds) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.buildInput(username, legalBuilds, error);
            while (buildCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (buildCoords == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                }
                virtualView.notifyDisconnection();
            }
            returnCoords = buildCoords;
            error = !legalBuilds.contains(buildCoords);
            buildCoords = null;
        } while(error);
        return returnCoords;
    }

    public synchronized boolean getInputUseEffect(String player, String god) throws IOException {
        boolean valid = false;
        boolean returnValue;
        do {
            virtualView.effectInput(player, god);
            while (useEffect == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(player)) {
                    while (useEffect == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                }
                virtualView.notifyDisconnection();
            }
            if (useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y")
                    || useEffect.toLowerCase().equals("no") || useEffect.toLowerCase().equals("n")) valid = true;
            returnValue =  useEffect.toLowerCase().equals("yes") || useEffect.toLowerCase().equals("y");
            useEffect = null;
        } while (!valid);
        return returnValue;
    }

    public synchronized Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.removeBlock(username, legalRemoves, error);
            while (removeCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (removeCoords == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                }
                virtualView.notifyDisconnection();
            }
            error = !legalRemoves.contains(removeCoords);
            returnCoords = removeCoords;
            removeCoords = null;
        } while(error);
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

    public synchronized void addDisconnectedPlayer(String player) {
        disconnectedPlayers.add(player);
        notifyAll();
    }
}
