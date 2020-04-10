package it.polimi.ingsw.PSP13.view.Immutables;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;

public class CellView {

    private final Level level;
    private final boolean dome;

    /**
     * creates an immutable Cell
     * @param cell
     */
    public CellView(Cell cell)
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
