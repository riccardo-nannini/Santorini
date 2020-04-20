package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class HeraDebuff extends Decorator {

    public HeraDebuff(Turn god) {
        super(god);
    }

    /**
     * In addition to the player's win condition, checks if the player moved to a perimetral cell.
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return false if the player's win condition is false or the builder moved to a perimetral cell, true otherwise.
     */
    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        if (super.checkWin(builder, precedentPosition, currentPosition)) {
            if (!isPerimetral(currentPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given position is perimetral
     * @param position position that is being tested
     * @return
     */
    private boolean isPerimetral(Coords position) {
        int x = 0;
        int y = 0;
        int i, j;
        for (i=0; i <= 4; i++) {
            Coords temp = new Coords(x+i, y);
            if (temp.equals(position)) return true;
        }
        i = 4;
        for (j=0; j <= 4; j++) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        j = 4;
        for (i=4; i >= 0; i--) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        i = 0;
        for (j=4; j >= 0; j--) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        return false;
    }
}
