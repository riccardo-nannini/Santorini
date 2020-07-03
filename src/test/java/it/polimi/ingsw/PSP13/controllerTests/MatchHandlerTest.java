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
import org.junit.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MatchHandlerTest {

    private static MatchHandler matchHandler;
    private static Player player1;
    private static Player player2;
    private static Match match;
    private static TurnHandler handler;
    private static VirtualView view;
    private static HashMap<String, ObjectOutputStream> outputMap;
    private static ObjectOutputStream stream;


    @Before
    public void before() {

        matchHandler = new MatchHandler();
        match = matchHandler.getMatch();

        player1 = new Player(Color.Red, "Antonio");

        outputMap = new HashMap<>();

        try {
            stream = new ObjectOutputStream(System.out);
            outputMap.put(player1.getUsername(),stream);
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

        Builder builder1 = new Builder();
        Builder builder2 = new Builder();
        player1.setBuilders(new Builder[]{builder1, builder2});
        player1.getBuilders()[0].setCell(match.getCell(new Coords(1, 1)));
        player1.getBuilders()[1].setCell(match.getCell(new Coords(2, 3)));

    }


    @Test
    public void init_CorrectInput_CorrectInitBehaviour(){

        player2 = new Player(Color.Yellow,"Riccardo");
        matchHandler.addPlayer(player2);
        outputMap.put(player2.getUsername(),stream);
        assertTrue(match.getPlayers().contains(player2));

        matchHandler.setGodsReceived("Apollo, Ares");
        matchHandler.setSelectedGod("Apollo");
        matchHandler.setSelectedStarter("Antonio");
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

    @Test
    public void sortPlayers_ThreePlayerList_ListSortedCorrectly() {
        match.getPlayers().clear();

        Player simone = new Player(Color.Blue,"Simone");
        Player antonio = new Player(Color.Red,"Antonio");
        Player riccardo = new Player(Color.Yellow,"Riccardo");

        match.addPlayer(simone);
        match.addPlayer(antonio);
        match.addPlayer(riccardo);

        matchHandler.setNumPlayers(3);

        assertEquals(simone,match.getPlayers().get(0));

        matchHandler.setSelectedStarter("Antonio");

        matchHandler.sortPlayers();

        assertEquals(antonio,match.getPlayers().get(0));
        assertEquals(riccardo,match.getPlayers().get(1));
        assertEquals(simone,match.getPlayers().get(2));
    }

    @Test
    public void sortPlayers2_ThreePlayerList_ListSortedCorrectly() {
        match.getPlayers().clear();

        Player simone = new Player(Color.Blue,"Simone");
        Player antonio = new Player(Color.Red,"Antonio");
        Player riccardo = new Player(Color.Yellow,"Riccardo");

        match.addPlayer(simone);
        match.addPlayer(antonio);
        match.addPlayer(riccardo);

        matchHandler.setNumPlayers(3);

        assertEquals(simone,match.getPlayers().get(0));

        matchHandler.setSelectedStarter("Riccardo");

        matchHandler.sortPlayers();

        assertEquals(riccardo,match.getPlayers().get(0));
        assertEquals(simone,match.getPlayers().get(1));
        assertEquals(antonio,match.getPlayers().get(2));
    }


    @Test
    public void sortPlayers_TwoPlayerList_ListSortedCorrectly() {
        match.getPlayers().clear();

        Player simone = new Player(Color.Blue,"Simone");
        Player antonio = new Player(Color.Red,"Antonio");

        match.addPlayer(simone);
        match.addPlayer(antonio);

        matchHandler.setNumPlayers(2);

        assertEquals(simone,match.getPlayers().get(0));

        matchHandler.setSelectedStarter("Antonio");

        matchHandler.sortPlayers();

        assertEquals(antonio,match.getPlayers().get(0));
        assertEquals(simone,match.getPlayers().get(1));
    }





}
