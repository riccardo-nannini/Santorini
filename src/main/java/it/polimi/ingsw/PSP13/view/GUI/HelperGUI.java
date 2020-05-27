package it.polimi.ingsw.PSP13.view.GUI;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.List;

public class HelperGUI {

    public TextFlow helperText;
    public AnchorPane helperPane;
    private double layoutSingleMessage = 20;

    public void setText(List<String> message){
        for (String text : message) {
            Text row = new Text(text);
            helperText.getChildren().add(row);
        }
        if (message.size() == 1) helperText.setLayoutY(layoutSingleMessage);
    }

    public void close() {
        Stage stage = (Stage) (helperPane.getScene().getWindow());
        stage.close();
    }
}
