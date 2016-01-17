package Controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

/**
 * Created by Emanuele on 10/12/2015.
 */
public class NavController extends BaseController {
    @FXML
    HBox child;

    @Override
    public void goToAndamento(ActionEvent actionEvent) {
        super.goToAndamento(actionEvent);

    }

    @Override
    public void goToClassifica(ActionEvent actionEvent) {
        super.goToClassifica(actionEvent);
    }

    @Override
    public void goToMercato(ActionEvent actionEvent) {
        super.goToMercato(actionEvent);
    }

    @Override
    public void goToRosa(Event actionEvent) {
        super.goToRosa(actionEvent);
    }
}
