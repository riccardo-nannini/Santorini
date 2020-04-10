package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public interface Input {

    public void moveInput();

    public void buildInput();

    /**
     *
     * @param error true if the previous input generated an error
     * @return the player's nickname
     */
    public void nicknameInput(boolean error);

    public void godInput(String player, List<String> chosenGods);

    public void builderSetUpInput(String player);

    public void godSelectionInput(String challenger, List<String> godsList);

}
