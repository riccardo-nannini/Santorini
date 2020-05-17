package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.*;
import it.polimi.ingsw.PSP13.model.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AthenaDebuff extends Decorator{

    public AthenaDebuff(Turn god)
    {
        super(god);
    }
    Player player;

    @Override
    public void start(String player) throws IOException {
        god.start(player);
    }

    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

        /**
         * In addition to the player's god condition on checkMove, the AthenaDebuff adds a check
         * on the movement action controlling if the builder is moving up.
         * @param builder builder that is currently moving
         * @param coords coordinates of the cell where the builder wants to move to
         * @return false if the builder is moving up, the result of player's god checkMove otherwise.
         * */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        player = match.getPlayerByBuilder(builder);
        if (god.checkMove(builder, coords)) {
            if (match.getHeight(builder.getCoords()) < match.getHeight(coords)) {
                return false;
            } else return true;
        } return false;
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
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        return god.checkWin(builder,precedentPosition,currentPosition);
    }

    @Override
    public List<Coords> getCellMoves(Builder builder) {
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleMove = new ArrayList<>();

        for(Coords coords : adjacents)
        {
            if(this.checkMove(builder, coords))
                possibleMove.add(coords);
        }
        return possibleMove;
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

