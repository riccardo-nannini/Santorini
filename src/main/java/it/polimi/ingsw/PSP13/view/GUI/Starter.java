package it.polimi.ingsw.PSP13.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class Starter {

    private GuiInput guiInput;
    public AnchorPane popupPane;
    public Label popupText;
    public Spinner<String> nameSpinner;

    /**
     * Initializes the starter popup scene
     * @param players list of the current players
     */
    public void upload(List<String> players) {
        popupText.setText("Choose the starting player:");
        setSpinner(players);
    }

    private void setSpinner(List<String> players) {
        ObservableList<String> playerNames = FXCollections.observableArrayList(players);
        SpinnerValueFactory<String> names = new SpinnerValueFactory.ListSpinnerValueFactory<>(playerNames);
        names.setValue(playerNames.get(0));
        nameSpinner.setValueFactory(names);
    }

    /**
     * Cosmetic effect when pressing a button
     * @param event the event that caused the method invocation
     */
    public void buttonPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Node selectedImage = event.getPickResult().getIntersectedNode();
            selectedImage.setBlendMode(BlendMode.MULTIPLY);
        }

    }

    /**
     * The method is invocated when the starter player is chosen.
     * Closes the popup and communicates the result to the server.
     * @param event the event that caused the method invocation
     */
    public void starterSelected(MouseEvent event) {
        Node selectedImage = event.getPickResult().getIntersectedNode();
        selectedImage.setBlendMode(BlendMode.SRC_OVER);
        guiInput.getController().notifyStarterSelection(nameSpinner.getValue());
        Stage stage = (Stage) popupPane.getScene().getWindow();
        stage.close();
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }
}
