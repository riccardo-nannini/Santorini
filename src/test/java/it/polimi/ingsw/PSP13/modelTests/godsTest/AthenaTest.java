package it.polimi.ingsw.PSP13.modelTests.godsTest;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.debuffs.Decorator;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.gods.Athena;
import it.polimi.ingsw.PSP13.model.player.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AthenaTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer;
    public static Builder opponentsbuilder1;
    public static Builder opponentsbuilder2;


    @BeforeClass
    public static void setup()
    {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");
        opponentPlayer = new Player(Color.Brown, 21, "Diego");
        new Turn(match);

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Athena());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1 ,opponentsbuilder2});
        opponentPlayer.setGod(new Turn());
    }

    @Test
    public void MovedCorrectlyTest() throws IllegalMoveException {
        opponentPlayer.getBuilders()[0].setCoords(new Coords(0,0));
        opponentPlayer.getBuilders()[1].setCoords(new Coords(0, 1));

        player.getBuilders()[0].setCoords(new Coords(2,2));
        player.getBuilders()[1].setCoords(new Coords(0,2));
        match.setCell(new Coords(2,2), Level.Floor);
        match.setCell(new Coords(2,3), Level.Floor);
        player.move(player.getBuilders()[0], new Coords(2,3));
        assertEquals(player.getBuilders()[0].getCoords(), new Coords(2, 3));
    }

    @Test
    public void AppliedDebuff_OpponentTest() throws IllegalMoveException {
        match.setCell(new Coords(1,0), Level.Base);
        match.setCell(new Coords(0,0), Level.Floor);
        player.getBuilders()[0].setCoords(new Coords(0,0));

        player.move(player.getBuilders()[0], new Coords(1,0));
        assertTrue(opponentPlayer.getGod() instanceof AthenaDebuff);
        assertFalse(player.getGod() instanceof AthenaDebuff);
    }

    @Test
    public void NotAppliedDebuffTest() throws IllegalMoveException {
        match.setCell(new Coords(1,0), Level.Floor);
        match.setCell(new Coords(0,0), Level.Floor);
        player.getBuilders()[0].setCoords(new Coords(0,0));

        player.move(player.getBuilders()[0], new Coords(1,0));
        assertFalse(opponentPlayer.getGod() instanceof AthenaDebuff);
    }


}
