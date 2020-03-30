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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        builders[0].setCoords(new Coords(4,4));
        builders[1].setCoords(new Coords(5,5));
        test.setBuilders(builders);
        match.setCell(new Coords(2,3), Level.Base);
        match.addPlayer(test);
        turn = new Turn(match);
    }

    @Before
    public void setBuilder()
    {
        turn.force(builders[0],new Coords(2,2));
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

    @Test(expected = IllegalMoveException.class)
    public void moveException() throws IllegalMoveException{
        turn.move(builders[0],new Coords(4,4));
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

    @Test(expected = IllegalBuildException.class)
    public void buildException() throws IllegalBuildException {
        turn.build(builders[0],new Coords(4,3));
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
        match.setCell(new Coords(3,3),Level.Medium);
        match.setCell(new Coords(3,4),Level.Top);
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


}
