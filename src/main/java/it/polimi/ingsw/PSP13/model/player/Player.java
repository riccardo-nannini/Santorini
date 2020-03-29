package it.polimi.ingsw.PSP13.model.player;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.exception.IllegalBuildException;
import it.polimi.ingsw.PSP13.model.exception.IllegalMoveException;

public class Player {

    private Color color;
    private Builder[] builders;
    private Turn god;
    private int age;
    private String username;


    public void move(Builder builder, Coords coords) throws IllegalMoveException
    {
        god.move(builder, coords);
    }

    public void build(Builder builder, Coords coords) throws IllegalBuildException
    {
        god.build(builder, coords);
    }

    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2)
    {
        god.setup(builder1, builder2, coords1, coords2);
    }

    public void win(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        god.checkWin(builder, precedentPosition, currentPosition);
    }

    /**
     * @param color
     * @param age
     * @param username
     * initializes a new instance with the basic attributes
     */
    public Player(Color color, int age, String username)
    {
        this.color = color;
        this.age = age;
        this.username = username;

        builders = new Builder[2];
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
        this.builders = builders;
    }

    public Turn getGod() {
        return god;
    }

    public void setGod(Turn god) {
        this.god = god;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
