package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.model.player.Coords;

import java.io.Serializable;
import java.util.List;

public class MessageCV implements Serializable {

    private static final long serialVersionUID = 423L;

    private int id;
    private boolean error;
    private String string;
    private List<Coords> coordsList;
    private List<String> stringList;
    private boolean callNumber;
    private int godsNumber;

    public MessageCV(int id, boolean error, String string, List<Coords> coordsList, List<String> stringList, boolean callNumber, int godsNumber)
    {
        this.id = id;
        this.error = error;
        this.string = string;
        this.coordsList = coordsList;
        this.stringList = stringList;
        this.callNumber = callNumber;
        this.godsNumber = godsNumber;
    }

    public MessageCV() {}

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

    public void setId(int id) {
        this.id = id;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setCoordsList(List<Coords> coordsList) {
        this.coordsList = coordsList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setCallNumber(boolean callNumber) {
        this.callNumber = callNumber;
    }

    public void setGodsNumber(int godsNumber) {
        this.godsNumber = godsNumber;
    }
}
