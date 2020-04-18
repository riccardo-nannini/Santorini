package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public class Prometheus extends Turn {

    private Boolean usedEffect;

    public Prometheus (TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
        usedEffect = false;
    }


    @Override
    public void start() {
        usedEffect = turnHandler.getInputUseEffect();
    }


    /**
     * In addition to turn's move allows the builder to build both before and after
     * moving if he does not move up.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        if (usedEffect) {
            List<Coords> possibleBuilds = getCellBuilds(builder);
            if (!possibleBuilds.isEmpty()) {
                Coords firstBuildCoords = turnHandler.getInputBuild(builder,possibleBuilds);
                build(builder, firstBuildCoords);
                match.notifyMap();
            } else usedEffect = false;
            super.move(builder,coords);
        }

    }


    /**
     * Unlike turn's checkmove if the player use Prometheus effect he cannot
     * move up
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return true if builder can move into coords' cell, else return false
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        if (usedEffect) {
            if (!Map.isLegal(coords) || builder == null) {
                throw new IllegalArgumentException();
            } else {
                int diff = match.getCell(coords).getLevel().getHeight() - match.getHeight(builder.getCoords());
                return match.getAdjacent(builder.getCoords()).contains(coords) && !match.isOccupied(coords) && diff <= 0;
            }
        } else return super.checkMove(builder,coords);
    }


}


