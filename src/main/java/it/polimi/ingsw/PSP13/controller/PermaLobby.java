package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PermaLobby implements Runnable{

    private ServerSocket serverSocket;
    private static ViewObserver viewObserver;
    private BlockingQueue<ClientHandler> socketList = new LinkedBlockingDeque<>();
    private BlockingQueue<ClientHandler> leftout = new LinkedBlockingDeque<>();
    private Map<String, Socket> usernameMap = new HashMap<>();
    private Map<Socket, ClientHandler> handlermap = new HashMap<>();
    private Map<Socket, ClientListener> listenerList = new HashMap<>();
    private Map<Socket, ObjectOutputStream> fillByClient = new HashMap<>();
    private Map<Socket,Boolean> rematchMap = new HashMap<>();
    private List<Thread> listenerThreads = new ArrayList<>();
    private int playersNumber = 3;
    private boolean lobbySetupDone = false;
    private boolean start = false;


    /**
     * Initializes the ServerSocket
     */
    private void setServerSocket()
    {
        try {
            serverSocket = new ServerSocket(Server.PORT);
        } catch (IOException e) {
            System.out.println("Cannot open server socket");
            System.exit(1);
            return;
        }
    }

    /**
     * This methods looks up for the socket related to the clienthandler
     * @param clientHandler the client i need to lookup for the the socket
     * @return the socket related to the clienthandler
     */
    private Socket getSocketFromClientHandler(ClientHandler clientHandler)
    {
        for(Map.Entry entry : handlermap.entrySet())
        {
            if(entry.getValue() == clientHandler)
                return (Socket)entry.getKey();
        }

        return null;
    }

    /**
     * Lobby thread that listens to incoming connections
     */
    @Override
    public void run()
    {
        setServerSocket();
        listening();
    }

    /**
     * Validates the nickname received from a client. check if it's already taken
     * @param socket the client who sent the nickname
     * @param nickname the username chosen
     */
    public synchronized void validateNickname(Socket socket, String nickname)
    {
        System.out.println("Received nickname " + nickname + " from: " + socket.getInetAddress());
        if(!usernameMap.containsKey(nickname) && nickname.length() <= 16)
        {
            usernameMap.put(nickname,socket);
            listenerList.get(socket).setUsername(nickname);
            handlermap.get(socket).confirmNickname();
            if(lobbySetupDone)
                notifyAll();

        }
        else
            handlermap.get(socket).nicknameIter(true);
    }

    /**
     * Checks if the conditions to start a match are met
     * and eventually starts a match
     */
    private void checkReady()
    {
        if(lobbySetupDone && socketList.size()>=playersNumber) {
            Runnable runnable = () -> {

                    createMatch();

            };
            Thread t = new Thread(runnable);
            t.start();

        }
    }


    /**
     * Validate the playersNumber input chosen by the first player
     * computes boolean value to get the listening cycle work correctly
     * @param playersNumber the number chosen by the first client
     */
    public synchronized void validatePlayerNumber(int playersNumber)
    {
        if(playersNumber == 2 || playersNumber == 3)
        {
            this.playersNumber = playersNumber;
            lobbySetupDone = true;
            checkReady();

        }
        else
            socketList.peek().playerNumberIter(true);
    }

    /**
     * Return the username from the usernameMap given a socket. given a value returns the key
     * @param socket the value
     * @return the key
     */
    private String getUsernameFromSocket(Socket socket)
    {
        for(Map.Entry entry : usernameMap.entrySet())
        {
            if(entry.getValue() == socket)
                return (String)entry.getKey();
        }

        return null;
    }

    /**
     * Closes the listener thread related to player username
     * @param username the player tat needs to be excluded
     */
    public synchronized void listenerThreadsShutdown(String username) {

        if(username == null) {
            ClientHandler[] clients = socketList.toArray(new ClientHandler[0]);
            ClientHandler clientToRemove = clients[clients.length-1];
            socketList.remove(clientToRemove);
            return;
        }

        Socket socket = usernameMap.get(username);
        ClientHandler handler = handlermap.get(socket);
        ClientHandler[] clients = socketList.toArray(new ClientHandler[0]);
        for(int i=playersNumber;i<clients.length;i++) {
            if(clients[i] == handler) {
                socketList.remove(handlermap.get(socket));
                usernameMap.remove(username);
                handlermap.remove(socket);
                fillByClient.remove(socket);
                return;
            }
        }

        for (Thread thread : listenerThreads) {
            if (thread.isAlive()) thread.interrupt();
        }
        viewObserver.updateDisconnection(username);
    }


    /**
     * Takes a disconnection from the client in the setup moment of the game
     * if the socket interested is the first, it must repeat setupIter() with another client
     * @param socket the disconnected client
     */
    public synchronized void takeSetupDisconnection(Socket socket)
    {
        if(start) return;

        boolean isFirst = handlermap.get(socket) == socketList.peek();
        socketList.remove(handlermap.get(socket));
        listenerList.remove(socket);
        usernameMap.remove(getUsernameFromSocket(socket));
        handlermap.remove(socket);
        fillByClient.remove(socket);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isFirst && !lobbySetupDone)
        {
            setupIter();
        }

        notifyAll();
    }

    /**
     * Accepts a client, creates a ClientHandler object and a ClientListener object.
     * then it fills some data structures and computers boolean for the cycle.
     * @throws IOException
     */
    private void socketAccept() throws IOException {

        Socket socket = serverSocket.accept();
        System.out.println("connected to: " + socket.getInetAddress());
        socket.setSoTimeout(20000);

        ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
        fillByClient.put(socket,obj);
        ClientHandler client = new ClientHandler(obj);
        socketList.add(client);
        handlermap.put(socket,client);

        ClientListener listener = new ClientListener(socket, this);
        listenerList.put(socket,listener);
        Thread thread = new Thread(listener);
        listenerThreads.add(thread);
        thread.start();

        if(socketList.size() > playersNumber)
        {
            client.playersLimitReached();
            return;
        }

        synchronized (this)
        {
            notifyAll();
        }

        checkReady();

    }

    /**
     * Creates a loop the listen to incoming connections
     */
    public void listening()
    {
        while(true)
        {
            try {
                socketAccept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asks the first client to choose playerNumber
     * if no client connected it waits
     */
    public synchronized void setupIter()
    {
        if(socketList.isEmpty())
        {
            try {
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        socketList.peek().playerNumberIter(false);
    }

    /**
     * Given all the data structures filled and a matchHandler instance this method starts the match
     */
    private void createMatch() {
        int diff = socketList.size() - playersNumber;
        if(diff > 0)
        {
            for(int i=0;i<diff;i++)
            {
                ((ClientHandler)(socketList.toArray()[playersNumber+i])).playersLimitReached();
            }
        }

        MatchHandler matchHandler;

        matchHandler = createMatchHandler();


        if(matchHandler == null)
            return;

        ViewObserver observer = new ViewObserver(matchHandler);
        viewObserver = observer;
        ClientListener.setViewObserver(observer);

        for(ClientListener listener : listenerList.values())
        {
            listener.setMsgDispatcher();
        }

        System.out.println("Setup routine ended successfully");
        start = true;

        try {
            matchHandler.init();
            matchHandler.play();
        } catch (Exception e) {
            System.out.println("Restarting the game");
            init();
            setupIter();
            return;
        }
        rematchMap.clear();
        start = false;
        playAgain();
    }

    /**
     * Resets the data structures
     */
    private synchronized void init() {
        socketList.clear();
        usernameMap.clear();
        handlermap.clear();
        listenerList.clear();
        fillByClient.clear();
        rematchMap.clear();
        lobbySetupDone = false;
        start = false;
        listenerThreads.clear();
    }

    /**
     * Handles the rematch iter
     */
    private synchronized void playAgain() {
        System.out.println("Rematch setup");

        while(rematchMap.size() < playersNumber)
        {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        for(Socket socket : rematchMap.keySet())
        {
            if(rematchMap.get(socket).equals(false))
            {
                socketList.remove(handlermap.get(socket));
                if (listenerList.containsKey(socket) && listenerList.get(socket)!=null) {
                    listenerList.get(socket).setAlive(false);
                    listenerList.remove(socket);
                }
                usernameMap.remove(getUsernameFromSocket(socket));
                handlermap.remove(socket);
                lobbySetupDone = false;
            }
        }

        setupIter();
    }

    /**
     * Given all the data structures filled, this method creates a matchHandler instance
     * @return an instance of matchHandler
     */
    private synchronized MatchHandler createMatchHandler() {

        MatchHandler matchHandler = new MatchHandler();

        while(usernameMap.size()<playersNumber) {
            try {
                wait();
                if(socketList.size()<playersNumber)
                    return null;
            } catch (InterruptedException ignored) {

            }
        }

        List<String> usernames = new ArrayList<>(usernameMap.keySet());
        int diff = socketList.size() - playersNumber;
        for(int i=0;i<diff;i++)
        {
            Socket socket = getSocketFromClientHandler(((ClientHandler)(socketList.toArray()[playersNumber+i])));
            usernames.remove(getUsernameFromSocket(socket));
        }

        HashMap<String, Color> result = new HashMap<>();
        Color[] colors = Color.values();
        int i=0;
        for(String nickname : usernames)
        {
            Color color = colors[i];
            i++;
            result.put(nickname,color);
            Player player = new Player(color, nickname);
            matchHandler.addPlayer(player);
            System.out.println(player);
        }
        matchHandler.setNumPlayers(playersNumber);

        HashMap<String, ObjectOutputStream> outputMap = new HashMap<>();

        for(Map.Entry entry : usernameMap.entrySet())
        {
            if(usernames.contains(entry.getKey()))
                outputMap.put((String)entry.getKey(),fillByClient.get(entry.getValue()));
        }

        VirtualView virtualView = new VirtualView(outputMap);
        virtualView.setColorsMap(result);
        matchHandler.setVirtualView(virtualView);

        return matchHandler;
    }

    /**
     * Fills a map with the client and its choice regarding rematch
     * @param socket the client who made a choice
     * @param choice client answer regarding if he wants to play again
     */
    public synchronized void fillPlayAgainMap(Socket socket, String choice)
    {
        if(choice.equals("yes") || choice.equals("y"))
            rematchMap.put(socket,true);
        else
            rematchMap.put(socket,false);

        notifyAll();

    }

    public boolean isStart()
    {
        return start;
    }

}
