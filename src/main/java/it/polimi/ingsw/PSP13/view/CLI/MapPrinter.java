package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageClientsInfoCV;

import java.util.ArrayList;
import java.util.List;

public class MapPrinter {

    private static MapVM map;
    private static BuilderMap builder = new BuilderMap();
    private static CellCLI[][] MapCLI;
    private static List<Coords> highlightedCells;
    private static String clientUsername;
    private static BuilderColor clientBuilderColor;
    private static String clientGod;
    private static List<String> opponentsUsernames;
    private static List<BuilderColor> opponentsColors;
    private static List<String> opponentsGods;
    private static boolean waitOtherClients;

    public MapPrinter() {
        highlightedCells = new ArrayList<>();
        waitOtherClients = true;
    }


    /**
     * Sets MapPrinter's players information attributes
     * using a MessageClientInfoCV
     * @param clientsInfo is the MessageClientInfoCV
     */
    public static void setClientsInfo(MessageClientsInfoCV clientsInfo) {
        MapPrinter.clientUsername = clientsInfo.getClientUsername();
        MapPrinter.clientBuilderColor = builderColorFromColor(clientsInfo.getClientColor());
        MapPrinter.clientGod = clientsInfo.getClientGod();
        MapPrinter.opponentsUsernames = clientsInfo.getOpponentsUsernames();
        MapPrinter.opponentsColors = new ArrayList<>();
        for (Color color : clientsInfo.getOpponentsColors()) {
            MapPrinter.opponentsColors.add(builderColorFromColor(color));
        }
        MapPrinter.opponentsGods = clientsInfo.getOpponentsGod();
    }

    /**
     * @param color
     * @return the corresponding BuilderColor
     */
    public static BuilderColor builderColorFromColor(Color color) {
        switch (color) {
            case Blue:
                return BuilderColor.Blue;
            case Yellow:
                return BuilderColor.Yellow;
            case Red:
                return BuilderColor.Red;
            default: return null;
        }
    }


    public static void setHighlightedCells(List<Coords> highlightedCells) {
        MapPrinter.highlightedCells = new ArrayList<>(highlightedCells);
    }

    /**
     * updates the instance of MapView and refreshes the video
     * @param map
     */
    public void updateMapCLI(MapVM map) {
        MapPrinter.map = map;
        printMap();
    }

    /**
     * updates the instance of BuilderView and refreshed the video
     * @param builder
     */
    public void updateBuildersCLI(BuilderVM builder) {
        MapPrinter.builder.updateBuilder(builder);
        printMap();
    }

