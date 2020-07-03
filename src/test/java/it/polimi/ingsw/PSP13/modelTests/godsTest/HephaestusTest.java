package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.controller.MatchHandler;
import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.gods.Hephaestus;
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

public class HephaestusTest {

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
        MatchHandler matchHandler = new MatchHandler();
        match = matchHandler.getMatch();
        player = new Player(Color.Blue, "Mario");
        opponentPlayer = new Player(Color.Yellow, "Diego");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);


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

        new Turn(match, handler);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Hephaestus());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1 ,opponentsbuilder2});
        opponentPlayer.setGod(new Turn(match,handler));

        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(0, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(0, 2)));

        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 2)));
    }

    @Before
    public void setUp() {
        player.setGod(new Hephaestus());
        match.setCellLevel(new Coords(3,2), Level.Floor);
        match.setCellLevel(new Coords(4,2), Level.Medium);
        match.getCell(new Coords(3,2)).setDome(false);
    }


    @Test
    public void BuildNoEffect_CorrectInput_CorrectSingleBuild() {

        handler.setUseEffect("no");
        Coords buildTo = new Coords(3,2);
        player.checkBuild(builder1,buildTo);
        try {
            player.build(builder1, buildTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertSame(match.getHeight(buildTo),1);
    }

    /**
     * Tests Hephaestus' build using his effect
     */
    @Test
    public void BuildWithEffect_CorrectInput_CorrectBuildOnTheFirstBlock() {

        handler.setUseEffect("yes");
        Coords buildTo = new Coords(3,2);
        player.checkBuild(builder1,buildTo);
        try {
            player.build(builder1, buildTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertSame(match.getHeight(buildTo),2);
    }

}
