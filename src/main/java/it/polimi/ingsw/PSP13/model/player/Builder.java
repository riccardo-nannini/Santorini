package it.polimi.ingsw.PSP13.model.player;

public class Builder {

    private Coords coords;

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Builder() {
        coords = null;
    }
}
