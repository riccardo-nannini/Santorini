package it.polimi.ingsw.PSP13.model.gods;

import it.polimi.ingsw.PSP13.model.Turn;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;

public class Poseidon extends Turn {

    private Boolean useEffect;
    private Coords coords1;
    private Coords coords2;
    private Coords coords3;
    private Builder unmovedBuilder;

    public Poseidon () {
        this.useEffect = false;
        this.coords1 = null;
        this.coords2 = null;
        this.coords3 = null;
        this.unmovedBuilder = null;
    }

    //momentaneo per test
    public Poseidon (Boolean useEffect, Coords coords1, Coords coords2, Coords coords3, Builder unmovedBuilder) {
        this.useEffect = useEffect;
        this.coords1 = coords1;
        this.coords2 = coords2;
        this.coords3 = coords3;
        this.unmovedBuilder = unmovedBuilder;
    }

    /**
     * In addiction to turn's move, sets the unmoved builder
     * @param builder builder that is currently moving
     * @param coords coordinates of the cell where the builder wants to move
     */
    @Override
    public void move(Builder builder, Coords coords) {
        if (match.getPlayerByBuilder(builder).getBuilders()[0] == builder) {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[1];
        } else {
            unmovedBuilder = match.getPlayerByBuilder(builder).getBuilders()[0];
        }
        builder.setCell(match.getCell(coords));
    }

    /**
     * If the unmoved builder is on the ground level, it can build
     * up to three times in neighbouring cells
     */
    @Override
    public void end() {
        if (match.getHeight(unmovedBuilder.getCoords()) == 0) {
            //richidi se usare l'effetto
            //se lo usa chiedi anche le 3 coordinate (quante ne vuole, max 3)
            // (checkando che siano tutte diverse fra loro ??)
            if (useEffect) {
                if (checkBuild(unmovedBuilder, coords1)) build(unmovedBuilder, coords1);
                if (checkBuild(unmovedBuilder, coords2)) build(unmovedBuilder, coords2);
                if (checkBuild(unmovedBuilder, coords3)) build(unmovedBuilder, coords3);
            }
        }
    }

}
