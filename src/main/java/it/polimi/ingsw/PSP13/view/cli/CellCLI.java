package it.polimi.ingsw.PSP13.view.cli;

import it.polimi.ingsw.PSP13.model.board.Level;

public class CellCLI {

    CellLevel level;
    String dome;
    String builder;

    public CellCLI(Level level, boolean dome, boolean builder) {
        if (dome) {
            this.dome = "\u29BE";
        }
        if (builder) {
            this.builder = "\uE77B";
        }
        switch (level) {
            case Floor:
                this.level = CellLevel.Floor;
                break;
            case Base:
                this.level = CellLevel.Base;
                break;
            case Medium:
                this.level = CellLevel.Medium;
                break;
            case Top:
                this.level = CellLevel.Top;
                break;
        }
    }

    public void printCell(int line) {
        if (line < 1 || line > 3) return;
        switch (line) {
            case 1:
            case 3:
                System.out.printf("%s%11s%11s", this.level + CellLevel.RESET, this.level + CellLevel.RESET, this.level + CellLevel.RESET);
                break;
            case 2:
                if (dome != null) {
                    System.out.printf("%s%11s%11s", this.level + CellLevel.RESET, "\u001B[34m" + this.dome + CellLevel.RESET, this.level + CellLevel.RESET);
                } else {
                    if (builder != null) {
                        System.out.printf("%s%6s%11s", this.level + CellLevel.RESET, this.builder + CellLevel.RESET, this.level + CellLevel.RESET);
                    } else {
                        System.out.printf("%s%11s%11s", this.level + CellLevel.RESET, this.level + CellLevel.RESET, this.level + CellLevel.RESET);
                    }
                }
                break;
        }
    }
}