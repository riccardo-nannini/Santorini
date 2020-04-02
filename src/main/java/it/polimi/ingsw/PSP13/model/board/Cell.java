package it.polimi.ingsw.PSP13.model.board;

public class Cell {

    private Level level;
    private boolean dome;

    public Level getLevel() {
        return level;
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

    public Cell()
    {
        level = Level.Floor;
        dome = false;
    }

}
