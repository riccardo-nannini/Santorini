package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.gods.Athena;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AthenaTest {

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
    public static void setup()
    {
        MatchHandler matchHandler = null;
        try {
            matchHandler = new MatchHandler();
            match = matchHandler.getMatch();
            match.start(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new Player(Color.Blue, "Mario");
        opponentPlayer = new Player(Color.Yellow, "Diego");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        new Turn(match, null);

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

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Athena());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1 ,opponentsbuilder2});
        opponentPlayer.setGod(new Turn());

        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 2)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(0, 2)));
        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(0, 1)));
    }


    @Test
    public void move_CorrectInput_CorrectMove() {
        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(0, 1)));

        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 2)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(0, 2)));
        match.setCellLevel(new Coords(2,2), Level.Floor);
        match.setCellLevel(new Coords(2,3), Level.Floor);
        try {
            player.move(player.getBuilders()[0], new Coords(2,3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(player.getBuilders()[0].getCoords(), new Coords(2, 3));

    }

    /**
     * Tests if AthenaDebuff is correctly applied on the opponent
     * when she moves up
     */
    @Test
    public void applyDebuffOnTheOpponent_AthenaMoveUp_DebuffAppliedCorrectly() {
        match.setCellLevel(new Coords(1,0), Level.Base);
        match.setCellLevel(new Coords(0,0), Level.Floor);
        player.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));

        try {
            player.move(player.getBuilders()[0], new Coords(1,0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(opponentPlayer.getGod() instanceof AthenaDebuff);
        assertFalse(player.getGod() instanceof AthenaDebuff);
    }

    /**
     * Tests if AthenaDebuff is correctly NOT applied on the opponent
     * when she DOESN'T move up
     */
    @Test
    public void notAppliedDebuffOnTheOpponent_AthenaNotMoveUp_DebuffNotAppliedCorrectly() {
        match.setCellLevel(new Coords(1,0), Level.Floor);
        match.setCellLevel(new Coords(0,0), Level.Floor);
        player.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));

        try {
            player.move(player.getBuilders()[0], new Coords(1,0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(opponentPlayer.getGod() instanceof AthenaDebuff);
    }


}
