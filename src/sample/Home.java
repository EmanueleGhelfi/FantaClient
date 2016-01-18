package sample;/**
 * Created by Emanuele on 28/10/2015.
 */

import Constants.Communication;
import Controllers.*;
import Model.*;
import com.guigarage.flatterfx.FlatterFX;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

public class Home extends Application {

    private ClientClass client;
    private ArrayList<Player> goalkeepers;
    private ArrayList<Player> defensors;
    private ArrayList<Player> midfielders;
    private ArrayList<Player> strikers;
    private int money;
    private User user;
    /*********************************************** SimpleUser per rifare il login se server si disconnette *****************/
    private SimpleUser userToSend;
    /******************************************** Dialog for login ****************************/
    private javafx.scene.control.Dialog<Pair<String, String>> dialog;
    // Dice se il client deve riconnettersi al server oppure se è già connesso
    private boolean needToConnect=false;

    ///******************************************** User temporaneo per modificare i dati *****************************/
    private User userTemp;    // se approvato da server diventerà user

    /***************************************Boolean for sync user's data when modified ********************************/
    private boolean needToSyncHome;
    private boolean needToSyncProfile;
    private boolean needToSyncClassification;

    private String userName;
    private ArrayList<Player> team;
    //Classifica ultima giornata
    private ArrayList <SimpleTeam> lastDayteams = new ArrayList<>();
    //Titolari
    private ArrayList<Player> formazione;
    //Riserve
    private ArrayList<Player> bench;
    //Classifica generale
    private ArrayList <SimpleTeam> teams = new ArrayList<>();

    private File profileImage;

    private File selectedFile;

    /*********************************Informazioni su team ****************************************************/
    private InfoClass info;

    /******************************** Giornata da chiedere al server ********************************************/
    private int dayToAsk;

    //******************************* CONTROLLER *****************************************************************///
    private HomeController controller;
    private ClassificaController classificaController;
    private AndamentoController andamentoController;
    private MercatoController mercatoController;
    private EndGameController endGameController;
    private Stage primaryStage;


    /******************** All the FXML Resources ****************************************************************/
    public static final String ROSA = "home";
    //OLD
    //public static final String ROSA_FXML = "/FXML/home.fxml";
    public static final String ROSA_FXML = "/FXML/newHome.fxml";
    public static final String CLASSIFICA = "classifica";
    public static final String CLASSIFICA_FXML =
            "/FXML/classifica.fxml";
    public static final String MERCATO = "mercato";
    public static final String MERCATO_FXML =
            "/FXML/mercato.fxml";
    public static final String ANDAMENTO = "andamento";
    public static final String ANDAMENTO_FXML =
            "/FXML/andamento.fxml";
    // Container for all the screens
    private ScreensController mainContainer;
    //Scene
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<SimpleTeam> getLastDayteams() {
        return lastDayteams;
    }

