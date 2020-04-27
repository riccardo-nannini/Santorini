package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.gods.Hera;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeraTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer;
    public static Builder opponentsbuilder1;
    public static Builder opponentsbuilder2;
    public static TurnHandler handler;
    public static VirtualView view;

    @BeforeClass
    public static void setup() {
        MatchHandler matchHandler = null;
        try {
            matchHandler = new MatchHandler();
            match = matchHandler.getMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(Color.Blue, "Mario");
        opponentPlayer = new Player(Color.Yellow, "Diego");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        HashMap<String, ObjectOutputStream> outputMap = new HashMap<>();
        ObjectOutputStream stream;
        try {
            stream = new ObjectOutputStream(System.out);
            outputMap.put(player.getUsername(),stream);
            view = new VirtualView(outputMap);

            handler = new TurnHandler(view);
            handler.setMatchHandler(matchHandler);
            match.start(view);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Turn(match, handler);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Hera());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1, opponentsbuilder2});
        opponentPlayer.setGod(new Turn());
    }

    @Test
    public void AppliedDebuff_OpponentTest() {
        try {
            player.setup(player.getBuilders()[0], player.getBuilders()[1],
                    new Coords(0,0), new Coords(0,1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(opponentPlayer.getGod() instanceof HeraDebuff);
    }

    @Test
    public void NotAppliedDebuff_SamePlayerTest() {
        try {
            player.setup(player.getBuilders()[0], player.getBuilders()[1],
                    new Coords(0,0), new Coords(0,1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(player.getGod() instanceof HeraDebuff);
    }

}
