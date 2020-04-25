package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.Serializable;
import java.util.List;

public class MessageCV implements Serializable {

    private static final long serialVersionUID = 423L;

    private final int id;

    private final boolean error;
    private final String string;
    private final List<Coords> coordsList;
    private final List<String> stringList;
    private final boolean callNumber;
    private final int godsNumber;

    public MessageCV(int id, boolean error, String string, List<Coords> coordsList, List<String> stringList, boolean callNumber, int godsNUmber)
    {
        this.id = id;
        this.string = string;
        this. error = error;
        this.coordsList = coordsList;
        this.stringList = stringList;
        this.callNumber = callNumber;
        this.godsNumber = godsNUmber;
    }

    public int getId() {
        return id;
    }

    public boolean isError() {
        return error;
    }

    public String getString() {
        return string;
    }

    public List<Coords> getCoordsList() {
        return coordsList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public boolean isCallNumber() {
        return callNumber;
    }

    public int getGodsNumber() {
        return godsNumber;
    }


}
