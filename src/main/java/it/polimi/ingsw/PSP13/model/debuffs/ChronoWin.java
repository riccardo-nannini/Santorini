package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;

public class ChronoWin extends Decorator {

    public ChronoWin(Turn god)
    {
        super(god);
    }

    @Override
    public void checkWin() {
        super.checkWin();
    }
}
