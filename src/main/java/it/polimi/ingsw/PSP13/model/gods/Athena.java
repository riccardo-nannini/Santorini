package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.debuffs.AthenaDebuff;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;

public class Athena extends Turn {

    /**
     * Sets the effect description
     */
    public Athena() {
        effect = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn";
    }

    /**
     * In addition to calling the player's god move the method checks if the builder is
     * moving up.
     * If this is the case it will decorate other players' god with the AthenDebuff
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move to
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        Coords precedentPosition = builder.getCoords();
        super.move(builder, coords);
        if (match.getHeight(precedentPosition) < builder.getHeight()) {
            for (Player player : match.getPlayers()) {
                if (player != match.getPlayerByBuilder(builder)) {
                    AthenaDebuff debuff = new AthenaDebuff(player.getGod());
                    player.setGod(debuff);
                }
            }
        }
    }


}
