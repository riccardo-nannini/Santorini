package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HeraDebuffTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;

    @BeforeClass
    public static void setup() {
        match = new Match();
        try {
            match.start(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(Color.Blue, "Mario");

        match.addPlayer(player);

        new Turn(match, null);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        HeraDebuff debuff = new HeraDebuff(new Turn());
        player.setGod(debuff);

        match.setCellLevel(new Coords(2,3), Level.Medium);
        match.setCellLevel(new Coords(2,4), Level.Top);
        match.setCellLevel(new Coords(2, 2), Level.Top);
        match.setCellLevel(new Coords(1, 0), Level.Medium);
        match.setCellLevel(new Coords(0, 0), Level.Top);
        match.setCellLevel(new Coords(0 , 1), Level.Floor);
        match.setCellLevel(new Coords(1, 1), Level.Top);
    }

    @Test
    public void checkWin_NormalMovement_ExpectedFalse() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(1,3));
        assertFalse(result);
        result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(0,1));
        assertFalse(result);
    }

    @Test
    public void checkWin_Perimetral_WinMovement_ExpectedFalse() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(2,4));
        assertFalse(result);
                result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(0,0));
        assertFalse(result);
    }

    @Test
    public void checkWin_NotPerimetral_WinMovement_ExpectedTrue() {
        boolean result = player.win(player.getBuilders()[0],
                new Coords(2,3), new Coords(2,2));
        assertTrue(result);
        result = player.win(player.getBuilders()[0],
                new Coords(1,0), new Coords(1,1));
        assertTrue(result);
    }
}
