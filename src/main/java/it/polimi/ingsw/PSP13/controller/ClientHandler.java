package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        MessageCV msg = new MessageCV(2);
        msg.setError(error);
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
        MessageCV msg = new MessageCV(13);
        msg.setError(error);
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
        MessageCV msg = new MessageCV(14);
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
        MessageCV msg = new MessageCV(16);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
