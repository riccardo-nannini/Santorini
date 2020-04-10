package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.view.Input;

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

    public void init() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        match = new Match();
        match.start();
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
            input.godSelectionInput(challenger.getUsername(), godsList);
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
            Object god = clazz.getDeclaredConstructor().newInstance();
            Player currentPlayer = match.getPlayers().get(pos);
            currentPlayer.setGod((Turn) god);
        }
    }

    public void builderSetUp(Input input) {
        Player currentPlayer;
        Coords pos1 = null;
        Coords pos2 = null;
        Builder[] builders = new Builder[2];

        for (int i=0; i < numPlayers; i++) {
            currentPlayer = match.getPlayers().get(i);
            for (int numBuilder = 0; numBuilder < 2; numBuilder++) {
                do {
                    input.builderSetUpInput(currentPlayer.getUsername());
                } while (match.isOccupied(coords));
                builders[numBuilder] = new Builder();
            }
            currentPlayer.god.setup(builders[0], builders[1], pos1, pos2);
        }
    }

    public  void play() {}

}
