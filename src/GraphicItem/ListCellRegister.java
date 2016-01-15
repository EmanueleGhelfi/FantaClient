package GraphicItem;

import Controllers.MercatoController;
import Controllers.RegisterController;
import Model.Player;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Created by Emanuele on 15/12/2015.
 */
public class ListCellRegister extends ListCell<Player> {

    private RegisterController registerController;

    public ListCellRegister(RegisterController registerController) {
        this.registerController = registerController;
    }

    @Override
    protected void updateItem(Player item, boolean empty) {
        super.updateItem(item, empty);

        if(empty){
            setGraphic(null);

        }
        else {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(4);
            gridPane.setPadding(new Insets(0,10,0,10));
            /*
            gridPane.getColumnConstraints().add(new ColumnConstraints(70)); // column 0 is 100 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(70)); // column 1 is 50 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(20)); // column 2 is 20 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(50)); // column 3 is 30 wide
            */

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(20);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(5);
            ColumnConstraints col4 = new ColumnConstraints();
            col3.setPercentWidth(20);
            gridPane.getColumnConstraints().addAll(col1,col2,col3,col4);

            Text name = new Text(item.getCognome());
            gridPane.add(name,0,0);

            Text team = new Text((item.getSquadra()));
            gridPane.add(team,1,0);

            Text cost = new Text(String.valueOf(item.getCosto()));
            gridPane.add(cost,2,0);

            JFXButton button = new JFXButton();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Clicked the player"+ item.getCognome());
                    registerController.removePlayer(item);
                }
            });
            //button.setPrefWidth(60.00);
            button.setPrefHeight(10.00);
            button.setText("REMOVE");
            button.getStyleClass().add("removeJFXButton");
            button.setButtonType(JFXButton.ButtonType.RAISED);

            gridPane.add(button,3,0);

            setGraphic(gridPane);
        }
    }

}
