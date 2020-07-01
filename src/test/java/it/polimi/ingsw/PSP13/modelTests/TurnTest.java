package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
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
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class TurnTest {

    private static Turn turn;
    private static Builder[] builders = new Builder[2];
    private static Match match;
    public static TurnHandler handler;
    public static VirtualView view;

    @BeforeClass
    public static void init()
    {
        MatchHandler matchHandler = new MatchHandler();
        match = matchHandler.getMatch();
        Player test = new Player(Color.Blue,"test");

        HashMap<String, ObjectOutputStream> outputMap = new HashMap<>();
        ObjectOutputStream stream;

        try {
            stream = new ObjectOutputStream(System.out);
            outputMap.put(test.getUsername(),stream);
            view = new VirtualView(outputMap);

            handler = new TurnHandler(view);
            handler.setMatchHandler(matchHandler);
            match.start(view);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Turn(match, handler);

        builders[0] = new Builder();
        builders[1] = new Builder();
        builders[0].setCell(match.getCell(new Coords(4, 4)));
        builders[1].setCell(match.getCell(new Coords(4, 3)));
        test.setBuilders(builders);
        match.setCellLevel(new Coords(2,3), Level.Base);
        match.addPlayer(test);
        turn = new Turn(match,null);
    }

    @Before
    public void setBuilder()
    {
        try {
            turn.force(builders[0],new Coords(2,2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        match.setCellLevel(new Coords(3,2), Level.Floor);
        match.setCellLevel(new Coords(3,3), Level.Floor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setup_IllegalCoords_ShouldThrowException()
    {
        try {
            turn.setup(builders[0], builders[1],new Coords(-1,-1),new Coords(2,3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setup_CorrectInput_CorrectBehaviour()
    {
        try {
            turn.setup(builders[0], builders[1],new Coords(1,1),new Coords(4,3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(new Coords(1,1),builders[0].getCoords());
        assertEquals(new Coords(4,3),builders[1].getCoords());
    }

    @Test
    public void move_CorrectInput_CorrectBehaviour()
    {
        try {
            turn.move(builders[0],new Coords(2,3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(builders[0].getCoords(),new Coords(2,3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMove_IllegalCoords_ShouldThrowException()
    {
        turn.checkMove(builders[0],new Coords(-1,-1));
    }

    @Test
    public void checkMove_CorrectInput_CorrectBehaviour()
    {
        boolean result;
        result = turn.checkMove(builders[0],new Coords(2,3));
        assertTrue(result);
    }

    @Test
    public void checkMove_WrongInput_CorrectBehaviour()
    {
        boolean result;
        result = turn.checkMove(builders[0],new Coords(4,4));
        assertFalse(result);
    }

    @Test
    public void build_CorrectInput_CorrectBehaviour()
    {
        try {
            turn.build(builders[0],new Coords(2,1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(Level.Base,match.getCell(new Coords(2,1)).getLevel());
    }

    @Test
    public void checkBuild_CorrectInput_CorrectBehaviour()
    {
        boolean result;
        result = turn.checkBuild(builders[0],new Coords(2,1));
        assertTrue(result);
    }

    @Test
    public void checkBuild_WrongInput_CorrectBehaviour()
    {
        boolean result;
        result = turn.checkBuild(builders[0],new Coords(4,4));
        assertFalse(result);
    }

    @Test
    public void checkWin_WinningInput_ExpectedWin()
    {
        boolean result;
        try {
            turn.force(builders[0],new Coords(3,3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        match.setCellLevel(new Coords(3,3), Level.Medium);
        match.setCellLevel(new Coords(3,4), Level.Top);
        result = turn.checkWin(builders[0],new Coords(3,3),new Coords(3,4));
        assertTrue(result);
    }

    @Test
    public void checkWin_NotWinningInput_ExpectedNotWin()
    {
        boolean result;
        result = turn.checkWin(builders[0],new Coords(3,3),new Coords(2,3));
        assertFalse(result);
    }

    @Test
    public void getCellMoves_CorrectInput_CorrectBehaviour()
    {
        match.setCellLevel(new Coords(3,2), Level.Top);

        List<Coords> list = turn.getPossibleMoves(builders[0]);
        assertThat(list, hasItems(new Coords(1,1)));
        assertThat(list, hasItems(new Coords(1,2)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertThat(list, hasItems(new Coords(2,1)));
        assertThat(list, hasItems(new Coords(2,3)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),7);
    }

    @Test
    public void getCellBuilds_CorrectInput_CorrectBehaviour()
    {
        match.setCellLevel(new Coords(3,2), Level.Top);

        List<Coords> list = turn.getBuildableCells(builders[0]);
        assertThat(list, hasItems(new Coords(1,1)));
        assertThat(list, hasItems(new Coords(1,2)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertThat(list, hasItems(new Coords(2,1)));
        assertThat(list, hasItems(new Coords(2,3)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertThat(list, hasItems(new Coords(3,2)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),8);

        list.clear();

        match.getCell(new Coords(3,2)).setDome(true);
        list = turn.getBuildableCells(builders[0]);
        assertThat(list, hasItems(new Coords(1,1)));
        assertThat(list, hasItems(new Coords(1,2)));
        assertThat(list, hasItems(new Coords(1,3)));
        assertThat(list, hasItems(new Coords(2,1)));
        assertThat(list, hasItems(new Coords(2,3)));
        assertThat(list, hasItems(new Coords(3,1)));
        assertThat(list, hasItems(new Coords(3,3)));
        assertEquals(list.size(),7);
    }



}
