package it.polimi.ingsw.PSP13.view.cli;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.board.Map;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

import java.util.ArrayList;
import java.util.List;

public class MapPrinter {

    public Map map;
    public CellCLI[][] MapCLI;
    public List<Builder> builders;

    void updateMapCLI(Map map, List<Builder> builders) {

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
                MapCLI[i][j] = new CellCLI(map.matrix[i][j].getLevel(), map.matrix[i][j].getDome(), worker);
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

        Builder b2 = new Builder();
        b2.setCell(new Cell(2,2));
        builderList.add(b2);

        mappa.setCell(new Coords(2,3), Level.Top);
        mappa.getCell(new Coords(2,3)).setDome(true);
        mappa.setCell(new Coords(1,4), Level.Medium);
        mappa.setCell(new Coords(0,4), Level.Medium);
        mappa.setCell(new Coords(0,0), Level.Base);
        mappa.setCell(new Coords(3,3), Level.Top);
        mappa.setCell(new Coords(2,2), Level.Medium);
        mappa.setCell(new Coords(4,0), Level.Medium);
        mappa.getCell(new Coords(4,0)).setDome(true);
        mappa.setCell(new Coords(3,1), Level.Base);
        MapPrinter printer = new MapPrinter();
        printer.map = mappa;
        printer.builders = builderList;
        printer.printMap();
    }
}
