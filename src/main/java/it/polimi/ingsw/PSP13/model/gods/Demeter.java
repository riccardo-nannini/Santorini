package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public class Demeter extends Turn {

    /**
     * In addiction to turn's build allows the builder to build one additional
     * time, but not on the space.
     * @param builder builder that is currently building
     * @param coords coordinates of the cell where the builder wants to build
     */
    @Override
    public void build(Builder builder, Coords coords) {
        super.build(builder, coords);
        List<Coords> possibleBuilds = getCellBuilds(builder);
        possibleBuilds.remove(coords);
        if (!possibleBuilds.isEmpty()) {
            boolean useEffect = turnHandler.getInputUseEffect("Demeter");
            if (useEffect) {
                Coords secondCoords = turnHandler.getInputBuild(builder, possibleBuilds);
                super.build(builder, secondCoords);
            }
        }
    }

}
