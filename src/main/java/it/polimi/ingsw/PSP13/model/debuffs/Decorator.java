package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;

public class Decorator extends Turn {

    protected Turn god;

    public Turn removeGod()
    {
        return god;
    }

    public Decorator(Turn god)
    {
        this.god = god;
    }
}
