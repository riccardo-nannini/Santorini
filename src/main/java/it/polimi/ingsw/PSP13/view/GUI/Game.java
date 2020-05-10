package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.model.player.Coords;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Game{

    @FXML
    private GridPane gridBoard;
    @FXML
    private Pane blackFilter;
    @FXML
    private Button selectBuilderBtn;
    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button10;
    @FXML
    private Button button11;

    private List<Coords> coords = new ArrayList<>();

    public void setList()
    {
        coords.clear();
        coords.add(new Coords(0,0));
        coords.add(new Coords(0,1));
        coords.add(new Coords(1,0));
        coords.add(new Coords(1,1));
    }

    @FXML
    public void gridBtnClick(ActionEvent event)
    {
        gridBoard.setOpacity(0);
        gridBoard.setDisable(true);
        blackFilter.setOpacity(0);
    }

    @FXML
    public void selectBuilder(ActionEvent event)
    {
        setList();

        gridBoard.setOpacity(0.6);
        gridBoard.setDisable(false);

        blackFilter.setOpacity(0.4);

        for(Coords coord : coords){
            switch (coord.getX())
            {
                case 0:
                {
                    switch (coord.getY())
                    {
                        case 0:
                         {
                             button00.getStyleClass().clear();
                             button00.getStyleClass().add("highlighted");
                         }
                        case 1:
                        {
                            button01.getStyleClass().clear();
                            button01.getStyleClass().add("highlighted");
                        }
                    }
                }
                case 1:
                {
                    switch (coord.getY())
                    {
                        case 0:
                        {
                            button10.getStyleClass().clear();
                            button10.getStyleClass().add("highlighted");
                        }
                        case 1:
                        {
                            button11.getStyleClass().clear();
                            button11.getStyleClass().add("highlighted");
                        }
                    }
                }
            }
        }

    }

}
