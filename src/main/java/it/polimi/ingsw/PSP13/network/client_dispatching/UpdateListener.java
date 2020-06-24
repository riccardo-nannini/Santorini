package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.network.Client;
import it.polimi.ingsw.PSP13.network.client_callback.ControllerCallback;
import it.polimi.ingsw.PSP13.network.client_callback.HearthBeat;
import it.polimi.ingsw.PSP13.view.Input;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class UpdateListener implements Runnable{

    private InputStream inputStream;
    private ObjectInputStream objInput;
    private MsgMap dispatcher;
    private Input input;
    private ControllerCallback callback;
    private boolean exit = false;

    public UpdateListener(Socket socket, Input input, ControllerCallback callback)
    {
        try {
            this.callback = callback;
            inputStream = socket.getInputStream();
            objInput = new ObjectInputStream(inputStream);

            this.input = input;
            dispatcher = new MsgMap(input, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        Thread hearthBeat = new Thread(new HearthBeat(callback));
        hearthBeat.start();

        while(!exit)
        {
            try {
                Object obj = objInput.readObject();
                dispatch(obj);

            } catch (IOException | ClassNotFoundException e) {
                exit = true;
            }
        }
        try {
            objInput.close();
        } catch (IOException e) {
            System.out.println("Unable to close the stream");
        }
        hearthBeat.interrupt();
        Client.main(new String[0]);
    }


    private void dispatch(Object obj)
    {
        if(obj instanceof MessageFromControllerToView)
            dispatcher.dispatch((MessageFromControllerToView)obj);
        else
        {
            if(obj instanceof BuilderVM)
                input.updateBuilders((BuilderVM)obj);
            if(obj instanceof MapVM)
                input.updateMap((MapVM)obj);
            if(obj instanceof MessageClientsInfoCV)
                input.setupClientsInfo((MessageClientsInfoCV)obj);
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

}
