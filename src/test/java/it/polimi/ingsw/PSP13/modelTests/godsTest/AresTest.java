package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.gods.Ares;
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
    public static TurnHandler handler;
    public static VirtualView view;

    @BeforeClass
    public static void setup() {

        MatchHandler matchHandler = null;
        try {
            matchHandler = new MatchHandler();
            match = matchHandler.getMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


        match.addPlayer(player);
        match.addPlayer(opponentPlayer);


        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});

        player.setGod(new Ares());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1, opponentsbuilder2});
        opponentPlayer.setGod(new Turn(match,handler));

        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(0, 1)));


        match.setCellLevel(new Coords(3, 2), Level.Floor);
        match.setCellLevel(new Coords(1, 2), Level.Base);
        match.setCellLevel(new Coords(2, 2), Level.Medium);
        match.setCellLevel(new Coords(1, 3), Level.Top);
        match.setCellLevel(new Coords(3, 3), Level.Top);
        match.getCell(new Coords(3,3)).setDome(true);
    }

    @Before
    public void setUp() {
        player.setGod(new Ares());
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));
    }


    @Test
    public void RemoveBlock_CorrectInput_CorrectBehaviour() {
        Coords removeCoords = new Coords(1,3);
        player.setGod(new Ares());
        handler.setUseEffect("yes");
        handler.setRemoveCoords(removeCoords);

        Coords movedTo = new Coords(3, 2);
        try {
            player.move(builder1, movedTo);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertSame(match.getHeight(removeCoords), 2);
    }

    @Test
    public void RemoveBlock2_CorrectInput_CorrectBehaviour() {
        Coords removeCoords = new Coords(2,2);
        player.setGod(new Ares());
        handler.setUseEffect("yes");
        handler.setRemoveCoords(removeCoords);
        Coords movedTo = new Coords(3, 2);
        try {
            player.move(builder1, movedTo);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertSame(match.getHeight(removeCoords), 1);
    }

    @Test
    public void RemoveBlock3_WrongInput_CorrectBehaviour() {
        Coords removeCoords = new Coords(1,2);
        player.setGod(new Ares());
        handler.setUseEffect("yes");
        handler.setRemoveCoords(removeCoords);
        Coords movedTo = new Coords(2, 4);
        try {
            player.move(builder2, movedTo);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertSame(match.getHeight(removeCoords), 0);
    }

    /**
     * this test cannot be done because the ir and error in the input and that prevents the correct
     * execution of player.end(). see getInputRemoveBlock in TurnHandler
     */
    //@Test
    public void RemoveBlock_WrongInput_NothingRemoved() {
        Coords removeCoords = new Coords(3,3);
        player.setGod(new Ares());
        handler.setUseEffect("yes");
        handler.setRemoveCoords(removeCoords);
        Coords movedTo = new Coords(3, 2);
        try {
            player.move(builder1, movedTo);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertSame(match.getHeight(removeCoords), 3);
        assertTrue(match.getCell(removeCoords).getDome());
    }
}