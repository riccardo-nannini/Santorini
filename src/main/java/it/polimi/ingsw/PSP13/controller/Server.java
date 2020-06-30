package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.network.Client;

import java.util.Scanner;

public class Server {

    public static int PORT=7777;

    /**
     * server main thread
     * @param args contains indications for the CLI usage
     */
    public static void main(String[] args) {

        System.out.println("Insert the port number: ");
        Scanner scanner = new Scanner(System.in);
        String port = scanner.nextLine();
        Server.PORT = Integer.parseInt(port);

        System.out.println("Server online.");

        PermaLobby lobby = new PermaLobby();
        Thread threadListening = new Thread(lobby);
        threadListening.start();

        lobby.setupIter();

    }

}
