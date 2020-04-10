package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Immutables.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapPrinter {

    private MapView map;
    private CellCLI[][] MapCLI;
    private List<Builder> builders;

    private void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    void updateMapCLI(MapView map) {
        this.map = map;
        clearConsole();
        printMap();
    }

    void printMap() {
        boolean worker;
        MapCLI = new CellCLI[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                worker = false;
                for (Builder b : builders) {
                    if (b.getCoords().equals(new Coords(i,j))) worker = true;
                }
                MapCLI[i][j] = new CellCLI(map.getMap()[i][j].getLevel(), map.getMap()[i][j].getDome(), worker);
            }
        }
        System.out.println("\n\n");
        System.out.printf("%50s","");
        for (int row=0; row<5; row++) {
            for (int line = 1; line <= 3; line++) {
                for (int col=0; col<5; col++) {
                    MapCLI[row][col].printCell(line);
                    if (col<4) System.out.print(" \u2016 ");
                }
                System.out.println();
                System.out.printf("%50s","");
            }
            if (row<4) for (int i = 0; i < 51; i++) { System.out.print("\u2550");}
            System.out.println();
            System.out.printf("%50s","");
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        Map mappa = new Map();

        Builder b1 = new Builder();
        b1.setCell(new Cell(1,2));
        List<Builder> builderList = new ArrayList<>();
        builderList.add(b1);
        mappa.setCell(new Coords(2,3), Level.Top);
        mappa.getCell(new Coords(2,3)).setDome(true);
        mappa.setCell(new Coords(1,4), Level.Medium);
        mappa.setCell(new Coords(0,0), Level.Base);
        MapView mapView = new MapView(mappa.getMatrix());
        MapPrinter printer = new MapPrinter();
        printer.map = mapView;
        printer.builders = builderList;
        printer.printMap();

        printer.updateMapCLI(mapView);
    }
}
