package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HypnusDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

public class Hypnus extends Turn {

    /**
     * Adds the Hypnusdebuff decorator to every opponent player in order to apply Hypnus's move
     * condition on them.
     * @param builder1
     * @param builder2
     * @param coords1
     * @param coords2
     * @throws IllegalArgumentException
     */
    @Override
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IllegalArgumentException {
        super.setup(builder1, builder2, coords1, coords2);
        for (Player player : match.getPlayers()) {
            if (player != match.getPlayerByBuilder(builder1)) {
                HypnusDebuff debuff = new HypnusDebuff(player.getGod());
                player.setGod(debuff);
            }
        }
    }
}
