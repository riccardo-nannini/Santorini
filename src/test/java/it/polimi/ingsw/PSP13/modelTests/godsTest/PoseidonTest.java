package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.gods.Poseidon;
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

public class PoseidonTest {

    //TODO aggiungere nuovi test, correggere quelli che ci sono già

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
        player.setGod(new Poseidon());

        player.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));
    }

    @Before
    public void setUp() {
        player.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));
        player.setGod(new Poseidon());
    }

    @Test
    public void noEffectTest()
    {
        Coords toMove = new Coords(1,2);
        handler.setUseEffect("no");
        handler.setMoveCoords(toMove);
        try {
            player.move(builder1,toMove);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(builder1.getCoords(),new Coords(1,2));

        toMove = new Coords(3,2);
        handler.setUseEffect("no");
        handler.setMoveCoords(toMove);
        try {
            player.move(builder2,toMove);
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(builder2.getCoords(),new Coords(3,2));
    }


    @Test
    public void tripleBuildTest() {

        Runnable runnable =
                new Runnable(){
                    public void run(){
                        for(int i=0;i<3;i++)
                        {
                            handler.setBuildCoords(new Coords(2,4));
                            handler.setUseEffect("yes");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };


        Coords toMove = new Coords(1,2);
        Coords buildCoords = new Coords(2,4);
        handler.setUseEffect("yes");
        handler.setBuildCoords(buildCoords);
        try {
            player.move(builder1,toMove);
            Thread thread = new Thread(runnable);
            thread.start();
            player.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(builder1.getCoords(),new Coords(1,2));
        assertEquals(match.getCell(buildCoords).getLevel(), Level.Top);
    }

}
