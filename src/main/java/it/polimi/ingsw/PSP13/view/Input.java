package it.polimi.ingsw.PSP13.view;


public abstract class Input {

    protected ObservableToController controller;

    public Input(ObservableToController controller)
    {
        this.controller = controller;
    }

    /**
     * the user is asked to enter a nickname for the game
     */
    public void getNickname()
    {}

    /**
     * the challenger selects from the list of all the gods only the gods that will be played this game
     */
    public void getGodSelection()
    {}

    /**
     * the user is asked to write the name of the god he wants to play with
     */
    public void getGod()
    {}

    /**
     * the user is asked to insert the initial position of his builders
     */
    public void setupBuilder()
    {}

}
