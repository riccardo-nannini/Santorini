package it.polimi.ingsw.PSP13.immutables;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;

import java.io.Serializable;

public class CellVM implements Serializable {

    private static final long serialVersionUID = 6732L;
    private final Level level;
    private final boolean dome;

    /**
     * Creates an immutable Cell
     * @param cell
     */
    public CellVM(Cell cell)
    {
        this.level = cell.getLevel();
        this.dome = cell.getDome();
    }

    public Level getLevel() {
        return level;
    }

    public boolean getDome() {
        return dome;
    }


}
