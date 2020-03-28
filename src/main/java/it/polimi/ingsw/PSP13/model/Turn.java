package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.player.*;
import it.polimi.ingsw.PSP13.model.board.*;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.PSP13.model.board.Map.isLegal;

public class Turn {

    protected Match match;

    public Turn(Match match)
    {
        this.match = match;
    }

    public Turn()
    {

    }


    public void move()
    {}

    public boolean checkMove()
    {}

    public void build(Builder builder, Coords buildingPosition) throws IllegalBuildException
    {
        if(!checkBuild(builder, buildingPosition)) {
            throw new IllegalBuildException();
        }
        int currentLevel = match.getHeight(buildingPosition);
        match.setCell(buildingPosition, Level.findLevelByHeight(currentLevel+1));
    }

    public boolean checkBuild(Builder builder, Coords buildingPosition)
    {
        if (!isLegal(buildingPosition)) return false;
        if (match.isOccupied(buildingPosition)) return false;
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        if(!adjacents.contains(buildingPosition)) return false;

        return true;
    }

    public void setup()
    {}

    public boolean checkWin(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        if (match.getCell(precedentPosition).getLevel() == Level.Medium
                && match.getCell(currentPosition).getLevel() == Level.Top) {
            return true;
        }
        return false;
    }

    public void force()
    {}

    public void end()
    {}


}
