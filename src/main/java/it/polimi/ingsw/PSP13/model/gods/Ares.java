package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Ares extends Turn {

    private Builder unmovedBuilder;
    private Coords removeCoords;
    private Boolean useEffect;

    public Ares () {
        unmovedBuilder = null;
        removeCoords = null;
        useEffect = null;
    }

    //momentaneo per testing
    public Ares (Coords coords, Boolean useEffect) {
        this.useEffect = useEffect;
        this.removeCoords = coords;
    }

    /**
     *In addiction to turn's move
     *sets the unmovedBuilder
     */
    @Override
    public void move(Builder builder, Coords coords) throws IllegalMoveException {
        if (checkMove(builder, coords)) {
            if (builder.getPlayer().getBuilders()[0] == builder) {
                unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
            } else {
                unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
            }
            builder.setCoords(coords);
        } else {
            throw new IllegalMoveException();
        }
    }

    /**
     * removes an unoccupied block (not dome)
     * neighbouring player's unmoved worker
     */
    @Override
    public void end() {
        if (useEffect) {
            if (match.getAdjacent(unmovedBuilder.getCoords()).contains(removeCoords) && !match.isOccupied(removeCoords)) {
                int level = match.getHeight(removeCoords);
                if (level >= 1) {
                    match.getCell(removeCoords).setLevel(Level.findLevelByHeight(level - 1));
                }
            }
        }
    }

}
