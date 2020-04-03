package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Demeter extends Turn {

    private Boolean useEffect;
    private Coords secondCoords;

    public Demeter () {
        this.useEffect = false;
        this.secondCoords = null;
    }

    //momentaneo per test
    public Demeter (Boolean useEffect, Coords secondCoords) {
        this.useEffect = useEffect;
        this.secondCoords = secondCoords;
    }

    /**
     * In addiction to turn's build allows the builder to build one additional
     * time, but not on the space.
     * @param builder builder that is currently building
     * @param coords coordinates of the cell where the builder wants to build
     */
    @Override
    public void build(Builder builder, Coords coords) {
        super.build(builder, coords);
        //richiesta all'utente di voler usare l'effetto di demeter --> set useEffecet
        //se vuole usarlo, chiediamo anche le nuove coordinate --> set SecondCoords
        if (useEffect) {
            if (checkBuild(builder,secondCoords) && coords != secondCoords) {
                super.build(builder,secondCoords);
            }
        }
    }
}
