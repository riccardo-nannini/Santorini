package it.polimi.ingsw.PSP13.network;

import it.polimi.ingsw.PSP13.network.client_callback.ControllerCallback;
import it.polimi.ingsw.PSP13.network.client_dispatching.UpdateListener;
import it.polimi.ingsw.PSP13.view.CLI.CliInput;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final int PORT=7777;

    public static void main(String[] args) {
        System.out.println("type the ip address of the server:");
        Scanner scanner = new Scanner(System.in);
        String server = scanner.nextLine();

        Input input;

        try {
            Socket socket = null;
            socket = new Socket(server, PORT);

            ControllerCallback callback = new ControllerCallback(socket);
            ObservableToController obs = new ObservableToController(callback);

            System.out.println("Choose the graphic mode (insert GUI or CLI:");
            if(scanner.nextLine().equals("CLI"))
            {
                input = new CliInput(obs);
                //start gui
            }
            else
            {
                input = new CliInput(obs);
            }

            UpdateListener updateListener = new UpdateListener(socket, input);
            Thread thread = new Thread(updateListener, "listener");
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
