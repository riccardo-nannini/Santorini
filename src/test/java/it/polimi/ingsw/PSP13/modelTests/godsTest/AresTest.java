package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.gods.Ares;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class AresTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer;
    public static Builder opponentsbuilder1;
    public static Builder opponentsbuilder2;

    @BeforeClass
    public static void setup() {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");
        opponentPlayer = new Player(Color.Brown, 21, "Diego");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});

        player.setGod(new Ares());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1, opponentsbuilder2});
        opponentPlayer.setGod(new Turn(match));

        opponentPlayer.getBuilders()[0].setCoords(new Coords(0, 0));
        opponentPlayer.getBuilders()[1].setCoords(new Coords(0, 1));


        match.setCell(new Coords(3, 2), Level.Floor);
        match.setCell(new Coords(1, 2), Level.Base);
        match.setCell(new Coords(2, 2), Level.Medium);
        match.setCell(new Coords(1, 3), Level.Top);
        match.setCell(new Coords(3, 3), Level.Top);
        match.getCell(new Coords(3,3)).setDome(true);
    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCoords(new Coords(2, 1));
        player.getBuilders()[1].setCoords(new Coords(2, 3));
    }


    @Test
    public void RemoveBlock_CorrectInput_CorrectBehaviour() throws IllegalMoveException {
        Coords removeCoords = new Coords(1,3);
        player.setGod(new Ares(removeCoords, true));
        Coords movedTo = new Coords(3, 2);
        player.move(builder1, movedTo);
        player.end();
        assertSame(match.getHeight(removeCoords), 2);
    }

    @Test
    public void RemoveBlock2_CorrectInput_CorrectBehaviour() throws IllegalMoveException {
        Coords removeCoords = new Coords(2,2);
        player.setGod(new Ares(removeCoords, true));
        Coords movedTo = new Coords(3, 2);
        player.move(builder1, movedTo);
        player.end();
        assertSame(match.getHeight(removeCoords), 1);
    }

    @Test
    public void RemoveBlock3_WrongInput_CorrectBehaviour() throws IllegalMoveException {
        Coords removeCoords = new Coords(1,2);
        player.setGod(new Ares(removeCoords, true));
        Coords movedTo = new Coords(2, 4);
        player.move(builder2, movedTo);
        player.end();
        assertSame(match.getHeight(removeCoords), 0);
    }

    @Test
    public void RemoveBlock_WrongInput_NothingRemoved() throws IllegalMoveException {
        Coords removeCoords = new Coords(3,3);
        player.setGod(new Ares(removeCoords, true));
        Coords movedTo = new Coords(3, 2);
        player.move(builder1, movedTo);
        player.end();
        assertSame(match.getHeight(removeCoords), 3);
        assertTrue(match.getCell(removeCoords).getDome());
    }
}