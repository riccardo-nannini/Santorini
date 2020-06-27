package it.polimi.ingsw.PSP13.modelTests;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private static Player player;
    private static Turn turn;
    private static Builder build;

    @BeforeClass
    public static void setup()
    {
        player = new Player(Color.Blue, "test");
        turn = new Turn(new Match(),null);
        player.setGod(turn);

        Builder builders[] = new Builder[2];
        builders[0] = new Builder();
        builders[1] = new Builder();
        builders[0].setCell(new Cell(3,3));
        builders[1].setCell(new Cell(3,3));
        build = builders[0];
        player.setBuilders(builders);
    }


    @Test
    public void start_CorrectInput_CorrectBehaviour()
    {
        try {
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setUsername_NewUsername_UsernameSetCorrectly()
    {
        player.setUsername("setNickname");
        assertEquals(player.getUsername(),"setNickname");
    }

    @Test
    public void setColor_PlayerColor_ColorSetCorrectly()
    {
        player.setColor(Color.Red);
        assertEquals(player.getColor(), Color.Red);
    }

    @Test
    public void getCellMoves_CorrectInput_CorrectBehaviour()
    {
        List<Coords> test = player.getCellMoves(build);
        assertTrue(test.equals(turn.getCellMoves(build)));

    }

    @Test
    public void getCellBuilds_CorrectInput_CorrectBehaviour()
    {
        List<Coords> test = player.getCellBuilds(build);
        assertEquals(test,turn.getCellBuilds(build));

    }


}
