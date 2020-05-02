package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.gods.Ares;
import it.polimi.ingsw.PSP13.model.gods.Artemis;
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

import static org.junit.Assert.assertEquals;

public class ArtemisTest {

    //TODO coverage a 50% su sta classe, aggiungere test

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
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

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Artemis());

        player.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));
    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(1, 1)));
    }

    @Test
    public void NoAdditionalMoveTest() {
        player.setGod(new Artemis());
        handler.setUseEffect("no");
        Coords movedTo = new Coords(2, 4);
        try {
            player.move(builder2, movedTo);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(builder2.getCoords(),new Coords(2,4));
    }

    @Test
    public void AdditionalMoveTest() {
        Coords effectCoords = new Coords(1,4);
        player.setGod(new Artemis());
        handler.setUseEffect("yes");
        handler.setMoveCoords(effectCoords);
        Coords movedTo = new Coords(2, 4);
        try {
            player.move(builder1, movedTo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(builder1.getCoords(),effectCoords);
    }

}
