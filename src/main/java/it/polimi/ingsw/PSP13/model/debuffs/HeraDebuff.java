package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeraDebuff extends Decorator {

    public HeraDebuff(Turn god) {
        super(god);
    }
    private Player player;

    @Override
    public void start(String player) throws IOException {
        god.start(player);
    }

    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        return god.checkMove(builder, coords);
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
        player = match.getPlayerByBuilder(builder);
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

    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        god.build(builder, buildingPosition);
    }

    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {
        return god.checkBuild(builder, buildingPosition);
    }

    @Override
    public List<Coords> getCellMoves(Builder builder) {
        return god.getCellMoves(builder);
    }

    @Override
    public List<Coords> getCellBuilds(Builder builder) {
        return god.getCellBuilds(builder);
    }

    /**
     * Removes the AthenaDebuff decorator since the effect only applies for one turn
     */
    @Override
    public void end() throws IOException {
        god.end();
        removeDecorator(player);
    }
}
