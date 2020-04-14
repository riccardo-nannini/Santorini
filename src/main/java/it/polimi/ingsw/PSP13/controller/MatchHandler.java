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
    private TurnHandler turn;

    private int numPlayers;
    private Input input;
    private String nick;
    private String godsReceived;
    private String selectedGod;
    private Coords coords;
    private Builder currentBuilder;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MatchHandler m = new MatchHandler();
        ViewObserver v = new ViewObserver(m);
        ObservableToController o = new ObservableToController();
        o.addObserver(v);
        CliInput cli = new CliInput(o);
        m.setInput(cli);
        m.init();
    }


    public void init() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        match = new Match();
        match.start();
        new Turn(match, new TurnHandler());
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

        boolean valid;
        List<String> godsInput;
        do {
            valid = true;
            input.godSelectionInput(challenger.getUsername(), godsList, numPlayers);
            godsInput = new ArrayList<>(Arrays.asList(godsReceived.split("\\s*,\\s*")));
            if (!godsList.containsAll(godsInput)) valid = false;
            for (String currentGod : godsInput) {
                for (String otherGod : godsInput) {
                    if (currentGod != otherGod && currentGod.equals(otherGod)) {
                        valid = false;
                        break;
                    }
                }
            }
        } while(!valid);

        List<String> chosenGods = new ArrayList<String>(Arrays.asList(godsReceived.split("\\s*,\\s*")));

        godAssignment(input, challenger, chosenGods);

    }

    public void godAssignment(Input input, Player challenger, List<String> chosenGods) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean valid;
        int pos = match.getPlayers().indexOf(challenger);
        for (int i = 0; i < numPlayers; i++) {
            pos = (pos+1) % numPlayers;
            String player = match.getPlayers().get(pos).getUsername();
            do {
                valid = true;
                input.godInput(player, chosenGods);
                if (!chosenGods.contains(selectedGod)) valid = false;
            } while(!valid);
            chosenGods.remove(selectedGod);
            Class<?> clazz = Class.forName("it.polimi.ingsw.PSP13.model.gods." + selectedGod);
            Class[] c = new Class[1];
            c[0] = TurnHandler.class;
            Object[] ob = new Object[1];
            ob[0] = new TurnHandler();
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

        for (int i=0; i < numPlayers; i++) {
            pos1 = null;
            currentPlayer = match.getPlayers().get(i);
            for (int numBuilder = 0; numBuilder < 2; numBuilder++) {
                do {
                    input.builderSetUpInput(currentPlayer.getUsername());
                } while (match.isOccupied(coords));
                if (pos1 == null) { //TODO aggiungere check che seconda pos != prima pos
                    pos1 = coords;
                } else {
                    pos2 = coords;
                }
                builders[numBuilder] = new Builder();
            }
            currentPlayer.setBuilders(builders);
            currentPlayer.setup(builders[0], builders[1], pos1, pos2);
        }
    }

    public  void play() {
        List<Player> players = match.getPlayers();
        Player currentPlayer;
        Coords precedentPosition;
        while (true) {
            for (int i = 0; i < numPlayers; i++) {
                currentPlayer = players.get(i);
                currentPlayer.start();
                turn.getInputBuilder(currentPlayer);
                precedentPosition = currentBuilder.getCoords();
                turn.getInputMove(currentBuilder, currentPlayer.getCellMoves(currentBuilder));
                currentPlayer.move(currentBuilder, coords);
                if (currentPlayer.win(currentBuilder, precedentPosition, coords)) // fai vincere
                turn.getInputBuild(currentBuilder, currentPlayer.getCellBuilds(currentBuilder));
                currentPlayer.build(currentBuilder, coords);
                currentPlayer.end();
            }
        }

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
}
