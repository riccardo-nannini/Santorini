package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class HeraDebuffTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static TurnHandler handler;
    public static VirtualView view;

    @BeforeClass
    public static void setup() {
        MatchHandler matchHandler = new MatchHandler();
        match = matchHandler.getMatch();

        player = new Player(Color.Blue, "Mario");

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

        match.addPlayer(player);

        new Turn(match, handler);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        HeraDebuff debuff = new HeraDebuff(new Turn());
        player.setGod(debuff);

        match.setCellLevel(new Coords(2,3), Level.Medium);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.setCellLevel(new Coords(1,4), Level.Floor);
        match.setCellLevel(new Coords(2, 2), Level.Top);
        match.setCellLevel(new Coords(1, 0), Level.Medium);
        match.setCellLevel(new Coords(0, 0), Level.Top);
        match.setCellLevel(new Coords(0 , 1), Level.Floor);
        match.setCellLevel(new Coords(1, 1), Level.Top);
        match.setCellLevel(new Coords(1, 3), Level.Medium);

        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 3)));
    }

    @Test
    public void checkWin_NormalNotWinningMovement_ExpectedFalse() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(1,3));
        assertFalse(result);
        result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(0,1));
        assertFalse(result);
    }

    @Test
    public void checkWin_PerimetricWinningMovement_ExpectedFalse() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(2,4));
        assertFalse(result);
                result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(0,0));
        assertFalse(result);
    }

    @Test
    public void checkWin_NotPerimetralWinningMovement_ExpectedTrue() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(2,2));
        assertTrue(result);
        result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(1,1));
        assertTrue(result);
    }

    @Test
    public void completeTurn_CorrectInput_CorrectBehaviour() {
        Coords moveCoords = new Coords(2,4);
        Coords buildCoords = new Coords(1,4);

        try {
            player.start();

            if (player.checkMove(builder1,moveCoords)) {
                player.move(builder1, moveCoords);
            }
            if (player.checkBuild(builder1,buildCoords)) {
                player.build(builder1, buildCoords);
            }

            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(builder1.getCoords(),new Coords(2,4));
        assertSame(match.getHeight(new Coords(1, 4)), 1);
    }

}
