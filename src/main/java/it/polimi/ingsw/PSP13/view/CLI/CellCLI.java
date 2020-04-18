package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.immutables.CellVM;
import it.polimi.ingsw.PSP13.model.player.Color;

public class CellCLI {

    private CellLevel level;
    private BuilderColor builder = null;
    private String dome;

    /**
     * initializes a Cell datatype for the CLI
     * @param cell
     * @param workerColor
     * @param highlighted
     */
    public CellCLI(CellVM cell, Color workerColor, boolean highlighted) {
        if (cell.getDome())
            this.dome = "\u29BE";
        if (workerColor != null) {
            switch (workerColor) {
                case Blue:
                    this.builder = BuilderColor.Blue;
                    break;
                case Yellow:
                    this.builder = BuilderColor.Yellow;
                    break;
                case Red:
                    this.builder = BuilderColor.Red;
                    break;
            }
        }
        switch (cell.getLevel()) {
            case Floor:
                if (highlighted) {
                    this.level = CellLevel.FloorHighlighted;
                } else {
                    this.level = CellLevel.Floor;
                }
                break;
            case Base:
                if (highlighted) {
                    this.level = CellLevel.BaseHighlighted;
                } else {
                    this.level = CellLevel.Base;
                }
                break;
            case Medium:
                if (highlighted) {
                    this.level = CellLevel.MediumHighlighted;
                } else {
                    this.level = CellLevel.Medium;
                }
                break;
            case Top:
                if (highlighted) {
                    this.level = CellLevel.TopHighlighted;
                } else {
                    this.level = CellLevel.Top;
                }
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
                System.out.printf("%s%11s%11s", this.level +  CellLevel.RESET, this.level + CellLevel.RESET, this.level + CellLevel.RESET);
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