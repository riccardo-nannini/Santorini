package it.polimi.ingsw.PSP13.model.player;

import it.polimi.ingsw.PSP13.model.board.Cell;

public class Builder {

    private Cell cell;

    public Coords getCoords() {
        return cell.getCoords();
    }

    public int getHeight()
    {
        return cell.getLevel().getHeight();
    }

    public void setCell(Cell cell)
    {
        this.cell = cell;
    }

    public Builder() {
        cell = null;
    }
}
