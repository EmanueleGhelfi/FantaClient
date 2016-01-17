package GraphicItem;

import Controllers.MercatoController;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Created by Emanuele on 22/10/2015.
 */
public class ListCellCustom extends javafx.scene.control.ListCell <Player>{

    private MercatoController mercatoController;

    public ListCellCustom(MercatoController mercatoController) {
        this.mercatoController = mercatoController;
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
            /*gridPane.getColumnConstraints().add(new ColumnConstraints(70)); // column 0 is 100 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(10)); // column 1 is 50 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(20)); // column 2 is 20 wide
            //gridPane.getColumnConstraints().add(new ColumnConstraints(50)); // column 3 is 30 wide
            */
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(50);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(10);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(40);
            gridPane.getColumnConstraints().addAll(col1,col2,col3);

            Text name = new Text(item.getCognome());
            gridPane.add(name,0,0);

            /*Text team = new Text((item.getSquadra()));
            gridPane.add(team,1,0);
            */

            Text cost = new Text(String.valueOf(item.getCosto()));
            gridPane.add(cost,1,0);

            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.print("Clicked the player"+ item.getCognome());
                    mercatoController.removePlayer(item);
                }
            });
            button.setPrefWidth(60.00);
            button.setPrefHeight(10.00);
            button.setText("REMOVE");
            button.getStyleClass().add("removeButton");
            getStyleClass().add("classHover");
            gridPane.add(button,2,0);

            setGraphic(gridPane);
        }
    }

}
