package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.client_callback.MessageVC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener implements Runnable {

    private final ObjectInputStream input;
    private static ViewObserver viewObserver;

    public ClientListener (ObjectInputStream input) {
        this.input = input;
    }

    @Override
    public void run()
    {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("Connection dropped");
        }
    }

    private void handleClientConnection() throws IOException {

        try {
            while (true) {
                Object next = input.readObject();
                dispatcher(next);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
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
            default:
                break;
        }

    }

    public static void setViewObserver(ViewObserver viewObserver) {
        ClientListener.viewObserver = viewObserver;
    }

}
