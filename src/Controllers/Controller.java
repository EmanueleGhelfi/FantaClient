package Controllers;

import Constants.Communication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.ClientApp;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    // Reference to the main application
    private ClientApp clientApp;
    //FXML elements
    @FXML private TextField user;
    @FXML private PasswordField pw;
    @FXML private Text aut;
    @FXML private TextField ipText;
    @FXML private Button loginButton;
    @FXML private Button createButton;
    @FXML private ImageView pwError;
    @FXML private ImageView userError;
    @FXML private ImageView ipError;
    @FXML private ProgressIndicator progressIndicator;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param clientApp
     */
    public void setMainApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    public void Send(ActionEvent actionEvent) {
        //clientApp.changeText("CIAO");
        if (!user.getText().equals("") && !pw.getText().equals("") && !(ipText.getText().equals(""))) {
            progressIndicator.setVisible(true);
            loginButton.setDisable(true);
            createButton.setDisable(true);
            user.setDisable(true);
            pw.setDisable(true);
            ipText.setDisable(true);
            clientApp.send(Communication.Auth, user.getText(), pw.getText(), ipText.getText());
        }
        else {
            System.out.println("Non mando");
            if (user.getText().equals(""))
                user.getStyleClass().add("redBorder");
            if (pw.getText().equals(""))
                pw.getStyleClass().add("redBorder");
            if (ipText.getText().equals(""))
                ipText.getStyleClass().add("redBorder");
        }
    }

    public void setOutput(String out){
        progressIndicator.setVisible(false);
        aut.setVisible(true);
        user.getStyleClass().add("redBorder");
        pw.getStyleClass().add("redBorder");
        userError.setVisible(true);
        pwError.setVisible(true);
        loginButton.setDisable(false);
        createButton.setDisable(false);
        user.setDisable(false);
        pw.setDisable(false);
        ipText.setDisable(false);

    }

    public void Create(ActionEvent actionEvent) {
        clientApp.changeApp("change");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userError.setVisible(false);
        pwError.setVisible(false);
        ipError.setVisible(false);
        aut.setVisible(false);
        progressIndicator.setVisible(false);
        loginButton.setDisable(true);

        user.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loginButton.setDisable(newValue.trim().isEmpty());
                user.getStyleClass().remove("redBorder");
                userError.setVisible(false);
                aut.setVisible(false);
            }
        });

        pw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //loginButton.setDisable(newValue.trim().isEmpty());
                pw.getStyleClass().remove("redBorder");
                pwError.setVisible(false);
                aut.setVisible(false);
            }
        });

        ipText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //loginButton.setDisable(newValue.trim().isEmpty());
                ipText.getStyleClass().remove("redBorder");
                ipError.setVisible(false);
                aut.setVisible(false);
            }
        });
    }

    public void ShowError() {
        progressIndicator.setVisible(false);
        ipText.getStyleClass().add("redBorder");
        ipError.setVisible(true);
        aut.setVisible(true);
        loginButton.setDisable(false);
        createButton.setDisable(false);
        user.setDisable(false);
        pw.setDisable(false);
        ipText.setDisable(false);

    }
}
