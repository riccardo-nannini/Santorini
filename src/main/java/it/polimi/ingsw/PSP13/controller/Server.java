package it.polimi.ingsw.PSP13.controller;




import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Server {

    public final static int SOCKET_PORT = 7777;


    public static void main(String[] args) throws IOException {

        Server server = new Server();

        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        MatchHandler matchHandler = new MatchHandler();

        VirtualView virtualView = server.acceptClients(socket,matchHandler);

        //START CONTROLLER

    }


    /**
     * Accepts clients adding them into the server asks them an username
     * @param socket Server socket
     * @param matchHandler instance of MatchHandler used to add a player into the controller
     * @throws IOException if an I/O error occurs while writing stream header
     * @return the VirtualView containing client's sockets and theirs username
     */
    //TODO FARE THREAD SEPARATO PER LEGGERE NICKNAME DAI GIOCATORI
    public VirtualView acceptClients(ServerSocket socket, MatchHandler matchHandler) throws IOException {
        ObjectOutputStream output;
        ObjectInputStream input;
        String username;
        List<String> usernameList = new ArrayList<String>();
        HashMap<String, Socket> socketMap = new HashMap<String, Socket>();

        for (int i=0; i<3; i++) {
            try {
                Socket client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                boolean valid, error;
                error = false;
                do {
                    valid = true;

                    MessageCV message = new MessageCV();
                    message.setError(error);
                    message.setId(2);
                    output.writeObject(message);

                    Object usernameObject = input.readObject();
                    username = (String)usernameObject;

                    for (String otherUsername : usernameList) {
                        if (otherUsername.equals(username)) {
                            error = true;
                            valid = false;
                            break;
                        }
                    }
                } while(!valid);

                usernameList.add(username);

                matchHandler.addPlayer(username);

                ClientListener clientListener = new ClientListener(client);
                Thread thread = new Thread(clientListener);
                thread.start();

                socketMap.put(username,client);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("connection dropped");
            }
        }

        return new VirtualView(socketMap);

    }


}

