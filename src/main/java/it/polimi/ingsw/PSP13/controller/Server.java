package it.polimi.ingsw.PSP13.controller;

import java.util.Scanner;

public class Server {

    public static int PORT=7777;

    /**
     * Server main thread
     * @param args
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
