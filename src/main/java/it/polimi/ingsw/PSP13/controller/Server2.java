package it.polimi.ingsw.PSP13.controller;

public class Server2{

    public static final int PORT=7777;

    public static void main(String[] args) {

        System.out.println("Server online.");

        PermaLobby lobby = new PermaLobby();
        Thread threadListening = new Thread(lobby);
        threadListening.start();

        lobby.setupIter();

    }

}
