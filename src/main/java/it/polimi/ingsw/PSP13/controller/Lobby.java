package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Lobby implements Runnable{

    private ServerSocket serverSocket;
    private BlockingQueue<ClientHandler> socketList = new LinkedBlockingDeque<>();
    private Map<String, Socket> usernameMap = new HashMap<>();
    private Map<Socket, ClientHandler> map = new HashMap<>();
    private Map<Socket, ClientListener> listenerList = new HashMap<>();
    private Map<Socket, ObjectOutputStream> fillByClient = new HashMap<>();
    private int playersNumber = 3;

    private boolean stop = false;
    private boolean wait = false;
    private boolean lobbySetupDone = false;

    /**
     * initializes the ServerSocket
     */
    private void setServerSocket()
    {
        try {
            serverSocket = new ServerSocket(Server.PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
    }

    /**
     * return the username from the usernameMap given a socket. given a value returns the key
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
     * takes a disconnection from the client in the setup moment of the game
     * if the socket interested is the first, must repeat setupIter() with another client
     * @param socket the disconnected client
     */
    public synchronized void takeSetupDisconnection(Socket socket)
    {
        if(lobbySetupDone)
            return;

        if(map.get(socket) == socketList.peek())
        {
            try {
                socketList.take();
                setupIter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listenerList.remove(socket);

        usernameMap.remove(getUsernameFromSocket(socket));
        map.remove(socket);

        stop = false;

    }

    /**
     * validates the nickname received from a client. check if it's already taken
     * @param socket the client who sent the nickname
     * @param nickname the username chosen
     */
    public synchronized void validateNickname(Socket socket, String nickname)
    {
        System.out.println("received nickname " + nickname + " from: " + socket.getInetAddress());
        if(!usernameMap.containsKey(nickname))
        {
            usernameMap.put(nickname,socket);
            listenerList.get(socket).setUsername(nickname);
            if(map.get(socket) == socketList.peek())
                notifyAll();
        }
        else
            map.get(socket).nicknameIter(true);
    }

    /**
     * validate the playersNumber input chosen by the first player
     * computes boolean value to get the listening cycle work correctly
     * @param playersNumber the number chosen by the first client
     */
    public synchronized void validatePlayerNumber(int playersNumber)
    {
        if(playersNumber == 2 || playersNumber == 3)
        {
            this.playersNumber = playersNumber;
            wait = false;
            lobbySetupDone = true;
            notifyAll();
        }
        else
            socketList.peek().playerNumberIter(true);
    }

    /**
     * asks the first client to choose playerNumber
     * if no client connected it waits
     */
    public synchronized void setupIter()
    {
        while(socketList.isEmpty())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socketList.peek().playerNumberIter(false);

    }

    /**
     * accepts a client, creates a ClientHandler object and a ClientListener object.
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
        map.put(socket,client);

        ClientListener listener = new ClientListener(socket, this);
        listenerList.put(socket,listener);
        Thread thread = new Thread(listener);
        thread.start();

        if(socketList.size() >= playersNumber)
        {
            if(!lobbySetupDone)
                wait = true;
            else
            {
                stop = true;
                wait = false;
            }

        }


    }

    /**
     * this method accept clients of pauses based on current situation.
     * if 3 client already connected, it waits
     * if playersNum isn't received, it waits
     * it stops when playersNum client are currently connected
     */
    private void listening()
    {
        setServerSocket();
        while(!stop)
        {
            try {
                socketAccept();

                while(wait)
                {
                    synchronized (this)
                    {
                        wait();
                    }
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * given all the data structures filled and a matchHandler instance this method starts the match
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    private void createMatch() throws NoSuchMethodException, InstantiationException, IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        int diff = socketList.size() - playersNumber;
        if(diff > 0)
        {
            for(int i=0;i<diff;i++)
            {
                ((ClientHandler)(socketList.toArray()[playersNumber+i])).lateClientMustDisconnect();
            }
        }


        MatchHandler matchHandler = null;
        try {
            matchHandler = createMatchHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ViewObserver viewObserver = new ViewObserver(matchHandler);
        ClientListener.setViewObserver(viewObserver);

        matchHandler.init();
        matchHandler.play();

        System.out.println("Setup routine ended successfully");

    }

    /**
     * accept client method
     */
    @Override
    public void run()
    {
        listening();
        try {
            createMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * given all the data structures filled, this method creates a matchHandler instance
     * @return
     * @throws IOException
     */
    private MatchHandler createMatchHandler() throws IOException {

        MatchHandler matchHandler = new MatchHandler();

        HashMap<String, Color> result = new HashMap<>();
        Color[] colors = Color.values();
        int i=0;
        for(String nickname : usernameMap.keySet())
        {
            Color color = colors[i];
            i++;
            result.put(nickname,color);
            Player player = new Player(color, nickname);
            matchHandler.addPlayer(player);
        }
        matchHandler.setNumPlayers(playersNumber);

        HashMap<String, ObjectOutputStream> outputMap = new HashMap<>();
        for(Map.Entry entry : usernameMap.entrySet())
        {
            outputMap.put((String)entry.getKey(),fillByClient.get(entry.getValue()));
        }

        VirtualView virtualView = new VirtualView(outputMap);
        virtualView.setColorsMap(result);
        matchHandler.setVirtualView(virtualView);

        return matchHandler;
    }


    public boolean isLobbySetupDone() {
        return lobbySetupDone;
    }


}
