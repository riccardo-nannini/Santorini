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
    private static String godEffectDescription;
    private static boolean showEffect;

    public MapPrinter() {
        highlightedCells = new ArrayList<>();
        waitOtherClients = true;
        showEffect = false;
        godEffectDescription = "";
    }


    /**
     * Sets MapPrinter's players information attributes
     * using a MessageClientInfoCV
     * @param clientsInfo is the MessageClientInfoCV
     */
    public static void setClientsInfo(MessageClientsInfoCV clientsInfo) {
        builder.clear();
        MapPrinter.clientUsername = clientsInfo.getClientUsername();
        MapPrinter.clientBuilderColor = builderColorFromColor(clientsInfo.getClientColor());
        MapPrinter.clientGod = clientsInfo.getClientGod();
        MapPrinter.godEffectDescription = clientsInfo.getClientEffect();
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
            default:
                return null;
        }
    }


    public static void setHighlightedCells(List<Coords> highlightedCells) {
        MapPrinter.highlightedCells = new ArrayList<>(highlightedCells);
    }

    /**
     * updates the instance of MapView and refreshes the video
     *
     * @param map
     */
    public void updateMapCLI(MapVM map) {
        MapPrinter.map = map;
        printMap();
    }

    /**
     * updates the instance of BuilderView and refreshed the video
     *
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MapCLI[i][j] = new CellCLI(map.getMap()[i][j], builder.checkBuilder(i, j), isHighlighted(i, j));
            }
        }
        System.out.printf("%30s", "");
        System.out.printf("%6s 0 %7s 1 %7s 2 %8s 3 %7s 4 \n\n", "", "", "", "", "");
        System.out.printf("%30s", "");
        for (int row = 0; row < 5; row++) {
            for (int line = 1; line <= 3; line++) {
                for (int col = 0; col < 5; col++) {
                    if (line == 2 && col == 0)
                        System.out.print(row + "  ");
                    if (line != 2 && col == 0)
                        System.out.print("   ");
                    MapCLI[row][col].printCell(line);
                    if (col < 4) System.out.print(" \u2016 ");
                }
                if (row == 0) {
                    switch (line) {
                        case 1:
                            System.out.printf("%25s %s%s", "\u001B[1mUsername:", clientUsername, "\u001B[0m");
                            break;
                        case 2:
                            System.out.printf("%28s %s", "\u001B[1mWorkers:\u001B[0m", clientBuilderColor);
                            break;
                        case 3:
                            System.out.printf("%20s %s%s", "\u001B[1mGod:", clientGod, "\u001B[0m");
                        default:
                            break;
                    }
                } else if ((row == 1) && (line == 3)) {
                    System.out.printf("%29s", "\u001B[1mOPPONENTS\u001B[0m");
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
                } else if ((row == 3) && (opponentsUsernames.size() == 2)) {
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
                System.out.printf("%30s", "");
            }
            if (row < 4) for (int i = 0; i < 55; i++) {
                System.out.print("\u2550");
            }
            System.out.println();
            System.out.printf("%30s", "");
        }
        highlightedCells.clear();
        System.out.println();
        if (waitOtherClients) System.out.println("Please wait your turn... ... ...");
        if (showEffect) printEffectDescription();
        System.out.println();
    }

    public static void setWaitOtherClients(boolean waitOtherClients) {
        MapPrinter.waitOtherClients = waitOtherClients;
    }


    public static void setShowEffect(boolean showEffect) {
        MapPrinter.showEffect = showEffect;
    }

    /**
     * Prints the win message
     */
    public void notifyWin() {
        System.out.println(" HAI VINTO, COMPLIMENTI");
    }

    /**
     * Prints the lose message
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
        Coords coords = new Coords(x, y);
        for (Coords cell : highlightedCells) {
            if (coords.equals(cell)) return true;
        }
        return false;
    }

    public static void setGodEffect(String godEffect) {
        MapPrinter.godEffectDescription = godEffect;
    }

    /**
     * Prints the god effect description
     */
    public static void printEffectDescription() {
        System.out.println("\u001B[1m" + clientGod.toUpperCase() + "'S EFFECT\u001B[0m: " + godEffectDescription);
    }

}