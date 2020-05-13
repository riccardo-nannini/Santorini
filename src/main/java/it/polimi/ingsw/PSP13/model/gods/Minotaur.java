package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.controller.TurnHandler;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;

public class Minotaur extends Turn {

    /**
     * Sets the effect description
     */
    public Minotaur() {
        effect = "Your Worker may move into an opponent Worker's space, if their Worker can be forced one space straight backwards to an unoccupied space at any level";
    }

    /**
     * @param builder
     * @param coords
     * @return coordinate of the cell in the next space
     * in the same direction of builder's coords
     */
    private Coords minotaurForce(Builder builder, Coords coords)
    {
        int x = (coords.getX() - builder.getCoords().getX()) + coords.getX();
        int y = (coords.getY() - builder.getCoords().getY()) + coords.getY();
        return new Coords(x,y);
    }

    /**
     * Adds to the turn's move Minotaur's effect:
     * the builder can move into an opponent builder's cell, if the next space in the same
     * direction is unoccupied. Their builder is forced into that space
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     *
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        if (match.isOccupied(coords) && !match.getCell(coords).getDome()) {
            Coords forcedPos = minotaurForce(builder, coords);

            Builder[] oppoBuilders = match.getPlayerByBuilder(match.getBuilderByCoords(coords)).getBuilders();

            match.getBuilderByCoords(coords).setCell(match.getCell(forcedPos));
            builder.setCell(match.getCell(coords));

            match.notifyBuilder(oppoBuilders[0], oppoBuilders[1]);

        } else {
            builder.setCell(match.getCell(coords));
        }
        match.notifyBuilder(builder,match.getOtherBuilder(builder));
    }


    /**
     * Checks if the moving position is legal considering Minotaur's effect:
     * the builder can move into an opponent builder's cell, if the next space in the same
     * direction is unoccupied. Their builder is forced into that space.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return true if moving position is legal, else otherwise
     */
    public boolean checkMove(Builder builder, Coords coords) {
        if (!Map.isLegal(coords) || builder == null) {
            return false;
        } else {
            if(match.getCell(coords).getDome())
                return false;
            if(match.isOccupied(coords) && !match.getCell(coords).getDome())
            {
                if(!Map.isLegal(minotaurForce(builder,coords)))
                    return false;
                if (match.getPlayerByBuilder(match.getBuilderByCoords(coords)) == match.getPlayerByBuilder(builder))
                    return false;
                if(match.isOccupied(minotaurForce(builder,coords)))
                    return false;
            }

            return match.getAdjacent(builder.getCoords()).contains(coords);
        }
    }
}
