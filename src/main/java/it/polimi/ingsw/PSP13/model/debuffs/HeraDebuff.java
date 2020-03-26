package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;

public class HeraDebuff extends Decorator {

    public HeraDebuff(Turn god)
    {
        super(god);
    }

    @Override
    public void checkWin() {
        super.checkWin();
    }
}