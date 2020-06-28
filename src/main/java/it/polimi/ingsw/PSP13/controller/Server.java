package it.polimi.ingsw.PSP13.controller;

public class Server {

    public static final int PORT=7777;

    /**
     * server main thread
     * @param
     */
    public static void main(String[] args) {

        System.out.println("Server online.");

        PermaLobby lobby = new PermaLobby();
        Thread threadListening = new Thread(lobby);
        threadListening.start();

        lobby.setupIter();

    }

}
