package it.polimi.ingsw.PSP13.network.client_callback;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.MessageID;

import java.io.Serializable;

public class MessageFromViewToController implements Serializable {

    private static final long serialVersionUID = 87L;

    private final MessageID messageID;
    private final String string;
    private final Coords coords;
    private final int playerNum;


    public MessageFromViewToController(MessageID messageID, String string, Coords coords, int playerNum)
    {
        this.messageID = messageID;
        this.string = string;
        this.coords = coords;
        this.playerNum = playerNum;
    }

    public MessageID getMessageID() {
        return messageID;
    }

    public String getString() {
        return string;
    }

    public Coords getCoords() {
        return coords;
    }

    public int getPlayerNum() {
        return playerNum;
    }


}
