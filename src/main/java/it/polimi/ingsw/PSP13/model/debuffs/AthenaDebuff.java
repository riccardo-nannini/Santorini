package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AthenaDebuff extends Decorator{

    public AthenaDebuff(Turn god)
    {
        super(god);
    }
    Player player;

    /**
     * @param builder1 the first player's builder
     * @param builder2 the second player's builder
     * @param coords1 the coordinates where the first builder will be placed
     * @param coords2 the coordinates where the second builder will be placed
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @Override
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IllegalArgumentException, IOException {
        god.setup(builder1, builder2, coords1, coords2);
    }


    /**
     * The AthenaDebuff decorator uses the player's god start method
     * @param player current player
     * @throws IOException
     */
    @Override
    public void start(String player) throws IOException {
        god.start(player);
    }

    /**
     * Checks if the builder belongs to the player
     * @param player current player
     * @param builder selected builder
     * @return true if the builder belongs to the player, false otherwise
     */
    @Override
    public boolean builderSelection(String player, Builder builder) {
        return god.builderSelection(player, builder);
    }

    /**
     * The AthenaDebuff decorator uses the player's god move method
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @throws IOException
     */
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

    /**
     * The AthenaDebuff decorator uses the player's god build method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IOException
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        god.build(builder, buildingPosition);
    }

    /**
     * The AthenaDebuff decorator uses the player's god checkBuild method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position, false otherwise
     */
    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {
        return god.checkBuild(builder, buildingPosition);
    }

    /**
     * The AthenaDebuff decorator uses the player's god checkWin method
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return true if the player did win in this turn, false otherwise
     */
    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        return god.checkWin(builder,precedentPosition,currentPosition);
    }

    /**
     * The AthenaDebuff decorator uses the player's god getPossibleMoves method
     * @param builder the current builder
     * @return list of possible moves
     */
    @Override
    public List<Coords> getPossibleMoves(Builder builder) {
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        List<Coords> possibleMove = new ArrayList<>();

        for(Coords coords : adjacents)
        {
            if(this.checkMove(builder, coords))
                possibleMove.add(coords);
        }
        return possibleMove;
    }

    /**
     * The AthenaDebuff decorator uses the player's god getBuildableCells method
     * @param builder the current builder
     * @return list of the buildable cells
     */
    @Override
    public List<Coords> getBuildableCells(Builder builder) {
        return god.getBuildableCells(builder);
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

