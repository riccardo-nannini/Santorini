package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.gods.Minotaur;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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


        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Minotaur());

        opponentsbuilder1_1 = new Builder();
        opponentsbuilder1_2 = new Builder();
        opponentPlayer1.setBuilders(new Builder[]{opponentsbuilder1_1 ,opponentsbuilder1_2});
        opponentPlayer1.setGod(new Turn(match));

        opponentsbuilder2_1 = new Builder();
        opponentsbuilder2_2 = new Builder();
        opponentPlayer2.setBuilders(new Builder[]{opponentsbuilder2_1 ,opponentsbuilder2_2});
        opponentPlayer2.setGod(new Turn(match));

        match.setCellLevel(new Coords(4,2), Level.Floor);
        match.setCellLevel(new Coords(3,2), Level.Medium);
        match.setCellLevel(new Coords(2,2), Level.Base);
        match.setCellLevel(new Coords(3,3), Level.Medium);
        match.setCellLevel(new Coords(4,4), Level.Top);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.getCell(new Coords(2,4)).setDome(true);
        match.setCellLevel(new Coords(1,1), Level.Floor);
        match.setCellLevel(new Coords(0,0), Level.Floor);
        match.setCellLevel(new Coords(2,1), Level.Floor);
    }

    @Before
    public void setUp() {
        match.setCellLevel(new Coords(2,1), Level.Floor);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.getCell(new Coords(2,4)).setDome(true);

        opponentPlayer1.getBuilders()[0].setCell(match.getCell(new Coords(3, 3)));
        opponentPlayer1.getBuilders()[1].setCell(match.getCell(new Coords(3, 2)));

        opponentPlayer2.getBuilders()[0].setCell(match.getCell(new Coords(4, 2)));
        opponentPlayer2.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));

        player.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 2)));

        player.setGod(new Minotaur());
    }


    @Test
    public void MoveWithEffect_CorrectInput_CorrectBehaviour(){
        player.move(builder2, new Coords(3,3));
        assertEquals(builder2.getCoords(),new Coords(3,3));
        assertEquals(opponentsbuilder1_1.getCoords(),new Coords(4,4));
    }

    @Test
    public void MoveWithEffect3_CorrectInput_CorrectBehaviour(){
        match.setCellLevel(new Coords(2,4), Level.Floor);
        match.getCell(new Coords(2,4)).setDome(false);
        player.move(builder2, new Coords(2,3));
        assertEquals(builder2.getCoords(),new Coords(2,3));
        assertEquals(opponentsbuilder2_2.getCoords(),new Coords(2,4));
    }


    @Test
    public void MoveNoEffect_CorrectInput_CorrectBehaviour(){
        player.move(builder2, new Coords(2,1));
        assertEquals(builder2.getCoords(),new Coords(2,1));
    }

    @Test
    public void checkMoveNormal_True()
    {
        boolean result;
        result = player.getGod().checkMove(builder1,new Coords(2,1));
        assertTrue(result);
    }

    @Test
    public void checkMoveNormal_False()
    {
        boolean result;
        result = player.getGod().checkMove(builder2,new Coords(3,4));
        assertFalse(result);

        player.getBuilders()[1].setCell(match.getCell(new Coords(3, 4)));
        result = player.getGod().checkMove(builder2,new Coords(3,3));
        assertFalse(result);
    }

    @Test
    public void checkMove_WithOpponent_True()
    {
        boolean result;
        player.getBuilders()[1].setCell(match.getCell(new Coords(3, 3)));
        result = player.getGod().checkMove(builder2,new Coords(2,3));
        assertTrue(result);
    }

    @Test
    public void checkMove_WithOpponent_False()
    {
        boolean result;
        result = player.getGod().checkMove(builder2,new Coords(2,3));
        assertFalse(result);
    }

}
