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
    private Input input;
    private String nick;
    private String godsReceived;
    private String selectedGod;
    private Coords coords;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MatchHandler m = new MatchHandler();
        ViewObserver v = new ViewObserver(m);
        ObservableToController o = new ObservableToController();
        o.addObserver(v);
        CliInput cli = new CliInput(o);
        m.setInput(cli);
        m.init();
        m.play();
    }


    public void init() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        match = new Match();
        turnHandler = new TurnHandler(input);
        turnHandler.setMatchHandler(this);
        match.start(input);
        Turn.setMatch(match);
        Turn.setTurnHandler(turnHandler);
        numPlayers = 3;
        playerInit(input);
        godSelection(input);
        builderSetUp(input);

    }

    public void playerInit(Input input) {
        List<Color> colors = Color.getColors();
        for (int i=0; i < numPlayers; i++) {
            boolean valid, error;
            error = false;
            do {
                valid = true;
                input.nicknameInput(error);
                for (Player otherPlayers : match.getPlayers()) {
                    if (otherPlayers != null && otherPlayers.getUsername().equals(nick)) {
                        error = true;
                        valid = false;
                        break;
                    }
                }
            } while(!valid);
            Player currentPlayer = new Player(colors.remove(0), nick);
            match.addPlayer(currentPlayer);
        }
    }

    public void godSelection(Input input) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Random r = new Random();
        Player challenger = match.getPlayers().get(r.nextInt(3));
        String godsString = "Apollo, Ares, Artemis, Athena, Atlas, Demeter, Hephaestus, Hera, Hypnus, Minotaur, Pan ,Poseidon, Prometheus, Zeus";
        List<String> godsList = new ArrayList<String>(Arrays.asList(godsString.split("\\s*,\\s*")));

        boolean error;
        List<String> godsInput;
        do {
            error = false;
            input.godSelectionInput(challenger.getUsername(), godsList, numPlayers, error);
            godsInput = new ArrayList<>(Arrays.asList(godsReceived.split("\\s*,\\s*")));
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

        List<String> chosenGods = new ArrayList<String>(Arrays.asList(godsReceived.split("\\s*,\\s*")));

        godAssignment(input, challenger, chosenGods);

    }

    public void godAssignment(Input input, Player challenger, List<String> chosenGods) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean error;
        int pos = match.getPlayers().indexOf(challenger);
        for (int i = 0; i < numPlayers; i++) {
            error = false;
            pos = (pos+1) % numPlayers;
            String player = match.getPlayers().get(pos).getUsername();
            do {
                input.godInput(player, chosenGods, error);
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
        }
    }

    public void builderSetUp(Input input) {
        Player currentPlayer;
        Coords pos1;
        Coords pos2 = null;
        Builder[] builders = new Builder[2];
        boolean firstCall, error;

        for (int i=0; i < numPlayers; i++) {
            firstCall = true;
            error = false;
            pos1 = null;
            currentPlayer = match.getPlayers().get(i);
            for (int numBuilder = 0; numBuilder < 2; numBuilder++) {
                do {
                    input.builderSetUpInput(currentPlayer.getUsername(), firstCall, error);
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
            }
            currentPlayer.setBuilders(builders);
            currentPlayer.setup(builders[0], builders[1], pos1, pos2);
        }
    }

   //TODO potrebbero esserci problemi con le condizione di perdita nel caso di dei con input aggiuntivi
   //TODO gestire caso giocatore faccia move in una posizione in cui non può fare una build

    public  void play() {
        List<Player> players = match.getPlayers();
        List<Coords> possibleMoves, possibleBuilds;
        Builder currentBuilder;
        Player winner = null;
        endGame = false;
        while (!endGame) {
            for (Player currentPlayer : players) {
                if (players.size() < 2) {
                    input.notifyWin(currentPlayer.getUsername());
                    endGame = true;
                    break;
                }
                turnHandler.getInputBuilder(currentPlayer);
                currentBuilder = match.getBuilderByCoords(coords);
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
        input.notifyWin(winner.getUsername());
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setGodsReceived(String godsReceived) {
        this.godsReceived = godsReceived;
    }

    public void setSelectedGod(String selectedGod) {
        this.selectedGod = selectedGod;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
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