    public void setLastDayteams(ArrayList<SimpleTeam> lastDayteams) {
        this.lastDayteams = lastDayteams;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        System.out.println("ECCOMI");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/home.fxml"));

        Group root = null;
        try {
            /************ LOAD EVERY SCREEN IN THE MAINCONTAINER*************************/

            mainContainer = new ScreensController();

            controller = (HomeController) mainContainer.loadScreen(Home.ROSA,
                    Home.ROSA_FXML);

            classificaController = (ClassificaController) mainContainer.loadScreen(Home.CLASSIFICA,
                    Home.CLASSIFICA_FXML);

            mercatoController = (MercatoController) mainContainer.loadScreen(Home.MERCATO,
                    Home.MERCATO_FXML);

            andamentoController = (AndamentoController) mainContainer.loadScreen(Home.ANDAMENTO,
                    Home.ANDAMENTO_FXML);

            /**************** SET SCREEN ROSA*********************************************************/
            mainContainer.setScreen(Home.ROSA);

            // Give the controller access to the main app.
             //controller = loader.getController();
            controller.setHome(this);
            controller.initView();
            //Parent root = loader.load(getClass().getResource("sample.fxml"));

            /*primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 950, 478));
            */

            // TODO: For icons
            primaryStage.getIcons().add(new Image("/Images/icon.png"));

            root = new Group();
            root.getChildren().addAll(mainContainer);
            scene = new Scene(root);



            primaryStage.setScene(scene);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            primaryStage.setX(screenSize.getWidth()/2-1200/2);
            primaryStage.setY(screenSize.getHeight()/2-700/2);
            // to avoid resize
            primaryStage.setResizable(false);




            //Add stylesheets
            //scene.getStylesheets().add(getClass().getResource("/CSS/mycss.css").toExternalForm());
            //scene.getStylesheets().add(getClass().getResource("/CSS/bootstrap.css").toExternalForm());
            //scene.getStylesheets().add(getClass().getResource("/CSS/personalcss.css").toExternalForm());

            primaryStage.show();
            HomeTask task = new HomeTask(this,client);
            new Thread(task).start();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    System.out.println("STO CHIUDENDO BOTTEGA");
                    Platform.exit();
                    System.exit(0);
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Home(ClientClass client, String user) {
        this.client = client;
        //this.money = 250;
        this.userName = user;
    }

    public int getDayToAsk() {
        return dayToAsk;
    }

    public void setDayToAsk(int dayToAsk) {
        this.dayToAsk = dayToAsk;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Player> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Player> team) {
        this.team = team;
    }

    public ArrayList<Player> getStrikers() {
        return strikers;
    }

    public void setStrikers(ArrayList<Player> strikers) {
        this.strikers = strikers;
    }

    public int getMoney() {
        return money;
    }

    public boolean isNeedToConnect() {
        return needToConnect;
    }

    public void setNeedToConnect(boolean needToConnect) {
        this.needToConnect = needToConnect;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Player> getMidfielders() {
        return midfielders;
    }

    public void setMidfielders(ArrayList<Player> midfielders) {
        this.midfielders = midfielders;
    }

    public ArrayList<Player> getGoalkeepers() {
        return goalkeepers;
    }

    public void setGoalkeepers(ArrayList<Player> goalkeepers) {
        this.goalkeepers = goalkeepers;
    }

    public ArrayList<Player> getDefensors() {
        return defensors;
    }

    public void setDefensors(ArrayList<Player> defensors) {
        this.defensors = defensors;
    }

    public ArrayList<SimpleTeam> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<SimpleTeam> teams) {
        this.teams = teams;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public User getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(User userTemp) {
        this.userTemp = userTemp;
    }

    public boolean isNeedToSyncClassification() {
        return needToSyncClassification;
    }

    public void setNeedToSyncClassification(boolean needToSyncClassification) {
        this.needToSyncClassification = needToSyncClassification;
    }

    public boolean isNeedToSyncHome() {
        return needToSyncHome;
    }

    public void setNeedToSyncHome(boolean needToSyncHome) {
        this.needToSyncHome = needToSyncHome;
    }

    public boolean isNeedToSyncProfile() {
        return needToSyncProfile;
    }

    public void setNeedToSyncProfile(boolean needToSyncProfile) {
        this.needToSyncProfile = needToSyncProfile;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void initTable() {
        System.out.println("Ciao, init table");
        System.out.println(team.toString());
        controller.initTable(team);
    }

    public SimpleUser getUserToSend() {
        return userToSend;
    }

    public void setUserToSend(SimpleUser userToSend) {
        this.userToSend = userToSend;
    }

    public ArrayList<Player> getFormazione() {
        return formazione;
    }

    public void setFormazione(ArrayList<Player> formazione) {
        this.formazione = formazione;
    }

    public ArrayList<Player> getBench() {
        return bench;
    }

    public void setBench(ArrayList<Player> bench) {
        this.bench = bench;
    }


    public void goToClassifica() {
       mainContainer.setScreen(CLASSIFICA);
        classificaController.setHome(this);
        classificaController.initView();
    }


/****************************************** SEND TEAM TO SERVER ***********************************************************************************/
    public void sendTeam(ArrayList<Player> formazione, ArrayList<Player> bench) {
        this.formazione = formazione;
        this.bench = bench;
        System.out.println(bench);
        System.out.println(formazione);
        client.getOut().println(Communication.SENDTITOLARI);
    }

    /************************************* CHIEDO CLASSIFICA AL SERVER ***********************************************************************///
    public void askForClassifica() {
        client.getOut().println(Communication.GETCLASSIFICA);
    }



    public void initClassifica() {
        classificaController.setTeams(teams);
        classificaController.CallInitClassifica();
        //client.getOut().println("GETGIORNATE");
        client.getOut().println(Communication.GETLASTDAY);
    }


public void goToRosa() {
    mainContainer.setScreen(ROSA);
    controller.initWelcomeText();

}

    public void goToAndamento() {
        andamentoController.setHome(this);
        andamentoController.initView(this.user);
        mainContainer.setScreen(ANDAMENTO);
    }

    public void goToMercato() {
        mercatoController.setHome(this);
        mercatoController.initView();
        mainContainer.setScreen(MERCATO);
    }

    public void getAllPlayers() {
        client.getOut().println(Communication.GETALLPLAYERS);
    }

    public void initTableMercato() {
        mercatoController.setAllPor(goalkeepers);
        mercatoController.setAllDef(defensors);
        mercatoController.setAllCen(midfielders);
        mercatoController.setAllAtk(strikers);
        mercatoController.initTable();
    }

    // Invia il team al server dopo aver modificato la formazione
    public void sendModifiedTeamToServer() {
        client.getOut().println(Communication.SENDMODIFIEDTEAM);
        controller.initTable(team);
        controller.clearAll();
        controller.clearPlayer();
    }

    public void changeLvVoti(String giornate, ArrayList<PlayerVoto> votiArray) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                classificaController.changeLvVoti(giornate,votiArray);
            }
        });
    }

    public void InitComboBoxVoti(int giornateInt) {
        classificaController.initComboBoxVoti(giornateInt);
    }

    public void askForVoti(int value) {
        this.dayToAsk=value;
        client.getOut().println(Communication.GETVOTI);
    }

