package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.MessageID;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;
import it.polimi.ingsw.PSP13.network.client_dispatching.MsgMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener implements Runnable {

    private Socket socket;
    private String username;
    private final ObjectInputStream input;
    private static ViewObserver viewObserver;
    private PermaLobby lobby;
    private MsgDispatcher msgDispatcher;

    public ClientListener (Socket socket, PermaLobby lobby) throws IOException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.lobby = lobby;
        this.username = socket.getInetAddress().toString();
        this.msgDispatcher = new MsgDispatcher(lobby,viewObserver, socket);
    }

    public void setMsgDispatcher() {
        this.msgDispatcher = new MsgDispatcher(lobby,viewObserver, socket);
    }

    @Override
    public void run()
    {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("Connection dropped from " + username);

            lobby.takeSetupDisconnection(socket);
            if(lobby.isStart())
                viewObserver.updateDisconnection(username);
        }
    }

    private void handleClientConnection() throws IOException {

        try {
            while (true) {
                Object next = input.readObject();
                dispatcher(next);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Invalid stream from client " + username);
        }
    }

    private void dispatcher(Object message) {
        if (! (message instanceof MessageFromViewToController)) return;
        MessageFromViewToController messageVC = (MessageFromViewToController) message;
        msgDispatcher.dispatch(messageVC);
    }


    public static void setViewObserver(ViewObserver viewObserver) {
        ClientListener.viewObserver = viewObserver;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
