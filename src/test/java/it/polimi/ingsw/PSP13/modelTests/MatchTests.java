package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MatchTests {

    private static Match match;

    @BeforeClass
    public static void setup()
    {
        match = new Match();
        try {
            match.start(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player test = new Player(Color.Blue,"test");
        Builder[] builders = new Builder[2];
        builders[0] = new Builder();
        builders[1] = new Builder();
        builders[0].setCell(match.getCell(new Coords(4, 4)));
        builders[1].setCell(match.getCell(new Coords(4, 3)));
        test.setBuilders(builders);
        match.addPlayer(test);
    }

    @Test()
    public void OccupiedTestException()
    {
        boolean bool = match.isOccupied(new Coords(-1,-1));
        assertTrue(bool);
    }

    @Test
    public void OccupiedTestTrue()
    {
        boolean result;

        match.setCellLevel(new Coords(3,2), Level.Top);
        match.getCell(new Coords(3,2)).setDome(true);
        result = match.isOccupied(new Coords(3,2));
        assertTrue(result);


        result = match.isOccupied(new Coords(4,4));
        assertTrue(result);
    }

    @Test
    public void OccupiedTestFalse()
    {
        boolean result;

        result = match.isOccupied(new Coords(1,3));
        assertFalse(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getBuilderByCoordsException()
    {
        match.getBuilderByCoords(new Coords(1,4));
    }

    @Test
    public void getBuilderByCoordsTest()
    {
        Builder test;
        test = match.getBuilderByCoords(new Coords(4,4));
        assertEquals(match.getPlayers().get(0).getBuilders()[0],test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPlayerByBuilderException()
    {
        Player test;
        test = match.getPlayerByBuilder(new Builder());
    }

    @Test
    public void getPlayerByBuilderTest()
    {
        Player test;
        test = match.getPlayerByBuilder(match.getPlayers().get(0).getBuilders()[1]);
        assertEquals(match.getPlayers().get(0),test);
    }

}
