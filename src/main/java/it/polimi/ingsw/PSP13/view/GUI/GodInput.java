package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

public class GodInput implements GodHandlerInterface {

    private final GodDispatcherGUI godDispatcher;
    private List<String> godsList;
    private String selectedGod = null;
    private boolean isChoosing;
    private List<ImageView> icons = new ArrayList<>();
    private final int offsetIcon1 = 40;
    private final int offsetIcon2 = 275;
    private final int offsetIcon3 = 540;
    private final int offsetIcon4 = 100;
    private final int offsetIcon5 = 500;



    public GodInput (GodDispatcherGUI godDispatcher) {
        this.godDispatcher = godDispatcher;
    }

    @Override
    public void initialization() {
        icons.add(godDispatcher.godIcon8);
        icons.add(godDispatcher.godIcon7);
        icons.add(godDispatcher.godIcon6);
    }

    @Override
    public void upload() {
        initialization();
        godDispatcher.descriptionBox.setEditable(false);
        godDispatcher.infoBox.setDisable(true);
        Text text = new Text();
        if (isChoosing) {
            text.setText("   Select your god!");
        } else {
            text.setText("  Wait while the opponent is chosing his god..");
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

    @Override
    public void confirmClicked() {
        if (!isChoosing) return;
        if (selectedGod == null) {
            if (godDispatcher.infoBox.getChildren().size() < 2) {
                Text text = new Text();
                text.setStyle("-fx-fill: RED;");
                text.setText("\nPlease select a god");
                godDispatcher.infoBox.getChildren().add(text);
            }
        } else {
            godDispatcher.getGuiInput().getController().notifyGod(selectedGod);

        }
    }

    @Override
    public void helperClicked() {
        System.out.println("Helper clicked!");
    }

    public void setGodsList(List<String> godsList) {
        this.godsList = godsList;
    }

    public void setIsChoosing (boolean isChoosing) {
        this.isChoosing = isChoosing;
    }
}
