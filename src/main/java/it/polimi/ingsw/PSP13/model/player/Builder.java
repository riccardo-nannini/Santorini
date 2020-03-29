package it.polimi.ingsw.PSP13.model.player;

public class Builder {

    private Coords coords;

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Builder(Player player) {
        this.player = player;
    }
    public Builder() {
        coords = null;
        player = null;
    }
}
