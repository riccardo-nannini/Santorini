package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.CLI.CliInput;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatchHandler {

    private Match match;
    private TurnHandler turnHandler;

    private int numPlayers;
    boolean endGame;
    private VirtualView virtualView;
    private String godsReceived = null;
    private String selectedGod = null;
    private Coords coords = null;

    public MatchHandler () throws IOException {
        match = new Match();
    }

    public void init() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        numPlayers = 3;
        turnHandler = new TurnHandler(virtualView);
        turnHandler.setMatchHandler(this);
        Turn.setMatch(match);
        Turn.setTurnHandler(turnHandler);
        match.start(virtualView);
        godSelection(virtualView);
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
            while (godsReceived == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //TODO
                }
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
        int pos = match.getPlayers().indexOf(challenger);
        for (int i = 0; i < numPlayers; i++) {
            error = false;
            pos = (pos+1) % numPlayers;
            String player = match.getPlayers().get(pos).getUsername();
            do {
                virtualView.godInput(player, chosenGods, error);
                while (selectedGod == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //TODO
                    }
                }
                error = false;
                if (!chosenGods.contains(selectedGod)) error = true;
            } while(error);
            chosenGods.remove(selectedGod);
            Class<?> clazz = Class.forName("it.polimi.ingsw.PSP13.model.gods." + selectedGod);
            Class[] c = new Class[0];
            Object[] ob = new Object[0];
            Object god = clazz.getDeclaredConstructor(c).newInstance(ob);
            Player currentPlayer = match.getPlayers().get(pos);
            currentPlayer.setGod((Turn) god);
            selectedGod = null;
        }
    }

    //TODO bug quando viene sbagliata la posizione del secondo builder
    public synchronized void builderSetUp(VirtualView virtualView) throws IOException {
        Player currentPlayer;
        Coords pos1;
        Coords pos2 = null;
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
                    while (coords == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            //TODO
                        }
                    }
                    error = false;
                    if (match.isOccupied(coords)) error = true;
                    if (pos1 != null && pos1.equals(coords)) error = true;
                } while (error);
                if (pos1 == null) {
                    pos1 = coords;
                } else {
                    pos2 = coords;
                }
                builders[numBuilder] = new Builder();
                firstCall = false;
                error = false;
                coords = null;
            }
            currentPlayer.setBuilders(builders);
            currentPlayer.setup(builders[0], builders[1], pos1, pos2);
        }
    }

    public synchronized void play() throws IOException {
        //TODO potrebbero esserci problemi con le condizione di perdita nel caso di dei con input aggiuntivi
        //TODO gestire caso giocatore faccia move in una posizione in cui non può fare una build
        List<Player> players = match.getPlayers();
        List<Coords> possibleMoves, possibleBuilds;
        Builder currentBuilder;
        Coords builderPos;
        Player winner = null;
        endGame = false;
        while (!endGame) {
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
                    players.remove(currentPlayer);
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
                    players.remove(currentPlayer);
                    continue;
                }
                coords = turnHandler.getInputBuild(currentBuilder, possibleBuilds);
                currentPlayer.build(currentBuilder, coords);
                currentPlayer.end();
            }
        }
        virtualView.notifyWin(winner.getUsername());
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
