package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ares extends Turn {

    private Builder unmovedBuilder;

    /**
     * Sets the effect description
     */
    public Ares () {
        effect =  "You may remove an unoccupied block (not dome) neighboring your unmoved Worker";

    }
    /**
     * In addiction to turn's move
     * sets the unmovedBuilder
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
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
    public void end() throws IOException {
        List<Coords> possibleRemoves = getRemovableCells(unmovedBuilder);
        if (!possibleRemoves.isEmpty()) {
            String username = match.getPlayerByBuilder(unmovedBuilder).getUsername();
            boolean useEffect = turnHandler.getInputUseEffect(username,"Ares");
            if (useEffect) {
                Coords removeCoords = turnHandler.getInputRemoveBlock(unmovedBuilder,possibleRemoves);
                int level = match.getHeight(removeCoords);
                match.getCell(removeCoords).setLevel(Level.findLevelByHeight(level - 1));
                match.notifyMap();
            }
        }
    }

    /**
     * @param builder
     * @return a list of unoccupied cells with a level > 0 neighbouring builder
     */
    public List<Coords> getRemovableCells(Builder builder) {

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
