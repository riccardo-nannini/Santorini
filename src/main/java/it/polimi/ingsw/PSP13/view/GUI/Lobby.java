package it.polimi.ingsw.PSP13.view.GUI;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Lobby implements Initializable{

    private GuiInput guiInput;

    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane parent;
    @FXML
    private AnchorPane slide;
    @FXML
    private AnchorPane slide1;
    @FXML
    private TextField serverText;
    @FXML
    private TextField nicknameText;
    @FXML
    private Button ok;
    @FXML
    private Button okFirst;
    @FXML
    private Button okSlide;
    @FXML
    private Label errorLabel;
    @FXML
    private Label nicknameError;
    @FXML
    private Label waitLabel;
    @FXML
    private Spinner<Integer> spinner;

    private boolean nicknameSent = false;
    private boolean nextScene = false;

    public void nicknameError(){
        nicknameError.setText("The nickname you have chosen\nis not available for this match,\nplease insert another nickname");
        nicknameError.setVisible(true);
        nicknameSent = false;
    }

    //public static EventType<UpdateEvent> etype = new EventType<>(EventType.ROOT,"prova");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*slide1.addEventHandler(etype,updateEvent -> {
            try {
                chooseP();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/

        SpinnerValueFactory<Integer> numbers = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,3,3);
        spinner.setValueFactory(numbers);

    }

    public void goBacktoNickname() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Insert your nickname");
        popup.setMinWidth(300);

        Label label = new Label("The nickname you have chosen\nis not available for this match,\nplease insert another nickname");
        TextField field = new TextField();
        Button send = new Button("OK");
        send.setOnAction(e -> {
            if(!field.getText().equals("")){
                guiInput.getController().notifyNickname(field.getText());
                popup.close();
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label,field,send);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        popup.setScene(scene);
        popup.showAndWait();

    }

    public void sceneChangeGodSelection(List<String> godsList, int godsNumber, boolean isChallenger) throws IOException {

        if (guiInput.getGodDispatcher() != null) return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/godSelection.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        GodDispatcherGUI godDispatcher = loader.<GodDispatcherGUI>getController();
        GodSelection godSelection = new GodSelection(godDispatcher);
        godDispatcher.setGodHandler(godSelection);
        godSelection.setGodsList(godsList);
        godSelection.setGodsNumber(godsNumber);
        godSelection.setIsChallenger(isChallenger);
        godDispatcher.upload();
        godDispatcher.setGuiInput(guiInput);
        guiInput.setGodDispatcher(godDispatcher);

        Scene selectionScene = new Scene(pane);
        selectionScene.getStylesheets().add("god_selection.css");

        Stage stage = (Stage) (parent.getScene().getWindow());
        stage.setScene(selectionScene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    public void gameStart(ActionEvent event) throws Exception
    {
        okSlide.setDisable(true);
        if(nicknameSent)
            return;
        nicknameError.setVisible(false);
        String nickname = nicknameText.getText();
        guiInput.getController().notifyNickname(nickname);
        nicknameSent=true;


        if(guiInput.isFirst())
            chooseP();
        else
        {
            nicknameError.setText("Please wait\nuntil a match is found...");
            nicknameError.setVisible(true);
        }

    }

    @FXML
    public void textCheckNickname(KeyEvent event)
    {
        if(nicknameText.getText().equals(""))
        {
            okSlide.getStyleClass().clear();
            okSlide.getStyleClass().add("textEmpty");
            okSlide.setDisable(true);

        }
        else
        {
            okSlide.getStyleClass().remove("textEmpty");
            okSlide.getStyleClass().add("textFilled");
            okSlide.setDisable(false);
        }
    }

    @FXML
    public void textCheck(KeyEvent event)
    {
        if(serverText.getText().equals(""))
        {
            ok.getStyleClass().clear();
            ok.getStyleClass().add("textEmpty");
            ok.setDisable(true);

        }
        else
        {
            ok.getStyleClass().remove("textEmpty");
            ok.getStyleClass().add("textFilled");
            ok.setDisable(false);
        }

    }

    @FXML
    public void sendPnumber()
    {   okFirst.setDisable(true);
        guiInput.getController().notifyPlayersNumber((Integer)spinner.getValue());
        waitLabel.setText("Please wait\nuntil a match is found...");
        waitLabel.setVisible(true);
    }

    public void chooseP() throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("lobby.fxml"));
        Scene scene = parent.getScene();

        root.translateXProperty().set(scene.getWidth());
        parent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(slide.translateXProperty(),230, Interpolator.EASE_IN);

        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            slide1.setVisible(true);
        });
        timeline.play();

    }

    public void rematch() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("lobby.fxml"));
        Scene scene = parent.getScene();

        root.translateXProperty().set(scene.getWidth());
        parent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(container.translateXProperty(),230, Interpolator.EASE_IN);

        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            slide.setVisible(true);
        });
        timeline.play();

        okSlide.setDisable(true);
        nicknameText.setText("***");
        nicknameError.setText("Please wait\nuntil a match is found...");
        nicknameError.setVisible(true);
        nicknameSent = true;
    }

    @FXML
    public void connect(ActionEvent event) throws IOException {
        ok.setDisable(true);
        errorLabel.setVisible(false);

        Pattern p = Pattern.compile("((\\d{1,3}[.]){3}\\d{1,3})");
        String text = serverText.getText();
        if(!p.matcher(text).matches())
        {
            errorLabel.setText("Invalid format,\ninsert an IP address!");
            errorLabel.setVisible(true);
            return;
        }

        guiInput = new GuiInput();
        guiInput.setLoginController(this);
        try {
            guiInput.connectToServer(serverText.getText());
        }catch (IOException e){
            errorLabel.setText("Cannot establish a connection,\nyou may be offline!\n(Or the server could... \uD83E\uDD14 )");
            errorLabel.setVisible(true);
            return;
        }


        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("lobby.fxml"));
        Scene scene = ((Node)event.getSource()).getScene();

        root.translateXProperty().set(scene.getWidth());
        parent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(container.translateXProperty(),230, Interpolator.EASE_IN);

        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            slide.setVisible(true);
        });
        timeline.play();


    }

    public AnchorPane getSlide1() {
        return slide1;
    }

    public boolean isNicknameSent() {
        return nicknameSent;
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }

}