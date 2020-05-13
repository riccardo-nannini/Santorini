package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.MessageID;

import java.io.Serializable;
import java.util.List;

public class MessageFromControllerToView implements Serializable {

    private static final long serialVersionUID = 423L;

    private final MessageID messageID;
    private final boolean error;
    private final String string;
    private final List<Coords> coordsList;
    private final List<String> stringList;
    private final boolean callNumber;
    private final int godsNumber;

    public MessageFromControllerToView(MessageID id, boolean error){
        this.messageID = id;
        this.error = error;
        string = null;
        coordsList = null;
        stringList = null;
        callNumber = false;
        godsNumber = 0;
    }



    public MessageFromControllerToView(MessageID messageID, boolean error, String string, List<Coords> coordsList, List<String> stringList, boolean callNumber, int godsNumber)
    {
        this.messageID = messageID;
        this.error = error;
        this.string = string;
        this.coordsList = coordsList;
        this.stringList = stringList;
        this.callNumber = callNumber;
        this.godsNumber = godsNumber;
    }


    public MessageID getMessageID() {
        return messageID;
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
