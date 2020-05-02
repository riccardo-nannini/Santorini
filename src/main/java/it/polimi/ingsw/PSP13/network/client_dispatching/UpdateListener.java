package it.polimi.ingsw.PSP13.network.client_dispatching;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.view.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class UpdateListener implements Runnable{

    private InputStream inputStream;
    private ObjectInputStream objInput;
    private MsgMap dispatcher;
    private Input input;

    public UpdateListener(Socket socket, Input input)
    {
        try {
            inputStream = socket.getInputStream();
            objInput = new ObjectInputStream(inputStream);

            this.input = input;
            dispatcher = new MsgMap(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Object obj = objInput.readObject();
                dispatch(obj);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void dispatch(Object obj)
    {
        if(obj instanceof MessageCV)
            dispatcher.dispatch((MessageCV)obj);
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

}
