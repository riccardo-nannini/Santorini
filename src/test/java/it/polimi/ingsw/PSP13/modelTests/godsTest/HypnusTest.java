package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HypnusDebuff;
import it.polimi.ingsw.PSP13.model.gods.Hypnus;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HypnusTest {

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;
    public static Player opponentPlayer;
    public static Builder opponentsbuilder1;
    public static Builder opponentsbuilder2;

    @BeforeClass
    public static void setup() {
        match = new Match();
        match.start();
        player = new Player(Color.Blue, 21, "Mario");
        opponentPlayer = new Player(Color.Brown, 21, "Diego");
        new Turn(match);

        match.addPlayer(player);
        match.addPlayer(opponentPlayer);

        builder1 = new Builder();
        builder2 = new Builder();
        player.setBuilders(new Builder[]{builder1, builder2});
        player.setGod(new Hypnus());

        opponentsbuilder1 = new Builder();
        opponentsbuilder2 = new Builder();
        opponentPlayer.setBuilders(new Builder[]{opponentsbuilder1, opponentsbuilder2});
        opponentPlayer.setGod(new Turn());
    }

    @Test
    public void AppliedDebuff_OpponentTest() {
        player.setup(player.getBuilders()[0], player.getBuilders()[1],
                new Coords(0,0), new Coords(0,1));
        assertTrue(opponentPlayer.getGod() instanceof HypnusDebuff);
    }

    @Test
    public void NotAppliedDebuff_SamePlayerTest() {
        player.setup(player.getBuilders()[0], player.getBuilders()[1],
                new Coords(0,0), new Coords(0,1));
        assertFalse(player.getGod() instanceof HypnusDebuff);
    }

}
