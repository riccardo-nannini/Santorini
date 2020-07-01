package it.polimi.ingsw.PSP13.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Disconnection {

    @FXML
    private AnchorPane disconnectionPane;

    public void close() {
        Stage stage = (Stage) (disconnectionPane.getScene().getWindow());
        stage.close();
    }
}
