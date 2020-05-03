package it.polimi.ingsw.PSP13.controller;

public class Server {

    public static final int PORT=7777;

    public static void main(String[] args) {

        System.out.println("Server online.");

        Lobby lobby = new Lobby();
        Thread thread = new Thread(lobby);
        thread.start();

        lobby.setupIter();
    }
}