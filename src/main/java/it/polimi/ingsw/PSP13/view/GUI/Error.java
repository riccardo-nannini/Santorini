package it.polimi.ingsw.PSP13.view.GUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class Error {

    public Text errorText;
    public TextFlow errorTextFlow;
    public AnchorPane errorPane;
    private final int longStringSize = 25;
    private final int shortStringLayout = 50;

    /**
     * Sets the error message and shifts the TextFlow if the message is short in order to improve the visualization
     * @param message error message
     */
    public void setText(String message){
        errorText.setText(message);
        if (message.length() < longStringSize) errorTextFlow.setLayoutY(shortStringLayout);
    }

    public void close() {
        Stage stage = (Stage) (errorPane.getScene().getWindow());
        stage.close();
    }
}
