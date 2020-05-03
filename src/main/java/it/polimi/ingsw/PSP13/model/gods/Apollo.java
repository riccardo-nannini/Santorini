package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.IOException;

public class Apollo extends Turn {

    /**
     * Sets the effect description
     */
    public Apollo() {
        effect = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated";
    }

    /**
     * @param apollo
     * @param coords
     * @return true if coords' cell is occupied by a player instead of a dome or it's not occupied
     */
    private boolean apolloCheck(Builder apollo, Coords coords)
    {
        if(!match.isOccupied(coords))
            return true;
        else
        {
            return !match.getCell(coords).getDome();
        }

    }

    /**
     * builder can move even if a cell is already occupied by another player's builder
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public boolean checkMove(Builder builder, Coords coords) throws IllegalArgumentException
    {
        if (!Map.isLegal(coords) || builder == null) {
            throw new IllegalArgumentException();
        } else {
            int diff = match.getCell(coords).getLevel().getHeight() - builder.getHeight();
            Builder otherBuilder;
            if (match.getPlayerByBuilder(builder).getBuilders()[0] == builder) {
                otherBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
            } else {
                otherBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
            }
            return Turn.match.getAdjacent(builder.getCoords()).contains(coords) && diff <= 1 && apolloCheck(builder, coords) && !otherBuilder.getCoords().equals(coords);
        }

    }

    /**
     * the opponent builder is forced to swap position if needed
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) throws IOException {

        if (match.isOccupied(coords) && !match.getCell(coords).getDome())
            {
                Builder opponent;
                opponent = match.getBuilderByCoords(coords);
                Coords old = builder.getCoords();
                match.getPlayerByBuilder(opponent).getGod().force(opponent, old);
            }

            super.move(builder, coords);

    }



}
