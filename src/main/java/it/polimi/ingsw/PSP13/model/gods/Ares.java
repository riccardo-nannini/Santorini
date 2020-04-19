package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.ArrayList;
import java.util.List;

public class Ares extends Turn {

    private Builder unmovedBuilder;

    /**
     * In addiction to turn's move
     * sets the unmovedBuilder
     */
    @Override
    public void move(Builder builder, Coords coords) {
        if (match.getPlayerByBuilder(builder).getBuilders()[0] == builder) {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
        } else {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
        }
        super.move(builder, coords);
    }

    /**
     * Removes an unoccupied block (not dome)
     * neighbouring player's unmoved worker
     * if useEffect = true
     */
    @Override
    public void end() {
        List<Coords> possibleRemoves = getCellRemoves(unmovedBuilder);
        if (!possibleRemoves.isEmpty()) {
            boolean useEffect = turnHandler.getInputUseEffect("Ares");
            if (useEffect) {
                Coords removeCoords = turnHandler.getInputRemoveBlock(unmovedBuilder,possibleRemoves);
                int level = match.getHeight(removeCoords);
                if (level >= 1) {
                    match.getCell(removeCoords).setLevel(Level.findLevelByHeight(level - 1));
                    match.notifyMap();
                }
            }
        }
    }


    public List<Coords> getCellRemoves(Builder builder) {

        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleRemoves = new ArrayList<>();

        for (Coords coords : adjacents) {
            if (!match.isOccupied(coords) && match.getHeight(coords) > 0) {
                possibleRemoves.add(coords);
            }
        }
        return possibleRemoves;
    }

}
