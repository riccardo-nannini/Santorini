package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.view.GUI.status.SetupStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodDispatcherGUI {

    private GodHandlerInterface godHandler;
    private Map<String,String> godEffects = null;
    private GuiInput guiInput;

    private Stage stage = null;
    public AnchorPane anchorPane1;
    public ImageView card;
    public ImageView confirm;
    public TextFlow infoBox;
    public TextArea descriptionBox;
    public ImageView godIcon1, godIcon2, godIcon3, godIcon4, godIcon5, godIcon6, godIcon7, godIcon8, godIcon9,
            godIcon10, godIcon11, godIcon12, godIcon13, godIcon14, godIcon15, godIcon16;
    //TODO generare le icone da codice?

    public void upload() {
        godHandler.upload();
    }

    public void godClicked(Event event) {
        if (((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
            String id = ((MouseEvent) event).getPickResult().getIntersectedNode().getId();
            Image cardImage = new Image("Cards/" + id + ".png");
            card.setImage(cardImage);
            if (godEffects != null) descriptionBox.setText(godEffects.get(id));
            System.out.println("Click dx");
        } else godHandler.godClicked(event);
    }

    public void confirmClicked() {
        godHandler.confirmClicked();
    }

    public void helperClicked() {
        godHandler.helperClicked();
    }

    public void setSceneGodInput(List<String> chosenGods, boolean isChoosing) throws IOException {
        AnchorPane root = anchorPane1;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/godSelection.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        GodDispatcherGUI godDispatcher = loader.<GodDispatcherGUI>getController();
        GodInput godInput = new GodInput(godDispatcher);
        godDispatcher.setGodHandler(godInput);
        godInput.setGodsList(chosenGods);
        godInput.setIsChoosing(isChoosing);
        godDispatcher.upload();
        godDispatcher.setGuiInput(guiInput);
        guiInput.setGodDispatcher(godDispatcher);

        Scene selectionScene = new Scene(pane);
        selectionScene.getStylesheets().add("god_selection.css");

        if (stage == null) stage = (Stage) (root.getScene().getWindow());
        stage.setScene(selectionScene);
        stage.show();
    }

    public void setScenePopUp(List<String> players) throws IOException {
        Stage popup = new Stage();

        Font.loadFont( GodDispatcherGUI.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toExternalForm(), 18);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/popupStarter.fxml"));
        AnchorPane popupPane = loader.<AnchorPane>load();

        PopupGUI popupGUI = loader.<PopupGUI>getController();
        popupGUI.upload(players);
        popupGUI.setGuiInput(guiInput);

        Scene starterScene = new Scene(popupPane);
        starterScene.getStylesheets().add("god_selection.css");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Starting player");
        popup.setScene(starterScene);
        popup.show();
    }

    public void setSceneGameBoard() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/mappa.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene lobby = new Scene(pane);
        lobby.getStylesheets().add("mappa.css");

        if (stage == null) stage = (Stage) (anchorPane1.getScene().getWindow());
        stage.setScene(lobby);

        Mappa map = loader.<Mappa>getController();
        map.setGuiInput(guiInput);
        guiInput.setMap(map);
    }


    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }

    public void setGodHandler(GodHandlerInterface godHandler) {
        this.godHandler = godHandler;
    }

    public GuiInput getGuiInput() {
        return guiInput;
    }

    public void setGodEffects(List<String> godEffects) {
        if (this.godEffects != null) return;
        Map<String, String> effects = new HashMap<>();
        for (String effect : godEffects) {
            String[] splitted = effect.split("\\s*;\\s*");
            effects.put(splitted[0], splitted[1]);
        }
        this.godEffects = effects;
    }

}