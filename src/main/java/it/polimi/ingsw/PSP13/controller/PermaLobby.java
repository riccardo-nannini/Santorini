package it.polimi.ingsw.PSP13.controller;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PermaLobby implements Runnable{

    private ServerSocket serverSocket;
    private BlockingQueue<ClientHandler> socketList = new LinkedBlockingDeque<>();
    private Map<String, Socket> usernameMap = new HashMap<>();
    private Map<Socket, ClientHandler> map = new HashMap<>();
    private Map<Socket, ClientListener> listenerList = new HashMap<>();
    private Map<Socket, ObjectOutputStream> fillByClient = new HashMap<>();
    private Map<Socket,Boolean> rematchMap = new HashMap<>();
    private int playersNumber = 3;
    private boolean lobbySetupDone = false;
    private boolean start = false;


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

    private Socket getSocketFromClientHandler(ClientHandler clientHandler)
    {
        for(Map.Entry entry : map.entrySet())
        {
            if(entry.getValue() == clientHandler)
                return (Socket)entry.getKey();
        }

        return null;
    }

    @Override
    public void run()
    {
        setServerSocket();
        listening();
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
            if(lobbySetupDone)
                notifyAll();

        }
        else
            map.get(socket).nicknameIter(true);
    }

    private void checkReady()
    {
        if(lobbySetupDone && socketList.size()>=playersNumber) {
            Runnable runnable = () -> {
                try {
                    createMatch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(runnable);
            t.start();

        }
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
            lobbySetupDone = true;
            checkReady();

        }
        else
            socketList.peek().playerNumberIter(true);
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


    //TODO killare il thread clientlistener del relativo client disconnesso invece che lasciarlo girare
    /**
     * takes a disconnection from the client in the setup moment of the game
     * if the socket interested is the first, it must repeat setupIter() with another client
     * @param socket the disconnected client
     */
    public synchronized void takeSetupDisconnection(Socket socket)
    {
        if(start)
            return;

        boolean isFirst = map.get(socket) == socketList.peek();
        socketList.remove(map.get(socket));
        listenerList.remove(socket);
        usernameMap.remove(getUsernameFromSocket(socket));
        map.remove(socket);
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
     * accepts a client, creates a ClientHandler object and a ClientListener object.
     * then it fills some data structures and computers boolean for the cycle.
     * @throws IOException
     */
    private void  socketAccept() throws IOException {

        Socket socket = serverSocket.accept();
        System.out.println("connected to: " + socket.getInetAddress());
        //socket.setSoTimeout(20000);

        ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
        fillByClient.put(socket,obj);
        ClientHandler client = new ClientHandler(obj);
        socketList.add(client);
        map.put(socket,client);

        ClientListener listener = new ClientListener(socket, this);
        listenerList.put(socket,listener);
        Thread thread = new Thread(listener);
        thread.start();

        if(socketList.size() > playersNumber)
        {
            client.playersLimitReachedCanWait();
            return;
        }

        synchronized (this)
        {
            notifyAll();
        }

        checkReady();

    }

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
     * asks the first client to choose playerNumber
     * if no client connected it waits
     */
    public synchronized void setupIter()
    {
        if(socketList.isEmpty())
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
     * given all the data structures filled and a matchHandler instance this method starts the match
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    private void createMatch() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        int diff = socketList.size() - playersNumber;
        if(diff > 0)
        {
            for(int i=0;i<diff;i++)
            {
                ((ClientHandler)(socketList.toArray()[playersNumber+i])).playersLimitReachedCanWait();
            }
        }

        MatchHandler matchHandler = null;
        try {
            matchHandler = createMatchHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(matchHandler == null)
            return;

        ViewObserver viewObserver = new ViewObserver(matchHandler);
        ClientListener.setViewObserver(viewObserver);

        for(ClientListener listener : listenerList.values())
        {
            listener.setMsgDispatcher();
        }

        System.out.println("Setup routine ended successfully");
        start = true;

        try {
            matchHandler.init();
            matchHandler.play();
        } catch (IOException e) {
            System.out.println("A client disconnected during the game");
            checkReady();
            return;
        }
        rematchMap.clear();
        start = false;
        playAgain();
    }

    private synchronized void playAgain() {
        System.out.println("Rematch setup");
        while(rematchMap.size() < playersNumber)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Socket socket : rematchMap.keySet())
        {
            if(rematchMap.get(socket).equals(false))
            {
                socketList.remove(map.get(socket));
                listenerList.remove(socket);
                usernameMap.remove(getUsernameFromSocket(socket));
                map.remove(socket);
                lobbySetupDone = false;
            }
        }

        setupIter();
    }

    /**
     * given all the data structures filled, this method creates a matchHandler instance
     * @return
     * @throws IOException
     */
    private synchronized MatchHandler createMatchHandler() throws IOException {

        MatchHandler matchHandler = new MatchHandler();

        while(usernameMap.size()<playersNumber) {
            try {
                wait();
                if(socketList.size()<playersNumber)
                    return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            outputMap.put((String)entry.getKey(),fillByClient.get(entry.getValue()));
        }

        VirtualView virtualView = new VirtualView(outputMap);
        virtualView.setColorsMap(result);
        matchHandler.setVirtualView(virtualView);

        return matchHandler;
    }

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
