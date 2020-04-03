package it.polimi.ingsw.PSP13.modelTests.godsTest;

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


import static org.junit.Assert.assertSame;

public class HephaestusTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer;
    public static Builder opponentsbuilder1;
    public static Builder opponentsbuilder2;

    @BeforeClass
    public static void setup()
    {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");
        opponentPlayer = new Player(Color.Brown, 21, "Diego");

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Hephaestus());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1 ,opponentsbuilder2});
        opponentPlayer.setGod(new Turn(match));

        opponentPlayer.getBuilders()[0].setCell(match.getCell(new Coords(0, 0)));
        opponentPlayer.getBuilders()[1].setCell(match.getCell(new Coords(0, 1)));
        player.getBuilders()[1].setCell(match.getCell(new Coords(0, 2)));
    }

    @Before
    public void setUp() {
        player.setGod(new Hephaestus());
        player.getBuilders()[0].setCell(match.getCell(new Coords(2, 2)));
        match.setCellLevel(new Coords(3,2), Level.Floor);
        match.getCell(new Coords(3,2)).setDome(false);
    }


    @Test
    public void BuildNoEffect_CorrectInput_CorrectBuilding() {
        Coords buildTo = new Coords(3,2);
        player.checkBuild(builder1,buildTo);
        player.build(builder1, buildTo);
        assertSame(match.getHeight(buildTo),1);
    }

    @Test
    public void BuildWithEffect_CorrectInput_CorrectBuilding() {
        player.setGod(new Hephaestus(true));
        Coords buildTo = new Coords(3,2);
        player.checkBuild(builder1,buildTo);
        player.build(builder1, buildTo);
        assertSame(match.getHeight(buildTo),2);
    }

}
