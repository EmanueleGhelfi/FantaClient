package GraphicItem;

import Controllers.ControllerSecond;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

/**
 * Created by Emanuele on 27/10/2015.
 */
public class ButtonCell extends TableCell<Player,Boolean> {

    final Button cellButton = new Button("Remove");
    private ControllerSecond controllerSecond;
    private TableView tableView;
    //private int id;

    public ButtonCell(ControllerSecond controllerSecond,TableView tableView) {
        this.controllerSecond = controllerSecond;
        this.tableView = tableView;
        //cellButton.setId(""+id);
        cellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controllerSecond.removeItemFromTeam((Player) getTableRow().getItem());
            }
        });
        //id++;
    }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }
