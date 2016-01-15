package GraphicItem;

import Controllers.BaseController;
import Controllers.ControllerSecond;
import Controllers.MercatoController;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

/**
 * Created by Emanuele on 04/12/2015.
 */
public class ButtonCellCompra extends TableCell<Player,Boolean> {
    private Button cellButton = new Button("Compra");
    private BaseController baseController;
    private TableView tableView;
    //private int id;

    public ButtonCellCompra(BaseController baseController,TableView tableView) {
        this.baseController = baseController;
        this.tableView = tableView;
        //cellButton.setId(""+id);
        cellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                baseController.OnCompra((Player) getTableRow().getItem());
            }
        });
        //id++;
    }

    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            cellButton.getStyleClass().add("buttonCompra");
            setGraphic(cellButton);
        }
    }
}
