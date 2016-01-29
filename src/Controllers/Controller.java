package Controllers;

import Constants.Communication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.ClientMain;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    // Reference to the main application
    private ClientMain clientApp;
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
    public void setMainApp(ClientMain clientApp) {
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
        if(ipText.getText()==null || ipText.getText().equals("")){
            ShowError();
        }
        else
            clientApp.changeApp(ipText.getText());
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
                /*loginButton.setDisable(newValue.trim().isEmpty() || pw.getText()== null || pw.getText().equals(""));
                user.getStyleClass().remove("redBorder");
                userError.setVisible(false);
                aut.setVisible(false);
                */
                DisableButtons(newValue,user,pw,ipText,userError);
            }
        });

        pw.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DisableButtons(newValue,pw,user,ipText,pwError);
            }
        });

        ipText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DisableButtons(newValue,ipText,pw,ipText,ipError);
            }
        });
    }


    public void DisableButtons(String newValue, TextField current, TextField other1, TextField other2, ImageView imageView){
        loginButton.setDisable(newValue.trim().isEmpty() || other1.getText()== null || other1.getText().equals("") || other2.getText()==null || other2.getText().equals(""));
        current.getStyleClass().remove("redBorder");
        imageView.setVisible(false);
        aut.setVisible(false);
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

    public void DoLogin(ActionEvent actionEvent) {
        Send(actionEvent);
    }
}
