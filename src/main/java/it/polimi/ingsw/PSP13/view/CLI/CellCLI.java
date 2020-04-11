package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.player.Color;
import it.polimi.ingsw.PSP13.view.Immutables.CellView;

public class CellCLI {

    private CellLevel level;
    private WorkerColor builder = null;
    private String dome;

    /**
     * initializes a Cell datatype for the CLI
     * @param cell
     * @param workerColor
     */
    public CellCLI(CellView cell, Color workerColor)
    {
        if(cell.getDome())
            this.dome = "\u29BE";
        if(workerColor != null)
        {
            switch (workerColor)
            {
                case Blue:
                    this.builder = WorkerColor.Blue;
                    break;
                case Yellow:
                    this.builder = WorkerColor.Yellow;
                    break;
                case Red:
                    this.builder = WorkerColor.Red;
                    break;
            }
        }
        switch(cell.getLevel())
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
     * stamps on video the line of the cell indicated
     * @param line
     */
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
                        System.out.printf("%s %6s%11s", this.level + CellLevel.RESET, this.builder + CellLevel.RESET, this.level + CellLevel.RESET);
                    } else {
                        System.out.printf("%s%11s%11s", this.level + CellLevel.RESET, this.level + CellLevel.RESET, this.level + CellLevel.RESET);
                    }
                }
                break;
        }
    }
}