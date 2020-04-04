package it.polimi.ingsw.PSP13.modelTests.godsTest;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.HypnusDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HypnusDebuffTest {

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
        HypnusDebuff debuff = new HypnusDebuff(new Turn());
        player.setGod(debuff);

        player.getBuilders()[1].setCell(match.getCell(new Coords(4, 3)));

        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(4,4), Level.Top);
        match.setCellLevel(new Coords(4,3), Level.Medium);
        match.setCellLevel(new Coords(3,3), Level.Base);
        match.setCellLevel(new Coords(3,2), Level.Medium);
        match.setCellLevel(new Coords(2,4), Level.Top);
    }

    @Test
    public void MovingLowerBuilder_ExpectedTrue() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 3)));
        assertTrue(player.checkMove(player.getBuilders()[0], new Coords(1,3)));
        assertTrue(player.checkMove(player.getBuilders()[0], new Coords(3,3)));
    }

    @Test
    public void MovingBuilderOnTheSameLevel_ExpectedTrue() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(3, 2)));
        assertTrue(player.checkMove(player.getBuilders()[1], new Coords(3, 3)));
        assertTrue(player.checkMove(player.getBuilders()[1], new Coords(4, 4)));
    }

    @Test
    public void MovingHigherBuilder_ExpectedFalse() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 4)));
        assertFalse(player.checkMove(player.getBuilders()[0], new Coords(2, 3)));
        assertFalse(player.checkMove(player.getBuilders()[0], new Coords(3, 3)));
        assertFalse(player.checkMove(player.getBuilders()[0], new Coords(1, 4)));
    }



}
