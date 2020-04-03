package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Atlas extends Turn {

    private Boolean useEffect;

    public Atlas() {
        this.useEffect = false;
    }

    //momentaneo per testing
    public Atlas(Boolean useEffect) {
        this.useEffect = useEffect;
    }

    /**
     * Adds to the standard build Atlas' effect:
     * the builder can build a dome at any level
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IllegalBuildException if buildingPosition is not legal
     */
    @Override
    public void build(Builder builder, Coords buildingPosition)
    {
        if (useEffect) {
            match.getCell(buildingPosition).setDome(true);
        } else {
            match.setCellLevel(buildingPosition, Level.findLevelByHeight(match.getHeight(buildingPosition)+1));
        }
    }
}
