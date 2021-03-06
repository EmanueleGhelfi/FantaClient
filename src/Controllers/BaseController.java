package Controllers;

import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.text.Text;
import sample.Home;

import java.util.Vector;

/**
 * Created by Emanuele on 01/12/2015.
 */
public class BaseController {

    private Home home;


    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void goToRosa(Event event) {
        home.goToRosa();
    }

    public void goToClassifica(ActionEvent actionEvent) {
        home.goToClassifica();
    }

    public void goToAndamento(ActionEvent actionEvent) {
        home.goToAndamento();
    }

    public void goToMercato(ActionEvent actionEvent) {
        home.goToMercato();
    }

    public void OnCompra(Player item) {
    }

}
