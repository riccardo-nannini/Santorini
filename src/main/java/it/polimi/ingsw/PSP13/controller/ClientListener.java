package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.client_callback.MessageVC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener implements Runnable {

    private Socket socket;
    private String username;
    private final ObjectInputStream input;
    private static ViewObserver viewObserver;
    private PermaLobby lobby;

    public ClientListener (Socket socket, PermaLobby lobby) throws IOException {
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.lobby = lobby;
        this.username = socket.getInetAddress().toString();
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
        if (! (message instanceof MessageVC)) return;
        MessageVC messageVC = (MessageVC) message;
        int id = messageVC.getId();
        switch (id) {
            case 0:
                if (messageVC.getCoords() != null) viewObserver.updateMoveInput(messageVC.getCoords());
                break;
            case 1:
                if (messageVC.getCoords() != null) viewObserver.updateBuildInput(messageVC.getCoords());
                break;
            case 2:
                if (messageVC.getString() != null) lobby.validateNickname(socket,messageVC.getString());
                break;
            case 3:
                if (messageVC.getString() != null) viewObserver.updateGod(messageVC.getString());
                break;
            case 4:
                if (messageVC.getCoords() != null) viewObserver.updateSetupBuilder(messageVC.getCoords());
                break;
            case 5:
                if (messageVC.getString() != null) viewObserver.updateGodSelection(messageVC.getString());
                break;
            case 6:
                if (messageVC.getString() != null) viewObserver.updateEffect(messageVC.getString());
                break;
            case 7:
                if (messageVC.getCoords() != null) viewObserver.updateBuilderChoice(messageVC.getCoords());
                break;
            case 8:
                if (messageVC.getCoords() != null) viewObserver.updateRemoveInput(messageVC.getCoords());
                break;
            case 13:
                if (messageVC.getPlayerNum() != 0) lobby.validatePlayerNumber(messageVC.getPlayerNum());
                break;
            case 15:
                if (messageVC.getString() != null) viewObserver.updateStarter(messageVC.getString());
                break;
            case 16:
                if (messageVC.getString() != null) lobby.fillPlayAgainMap(socket, messageVC.getString());
                break;
            default:
                break;
        }

    }

    public static void setViewObserver(ViewObserver viewObserver) {
        ClientListener.viewObserver = viewObserver;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
