package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Immutables.MapView;
import it.polimi.ingsw.PSP13.view.Immutables.WorkerVM;

public class MapPrinter {

    private static MapView map;
    private static BuilderMap builder = new BuilderMap();
    private static CellCLI[][] MapCLI;

    /**
     * updates the instance of MapView and refreshes the video
     * @param map
     */
    void updateMapCLI(MapView map) {
        this.map = map;
        printMap();
    }

    /**
     * updates the instance of BuilderView and refreshed the video
     * @param builder
     */
    void updateBuildersCLI(WorkerVM builder) {
        this.builder.updateBuilder(builder);
        printMap();
    }

    /**
     * stamps on video the current state of the board
     */
    public static void printMap() {

        MapCLI = new CellCLI[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                MapCLI[i][j] = new CellCLI(map.getMap()[i][j], builder.checkBuilder(i,j));
            }
        }
        System.out.println("\n\n");
        System.out.printf("%50s","");
        System.out.printf("%6s 0 %7s 1 %7s 2 %8s 3 %7s 4 \n\n","","","","","");
        System.out.printf("%50s","");
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
                System.out.println();
                System.out.printf("%50s","");
            }
            if (row<4) for (int i = 0; i < 55; i++) { System.out.print("\u2550");}
            System.out.println();
            System.out.printf("%50s","");
        }
        System.out.println("\n\n");
    }


    public static void main(String[] args) {
        Map mappa = new Map();
        Coords[] coordsBlue = new Coords[2];
        Coords[] coordsRed = new Coords[2];
        Coords[] coordsYellow = new Coords[2];
        BuilderMap builderMap = new BuilderMap();
        WorkerVM workerBlue = new WorkerVM(Color.Blue,coordsBlue);
        WorkerVM workerRed = new WorkerVM(Color.Red,coordsRed);
        WorkerVM workerYellow = new WorkerVM(Color.Yellow,coordsYellow);
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

        MapView mapView = new MapView(mappa.getMatrix());
        MapPrinter printer = new MapPrinter();
        printer.map = mapView;
        printer.builder = builderMap;

        MapPrinter.printMap();

        //printer.updateMapCLI(mapView);
    }
}
