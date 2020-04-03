package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Prometheus extends Turn {

    private Boolean useEffect;
    private Coords beforeMove;

    public Prometheus () {
        this.useEffect = false;
        this.beforeMove = null;
    }

    //momentaneo per test
    public Prometheus (Boolean useEffect, Coords beforeMove) {
        this.useEffect = useEffect;
        this.beforeMove = beforeMove;
    }

    /**
     * In addition to turn's move allows the builder to build both before and after
     * moving if he does not move up.
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        if (match.getHeight(coords) <= match.getHeight(builder.getCoords())) {
            //richiesta uso effetto -> set useEffect
            // se lo usa, richiedo sencondo input (casella in cui voglio costruir
            // e prima di muovermi -> set beforeMove
            if (useEffect) {
                if (checkBuild(builder, beforeMove)) {
                    build(builder, beforeMove);
                }
            }
        }
        super.move(builder, coords);
    }
}
