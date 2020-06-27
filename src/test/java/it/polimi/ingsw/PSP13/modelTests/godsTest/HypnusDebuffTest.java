package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.debuffs.HypnusDebuff;
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

public class HypnusDebuffTest {

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

        new Turn(match, null);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        HypnusDebuff debuff = new HypnusDebuff(new Turn());
        player.setGod(debuff);

        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 2)));

        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(4,4), Level.Top);
        match.setCellLevel(new Coords(4,3), Level.Medium);
        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(3,2), Level.Top);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.setCellLevel(new Coords(2,3), Level.Floor);
        match.setCellLevel(new Coords(2,2), Level.Top);

    }

    @Before
    public void setUp() {
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 2)));
        player.getBuilders()[0].setCell(match.getCell(new Coords(0,0)));
        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(4,4), Level.Top);
        match.setCellLevel(new Coords(4,3), Level.Medium);
        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(3,2), Level.Medium);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.setCellLevel(new Coords(2,3), Level.Floor);
        match.setCellLevel(new Coords(2,2), Level.Medium);
    }


    @Test
    public void ChoosingLowerBuilder_CorrectInput_ExpectedTrue() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 3)));
        assertTrue(player.builderSelection(player.getBuilders()[0]));
    }

    @Test
    public void ChoosingBuilderOnTheSameLevel_ExpectedTrue() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(3, 2)));
        assertTrue(player.builderSelection(player.getBuilders()[0]));
    }

    @Test
    public void ChoosingHigherBuilder_CorrectInput_ExpectedFalse() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 4)));
        assertFalse(player.builderSelection(player.getBuilders()[0]));
    }

    @Test
    public void completeTurn_CorrectInput_CorrectBehaviour() {
        Coords moveCoords = new Coords(3,3);
        Coords buildCoords = new Coords(2,3);

        try {
            player.start();

            if (player.checkMove(builder2,moveCoords)) {
                if (player.getCellMoves(builder2).contains(moveCoords)) {
                    player.getCellMoves(builder2);
                    player.move(builder2, moveCoords);
                }
            }
            if (player.checkBuild(builder2,buildCoords)) {
                if (player.getCellBuilds(builder2).contains(buildCoords)) {
                    player.build(builder2, buildCoords);
                }
            }

            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(builder2.getCoords(),new Coords(3,3));
        assertSame(match.getHeight(new Coords(2, 3)), 1);
    }


}
