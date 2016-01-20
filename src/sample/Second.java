package sample;/**
 * Created by Emanuele on 21/10/2015.
 */

import Constants.Communication;
import Controllers.ControllerSecond;
import Controllers.RegisterController;
import Model.ClientClass;
import Model.Player;
import Model.User;
import Utils.CommunicationUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Second extends Application {

    private ClientClass client;
    private ArrayList<Player> goalkeepers;
    private ArrayList<Player> defensors;
    private ArrayList<Player> midfielders;
    private ArrayList<Player> strikers;
    private boolean por;
    private boolean def;
    private boolean cen;
    private boolean atk;
    private int money = 350;
    private User newUser;
    private Stage primaryStage;
    private boolean active;
    private File selectedFile;
    private RegisterController controller;

    public static void main(String[] args) {
        launch(args);
    }


    public Second(ClientClass client) {
        this.client = client;
        this.money = 250;
        this.active=true;
    }

    public ClientClass getClient() {
        return client;
    }

    public void setClient(ClientClass client) {
        this.client = client;
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("ECCOMI");
        FXMLLoader loader = new FXMLLoader();
        //it was second
        loader.setLocation(getClass().getResource("/FXML/register.fxml"));
        Parent root = null;
        this.primaryStage = primaryStage;
        try {
            root = loader.load();


        // Show the scene containing the root layout.
        //Scene scene = new Scene(rootLayout);
        //primaryStage.setScene(scene);

        // Give the controller access to the main app.

        controller = loader.getController();
        controller.setMainApp(this);
        controller.initView();
        //Parent root = loader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root,1300,1600);
        scene.getStylesheets().add(getClass().getResource("/CSS/registercss1.css").toExternalForm());
        primaryStage.setScene(scene);
        //Handle click on x
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                }

            });
        primaryStage.show();
            RegisterTask task = new RegisterTask(this,client.getOut(),client.getIn(),client);
            new Thread(task).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void GoToHome() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Home homeApp = new Home(client,newUser.getUserName());
                active=false;
                try {
                    homeApp.start(primaryStage);
                    active=false;
                    //Platform.exit();
                } catch (Exception e) {
                    System.out.println("EXC");
                    e.printStackTrace();
                }
            }
        });
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void SendTeam(User newUser, File file) throws IOException {
        this.newUser = newUser;
        //this.newUser.setSoldi(money);
        this.selectedFile=file;
        //client.getOut().println(Communication.SENDTEAM);
        Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        CommunicationUtils.SendCommunicationInfo(client.getOut(),Communication.SENDTEAM,newUserString);
        //client.getOut().println("SENDFILE");

    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setPor(ArrayList<Player> goalkeepers) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setPor(goalkeepers);
            }
        });
    }

    public void setDef(ArrayList<Player> defensors) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setDef(defensors);
            }
        });
    }

    public void setCen(ArrayList<Player> midfielders) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setCen(midfielders);
            }
        });
    }

    public void setAtt(ArrayList<Player> strikers) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setAtk(strikers);
            }
        });
    }


    public void ShowError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Impossibile registrarsi!");
        alert.setContentText("Lo Username potrebbe essere già presente, cambia Username e riprova");
        alert.showAndWait();
        controller.ShowError();
    }
}
