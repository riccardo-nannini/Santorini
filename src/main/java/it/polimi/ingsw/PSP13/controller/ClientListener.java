package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

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
    private boolean alive = true;
    private String ipAddress;

    public ClientListener (Socket socket, PermaLobby lobby) throws IOException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.lobby = lobby;
        this.username = null;
        this.ipAddress = socket.getInetAddress().toString();
        this.msgDispatcher = new MsgDispatcher(lobby,viewObserver, socket);
    }

    public void setMsgDispatcher() {
        this.msgDispatcher = new MsgDispatcher(lobby,viewObserver, socket);
    }


    /**
     * This methods runs the loop that listen to the socket input
     * and catches all the exceptions
     */
    @Override
    public void run()
    {
        try {
            handleClientConnection();
        } catch (IOException e) {
            try {
                input.close();
            } catch (IOException ioException) {
                System.out.println("Unable to close the stream");
            }

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Closing ClientListener for disconnected client");

                return;
            }

            if(username != null)
                System.out.println("Connection dropped from " + username);
            else
                System.out.println("Connection dropped from" + ipAddress);

            if(lobby.isStart()) {
                lobby.listenerThreadsShutdown(username);
            } else {
                lobby.takeSetupDisconnection(socket);
            }

        }
    }

    /**
     * This method starts a loop that listens to the socket
     * @throws IOException when an error related to the socket is thrown
     */
    private void handleClientConnection() throws IOException {

        try {
            while (alive) {
                Object next = input.readObject();
                dispatcher(next);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Invalid stream from client " + username);
        }
    }

    /**
     * Decodes the messages received
     * @param message the message to decode
     */
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }



}
