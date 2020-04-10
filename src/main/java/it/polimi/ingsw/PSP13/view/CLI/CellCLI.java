package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.view.Immutables.CellView;
import it.polimi.ingsw.PSP13.view.Immutables.BuilderView;

public class CellCLI {

    CellLevel level;
    String dome;
    String builder;

    /**
     * nuovo costruttore
     * @param cell
     * @param worker
     */
    public CellCLI(CellView cell, BuilderView worker)
    {
        if(cell.getDome())
            this.dome = "\u29BE";
        if(worker != null)
        {
            switch (worker.getColor())
            {
                case Blue:
                    this.builder = WorkerColor.Blue.toString();
                    break;
                case Violet:
                    this.builder = WorkerColor.Violet.toString();
                    break;
                case White:
                    this.builder = WorkerColor.White.toString();
                    break;
                case Brown:
                    this.builder = WorkerColor.Brown.toString();
                    break;
            }
        }
        switch(level)
        {
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

    /**
     * vecchio costruttore
     * @param level
     * @param dome
     * @param builder
     */
    public CellCLI(Level level, boolean dome, boolean builder) {
        if (dome) {
            this.dome = "\u29BE";
        }
        if (builder) {
            this.builder = "\uE77B";
        }
        switch(level)
        {
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