package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class AthenaDebuffTest {

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

        player = new Player(Color.Blue, "Mario");

        match.addPlayer(player);
        new Turn(match, null);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        AthenaDebuff debuff = new AthenaDebuff(new Turn());
        player.setGod(debuff);

    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 3)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(4, 3)));
        match.setCellLevel(new Coords(1,1), Level.Top);
        match.setCellLevel(new Coords(3,3), Level.Medium);
        match.setCellLevel(new Coords(2,3), Level.Medium);
        match.setCellLevel(new Coords(3,4), Level.Base);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.setCellLevel(new Coords(2, 2), Level.Top);
        AthenaDebuff debuff = new AthenaDebuff(new Turn());
        player.setGod(debuff);
    }

    @Test
    public void SameLevelMove_CorrectInput_ExpectedTrue(){
        assertTrue(player.checkMove(player.getBuilders()[0], new Coords(3,3)));
    }

    @Test
    public void MovingDown_CorrectInput_ExpectedTrue(){
        assertTrue(player.checkMove(player.getBuilders()[0], new Coords(3,4)));
    }

    @Test
    public void MovingUp_CorrectInput_ExpectedFalse(){
        assertFalse(player.checkMove(player.getBuilders()[0], new Coords(2,4)));
    }

    @Test
    public void endOfTurn_CorrectInput_DecoretorRemovedExpected(){
        player.checkMove(player.getBuilders()[0], new Coords(3,3));
        try {
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(player.getGod() instanceof AthenaDebuff);
    }

    @Test
    public void completeTurn_CorrectInput_CorrectBehaviour() {
        Coords moveCoords = new Coords(3,3);
        Coords buildCoords = new Coords(2,3);

        try {
            player.start();

            if (player.checkMove(builder1,moveCoords)) {
                if (player.getPossibleMoves(builder1).contains(moveCoords)) {
                    player.getPossibleMoves(builder1);
                    player.move(builder1, moveCoords);
                }
            }
            if (player.checkBuild(builder1,buildCoords)) {
                if (player.getBuildableCells(builder1).contains(buildCoords)) {
                    player.build(builder1, buildCoords);
                }
            }

            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(builder1.getCoords(),new Coords(3,3));
        assertSame(match.getHeight(new Coords(2, 3)), 3);
    }





}
