package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.gods.Atlas;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {

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

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Atlas());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1 ,opponentsbuilder2});
        opponentPlayer.setGod(new Turn(match));

        opponentPlayer.getBuilders()[0].setCoords(new Coords(0,0));
        opponentPlayer.getBuilders()[1].setCoords(new Coords(0, 1));
        player.getBuilders()[1].setCoords(new Coords(0,2));
        match.setCell(new Coords(3,2), Level.Floor);
        match.setCell(new Coords(1,2), Level.Base);
        match.setCell(new Coords(2,2), Level.Top);
        match.getCell(new Coords(2,2)).setDome(true);
        match.setCell(new Coords(1,3), Level.Top);
    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCoords(new Coords(2,3));
        player.setGod(new Atlas());
    }


    @Test
    public void BuildNoEffect_CorrectInput_CorrectBuilding() throws IllegalBuildException {
        player.build(builder1,new Coords(1,2));
        assertSame(match.getHeight(new Coords(1,2)),2);
    }

    @Test
    public void BuildWithEffect_CorrectInput_CorrectBuilding() throws IllegalBuildException {
        player.setGod(new Atlas(true));
        player.build(builder1,new Coords(3,2));
        assertSame(match.getHeight(new Coords(3,2)),0);
        assertTrue(match.getCell(new Coords(3,2)).getDome());
    }

    @Test (expected = IllegalBuildException.class)
    public void BuildWithEffect_WrongInput_ShouldThrowException() throws IllegalBuildException {
        player.setGod(new Atlas(true));
        player.build(builder1,new Coords(2,2));
    }

    @Test (expected = IllegalBuildException.class)
    public void BuildNoEffect_WrongInput_ShouldThrowException() throws IllegalBuildException {
        player.build(builder1,new Coords(2,2));
    }


}
