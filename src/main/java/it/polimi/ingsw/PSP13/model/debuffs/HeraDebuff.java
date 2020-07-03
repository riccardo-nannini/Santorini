package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.util.List;

public class HeraDebuff extends Decorator {

    public HeraDebuff(Turn god) {
        super(god);
    }
    Player player;
    private static boolean eliminated = false;
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
     * The HeraDebuff decorator uses the player's god start method
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
     * The HeraDebuff decorator uses the player's god move method
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @throws IOException
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {
        player = match.getPlayerByBuilder(builder);
        god.move(builder, coords);
    }

    /**
     * The HeraDebuff decorator uses the player's god checkMove method
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) {
        return god.checkMove(builder, coords);
    }

    /**
     * In addition to the player's win condition, checks if the player moved to a perimetral cell.
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return false if the player's win condition is false or the builder moved to a perimetral cell, true otherwise.
     */
    @Override
    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition) {
        if (god.checkWin(builder, precedentPosition, currentPosition)) {
            if (!isPerimetral(currentPosition)) return true;
        }
        return false;
    }

    /**
     * Checks if the given position is perimetral
     * @param position position that is being tested
     * @return
     */
    private boolean isPerimetral(Coords position) {
        int x = 0;
        int y = 0;
        int i, j;
        for (i=0; i <= 4; i++) {
            Coords temp = new Coords(x+i, y);
            if (temp.equals(position)) return true;
        }
        i = 4;
        for (j=0; j <= 4; j++) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        j = 4;
        for (i=4; i >= 0; i--) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        i = 0;
        for (j=4; j >= 0; j--) {
            Coords temp = new Coords(x+i, y+j);
            if (temp.equals(position)) return true;
        }
        return false;
    }

    /**
     * The HeraDebuff decorator uses the player's god build method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IOException
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IOException {
        god.build(builder, buildingPosition);
    }

    /**
     * The HeraDebuff decorator uses the player's god build method
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position, false otherwise
     */
    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {
        return god.checkBuild(builder, buildingPosition);
    }

    /**
     * The HeraDebuff decorator uses the player's god getPossibleMoves method
     * @param builder the current builder
     * @return list of possible moves
     */
    @Override
    public List<Coords> getPossibleMoves(Builder builder) {
        return god.getPossibleMoves(builder);
    }

    /**
     * The HeraDebuff decorator uses the player's god getBuildableCells method
     * @param builder the current builder
     * @return list of the buildable cells
     */
    @Override
    public List<Coords> getBuildableCells(Builder builder) {
        return god.getBuildableCells(builder);
    }

    public static void setEliminated(boolean eliminated) {
        HeraDebuff.eliminated = eliminated;
    }

    @Override
    public void end() throws IOException {
        god.end();
    }

    /**
     * If the player with Hera is eliminated removes the decorator
     */
    @Override
    public void eliminated() {
        if (eliminated) {
            removeDecorator(player);
        } else {
            god.eliminated();
        }
    }

}
