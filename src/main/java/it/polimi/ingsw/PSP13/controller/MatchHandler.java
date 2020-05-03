package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MatchHandler {

    private Match match;
    private TurnHandler turnHandler;

    private List<String> disconnectedPlayers = new ArrayList<String>();
    private int numPlayers;
    boolean endGame;
    private VirtualView virtualView;
    private String godsReceived = null;
    private String selectedGod = null;
    private Coords coords = null;

    public MatchHandler () {
        match = new Match();
    }

    public void init() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        numPlayers = match.getPlayers().size();
        turnHandler = new TurnHandler(virtualView);
        turnHandler.setMatchHandler(this);
        Turn.setMatch(match);
        Turn.setTurnHandler(turnHandler);
        match.start(virtualView);
        godSelection(virtualView);
        virtualView.notifyClientsInfo();
        builderSetUp(virtualView);
    }

    public void addPlayer(Player player) {
        match.addPlayer(player);
    }

    public synchronized void godSelection(VirtualView virtualView) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, IOException {
        //TODO gestire eccezioni invece di throws
        Random r = new Random();
        Player challenger = match.getPlayers().get(r.nextInt(3));
        String godsString = "Apollo, Ares, Artemis, Athena, Atlas, Demeter, Hephaestus, Hera, Hypnus, Minotaur, Pan ,Poseidon, Prometheus, Zeus";
        List<String> godsList = new ArrayList<String>(Arrays.asList(godsString.split("\\s*,\\s*")));

        boolean error;
        List<String> godsInput;
        do {
            error = false;
            virtualView.godSelectionInput(challenger.getUsername(), godsList, numPlayers, error);
            while (godsReceived == null && disconnectedPlayers.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
            if (!disconnectedPlayers.isEmpty()) {
                if (!disconnectedPlayers.contains(challenger.getUsername())) {
                    while (godsReceived == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                }
                virtualView.notifyDisconnection();
            }
            godsInput = new ArrayList<>(Arrays.asList(godsReceived.split("\\s*,\\s*")));
            godsReceived = null;
            if (!godsList.containsAll(godsInput)) error = true;
            if (godsInput.size() != numPlayers) error = true;
            for (String currentGod : godsInput) {
                for (String otherGod : godsInput) {
                    if (currentGod != otherGod && currentGod.equals(otherGod)) {
                        error = true;
                        break;
                    }
                }
            }
        } while(error);

        godAssignment(virtualView, challenger, godsInput);
    }

    public synchronized void godAssignment(VirtualView virtualView, Player challenger, List<String> chosenGods) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        boolean error;
        String receivedGod;
        int pos = match.getPlayers().indexOf(challenger);
        for (int i = 0; i < numPlayers; i++) {
            error = false;
            pos = (pos+1) % numPlayers;
            String player = match.getPlayers().get(pos).getUsername();
            if (chosenGods.size() > 1) {
                do {
                    virtualView.godInput(player, chosenGods, error);
                    while (selectedGod == null && disconnectedPlayers.isEmpty()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                    if (!disconnectedPlayers.isEmpty()) {
                        if (!disconnectedPlayers.contains(player)) {
                            while (selectedGod == null) {
                                try {
                                    wait();
                                } catch (InterruptedException e) {
                                    //TODO
                                }
                            }
                        }
                        virtualView.notifyDisconnection();
                    }
                    error = false;
                    if (!chosenGods.contains(selectedGod)) error = true;
                    receivedGod = selectedGod;
                    selectedGod = null;
                } while (error);
            } else {
                virtualView.godInput(player, chosenGods, error);
                receivedGod = chosenGods.get(0);
            }
            chosenGods.remove(receivedGod);
            Class<?> clazz = Class.forName("it.polimi.ingsw.PSP13.model.gods." + receivedGod);
            Class[] c = new Class[0];
            Object[] ob = new Object[0];
            Object god = clazz.getDeclaredConstructor(c).newInstance(ob);
            Player currentPlayer = match.getPlayers().get(pos);
            currentPlayer.setGod((Turn) god);
            virtualView.setGod(player,receivedGod);

        }
    }

    /**
     * Handles the set up of the builders for each players
     * @param virtualView
     * @throws IOException
     */
    public synchronized void builderSetUp(VirtualView virtualView) throws IOException {
        Player currentPlayer;
        Coords pos1;
        Coords pos2 = null;
        Coords receivedCoords;
        Builder[] builders = new Builder[2];
        boolean firstCall, error;
        match.notifyMap();
        for (int i=0; i < numPlayers; i++) {
            firstCall = true;
            error = false;
            pos1 = null;
            currentPlayer = match.getPlayers().get(i);
            for (int numBuilder = 0; numBuilder < 2; numBuilder++) {
                do {
                    virtualView.builderSetUpInput(currentPlayer.getUsername(), firstCall, error);
                    while (coords == null && disconnectedPlayers.isEmpty()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                    if (!disconnectedPlayers.isEmpty()) {
                        if (!disconnectedPlayers.contains(currentPlayer.getUsername())) {
                            while (coords == null) {
                                try {
                                    wait();
                                } catch (InterruptedException e) {
                                    //TODO
                                }
                            }
                        }
                        virtualView.notifyDisconnection();
                    }
                    error = false;
                    if (match.isOccupied(coords)) error = true;
                    if (pos1 != null && pos1.equals(coords)) error = true;
                    receivedCoords = coords;
                    coords = null;
                } while (error);
                if (pos1 == null) {
                    pos1 = receivedCoords;
                } else {
                    pos2 = receivedCoords;
                }
                builders[numBuilder] = new Builder();
                firstCall = false;
            }
            currentPlayer.setBuilders(builders);
            currentPlayer.setup(builders[0], builders[1], pos1, pos2);
        }
    }

    /**
     * Controller's method that handles the progress of the game
     * @throws IOException
     */
    public synchronized void play() throws IOException {
        List<Player> players;
        List<Coords> possibleMoves, possibleBuilds;
        Builder currentBuilder;
        Coords builderPos;
        Player winner = null;
        endGame = false;
        while (!endGame) {
            players = Collections.unmodifiableList(new ArrayList<>(match.getPlayers()));
            for (Player currentPlayer : players) {
                if (players.size() < 2) {
                    virtualView.notifyWin(currentPlayer.getUsername());
                    endGame = true;
                    break;
                }
                builderPos = turnHandler.getInputBuilder(currentPlayer);
                currentBuilder = match.getBuilderByCoords(builderPos);
                currentPlayer.start();
                possibleMoves = currentPlayer.getCellMoves(currentBuilder);
                if (possibleMoves.isEmpty()) {
                    virtualView.notifyLose(currentPlayer.getUsername());
                    match.removeBuilder(currentPlayer);
                    match.getPlayers().remove(currentPlayer);
                    continue;
                }
                coords = turnHandler.getInputMove(currentBuilder, possibleMoves);
                currentPlayer.move(currentBuilder, coords);
                if (endGame) {
                    winner = currentPlayer;
                    break;
                }
                possibleBuilds = currentPlayer.getCellBuilds(currentBuilder);
                if (possibleBuilds.isEmpty()) {
                    virtualView.notifyLose(currentPlayer.getUsername());
                    match.removeBuilder(currentPlayer);
                    match.getPlayers().remove(currentPlayer);
                    continue;
                }
                coords = turnHandler.getInputBuild(currentBuilder, possibleBuilds);
                currentPlayer.build(currentBuilder, coords);
                currentPlayer.end();
            }
        }
        this.notifyWinners(winner.getUsername());
    }


    /**
     * Notifies the players that the game ended and tells them if they did win or lose.
     * @param winner username of the winner
     * @throws IOException
     */
    public void notifyWinners(String winner) throws IOException {
        virtualView.notifyWin(winner);
        for (Player player : match.getPlayers()) {
            if (!player.getUsername().equals(winner)) virtualView.notifyLose(player.getUsername());
        }
    }

    public synchronized void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        notifyAll();
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public synchronized void setGodsReceived(String godsReceived) {
        this.godsReceived = godsReceived;
        notifyAll();
    }

    public synchronized void setSelectedGod(String selectedGod) {
        this.selectedGod = selectedGod;
        notifyAll();
    }

    public synchronized void setCoords(Coords coords) {
        this.coords = coords;
        notifyAll();
    }

    public synchronized void setEndGame(boolean endGame) {
        this.endGame = endGame;
        notifyAll();
    }

    public synchronized void addDisconnectedPlayer(String player) {
        disconnectedPlayers.add(player);
        notifyAll();
    }

    public Match getMatch() {
        return match;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public Coords getCoords() {
        return coords;
    }

}