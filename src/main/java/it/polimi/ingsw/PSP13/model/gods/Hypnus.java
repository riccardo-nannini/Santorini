package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HypnusDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;

public class Hypnus extends Turn {

    /**
     * Sets the effect description
     */
    public Hypnus() {
        effect = "If one of your opponent's Workers is higher than all of their others, it cannot move";
    }

    /**
     * Adds the Hypnusdebuff decorator to every opponent player in order to apply Hypnus's move
     * condition on them.
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
                HypnusDebuff debuff = new HypnusDebuff(player.getGod());
                player.setGod(debuff);
            }
        }
    }

    @Override
    public void eliminated() {
        HypnusDebuff.setEliminated(true);
        for (Player player : match.getPlayers()) {
            player.eliminated();
        }
    }
}
