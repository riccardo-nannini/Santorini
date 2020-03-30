package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;


import java.util.List;

public class Hephaestus extends Turn {

    //momentaneamente pubblico per i test
    private boolean useEffect;
    //useEffect potrà essere settato a 1 soltanto se il giocatore vuole costruire in una cella a livello max = 1

    public Hephaestus() {
        useEffect = false;
    }

    //momentaneo per test
    public Hephaestus(Boolean useEffect) {
        this.useEffect = useEffect;
    }


    /**
     * Builds a level in the specified position if useEffect == false,
     * 2 level otherwise
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @throws IllegalBuildException if buildingPosition is not legal
     */
    @Override
    public void build(Builder builder, Coords buildingPosition) throws IllegalBuildException{
        if(!checkBuild(builder, buildingPosition)) {
            throw new IllegalBuildException();
        }
        int currentLevel = match.getHeight(buildingPosition);
        if (useEffect) {
            match.setCell(buildingPosition, Level.findLevelByHeight(currentLevel+2));
        } else {
            match.setCell(buildingPosition, Level.findLevelByHeight(currentLevel+1));
        }
    }

    /**
     * In addition to the checks of turn's checkBuild,
     * if useEffect == true checks if the level of the
     * buildingPosition cell is <= 1
     * @param builder builder that is currently building
     * @param buildingPosition coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position in according to Hephaestus' effect (if useEffect == true), false otherwise
     */
    @Override
    public boolean checkBuild(Builder builder, Coords buildingPosition) {

        if (!Map.isLegal(buildingPosition)) return false;
        if (match.isOccupied(buildingPosition)) return false;
        List<Coords> adjacents = match.getAdjacent(builder.getCoords());
        if(!adjacents.contains(buildingPosition)) return false;
        if (useEffect) {
            return match.getHeight(buildingPosition) <= 1;
        }

        return true;



    }




}
