package it.polimi.ingsw.PSP13.view.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class EffectInput {

    private GuiInput guiInput;
    public AnchorPane effectPane;
    public ImageView godIcon;
    public TextFlow effectText;

    public void upload(String godName) {
        godIcon.setImage(new Image("Icons/" + godName + ".png"));
        effectText.getChildren().add(new Text("Do you want to use the effect of " + godName+"?"));
    }

    public void clickedYes(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            guiInput.getController().notifyEffect("yes");
            Stage stage = (Stage) effectPane.getScene().getWindow();
            stage.close();
        }
    }

    public void clickedNo(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            guiInput.getController().notifyEffect("no");
            Stage stage = (Stage) effectPane.getScene().getWindow();
            stage.close();
        }
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }
}
