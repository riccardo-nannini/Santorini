package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class TurnTest {

    private static Turn turn;
    private static Builder[] builders = new Builder[2];
    private static Match match;

    @BeforeClass
    public static void init()
    {
        match = new Match();
        match.start();
        Player test = new Player(Color.Blue,32,"test");
        builders[0] = new Builder();
        builders[1] = new Builder();
        builders[0].setCell(match.getCell(new Coords(4, 4)));
        builders[1].setCell(match.getCell(new Coords(4, 3)));
        test.setBuilders(builders);
        match.setCellLevel(new Coords(2,3), Level.Base);
        match.addPlayer(test);
        turn = new Turn(match);
    }

    @Before
    public void setBuilder()
    {
        turn.force(builders[0],new Coords(2,2));
        match.setCellLevel(new Coords(3,2),Level.Floor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupException()
    {
        turn.setup(builders[0], builders[1],new Coords(-1,-1),new Coords(2,3));
    }

    @Test
    public void setupCorrect()
    {
        turn.setup(builders[0], builders[1],new Coords(1,1),new Coords(4,3));
        assertEquals(new Coords(1,1),builders[0].getCoords());
        assertEquals(new Coords(4,3),builders[1].getCoords());
    }

    @Test
    public void moveCorrect() throws IllegalMoveException
    {
        turn.move(builders[0],new Coords(2,3));
        assertEquals(builders[0].getCoords(),new Coords(2,3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMoveException()
    {
        turn.checkMove(builders[0],new Coords(-1,-1));
    }

    @Test
    public void checkMoveTrue()
    {
        boolean result;
        result = turn.checkMove(builders[0],new Coords(2,3));
        assertTrue(result);
    }

    @Test
    public void checkMoveFalse()
    {
        boolean result;
        result = turn.checkMove(builders[0],new Coords(4,4));
        assertFalse(result);
    }

    @Test
    public void buildCorrect() throws IllegalBuildException
    {
        turn.build(builders[0],new Coords(2,1));
        assertEquals(Level.Base,match.getCell(new Coords(2,1)).getLevel());
    }

    @Test
    public void checkBuildTrue()
    {
        boolean result;
        result = turn.checkBuild(builders[0],new Coords(2,1));
        assertTrue(result);
    }

    @Test
    public void checkBuildFalse()
    {
        boolean result;
        result = turn.checkBuild(builders[0],new Coords(4,4));
        assertFalse(result);
    }

    @Test
    public void checkWinTrue()
    {
        boolean result;
        turn.force(builders[0],new Coords(3,3));
        match.setCellLevel(new Coords(3,3),Level.Medium);
        match.setCellLevel(new Coords(3,4),Level.Top);
        result = turn.checkWin(builders[0],new Coords(3,3),new Coords(3,4));
        assertTrue(result);
    }

    @Test
    public void checkWinFalse()
    {
        boolean result;
        result = turn.checkWin(builders[0],new Coords(3,3),new Coords(2,3));
        assertFalse(result);
    }

    @Test
    public void getCellMovesTest()
    {
        match.setCellLevel(new Coords(3,2),Level.Top);

        List<Coords> list = turn.getCellMoves(builders[0]);
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
    public void getCellBuildsTest()
    {
        match.setCellLevel(new Coords(3,2),Level.Top);

        List<Coords> list = turn.getCellBuilds(builders[0]);
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
        list = turn.getCellBuilds(builders[0]);
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
