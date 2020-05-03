package it.polimi.ingsw.PSP13.network;

import it.polimi.ingsw.PSP13.network.client_callback.ControllerCallback;
import it.polimi.ingsw.PSP13.network.client_callback.HearthBeat;
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
        System.out.println("Type the ip address of the \u001B[1mSERVER\u001B[0m:");
        Scanner scanner = new Scanner(System.in);
        String server = scanner.nextLine();

        Input input;

        try {
            Socket socket = null;
            socket = new Socket(server, PORT);

            ControllerCallback callback = new ControllerCallback(socket);
            ObservableToController obs = new ObservableToController(callback);

            System.out.println("Choose the graphic mode (insert \u001B[1mGUI\u001B[0m or \u001B[1mCLI\u001B[0m):");

            if(scanner.nextLine().equals("CLI"))
            {
                input = new CliInput(obs);
            }
            else
            {
                input = new CliInput(obs);
                //start gui
            }

            UpdateListener updateListener = new UpdateListener(socket, input);
            new Thread(new HearthBeat(callback)).start();
            Thread thread = new Thread(updateListener, "listener");
            thread.start();

            input.nicknameInput(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
