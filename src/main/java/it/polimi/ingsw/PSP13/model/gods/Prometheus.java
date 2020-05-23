package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;
import java.util.List;

public class Prometheus extends Turn {

    private Boolean usedEffect;

    /**
     * Sets the effect description
     */
    public Prometheus() {
        usedEffect = false;
        effect = " If your Worker does not move up, it may build both before and after moving";
    }



    @Override
    public void start(String player) throws IOException {
        usedEffect = turnHandler.getInputUseEffect(player, "Prometheus");
    }


    /**
     * In addition to turn's move allows the builder to build both before and after
     * moving if he does not move up.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        if (usedEffect) {
            List<Coords> possibleBuilds = getCellBuilds(builder);
            if (!possibleBuilds.isEmpty()) {
                Coords firstBuildCoords = turnHandler.getInputBuild(builder,possibleBuilds);
                int heightFirstBuild = match.getHeight(firstBuildCoords);
                int heightBuilder = match.getHeight(builder.getCoords());
                if (!(coords.equals(firstBuildCoords) && (heightFirstBuild == 3 || heightFirstBuild == heightBuilder))) {
                    build(builder, firstBuildCoords);
                }
            } else usedEffect = false;
        }
        super.move(builder,coords);
    }


    /**
     * Unlike turn's checkmove, if the player uses Prometheus effect he cannot
     * move up
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return true if builder can move into coords' cell, else return false
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        if (checkUseEffect(builder)) {
            if (usedEffect) {
                if (!Map.isLegal(coords)) {
                    throw new IllegalArgumentException();
                } else {
                    int diff = match.getCell(coords).getLevel().getHeight() - match.getHeight(builder.getCoords());
                    return match.getAdjacent(builder.getCoords()).contains(coords) && !match.isOccupied(coords) && diff <= 0;
                }
            } else return super.checkMove(builder, coords);
        }
        usedEffect = false;
        return super.checkMove(builder, coords);
    }

    /**
     * @param builder moving builder
     * @return true if there's a free cell near the builder with a height <= the height of builder's cell
     */
    public boolean checkUseEffect(Builder builder) {
        for (Coords adjacentCoords : match.getAdjacent(builder.getCoords())) {
            if (!match.isOccupied(adjacentCoords) && match.getHeight(adjacentCoords) <= match.getHeight(builder.getCoords())) {
                return true;
            }
        }
        return false;
    }


}
