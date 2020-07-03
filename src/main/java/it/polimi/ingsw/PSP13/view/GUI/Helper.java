package it.polimi.ingsw.PSP13.view.GUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.List;

public class Helper {

    public TextFlow helperText;
    public AnchorPane helperPane;
    private double layoutSingleMessage = 20;

    /**
     * Initializes the helper popup scene with a message
     * @param message the message to be displayed
     */
    public void setText(List<String> message){
        for (String text : message) {
            Text row = new Text(text);
            helperText.getChildren().add(row);
        }
        if (message.size() == 1) helperText.setLayoutY(layoutSingleMessage);
    }

    /**
     * Close the helper window
     */
    public void close() {
        Stage stage = (Stage) (helperPane.getScene().getWindow());
        stage.close();
    }
}
