package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodDispatcher {

    private GodHandlerInterface godHandler;
    private Map<String,String> godEffects = null;
    private GuiInput guiInput;

    private Stage stage = null;
    public AnchorPane anchorPane1;
    public ImageView card;
    public ImageView godFrame;
    public ImageView godBanner;
    public ImageView confirm;
    public TextFlow godName;
    public TextFlow infoBox;
    public TextFlow descriptionBox;
    public ImageView godIcon1, godIcon2, godIcon3, godIcon4, godIcon5, godIcon6, godIcon7, godIcon8, godIcon9,
            godIcon10, godIcon11, godIcon12, godIcon13, godIcon14, godIcon15, godIcon16;

    /**
     * The invocation is reflected to the current GodHandler object following a state pattern logic
     */
    public void upload() {
        godHandler.upload();
    }

    /**
     * If the event is a mouse right click, a window showing the god's card and the description of this effect
     * is showed. If the event is a mouse left click the invocation is reflected to the current GodHandler object
     * that handles the event following a state pattern logic
     * @param event the event that caused the method invocation
     */
    public void godClicked(Event event) {
        if (((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
            godBanner.setImage(new Image("nameBanner.png"));
            godFrame.setImage(new Image("godCardFrame.png"));
            String id = ((MouseEvent) event).getPickResult().getIntersectedNode().getId();
            Image cardImage = new Image("Cards/" + id + ".png");
            card.setImage(cardImage);
            godName.getChildren().clear();
            Text name = new Text(id);
            name.setStyle("-fx-fill: WHITE;");
            godName.getChildren().add(name);
            if (godEffects != null) {
                Text text = new Text(godEffects.get(id));
                descriptionBox.getChildren().clear();
                descriptionBox.getChildren().add(text);
            }
        } else godHandler.godClicked(event);
    }

    /**
     * Changes the current scene with the Login one
     * @throws IOException
     */
    public void setSceneLogin() throws IOException {
        AnchorPane root = anchorPane1;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/lobby.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("god_selection.css");

        if (stage == null) stage = (Stage) (root.getScene().getWindow());

        stage.setTitle("Santorini");
        stage.getIcons().add(new Image("island.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * The invocation is reflected to the current GodHandler object following a state pattern logic
     * @param event the event that caused the method invocation
     */
    public void confirmClicked(Event event) throws IOException {
        godHandler.confirmClicked(event);
    }

    /**
     * The invocation is reflected to the current GodHandler object following a state pattern logic
     * @param event the event that caused the method invocation
     * @throws IOException
     */
    public void helperClicked(Event event) throws IOException {
        godHandler.helperClicked(event);
    }

    /**
     * The icon being pressed is colored with a black shadow in order to add a more responsive look
     * @param event the event that caused the method invocation
     */
    public void buttonPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Node selectedImage = event.getPickResult().getIntersectedNode();
            selectedImage.setBlendMode(BlendMode.MULTIPLY);
        }

    }

    /**
     * Changes the current scene and updates the GodHandler attribute with a GodInput
     * object following a state pattern logic
     * @param chosenGods the gods chosen for this match
     * @param isChoosing true is the player is chosing is god, false if he is spectating
     * @throws IOException
     */
    public void setSceneGodInput(List<String> chosenGods, boolean isChoosing) throws IOException {
        AnchorPane root = anchorPane1;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/godSelection.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        GodDispatcher godDispatcher = loader.<GodDispatcher>getController();
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

    /**
     * Opens a popup where the player is asked to choose the starting player for the match
     * @param players list of players for this match
     * @throws IOException
     */
    public void setSceneStarterSelection(List<String> players) throws IOException {
        Stage popup = new Stage();

        Font.loadFont(GodDispatcher.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toExternalForm(), 18);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/starterPopup.fxml"));
        AnchorPane popupPane = loader.<AnchorPane>load();

        Starter starter = loader.<Starter>getController();
        starter.upload(players);
        starter.setGuiInput(guiInput);

        Scene starterScene = new Scene(popupPane);
        starterScene.getStylesheets().add("god_selection.css");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Starting player");
        popup.setScene(starterScene);
        popup.show();
    }

    /**
     * Opens a popup that shows some tips on how to use the GUI
     * @param event the event that caused the method invocation
     * @param message the messages that are going to be written in the popup
     * @throws IOException
     */
    public void setSceneHelperPopup (Event event, List<String> message) throws IOException {
        Node selectedImage = ((MouseEvent) event).getPickResult().getIntersectedNode();
        selectedImage.setBlendMode(BlendMode.SRC_OVER);
        Stage helper = new Stage();

        Font.loadFont(GodDispatcher.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toExternalForm(), 18);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/helper.fxml"));
        AnchorPane helperPane = loader.<AnchorPane>load();

        Helper helperGUI = loader.<Helper>getController();
        helperGUI.setText(message);

        Scene starterScene = new Scene(helperPane);
        starterScene.getStylesheets().add("god_selection.css");
        helper.initModality(Modality.APPLICATION_MODAL);
        helper.setTitle("Helper");
        helper.setScene(starterScene);

        helper.show();
    }

    /**
     * Opens a popup showing an error message
     * @param message string being printed in the popup
     * @throws IOException
     */
    public void setSceneErrorPopup(String message) throws IOException {
        Stage error = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/error.fxml"));
        AnchorPane errorPane = loader.<AnchorPane>load();

        Error errorGUI = loader.<Error>getController();
        errorGUI.setText(message);

        Scene starterScene = new Scene(errorPane);
        starterScene.getStylesheets().add("god_selection.css");
        error.initModality(Modality.APPLICATION_MODAL);
        error.setTitle("Error");
        error.setScene(starterScene);

        error.show();
    }

    /**
     * Changes the scene to the game board
     * @throws IOException
     */
    public void setSceneGameBoard() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GodDispatcher.class.getResource("/mappa.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene lobby = new Scene(pane);
        lobby.getStylesheets().add("mappa.css");

        if (stage == null) stage = (Stage) (anchorPane1.getScene().getWindow());
        stage.setScene(lobby);

        GameMap map = loader.<GameMap>getController();
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

    /**
     * Converts a List of Strings in the format "GodName;godeffect" in Map where GodName is the key and the
     * effect is the object
     * @param godEffects the list to be converted
     */
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