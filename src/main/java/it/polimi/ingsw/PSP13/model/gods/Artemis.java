package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public class Artemis extends Turn {

    /**
     * In addiction to turn's move allows the builder to move one additional
     * time
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        Coords startedCoords = builder.getCoords();
        super.move(builder, coords);
        if (checkWin(builder,startedCoords,coords)) return;
        List<Coords> possibleMoves = getCellMoves(builder);
        possibleMoves.remove(startedCoords);
        if (!possibleMoves.isEmpty()) {
            boolean useEffect = turnHandler.getInputUseEffect("Artemis");
            if (useEffect) {
                Coords secondCoords = turnHandler.getInputMove(builder, possibleMoves);
                super.move(builder, secondCoords);
            }
        }
    }

}
