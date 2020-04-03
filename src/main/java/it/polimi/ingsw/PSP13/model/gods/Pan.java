
package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Pan extends Turn {

    public Pan () { }

    /**
     * Adds to the standard win condition: win also if the builder moves down 2 or more levels
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return true if the player did win in this turn, false otherwise
     */

    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        int levelDiff = match.getHeight(precedentPosition) - match.getHeight(currentPosition);
        if (levelDiff >= 2) return true;
        return super.checkWin(builder,precedentPosition,currentPosition);
    }
}
