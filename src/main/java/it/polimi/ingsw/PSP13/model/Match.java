package it.polimi.ingsw.PSP13.model;

import it.polimi.ingsw.PSP13.controller.VirtualView;
import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.model.player.Player;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Match {

    private List<Player> players;
    private Map map;
    private ViewObservable observable;


    /**
     * starts a new match and initializes all the needed components
     */
    public Match() {
        map = new Map();
        players = new ArrayList<>();
    }

    public void start(VirtualView virtualView) throws IOException {
        observable = new ViewObservable(this, virtualView);
    }

    public Map getMap() {
        return this.map;
    }


    /**
     * @param player
     * adds a new player to this match player list
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }

    public void end()
    {

    }

    /**
     * @param coords
     * @return true if the cell related to the coordinates has a dome on it or
     * at least a player has got a worker which coordinates are equal to coords, false otherwise
     */
    public boolean isOccupied(Coords coords)
    {
        if(coords == null || !Map.isLegal(coords))
            return true;
        if(map.getCell(coords).getDome())
        {
            return true;
        }
        for(Player p : players)
        {
            if(coords.equals(p.getBuilders()[0].getCoords()) || coords.equals(p.getBuilders()[1].getCoords()))
                return true;

        }
        return false;
    }

    /**
     * @param coords
     * @return the call to map.getAdjacent
     */
    public List<Coords> getAdjacent(Coords coords)
    {
        return map.getAdjacent(coords);
    }

    /**
     *
     * @param coords
     * @param level
     * call to map.setCell
     */
    public void setCellLevel(Coords coords, Level level)
    {
        map.setCell(coords, level);
    }

    public Cell getCell(Coords coords) { return this.map.getCell(coords); }

    public int getHeight(Coords coords)
    {
        return map.getCell(coords).getLevel().getHeight();
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param coords
     * @return the builder which coordinates are param
     * @throws IllegalArgumentException if there is not builder is coords
     */
    public Builder getBuilderByCoords(Coords coords) throws IllegalArgumentException
    {
        for(Player p : players)
        {
            if(p.getBuilders()[0].getCoords().equals(coords))
                return p.getBuilders()[0];
            if(p.getBuilders()[1].getCoords().equals(coords))
                return p.getBuilders()[1];
        }

        throw new IllegalArgumentException();
    }

    /**
     *
     * @param builder
     * @return the player owning builder by param
     * @throws IllegalArgumentException if param is not a valid builder
     */
    public Player getPlayerByBuilder(Builder builder) throws IllegalArgumentException
    {
        for(Player p : players)
        {
            if(p.getBuilders()[0]==(builder) || p.getBuilders()[1]==(builder))
                return p;
        }

        throw new IllegalArgumentException();
    }

    /**
     * @param builder
     * @return the other builder of builder's player
     */
    public Builder getOtherBuilder(Builder builder) {
        if (getPlayerByBuilder(builder).getBuilders()[0] == builder) {
            return getPlayerByBuilder(builder).getBuilders()[1];
        } else {
            return getPlayerByBuilder(builder).getBuilders()[0];
        }
    }

    public void notifyMap() throws IOException {
        observable.notifyMap();
    };

    public void notifyBuilder(Builder builder1, Builder builder2) throws IOException {
        observable.notifyBuilder(builder1,builder2);
    }

    public void removeBuilder(Player player) throws IOException {
        observable.removeBuilder(player);
    }

    /**
     * @param username username of the player looked for
     * @return username's player , null if there are no corresponding player
     */
    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) return player;
        }
        return null;
    }


}
