package it.polimi.ingsw.PSP13.model.board;

public enum Level {

    Floor(0), Base(1), Medium(2), Top(3), Dome(4);

    private int level;


    public int getLevel() {
        return level;
    }


    Level(int level)
    {
        this.level = level;
    }

}
