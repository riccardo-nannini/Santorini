package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Minotaur extends Turn {

    public Minotaur() { }

    /**
     * Adds to the turn's move Minotaur's effect:
     * the builder can move into an opponent builder's cell, if the next space in the same
     * direction is unoccupied. Their builder is forced into that space.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @throws IllegalMoveException if checkMove(builder, coords) returns false or if coords' cell
     * is occupied by a dome or by a player's builder or if the next space in the same direction is occupied
     *
     */
    @Override
    public void move(Builder builder, Coords coords) throws IllegalMoveException {
        if (checkMove(builder, coords)) {
            if (match.isOccupied(coords)) {
                if (match.getHeight(coords) < 4) {
                    int x = (coords.getX() - builder.getCoords().getX()) + coords.getX();
                    int y = (coords.getY() - builder.getCoords().getY()) + coords.getY();
                    Coords forcedPos = new Coords(x,y);
                    if (!match.isOccupied(forcedPos)) {
                        if (!(match.getPlayerByBuilder(match.getBuilderByCoords(coords)) == match.getPlayerByBuilder(builder))) {
                            match.getBuilderByCoords(coords).setCoords(forcedPos);
                            builder.setCoords(coords);
                        } else { throw new IllegalMoveException(); }
                    } else { throw new IllegalMoveException(); }
                } else { throw new IllegalMoveException(); }
            } else {
                builder.setCoords(coords);
            }
        } else { throw new IllegalMoveException();}
    }


    /**
     * Unlike turn's checkmove, doesn't check if coords' cell is occupied
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return
     * @throws IllegalArgumentException if params aren't legal
     */
    public boolean checkMove(Builder builder, Coords coords) throws IllegalArgumentException {
        if (!Map.isLegal(coords) || builder == null) {
            throw new IllegalArgumentException();
        } else {
            int diff = match.getCell(coords).getLevel().getHeight() - match.getHeight(builder.getCoords());
            return match.getAdjacent(builder.getCoords()).contains(coords) && diff <= 1;
        }
    }
}
