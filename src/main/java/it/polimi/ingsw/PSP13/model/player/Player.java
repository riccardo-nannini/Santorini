package it.polimi.ingsw.PSP13.model.player;

import it.polimi.ingsw.PSP13.model.Turn;

import java.io.IOException;
import java.util.List;

public class Player {

    private Color color;
    private Builder[] builders;
    private Turn god;
    private String username;

    /**
     * @return the description of player's god
     */
    public String getEffect() { return god.getEffect(); }

    /**
     * Calls the builderSelection method of player's god
     * @param builder selected builder
     * @return true if the builder belongs to the player, false otherwise
     */
    public boolean builderSelection(Builder builder) { return god.builderSelection(username, builder); }

    /**
     * Calls the checkMove method of player's god
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     * @return true if builder can move into coords' cell, else return false
     */
    public boolean checkMove(Builder builder, Coords coords) { return god.checkMove(builder, coords); }

    /**
     * Calls the start method of player's god
     * @throws IOException
     */
    public void start() throws IOException {god.start(username);}

    /**
     * Calls the move method of player's god
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

    /**
     * Calls the checkBuild method of player's god
     * @param builder builder that is currently building
     * @param coords coordinates of the cell where the builder wants to build
     * @return true if it is possible to build in the specified position, false otherwise.
     */
    public boolean checkBuild(Builder builder, Coords coords) { return god.checkBuild(builder, coords); }

    /**
     * Calls the build method of player's god
     * @param builder builder that is currently building
     * @param coords coordinates of the cell where the builder wants to build
     */
    public void build(Builder builder, Coords coords) throws IOException {
        god.build(builder, coords);
    }

    /**
     * Calls the setup method of player's god
     * @param builder1 player's first builder
     * @param builder2 player's second builder
     * @param coords1 position of the first builder
     * @param coords2 position of the second builder
     */
    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IOException {
        god.setup(builder1, builder2, coords1, coords2);
    }

    /**
     * Calls the checkWin method of player's god
     * @param builder builder that was involved in the current turn
     * @param precedentPosition position occupied by the builder before moving
     * @param currentPosition position currently occupied by the builder
     * @return true if the player did win in this turn, false otherwise.
     */
    public boolean win(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        return god.checkWin(builder, precedentPosition, currentPosition);
    }

    /**
     * Calls the end method of player's god
     */
    public void end() throws IOException { god.end(); }

    /**
     * Calls the getPossibleMoves method of player's god
     * @param builder current builder
     * @return a list of adjacent cells where a builder can move in
     */
    public List<Coords> getPossibleMoves(Builder builder) {
        return god.getPossibleMoves(builder);
    }

    /**
     * Calls the getBuildableCells method of player's god
     * @param builder current builder
     * @return a list of adjacent cells where the builder can build on
     */
    public List<Coords> getBuildableCells(Builder builder) {
        return god.getBuildableCells(builder);
    }

    /**
     * @param color
     * @param username
     * initializes a new instance with the basic attributes
     */
    public Player(Color color, String username)
    {
        this.color = color;
        this.username = username;

        builders = new Builder[2];
        builders[0] = new Builder();
        builders[1] = new Builder();
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Builder[] getBuilders() {
        return builders;
    }

    public void setBuilders(Builder[] builders) {
        this.builders = builders.clone();
    }

    public Turn getGod() {
        return god;
    }

    public void setGod(Turn god) {
        this.god = god;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
