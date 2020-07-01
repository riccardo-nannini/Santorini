package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurnHandler {

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

    /**
     * The method asks the player which builder he wants to use this turn
     * @param player the current player
     * @return the coordinates of the chosen builder
     * @throws IOException
     */
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
                    } catch (InterruptedException ignored) {
                    }
                }
                if (!disconnectedPlayers.isEmpty()) {
                    if (!disconnectedPlayers.contains(player.getUsername())) {
                        while (builderPos == null && !disconnectedPlayers.contains(player.getUsername())) {
                            try {
                                wait();
                            } catch (InterruptedException ignored) {
                            }
                        }
                    }
                    virtualView.notifyDisconnection();
                }
                builder = matchHandler.getMatch().getBuilderByCoords(builderPos);
                valid = player.builderSelection(builder);
                if (valid && player.getPossibleMoves(builder).isEmpty()) {
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

    /**
     * The method asks the player where he wants to move his builder
     * @param builder the builder chosen for this turn
     * @param legalMoves the possible choices for the move action
     * @return the coordinates where the player wants to move the builder
     * @throws IOException
     */
    public synchronized Coords getInputMove(Builder builder, List<Coords> legalMoves) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.moveInput(username, legalMoves, error);
            while (moveCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (moveCoords == null && !disconnectedPlayers.contains(username)) {
                        try {
                            wait();
                        } catch (InterruptedException ignored) {
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

    /**
     * The method asks the player where he wants to build with his chosen builder
     * @param builder the builder chosen for this turn
     * @param legalBuilds the possible choices for the build action
     * @return the coordinates where the player wants to build on
     * @throws IOException
     */
    public synchronized Coords getInputBuild(Builder builder, List<Coords> legalBuilds) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.buildInput(username, legalBuilds, error);
            while (buildCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (buildCoords == null && !disconnectedPlayers.contains(username)) {
                        try {
                            wait();
                        } catch (InterruptedException ignored) {
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

    /**
     * This method asks the player if he wants to use the effect of his god in this turn
     * @param player the current player
     * @param god the player's god
     * @return true if the player wants to use the effect, false otherwise
     * @throws IOException
     */
    public synchronized boolean getInputUseEffect(String player, String god) throws IOException {
        boolean valid = false;
        boolean returnValue;
        do {
            virtualView.effectInput(player, god);
            while (useEffect == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(player)) {
                    while (useEffect == null && !disconnectedPlayers.contains(player)) {
                        try {
                            wait();
                        } catch (InterruptedException ignored) {
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

    /**
     * This method asks the player which block he wants to remove
     * @param builder current builder
     * @param legalRemoves list of blocks that can be removed
     * @return the coordinates of the block that the player wants to remove
     * @throws IOException
     */
    public synchronized Coords getInputRemoveBlock(Builder builder, List<Coords> legalRemoves) throws IOException {
        boolean error = false;
        Coords returnCoords;
        String username = matchHandler.getMatch().getPlayerByBuilder(builder).getUsername();
        do {
            virtualView.removeBlock(username, legalRemoves, error);
            while (removeCoords == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(username)) {
                    while (removeCoords == null && !disconnectedPlayers.contains(username)) {
                        try {
                            wait();
                        } catch (InterruptedException ignored) {
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
