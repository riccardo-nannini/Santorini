package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.*;
import it.polimi.ingsw.PSP13.model.Match;


public class AthenaDebuff extends Decorator{

    public AthenaDebuff(Turn god)
    {
        super(god);
    }

    Player player;

    /**
     * In addition to the player's god condition on checkMove, the AthenaDebuff adds a check
     * on the movement action controlling if the builder is moving up.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move to
     * @return false if the builder is moving up, the result of player's god checkMove otherwise.
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        player = match.getPlayerByBuilder(builder);
        if (god.checkMove(builder, coords)) {
            if (match.getHeight(builder.getCoords()) < match.getHeight(coords)) {
                return false;
            } else return true;
        } return false;
    }

    /**
     * Removes the AthenDebuff decorator since the effect only applies for one turn
     */
    @Override
    public void end() {
        god.end();
        removeDecorator(player);
    }
}

