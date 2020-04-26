package it.polimi.ingsw.PSP13.model.player;



import java.io.IOException;
import java.util.List;


public class Player {

    private Color color;
    private Builder[] builders;
    private Turn god;
    private String username;

    public boolean checkMove(Builder builder, Coords coords) { return god.checkMove(builder, coords); }

    public void start() throws IOException {god.start(username);}

    public void move(Builder builder, Coords coords) throws IOException {
        god.move(builder, coords);
    }

    public boolean checkBuild(Builder builder, Coords coords) { return god.checkBuild(builder, coords); }

    public void build(Builder builder, Coords coords) throws IOException {
        god.build(builder, coords);
    }

    public void setup(Builder builder1, Builder builder2, Coords coords1, Coords coords2) throws IOException {
        god.setup(builder1, builder2, coords1, coords2);
    }

    public boolean win(Builder builder, Coords precedentPosition, Coords currentPosition)
    {
        return god.checkWin(builder, precedentPosition, currentPosition);
    }

    public void end() throws IOException { god.end(); }

    public List<Coords> getCellMoves(Builder builder) {
        return god.getCellMoves(builder);
    }

    public List<Coords> getCellBuilds(Builder builder) {
        return god.getCellBuilds(builder);
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
import it.polimi.ingsw.PSP13.model.Turn;


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