    /**
     * stamps on video the current state of the board
     */
    public static void printMap() {

        MapCLI = new CellCLI[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                MapCLI[i][j] = new CellCLI(map.getMap()[i][j], builder.checkBuilder(i,j),isHighlighted(i,j));
            }
        }
        System.out.printf("%30s","");
        System.out.printf("%6s 0 %7s 1 %7s 2 %8s 3 %7s 4 \n\n","","","","","");
        System.out.printf("%30s","");
        for (int row=0; row<5; row++) {
            for (int line = 1; line <= 3; line++) {
                for (int col=0; col<5; col++) {
                    if(line == 2 && col==0)
                        System.out.print(row + "  ");
                    if(line != 2 && col==0)
                        System.out.print("   ");
                    MapCLI[row][col].printCell(line);
                    if (col<4) System.out.print(" \u2016 ");
                }
                if (row == 0) {
                    switch (line) {
                        case 1:
                            System.out.printf("%25s %s%s","\u001B[1mUsername:",clientUsername,"\u001B[0m");
                            break;
                        case 2:
                            System.out.printf("%28s %s","\u001B[1mWorkers:\u001B[0m",clientBuilderColor);
                            break;
                        case 3:
                            System.out.printf("%20s %s%s","\u001B[1mGod:",clientGod,"\u001B[0m");
                        default:
                            break;
                    }
                } else if ((row == 1) && (line == 3)) {
                    System.out.printf("%29s","\u001B[1mOPPONENTS\u001B[0m");
                } else if (row == 2) {
                    switch (line) {
                        case 1:
                            System.out.printf("%25s %s%s", "\u001B[1mUsername:", opponentsUsernames.get(0), "\u001B[0m");
                            break;
                        case 2:
                            System.out.printf("%28s %s", "\u001B[1mWorkers:\u001B[0m", opponentsColors.get(0));
                            break;
                        case 3:
                            System.out.printf("%20s %s%s", "\u001B[1mGod:", opponentsGods.get(0), "\u001B[0m");
                        default:
                            break;
                    }
                } else if ((row == 3) && (opponentsUsernames.size()==2)) {
                    switch (line) {
                        case 1:
                            System.out.printf("%25s %s%s", "\u001B[1mUsername:", opponentsUsernames.get(1), "\u001B[0m");
                            break;
                        case 2:
                            System.out.printf("%28s %s", "\u001B[1mWorkers:\u001B[0m", opponentsColors.get(1));
                            break;
                        case 3:
                            System.out.printf("%20s %s%s", "\u001B[1mGod:", opponentsGods.get(1), "\u001B[0m");
                        default:
                            break;
                    }
                }

                System.out.println();
                System.out.printf("%30s","");
            }
            if (row<4) for (int i = 0; i < 55; i++) { System.out.print("\u2550");}
            System.out.println();
            System.out.printf("%30s","");
        }
        System.out.println("\n");
        highlightedCells.clear();
        if (waitOtherClients) System.out.println("Please wait your turn... ... ...");
    }

    public static void setWaitOtherClients(boolean waitOtherClients) {
        MapPrinter.waitOtherClients = waitOtherClients;
    }


    /**
     * Prints the win message
     *
     */
    public void notifyWin() {
        System.out.println(" HAI VINTO, COMPLIMENTI");
    }

    /**
     * Prints the lose message
     *
     */
    public void notifyLose() {
        System.out.println("Hai perso!");
    }
    /**
     * @param x coordinate x
     * @param y coordinate y
     * @return true if the x-y cell is highlighted
     */
    public static boolean isHighlighted(int x, int y) {
        Coords coords = new Coords(x,y);
        for (Coords cell : highlightedCells) {
            if (coords.equals(cell)) return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Map mappa = new Map();
        Coords[] coordsBlue = new Coords[2];
        Coords[] coordsRed = new Coords[2];
        Coords[] coordsYellow = new Coords[2];
        BuilderMap builderMap = new BuilderMap();
        BuilderVM workerBlue = new BuilderVM(coordsBlue, Color.Blue);
        BuilderVM workerRed = new BuilderVM(coordsRed, Color.Red);
        BuilderVM workerYellow = new BuilderVM(coordsYellow, Color.Yellow);
        coordsBlue[0] = new Coords(1,2);
        coordsBlue[1] = new Coords(4,4);
        coordsRed[0] = new Coords(2,2);
        coordsRed[1] = new Coords(2,1);
        coordsYellow[0] = new Coords(4,2);
        coordsYellow[1] = new Coords(4,3);

        builderMap.updateBuilder(workerBlue);
        builderMap.updateBuilder(workerRed);
        builderMap.updateBuilder(workerYellow);

        mappa.setCell(new Coords(2,3), Level.Top);
        mappa.getCell(new Coords(2,3)).setDome(true);
        mappa.setCell(new Coords(1,4), Level.Medium);
        mappa.setCell(new Coords(0,0), Level.Base);

        MapVM mapView = new MapVM(mappa.getMatrix());
        MapPrinter printer = new MapPrinter();
        printer.map = mapView;
        printer.builder = builderMap;
        List<Coords> list= new ArrayList<Coords>();
        list.add(new Coords(0,0));
        list.add(new Coords(0,1));
        list.add(new Coords(1,0));

        printer.clientUsername = "Tony";
        printer.clientBuilderColor = BuilderColor.Blue;
        printer.clientGod = "Artemis";


        opponentsUsernames = new ArrayList<>();
        opponentsColors = new ArrayList<>();
        opponentsGods = new ArrayList<>();

        opponentsUsernames.add("Aurora");
        opponentsUsernames.add("Simone");

        opponentsGods.add("Cristina");
        opponentsGods.add("Mario");

        opponentsColors.add(BuilderColor.Red);
        opponentsColors.add(BuilderColor.Yellow);



        printer.setHighlightedCells(list);
        MapPrinter.printMap();
        MapPrinter.printMap();


        //printer.updateMapCLI(mapView);
    }
}
