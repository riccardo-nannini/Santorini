package it.polimi.ingsw.PSP13.model.board;

public class Cell {

    private Level level;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Cell()
    {
        level = Level.Floor;
    }

}
