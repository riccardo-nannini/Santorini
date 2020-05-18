package it.polimi.ingsw.PSP13.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class PopupGUI {

    private GuiInput guiInput;
    public AnchorPane popupPane;
    public Label popupText;
    public Spinner<String> nameSpinner;

    public void upload(List<String> players) {
        popupText.setText("Choose the starting player");
        setSpinner(players);
    }

    private void setSpinner(List<String> players) {
        ObservableList<String> playerNames = FXCollections.observableArrayList(players);
        SpinnerValueFactory<String> names = new SpinnerValueFactory.ListSpinnerValueFactory<>(playerNames);
        names.setValue(playerNames.get(0));
        nameSpinner.setValueFactory(names);
    }

    public void starterSelected() {
        guiInput.getController().notifyStarterSelection(nameSpinner.getValue());
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }
}
