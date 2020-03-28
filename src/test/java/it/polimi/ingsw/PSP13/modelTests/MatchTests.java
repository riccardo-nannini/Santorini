package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatchTests {

    private static Match match;

    @BeforeClass
    public static void setup()
    {
        match = new Match();
        match.start();
        Player test = new Player(Color.Blue,32,"test");
        Builder[] builders = new Builder[2];
        builders[0] = new Builder();
        builders[1] = new Builder();
        builders[0].setCoords(new Coords(4,4));
        builders[1].setCoords(new Coords(5,5));
        test.setBuilders(builders);
        match.addPlayer(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OccupiedTestException()
    {
        match.isOccupied(new Coords(-1,-1));
    }

    @Test
    public void OccupiedTestTrue()
    {
        boolean result;

        match.setCell(new Coords(3,2), Level.Base);
        result = match.isOccupied(new Coords(3,2));
        assertTrue(result);

        //match.getPlayers().get(0).getBuilders()[0].setCoords(new Coords(4,4));
       // match.getPlayers().get(0).getBuilders()[1].setCoords(new Coords(5,5));
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
}
