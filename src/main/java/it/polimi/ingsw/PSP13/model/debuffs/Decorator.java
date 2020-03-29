package it.polimi.ingsw.PSP13.model.debuffs;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Player;

public class Decorator extends Turn {

    protected Turn god;

    /**
     * Removes the decorator from the player's god attribute.
     * @param player
     */
    public void removeDecorator(Player player)
    {
        player.setGod(god);
    }

    public Decorator(Turn god)
    {
        this.god = god;
    }
}
