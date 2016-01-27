package sample;

import Constants.Communication;
import Controllers.Controller;
import Model.ClientClass;
import Model.CommunicationInfo;
import Model.SimpleUser;
import Utils.CommunicationUtils;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientMain extends Application {
    private ClientClass client;
    private Controller controller;
    private String user;
    private String pw;
    private Stage primaryStage;
    private boolean active = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        //Instatiate FXML loader
        FXMLLoader loader = new FXMLLoader();
        //Set FXML
        loader.setLocation(getClass().getResource("/FXML/clientApp.fxml"));
        Parent root = loader.load();
        //Set icoon
        primaryStage.getIcons().add(new Image("/Images/icon.png"));
        //Set Title
        primaryStage.setTitle("Manu's FantaClient");
        //Set Scene
        primaryStage.setScene(new Scene(root, 600, 400));
        //Avoid resize
        primaryStage.setResizable(false);
        //Give the controller reference to the main app
        controller = loader.getController();
        //Give the main app reference to the controller
        controller.setMainApp(this);
        //Show the stage
        primaryStage.show();
    }

    private void GoToHome() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Home homeApp = new Home(client,user);
                try {
                    primaryStage.hide();
                    homeApp.start(new Stage());
                    active=false;
                    //Platform.exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void send(String toSend, String user, String pw, String ip) {
        client = new ClientClass(ip);
        boolean connected=false;
        this.user = user;
        this.pw = pw;

        Task initTask = new Task() {
            @Override
            protected Object call() throws Exception {
                if(client.init()) {

                    Task<String> task = new Task<String>() {
                        @Override
                        protected String call() throws Exception {
                            String res = null;
                            BufferedReader reader = client.getIn();
                            try {
                                while (true && active) {
                                    if (reader.ready() && (res = reader.readLine()) != null) {
                                        //return res;
                                        //changeText(res);
                                        Gson gson = new Gson();
                                        CommunicationInfo communicationInfo = gson.fromJson(res,CommunicationInfo.class);
                                        switch (communicationInfo.getCode()) {
                                            case (Communication.AUTHOK):
                                                System.out.println("Client Authorized...");
                                                GoToHome();
                                                //controller.setOutput("Authorized");
                                                break;
                                            case (Communication.AUTHNO):
                                                //TODO: IMPROVE
                                                controller.setOutput("AUTHNO");
                                                break;
                                        }
                                    } else {
                                        Thread.sleep(1000);
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            return res;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.start();

                    PrintWriter out = client.getOut();
                    Gson gson = new Gson();
                    String userString = gson.toJson(new SimpleUser(pw, user));
                    CommunicationUtils.SendCommunicationInfo(out,toSend,userString);
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.ShowError();
                        }
                    });

                }

                return null;
            }
        };
        Thread thread = new Thread(initTask);
        thread.start();

    }

    public void changeApp(String text){
       // controller.setOutput(text);
        client = new ClientClass("localhost");
        client.init();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                RegisterMain app2 = new RegisterMain(client);
                try {
                    app2.start(new Stage());
                    active=false;
                    primaryStage.close();
                    //Platform.exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }





    public static void main(String[] args) throws IOException {
        launch(args);
        //listen();


    }

    /*

    public void listen(){

        String fromServer;

        while (true){
            if((fromServer = client.getIn().readLine())!=null){
                controller.setOutput(fromServer);
            }
        }
    }
    */

}
