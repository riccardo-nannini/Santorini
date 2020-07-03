package it.polimi.ingsw.PSP13.network.client_callback;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControllerCallback {

    private ObjectOutputStream out;

    public ControllerCallback(Socket socket)
    {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an object to the server
     * @param object the object to be sent
     */
    public void send(Object object)
    {
        try {
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
