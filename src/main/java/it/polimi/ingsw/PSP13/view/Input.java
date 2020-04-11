package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.List;

public abstract class Input {

    protected ObservableToController controller;

    public Input(ObservableToController controller)
    {
        this.controller = controller;
    }

    /**
     * asks the player where given builder should move
     */
    public void moveInput(){}

    /**
     * asks the player where given builder should move
     */
    public void buildInput(){}

    /**
     * asks the player to insert a nickname for this match
     * @param error true if the previous input generated an error
     * @return the player's nickname
     */
    public void nicknameInput(boolean error){}

    /**
     * asks the player which god he wants to play with
     * @param player the player who has to choose
     * @param chosenGods the gods available to be chosen
     */
    public void godInput(String player, List<String> chosenGods){}

    /**
     * asks the player the starting position of his builder
     * @param player the player who has to choose
     */
    public void builderSetUpInput(String player){}

    /**
     * asks the challenger to choose a set of gods for this match
     * @param challenger the player who has to choose
     * @param godsList contains the name of all the gods available to choose from
     * @param godsNumber the number of gods the challenger has to choose
     */
    public void godSelectionInput(String challenger, List<String> godsList, int godsNumber){}

}
