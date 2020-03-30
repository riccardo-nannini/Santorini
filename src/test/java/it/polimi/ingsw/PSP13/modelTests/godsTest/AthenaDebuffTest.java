package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaDebuffTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;

    @BeforeClass
    public static void setup() {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");

        match.addPlayer(player);
        new Turn(match);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        AthenaDebuff debuff = new AthenaDebuff(new Turn());
        player.setGod(debuff);

        player.getBuilders()[0].setCoords(new Coords(1,1));
        match.setCell(new Coords(3,3), Level.Medium);
        match.setCell(new Coords(2,3), Level.Medium);
        match.setCell(new Coords(3,4), Level.Base);
        match.setCell(new Coords(2,4), Level.Top);
        match.setCell(new Coords(2, 2), Level.Top);
    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCoords(new Coords(2,3));
    }
    @Test
    public void MovingSameLevel_NormalMoveExpected() throws IllegalMoveException {
        player.move(player.getBuilders()[0], new Coords(3,3));
        assertEquals(player.getBuilders()[0].getCoords(), new Coords(3, 3));
    }

    @Test(expected = IllegalMoveException.class)
    public void MovingUp_ExceptionExpected() throws IllegalMoveException {
        player.move(player.getBuilders()[0], new Coords(2,4));
    }

    @Test
    public void MovingDown_NormalMoveExpected() throws IllegalMoveException {
        player.move(player.getBuilders()[0], new Coords(3,4));
        assertEquals(player.getBuilders()[0].getCoords(), new Coords(3, 4));
    }

    @Test
    public void endOfTurn_DecoretorRemoveExpected() throws IllegalMoveException {
        player.move(player.getBuilders()[0], new Coords(3,3));
        player.end();
        assertFalse(player.getGod() instanceof AthenaDebuff);
    }
}
