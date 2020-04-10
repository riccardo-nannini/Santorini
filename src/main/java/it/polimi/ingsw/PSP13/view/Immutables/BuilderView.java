package it.polimi.ingsw.PSP13.view.Immutables;

import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;

public class BuilderView {

    private final Color color;
    private final Coords coords;

    public Color getColor() {
        return color;
    }

    public Coords getCoords() {
        return coords;
    }

    /**
     * creates an immutable builder
     * @param builder
     * @param player
     */
    public BuilderView(Builder builder, Player player)
    {
        this.color = player.getColor();
        this.coords = builder.getCoords();
    }




}
