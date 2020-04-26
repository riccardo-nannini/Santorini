package it.polimi.ingsw.PSP13.controller;




import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Player;
import it.polimi.ingsw.PSP13.network.client_callback.MessageVC;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Server {

    public final static int SOCKET_PORT = 7777;


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

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

        ViewObserver v = new ViewObserver(matchHandler);
        ClientListener.setViewObserver(v);
        matchHandler.setVirtualView(virtualView);
        matchHandler.init();
        matchHandler.play();
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
        List<Color> colors = Color.getColors();
        List<String> usernameList = new ArrayList<String>();
        HashMap<String, ObjectOutputStream> socketMap = new HashMap<String, ObjectOutputStream>();

        //TODO for sbagliato
        for (int i=0; i<3; i++) {
            try {
                Socket client = socket.accept();
                System.out.println("Accettato " + client.getInetAddress());
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                boolean valid, error;
                error = false;
                do {
                    valid = true;

                    MessageCV messageCV = new MessageCV();
                    messageCV.setError(error);
                    messageCV.setId(2);
                    System.out.println("Chiedo il nickname a " + client.getInetAddress());
                    output.writeObject(messageCV);

                    Object usernameObject = input.readObject();
                    MessageVC messageVC = (MessageVC)usernameObject;
                    username = messageVC.getString();
                    System.out.println("Ricevuto: " + username);

                    for (String otherUsername : usernameList) {
                        if (otherUsername.equals(username)) {
                            error = true;
                            valid = false;
                            break;
                        }
                    }
                } while(!valid);

                usernameList.add(username);

                Player player = new Player(colors.get(i), username);
                matchHandler.addPlayer(player);

                ClientListener clientListener = new ClientListener(input);
                Thread thread = new Thread(clientListener);
                thread.start();

                socketMap.put(username,output);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("connection dropped");
            }
        }

        return new VirtualView(socketMap);

    }


}

