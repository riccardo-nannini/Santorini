package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.gods.Apollo;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ApolloTest {

    private static Match match;
    private static Player player, opponentPlayer;
    private static Builder builder1, builder2, opponentsbuilder1, opponentsbuilder2;
    private static Turn turn,apollo;
    public static TurnHandler handler;
    public static VirtualView view;

    @BeforeClass
    public static void init()
    {
        MatchHandler matchHandler = null;
        matchHandler = new MatchHandler();
        match = matchHandler.getMatch();


        player = new Player(Color.Blue, "Mario");
        opponentPlayer = new Player(Color.Yellow, "Diego");

        HashMap<String, ObjectOutputStream> outputMap = new HashMap<>();
        ObjectOutputStream stream;

        try {
            stream = new ObjectOutputStream(System.out);
            outputMap.put(player.getUsername(),stream);
            view = new VirtualView(outputMap);

            handler = new TurnHandler(view);
            handler.setMatchHandler(matchHandler);
            match.start(view);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Turn(match, handler);

        apollo = new Apollo();

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(apollo);

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1, opponentsbuilder2});
        opponentPlayer.setGod(new Turn());

        player.getBuilders()[0].setCell(match.getCell(new Coords(3, 0)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(3, 1)));

        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(2, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(4, 0)));


        match.setCellLevel(new Coords(3, 1), Level.Top);
        match.setCellLevel(new Coords(3, 2), Level.Floor);
        match.setCellLevel(new Coords(1, 2), Level.Base);
        match.setCellLevel(new Coords(2, 2), Level.Medium);
        match.setCellLevel(new Coords(1, 3), Level.Top);
        match.setCellLevel(new Coords(3, 3), Level.Top);
        match.getCell(new Coords(3,3)).setDome(true);
        match.setCellLevel(new Coords(3, 0), Level.Floor);
        match.setCellLevel(new Coords(1, 2), Level.Base);
        match.setCellLevel(new Coords(4, 0), Level.Floor);
        match.setCellLevel(new Coords(4, 4), Level.Floor);
    }

    @Before
    public void setup()
    {
        player.getBuilders()[0].setCell(match.getCell(new Coords(3, 0)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(3, 1)));

        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(2, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(4, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkMoveException() throws IllegalArgumentException
    {
        apollo.checkMove(builder1,new Coords(5,5));
    }

    @Test
    public void checkMoveTrue()
    {
        boolean result;
        result = apollo.checkMove(builder1,new Coords(4,0));
        assertTrue(result);

        result = apollo.checkMove(builder1,new Coords(2,0));
        assertTrue(result);

        result = apollo.checkMove(builder2,new Coords(2,1));
        assertTrue(result);

    }

    @Test
    public void checkMoveFalse()
    {
        boolean result;
        result = apollo.checkMove(builder1,new Coords(4,4));
        assertFalse(result);

        result = apollo.checkMove(builder1,new Coords(3,1));
        assertFalse(result);

    }

    @Test
    public void moveWithEffectTest()
    {
        try
        {
            apollo.move(builder1,new Coords(2,0));
            assertEquals(builder1.getCoords(),new Coords(2,0));
            assertEquals(opponentsbuilder1.getCoords(),new Coords(3,0));

        }
        catch (Exception e)
        {}

    }

    @Test
    public void moveNoEffectTest()
    {
        try
        {
            apollo.move(builder1,new Coords(2,2));
            assertEquals(builder1.getCoords(),new Coords(2,2));

        }
        catch (Exception e)
        {}

    }

}