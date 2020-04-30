package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.ArrayList;
import java.util.List;

public class MapPrinter {

    private static MapVM map;
    private static BuilderMap builder = new BuilderMap();
    private static CellCLI[][] MapCLI;
    private static List<Coords> highlightedCells;

    public MapPrinter() {
        highlightedCells = new ArrayList<>();
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
     * Prints on video the current state of the board
     */
    public static void printMap() {

        MapCLI = new CellCLI[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MapCLI[i][j] = new CellCLI(map.getMap()[i][j], builder.checkBuilder(i, j), isHighlighted(i, j));
            }
        }
        System.out.println("\n\n");
        System.out.printf("%50s", "");
        System.out.printf("%6s 0 %7s 1 %7s 2 %8s 3 %7s 4 \n\n", "", "", "", "", "");
        System.out.printf("%50s", "");
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
                System.out.println();
                System.out.printf("%50s", "");
            }
            if (row < 4) for (int i = 0; i < 55; i++) {
                System.out.print("\u2550");
            }
            System.out.println();
            System.out.printf("%50s", "");
        }
        System.out.println("\n\n");
        highlightedCells.clear();
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
        Coords coords = new Coords(x, y);
        for (Coords cell : highlightedCells) {
            if (coords.equals(cell)) return true;
        }
        return false;
    }
}