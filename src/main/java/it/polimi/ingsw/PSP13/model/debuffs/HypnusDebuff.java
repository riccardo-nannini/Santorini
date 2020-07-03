package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.List;

public class HypnusDebuff extends Decorator {

    public HypnusDebuff(Turn god) {
        super(god);
    }

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
     * The HypnusDebuff decorator uses the player's god start method
     * @param player current player
     * @throws IOException
     */
    @Override
    public void start(String player) throws IOException {
        god.start(player);
    }

    /**
     * In addition to the player's god builderSelection method, checks if the player being choosed is higher than
     * the other player's builder
     * @param player current player
     * @param builder selected builder
     * @return true if the builder belongs to the player and is not higher than the other, false otherwise
     */
    @Override
    public boolean builderSelection(String player, Builder builder) {
        if (god.builderSelection(player, builder)) {
            if (!this.isHigher(builder)) return true;
        }
        return false;
    }

    /**
     * The HypnusDebuff decorator uses the player's god move method
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @throws IOException
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

    /**
     * The HypnusDebuff decorator uses the player's god move method
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        return god.checkMove(builder, coords);
    }

    /**
     * @param builder builder considered
     * @return true if the builder is higher than the player's other builder, false otherwise
     */
    private boolean isHigher(Builder builder) {
        Player player = match.getPlayerByBuilder(builder);
        Builder otherBuilder = player.getBuilders()[0] == builder ? player.getBuilders()[1] : player.getBuilders()[0];
        return builder.getHeight() > otherBuilder.getHeight();
    }

    /**
     * The HypnusDebuff decorator uses the player's god build method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IOException
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        god.build(builder, buildingPosition);
    }

    /**
     * The HypnusDebuff decorator uses the player's god checkBuild method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return
     */
    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {
        return god.checkBuild(builder, buildingPosition);
    }

    /**
     * The HypnusDebuff decorator uses the player's god checkWin method
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return
     */
    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        return god.checkWin(builder,precedentPosition,currentPosition);
    }

    /**
     * The HypnusDebuff decorator uses the player's god getPossibleMoves method
     * @param builder current builder
     * @return a list of adjacent cells where a builder can move in
     */
    @Override
    public List<Coords> getPossibleMoves(Builder builder) {
        return god.getPossibleMoves(builder);
    }

    /**
     * The HypnusDebuff decorator uses the player's god getBuildableCells method
     * @param builder current builder
     * @return a list of adjacent cells where a builder can build on
     */
    @Override
    public List<Coords> getBuildableCells(Builder builder) {
        return god.getBuildableCells(builder);
    }

    @Override
    public void end() throws IOException {
        god.end();
    }
}



