package it.polimi.ingsw.PSP13.model.player;

import it.polimi.ingsw.PSP13.model.Turn;

public class Player {

    private Color color;
    private Builder[] builders;
    private Turn god;
    private int age;
    private String username;

    public boolean checkMove(Builder builder, Coords coords) { return god.checkMove(builder, coords); }

    public void move(Builder builder, Coords coords)
    {
        god.move(builder, coords);
    }

    public boolean checkBuild(Builder builder, Coords coords) { return god.checkBuild(builder, coords); }

    public void build(Builder builder, Coords coords)
    {
        god.build(builder, coords);
    }

    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2)
    {
        god.setup(builder1, builder2, coords1, coords2);
    }

    public boolean win(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        return god.checkWin(builder, precedentPosition, currentPosition);
    }

    public void end() { god.end(); }

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
