package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Artemis extends Turn {

    private Boolean useEffect;
    private Coords secondCoords;
    private Coords startedCoords;

    public Artemis () {
        this.useEffect = false;
        this.secondCoords = null;
        this.startedCoords = null;
    }

    //momentaneo per test
    public Artemis (Boolean useEffect, Coords secondCoords) {
        this.useEffect = useEffect;
        this.secondCoords = secondCoords;
        this.startedCoords = null;
    }

    /**
     * In addiction to turn's move allows the builder to move one additional
     * time
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        startedCoords = builder.getCoords();
        super.move(builder, coords);
        //richiesta all'utente di voler usare l'effetto di artemide --> set useEffecet
        //se vuole usarlo, chiediamo anche le nuove coordinate --> set SecondCoords
        if (useEffect) {
            if (checkMove(builder,secondCoords) && startedCoords != secondCoords) {
                super.move(builder,secondCoords);
            }
        }
    }
}
