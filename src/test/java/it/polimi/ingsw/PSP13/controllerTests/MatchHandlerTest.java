package it.polimi.ingsw.PSP13.controllerTests;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.gods.Apollo;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MatchHandlerTest {

    private static MatchHandler matchHandler;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Match match;
    private static TurnHandler handler;
    private static VirtualView view;
    private static HashMap<String, ObjectOutputStream> outputMap;
    private static ObjectOutputStream stream;

    @BeforeClass
    public static void init()
    {
        matchHandler = new MatchHandler();
        match = matchHandler.getMatch();

        player1 = new Player(Color.Blue, "primo");
        //player2 = new Player(Color.Red, "secondo");
        //player3 = new Player(Color.Yellow, "terzo");

        outputMap = new HashMap<>();

        try {
            stream = new ObjectOutputStream(System.out);
            outputMap.put(player1.getUsername(),stream);
            //outputMap.put(player2.getUsername(),stream);
            //outputMap.put(player3.getUsername(),stream);
            view = new VirtualView(outputMap);
            matchHandler.setVirtualView(view);
            handler = new TurnHandler(view);
            handler.setMatchHandler(matchHandler);
            match.start(view);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new Turn(match, handler);

        match.addPlayer(player1);
        //match.addPlayer(player2);
        //match.addPlayer(player3);

        Builder builder1 = new Builder();
        Builder builder2 = new Builder();
        player1.setBuilders(new Builder[]{builder1, builder2});
        player1.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player1.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));

    }

    //@Test
    public void MatchInitTest(){

        player2 = new Player(Color.Red,"addPlayer");
        matchHandler.addPlayer(player2);
        outputMap.put(player2.getUsername(),stream);
        assertTrue(match.getPlayers().contains(player2));

        matchHandler.setGodsReceived("Apollo, Ares");
        matchHandler.setSelectedGod("Apollo");
        matchHandler.setSelectedStarter("primo");
        matchHandler.setCoords(new Coords(0,4));

        Runnable runnable = ()->{
            try {
                for(int i=0;i<3;i++)
                {
                    Thread.sleep(2000);
                    matchHandler.setCoords(new Coords(i,3));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable, "input thread");
        thread.start();
        try {
            matchHandler.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(match.getPlayers().get(0).getGod() instanceof Apollo || match.getPlayers().get(1).getGod() instanceof Apollo);


    }

    /**
     * l'eccezione non passa nel catch
     */
    @Test
    public void matchInitDisconnectionTest()
    {
        boolean thrown = false;

        matchHandler.setGodsReceived("Apollo, Ares");
        matchHandler.setSelectedGod("Apollo");
        matchHandler.setSelectedStarter("primo");
        matchHandler.setCoords(new Coords(0,4));

        matchHandler.addDisconnectedPlayer("primo");

        try {
            matchHandler.init();
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            thrown = true;
        }

        assertTrue(thrown);


    }



}
