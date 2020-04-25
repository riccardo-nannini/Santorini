package it.polimi.ingsw.PSP13.immutables;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.Serializable;

public class BuilderVM implements Serializable {


    private static final long serialVersionUID = 552L;
    private final Color color;
    private final Coords[] builders;

    public Color getColor() {
        return color;
    }

    public Coords[] getBuilders() {
        return builders;
    }

    /**
     * Creates an immutable couple of builders of the same color
     * @param builders
     * @param color
     */
    public BuilderVM(Coords[] builders, Color color)
    {
        this.builders = builders;
        this.color = color;
    }




}
