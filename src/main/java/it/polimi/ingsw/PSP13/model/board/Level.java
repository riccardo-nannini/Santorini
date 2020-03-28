package it.polimi.ingsw.PSP13.model.board;

public enum Level {

    Floor(0), Base(1), Medium(2), Top(3), Dome(4);

    private int height;


    public int getHeight() {
        return height;
    }


    Level(int height)
    {
        this.height = height;
    }

    /**
     *
     * @param height
     * @return the level value related to the height as param
     * @throws IllegalArgumentException if height is not a valid height
     */
    public static Level findLevelByHeight(int height) throws IllegalArgumentException
    {
        if(height<0 || height>4)
            throw new IllegalArgumentException();

        for(Level lvl : values())
        {
            if(lvl.getHeight() == height)
                return lvl;
        }

        throw new IllegalArgumentException();
    }

}
