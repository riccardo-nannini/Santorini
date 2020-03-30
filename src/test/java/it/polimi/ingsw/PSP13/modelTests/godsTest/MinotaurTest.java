package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.gods.Minotaur;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinotaurTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer1;
    public static Player opponentPlayer2;
    public static Builder opponentsbuilder1_1;
    public static Builder opponentsbuilder1_2;
    public static Builder opponentsbuilder2_1;
    public static Builder opponentsbuilder2_2;

    @BeforeClass
    public static void setup()
    {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");
        opponentPlayer1 = new Player(Color.Brown, 21, "Diego");
        opponentPlayer2 = new Player(Color.White, 21, "Maurizio");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer1);
        match.addPlayer(opponentPlayer2);


        builder1 = new Builder(player);
        builder2 = new Builder(player);
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Minotaur());

        opponentsbuilder1_1 = new Builder(opponentPlayer1);
        opponentsbuilder1_2 = new Builder(opponentPlayer1);
        opponentPlayer1.setBuilders(new Builder[]{opponentsbuilder1_1 ,opponentsbuilder1_2});
        opponentPlayer1.setGod(new Turn(match));

        opponentsbuilder2_1 = new Builder(opponentPlayer2);
        opponentsbuilder2_2 = new Builder(opponentPlayer2);
        opponentPlayer2.setBuilders(new Builder[]{opponentsbuilder2_1 ,opponentsbuilder2_2});
        opponentPlayer2.setGod(new Turn(match));

        match.setCell(new Coords(4,2), Level.Floor);
        match.setCell(new Coords(3,2), Level.Medium);
        match.setCell(new Coords(2,2), Level.Base);
        match.setCell(new Coords(3,3), Level.Medium);
        match.setCell(new Coords(4,4), Level.Top);
        match.setCell(new Coords(2,4), Level.Dome);
        match.setCell(new Coords(1,1), Level.Floor);
        match.setCell(new Coords(0,0), Level.Floor);
        match.setCell(new Coords(2,1), Level.Floor);
    }

    @Before
    public void setUp() {
        match.setCell(new Coords(2,1), Level.Floor);
        match.setCell(new Coords(2,4), Level.Dome);

        opponentPlayer1.getBuilders()[0].setCoords(new Coords(3,3));
        opponentPlayer1.getBuilders()[1].setCoords(new Coords(3, 2));

        opponentPlayer2.getBuilders()[0].setCoords(new Coords(4,2));
        opponentPlayer2.getBuilders()[1].setCoords(new Coords(2, 3));

        player.getBuilders()[0].setCoords(new Coords(1,1));
        player.getBuilders()[1].setCoords(new Coords(2,2));

        player.setGod(new Minotaur());
    }


    @Test
    public void MoveWithEffect_CorrectInput_CorrectBehaviour() throws IllegalMoveException {
        player.move(builder2, new Coords(3,3));
        assertEquals(builder2.getCoords(),new Coords(3,3));
        assertEquals(opponentsbuilder1_1.getCoords(),new Coords(4,4));
    }

    @Test (expected = IllegalMoveException.class)
    public void MoveWithEffect_WrongInput_ShouldThrowException() throws IllegalMoveException {
        player.move(builder2, new Coords(3,2));
    }

    @Test (expected = IllegalMoveException.class)
    public void MoveWithEffect2_WrongInput_ShouldThrowException() throws IllegalMoveException {
        player.move(builder2, new Coords(2,3));
    }

    @Test
    public void MoveWithEffect3_CorrectInput_CorrectBehaviour() throws IllegalMoveException {
        match.setCell(new Coords(2,4), Level.Floor);
        player.move(builder2, new Coords(2,3));
        assertEquals(builder2.getCoords(),new Coords(2,3));
        assertEquals(opponentsbuilder2_2.getCoords(),new Coords(2,4));
    }

    @Test (expected = IllegalMoveException.class)
    public void MoveWithEffect4_WrongInput_ShouldThrowException() throws IllegalMoveException {
        player.move(builder2, new Coords(1,1));
    }

    @Test
    public void MoveNoEffect_CorrectInput_CorrectBehaviour() throws IllegalMoveException {
        player.move(builder2, new Coords(2,1));
        assertEquals(builder2.getCoords(),new Coords(2,1));
    }

    @Test (expected = IllegalMoveException.class)
    public void MoveNoEffect_WrongInput_ShouldThrowException() throws IllegalMoveException {
        match.setCell(new Coords(2,1), Level.Dome);
        player.move(builder2, new Coords(2,1));
    }



}
