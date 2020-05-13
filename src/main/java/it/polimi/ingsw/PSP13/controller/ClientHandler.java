package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientHandler {

    private ObjectOutputStream out;

    public ClientHandler(ObjectOutputStream out) throws IOException {
        this.out = out;
    }

    /**
     * asks the client to choose a username
     * @param error true if there was an error with previous request
     */
    public void nicknameIter(boolean error)
    {
        MessageFromControllerToView msg = new MessageFromControllerToView(MessageID.processNickname, error);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * asks the client to choose the number of players for this match
     * @param error true if there was an error with previous request
     */
    public void playerNumberIter(boolean error)
    {
        MessageFromControllerToView msg = new MessageFromControllerToView(MessageID.processPlayersNumber, error);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * notices the client that the first player chose to play with 2 players
     * and a client is left out
     */
    public void lateClientMustDisconnect()
    {
        MessageFromControllerToView msg = new MessageFromControllerToView(MessageID.lobbyFull, false);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * notices the client that the first player chose to play with 2 players
     * and a client is left out
     */
    public void playersLimitReachedCanWait()
    {
        MessageFromControllerToView msg = new MessageFromControllerToView(MessageID.lobbyFull, false);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
