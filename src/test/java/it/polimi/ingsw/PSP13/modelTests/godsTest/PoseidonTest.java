package it.polimi.ingsw.PSP13.modelTests.godsTest;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.gods.Poseidon;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class PoseidonTest {

    //TODO aggiungere nuovi test, correggere quelli che ci sono già

    public static Match match;
    public static Player player;
    public static Builder builder1;
    public static Builder builder2;

    @BeforeClass
    public static void setup() {
        match = new Match();
        try {
            match.start(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(Color.Blue, "Mario");

        match.addPlayer(player);

        new Turn(match, null);

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
        player.getBuilders()[1].setCell(match.getCell(new Coords(1, 1)));
    }

    @Test
    public void UnmovedBuilder_1Build() {
    }

    @Test
    public void UnmovedBuilder_2Build() {
    }

    @Test
    public void UnmovedBuilder_3Build() {
    }
}
