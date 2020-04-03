package it.polimi.ingsw.PSP13.model.board;

import it.polimi.ingsw.PSP13.model.player.Coords;

public class Cell {

    private Level level;
    private Coords coords;
    private boolean dome;

    public Level getLevel() {
        return level;
    }

    public Coords getCoords() {
        return coords;
    }

    public boolean getDome()
    {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Cell(int x, int y)
    {
        level = Level.Floor;
        coords = new Coords(x,y);
        dome = false;
    }

}