    public void initLastDayList() {
        classificaController.setLastDayTeams(lastDayteams);
        classificaController.CallInitLastDayList();
        client.getOut().println(Communication.GETGIORNATE);

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void initWelcome() {
        //Do something
        controller.initWelcomeText();
    }

    public void EnableSendButton(String date) {
        controller.EnableSendButton(date);
    }

    public void downloadProfileImage() {
        client.getOut().println(Communication.GETIMAGE);
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                andamentoController.setProfileImage(profileImage);
            }
        });

        if(andamentoController.isNeedToDownloadResult()){
            //Ask for results to insert into xychart
            client.getOut().println(Communication.GETRESULTS);
        }

    }

    public void setResults(ArrayList<Results> results) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new Timeline(new KeyFrame(
                        javafx.util.Duration.millis(1000),
                        ae -> andamentoController.setResults(results)))
                        .play();
            }
        });

    }

    public void uploadPhoto() {
        client.getOut().println(Communication.SENDFILE);
    }



    public void SendModifiedDataToServer(User user) {
        this.userTemp = user;
        client.getOut().println(Communication.SENDMODIFIEDUSER);
    }

    public void OnApprovedModifiedData() {
        System.out.println("APPROVED");
        this.user=this.userTemp;
        this.needToSyncClassification=true;
        this.needToSyncHome=true;
        //this.needToSyncProfile=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .darkStyle()
                        .title("Success!")
                        .text("Informazioni Personali Modificate con Successo")
                        .show();

            }
        });


    }

    public void setInfo(InfoClass info) {
        this.info = info;
        andamentoController.setInfo(info);
    }

    public void ShowNotification() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications.create()
                        .darkStyle()
                        .title("Attenzione!")
                        .text("Sono presenti nuovi voti!")
                        .show();

            }
        });
    }

    public void ShowErrorPopup() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                andamentoController.ShowErrorPopup();
            }
        });

    }

    public void ShowLoginDialog() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Create the custom dialog.
                dialog = new Dialog<>();
                dialog.setTitle("Login Dialog");
                dialog.setHeaderText("ATTENZIONE, server disconnesso! Prova ad autenticarti");

                // Set the icon (must be included in the project).
                //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

                // Set the button types.
                ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                // Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Username");
                PasswordField password = new PasswordField();
                password.setPromptText("Password");
                TextField ipText = new TextField();
                ipText.setText("localhost");

                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(password, 1, 1);
                grid.add(new Label("Ip:"), 0, 2);
                grid.add(ipText, 1, 2);

                // Enable/Disable login button depending on whether a username was entered.
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

                // Do some validation (using the Java 8 lambda syntax).
                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty());
                });



                dialog.getDialogPane().setContent(grid);



                // Request focus on the username field by default.
                Platform.runLater(() -> username.requestFocus());

                // Convert the result to a username-password-pair when the login button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        //return new Pair<>(username.getText(), password.getText());
                        System.out.println("Username=" + username.getText() + ", Password=" + password.getText());
                        RetryLogin(username.getText(),password.getText(),ipText.getText());
                    }
                    //return null;
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent event) {
                        //doesn't close the dialog
                        System.out.println("CIAO");
                        event.consume();
                    }
                });

                result.ifPresent(usernamePassword -> {
                    //System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
                    //RetryLogin(usernamePassword.getKey(),usernamePassword.getValue());
                });

            }
        });


    }

    private void RetryLogin(String user, String pw, String ip) {
        if(needToConnect) {
            Home home = this;
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    client = new ClientClass(ip);

                    if (client.init()){
                        needToConnect=false;
                        client.getOut().println(Communication.Auth);
                        HomeTask task = new HomeTask(home, client);
                        Thread thread = new Thread(task);
                        thread.start();
                        userToSend = new SimpleUser(pw, user);
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ShowLoginDialog();
                            }
                        });

                    }
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.start();
        }
        else {
            userToSend = new SimpleUser(pw, user);
            client.getOut().println(Communication.Auth);
        }

    }

    public void dismissDialog() {
        dialog.hide();
        needToConnect=false;
    }

    public void showEndPopup() {
        Home home = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("END END");
                FXMLLoader loader = new FXMLLoader();
                //it was second
                loader.setLocation(getClass().getResource("/FXML/endGame.fxml"));
                Parent root = null;
                Stage secondaryStage = new Stage();
                try {
                    root = loader.load();


                    // Show the scene containing the root layout.
                    //Scene scene = new Scene(rootLayout);
                    //primaryStage.setScene(scene);

                    // Give the controller access to the main app.

                    endGameController = loader.getController();
                    endGameController.setHome(home);
                    //ndGameController.initView();
                    //Parent root = loader.load(getClass().getResource("sample.fxml"));
                    secondaryStage.setTitle("Hello World");
                    Scene scene = new Scene(root, 600, 400);
                    scene.getStylesheets().add(getClass().getResource("/CSS/registercss1.css").toExternalForm());
                    secondaryStage.setScene(scene);
                    //Handle click on x
                    secondaryStage.show();

                    endGameController.startAnimation();
                }
                catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });

    }
}
