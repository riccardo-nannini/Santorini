package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GodInput implements GodHandlerInterface {

    private final GodDispatcher godDispatcher;
    private List<String> godsList;
    private String selectedGod = null;
    private boolean isChoosing;
    private final List<ImageView> icons = new ArrayList<>();
    private final int offsetIcon1 = 40;
    private final int offsetIcon2 = 275;
    private final int offsetIcon3 = 540;
    private final int offsetIcon4 = 100;
    private final int offsetIcon5 = 500;


    public GodInput (GodDispatcher godDispatcher) {
        this.godDispatcher = godDispatcher;
    }

    /**
     * Initialized the icons list with the ones required in this view
     */
    @Override
    public void initialization() {
        icons.add(godDispatcher.godIcon8);
        icons.add(godDispatcher.godIcon7);
        icons.add(godDispatcher.godIcon6);
    }

    /**
     * Initializes the view using the attributes setted up in the object
     */
    @Override
    public void upload() {
        initialization();
        godDispatcher.descriptionBox.setDisable(false);
        godDispatcher.infoBox.setDisable(true);
        godDispatcher.godName.setDisable(true);
        Text text = new Text();
        if (isChoosing) {
            text.setText("Select your god!");
        } else {
            text.setText("Wait while the opponent is chosing his god..");
        }
        godDispatcher.infoBox.getChildren().add(text);
        if (godsList.size() == 2) {
            for (int godsIndex=0; godsIndex < godsList.size(); godsIndex++) {
                ImageView img = icons.get(godsIndex);
                img.setImage(new Image("Icons/" + godsList.get(godsIndex) +".png"));
                img.setId(godsList.get(godsIndex));
                if (godsIndex == 0) {
                    img.setLayoutX(offsetIcon4);
                } else {
                    img.setLayoutX(offsetIcon5);
                }
            }
        } else if (godsList.size() == 3) {
            for (int godsIndex=0; godsIndex < godsList.size(); godsIndex++) {
                ImageView img = icons.get(godsIndex);
                img.setImage(new Image("Icons/" + godsList.get(godsIndex) +".png"));
                img.setId(godsList.get(godsIndex));
                if (godsIndex == 0) {
                    img.setLayoutX(offsetIcon1);
                } else if (godsIndex == 1){
                    img.setLayoutX(offsetIcon2);
                } else {
                    img.setLayoutX(offsetIcon3);
                }
            }
        }

    }

    /**
     * If the player is the one choosing his god, the invocation of this methods saves the clicked god as the selected
     * one from the player and colors the god's icon with a black shadow in order to improve the visual feedback
     * of the view
     * @param event the event that caused the method invocation
     */
    @Override
    public void godClicked(Event event) {
        String id = ((MouseEvent) event).getPickResult().getIntersectedNode().getId();

        if (((MouseEvent) event).getButton() == MouseButton.PRIMARY && isChoosing) {
            Node selectedImage = ((MouseEvent) event).getPickResult().getIntersectedNode();
            if (selectedImage.getBlendMode() != null && selectedImage.getBlendMode().equals(BlendMode.MULTIPLY)) {
                selectedImage.setBlendMode(BlendMode.SRC_OVER);
            } else {
                if (selectedGod != null) {
                    for (ImageView img : icons) {
                        if (img.getId().equals(selectedGod)) img.setBlendMode(BlendMode.SRC_OVER);
                    }
                }
                selectedGod = id;
                selectedImage.setBlendMode(BlendMode.MULTIPLY);
            }
        }
    }

    /**
     * The method checks if the player already selected a god. If so it notifies the observable, otherwise it
     * displays an error message
     * @param event the event that caused the method invocation
     */
    @Override
    public void confirmClicked(Event event) throws IOException {
        Node selectedImage = ((MouseEvent) event).getPickResult().getIntersectedNode();
        selectedImage.setBlendMode(BlendMode.SRC_OVER);
        if (!isChoosing) return;
        if (selectedGod == null) {
            godDispatcher.setSceneErrorPopup("Please select a god!");
        } else {
            godDispatcher.getGuiInput().getController().notifyGod(selectedGod);

        }
    }

    /**
     * Shows a popup with a custom help message for the user
     * @param event the event that caused the method invocation
     * @throws IOException
     */
    @Override
    public void helperClicked(Event event) throws IOException {
        List<String> message = new ArrayList<>();
        if (isChoosing) {
            message.add("Left click: selects a god");
        }
        message.add("\nRight click: shows god info");
        godDispatcher.setSceneHelperPopup(event, message);
    }

    public void setGodsList(List<String> godsList) {
        this.godsList = godsList;
    }

    public void setIsChoosing (boolean isChoosing) {
        this.isChoosing = isChoosing;
    }
}
