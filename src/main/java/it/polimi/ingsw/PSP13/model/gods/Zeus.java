package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Zeus extends Turn {

    public Zeus()
    {

    }

    /**
     * a Builder can build on his own block
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IllegalBuildException
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IllegalBuildException
    {
        if(!checkBuild(builder, buildingPosition))
        {
            if(!builder.getCoords().equals(buildingPosition))
                throw new IllegalBuildException();
        }
        int currentLevel = match.getHeight(buildingPosition);
        match.setCell(buildingPosition, Level.findLevelByHeight(currentLevel+1));
    }


}
