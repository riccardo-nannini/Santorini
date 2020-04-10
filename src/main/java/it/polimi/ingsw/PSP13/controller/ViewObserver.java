package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.Match;
import it.polimi.ingsw.PSP13.model.player.Coords;

/**
 * this is the controller observer class which is subscribed to the view
 */
public class ViewObserver {

    private Match match;

    public ViewObserver(Match match)
    {
        this.match = match;
    }

    /**
     * check form string validation and set the value to the player instance
     * @param nickname the nickname chosen by the user
     */
    public void updateNickname(String nickname)
    {
        //dato x player setto il suo nickname
    }

    /**
     * sets the value of the god to the player instance and updates the list of gods left
     * @param god the god chosen by user
     */
    public void updateGod(String god)
    {
        //dato x player setto il suo turn
    }

    /**
     * sets the gods available for this match
     * @param gods
     */
    public void updateGodSelection(String[] gods)
    {
        //ho la selezione degli dèi disponibili per questa partita
    }

    /**
     * sets the initial coordinates of player builders
     * @param builder1
     * @param builder2
     */
    public void updateSetupBuilder(Coords builder1, Coords builder2)
    {
        //posiziono i builder prima di iniziare la partita
    }
}
