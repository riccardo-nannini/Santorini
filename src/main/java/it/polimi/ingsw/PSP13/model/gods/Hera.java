package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.HeraDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;


public class Hera extends Turn {

    /**
     * Adds the HeraDebuff decorator to every opponent player in order to apply Hera's win
     * condition on them
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
                HeraDebuff debuff = new HeraDebuff(player.getGod());
                player.setGod(debuff);
            }
        }
    }
}
