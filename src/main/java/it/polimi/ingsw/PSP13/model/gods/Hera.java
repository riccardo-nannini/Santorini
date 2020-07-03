package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;


public class Hera extends Turn {

    /**
     * Sets the effect description
     */
    public Hera() {
        effect = "An opponent cannot win by moving into a perimeter space";
    }

    /**
     * Adds the HeraDebuff decorator to every opponent player in order to apply Hera's win condition on them
     * @param builder1 the first player's builder
     * @param builder2 the second player's builder
     * @param coords1 the coordinates where the first builder will be placed
     * @param coords2 the coordinates where the second builder will be placed
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @Override
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IllegalArgumentException, IOException {
        super.setup(builder1, builder2, coords1, coords2);
        for (Player player : match.getPlayers()) {
            if (player != match.getPlayerByBuilder(builder1)) {
                HeraDebuff debuff = new HeraDebuff(player.getGod());
                player.setGod(debuff);
            }
        }
    }

    @Override
    public void eliminated() {
        HeraDebuff.setEliminated(true);
        for (Player player : match.getPlayers()) {
            player.eliminated();
        }
    }
}
