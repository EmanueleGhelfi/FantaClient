package Controllers;

import GraphicItem.ButtonCell;
import GraphicItem.ButtonCellCompra;
import GraphicItem.ListCellCustom;
import GraphicItem.ListCellRegister;
import Model.Player;
import Model.User;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.Second;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Emanuele on 15/12/2015.
 */
public class RegisterController extends BaseController {
    @FXML
    DatePicker date;

    @FXML
    JFXCheckBox confirm;

    @FXML
    JFXButton nextButton;

    @FXML
    JFXPasswordField password1;

    @FXML
    JFXPasswordField password2;

    @FXML
    JFXTextField email;

    @FXML
    JFXTextField username;

    @FXML
    JFXTextField teamName;

    @FXML
    ListView myDef;

    @FXML
    ListView myCen;

    @FXML
    ListView myAtk;

    @FXML
    ListView myPor;

    @FXML
    TableView listCen;

    @FXML
    TableView listPor;

    @FXML
    TableView listAtt;

    @FXML
    TableView listDef;

    @FXML
    Text moneyLabel;

    @FXML
    TabPane tabPane;

    @FXML
    JFXButton selectButton;

    /*
    @FXML
    Text fileLabel;
    */

    @FXML
    ImageView profileImage;


    @FXML
    ImageView errorUser;


    @FXML
    ImageView errorPassword;


    @FXML
    ImageView errorEmail;



    private Second clientApp;
    private RegisterController registerController;
    private Desktop desktop = Desktop.getDesktop();

    private int portnum=0;
    private int maxport=3;
    private int difnum=0;
    private int maxdif=8;
    private int cennum=0;
    private int maxcen=8;
    private int attnum=0;
    private int maxatt=6;

    private ObservableList<Player> teamList=null;

    //My player
    private ArrayList<Player> arrayPor;
    private ArrayList<Player> arrayDif;
    private ArrayList<Player> arrayCen;
    private ArrayList<Player> arrayAtk;

    private ArrayList<Player> myTeam;

    private int money;

    final int ROW_HEIGHT = 40;

    private File selectedFile;


    public void removePlayer(Player item) {
        System.out.println("STO RIMUOVENDO");

        switch (item.getRuolo()){
            case ('P'):
                arrayPor.remove(item);
                myPor.setItems(FXCollections.observableArrayList(arrayPor));
                //myPor.setPrefHeight(arrayPor.size() * ROW_HEIGHT + 2);
                myPor.refresh();
                break;
            case ('D'):
                arrayDif.remove(item);
                myDef.setItems(FXCollections.observableArrayList(arrayDif));
                //myDef.setPrefHeight(arrayDif.size() * ROW_HEIGHT + 2);
                break;
            case ('C'):
                arrayCen.remove(item);
                myCen.setItems(FXCollections.observableArrayList(arrayCen));
                //myCen.setPrefHeight(arrayCen.size() * ROW_HEIGHT + 2);
                break;
            case('A'):
                arrayAtk.remove(item);
                myAtk.setItems(FXCollections.observableArrayList(arrayAtk));
                //myAtk.setPrefHeight(arrayAtk.size() * ROW_HEIGHT + 2);
                break;


        }

        addSoldi(item.getCosto());
    }
    public void initView() {
        arrayAtk= new ArrayList<>();
        arrayCen= new ArrayList<>();
        arrayDif= new ArrayList<>();
        arrayPor= new ArrayList<>();
        registerController = this;

        errorEmail.setVisible(false);
        errorUser.setVisible(false);
        errorPassword.setVisible(false);
        teamList = FXCollections.observableList(new ArrayList<Player>());
        money=250;
        myAtk.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellRegister(registerController);
            }
        });

        myPor.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellRegister(registerController);
            }
        });

        myDef.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellRegister(registerController);
            }
        });

        myCen.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellRegister(registerController);
            }
        });

        // Do some validation (using the Java 8 lambda syntax).
        password2.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Eccomi");
            errorPassword.setVisible(!(password2.getText().equals("") || (password2.getText()).equals(password1.getText())));
            System.out.println(!(password2.getText().equals("") || (password2.getText()).equals(password1.getText())));
        });

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!confirm.isSelected()) {
                    errorEmail.setVisible(true);
                }
                else {
                    errorEmail.setVisible(false);
                }
            }
        });


    }



    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param clientApp
     */
    public void setMainApp(Second clientApp) {
        this.clientApp = clientApp;
    }


    public void setPor(ArrayList<Player> players){

        //initTable(teams);
        FillTableWithArray(listPor, players);



    }

    private void FillTableWithArray(TableView table, ArrayList<Player> players) {
        //init Table

        TableColumn nome = table.getVisibleLeafColumn(0);
        nome.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));

        TableColumn squadra = table.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));

        TableColumn costo = table.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player,Integer>("costo"));

        TableColumn compra = table.getVisibleLeafColumn(3);
        compra.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new ButtonCellCompra(registerController,table);
            }
        });

        table.setItems(FXCollections.observableArrayList(players));
    }


    public void setDef(ArrayList<Player> defensors) {
        FillTableWithArray(listDef,defensors);

    }

    public void setCen(ArrayList<Player> midfielders) {
        FillTableWithArray(listCen,midfielders);
    }

    public void setAtk(ArrayList<Player> strikers) {
        FillTableWithArray(listAtt,strikers);
    }

    public void SendTeam(ActionEvent actionEvent) {
        System.out.println("CREATE");
        String teamString = teamName.getText();
        String passwordString = password1.getText();
        String userName = username.getText();
        String textEmail = email.getText();
        LocalDate mydate = date.getValue();
        myTeam = new ArrayList<Player>();
        myTeam.addAll(arrayPor);
        myTeam.addAll(arrayDif);
        myTeam.addAll(arrayCen);
        myTeam.addAll(arrayAtk);
        if(myTeam.size()==(maxcen+maxdif+maxport+maxatt) && (teamString!="" && teamString!=null) && (passwordString!= "" && passwordString!=null) && (userName!= "" && userName!=null) && (textEmail!="" && textEmail!=null) && mydate!=null && selectedFile!=null){
            try {
                //clientApp.SendTeam(new User(mydate,textEmail,passwordString,myTeam,teamString,userName),selectedFile);
                User user = new User(mydate,textEmail,passwordString,myTeam,teamString,userName);
                user.setSoldi(money);
                clientApp.SendTeam(user,selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //Show Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile inviare i dati, ci sono dati mancanti!");

            alert.showAndWait();

            if(!confirm.isSelected()){
                errorEmail.setVisible(true);
            }
            /*
            try {
                clientApp.SendTeam(new User(mydate,textEmail,passwordString,myTeam,teamString,userName),selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

        }
    }

    @Override
    public void OnCompra(Player item) {
        ArrayList players = new ArrayList();
        boolean allow= true;
        switch (item.getRuolo()){
            case ('P'):
                if(arrayPor.size()<=2 && !arrayPor.contains(item)) {
                    allow = true;
                }
                else allow=false;

                break;

            case ('D'):
                if(arrayDif.size()<=7 && !arrayDif.contains(item))
                    allow=true;
                else allow=false;

                break;

            case ('C'):
                if(arrayCen.size()<=7 && !arrayCen.contains(item))
                    allow=true;
                else allow=false;

                break;

            case ('A'):
                if(arrayAtk.size()<=5 && !arrayAtk.contains(item))
                    allow=true;
                else allow=false;

                break;

        }

        if(item.getCosto()<=money && allow){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Vuoi davvero comprare "+item.getCognome()+" per "+item.getCosto()+" fantamilioni?");
            alert.setContentText("Sei davvero sicuro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                addPlayer(item);

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile comprare questo giocatore!");
            //alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();

        }
    }


    private void addPlayer(Player item) {

        switch (item.getRuolo()){
            case ('P'):
                arrayPor.add(item);
                myPor.setItems(FXCollections.observableArrayList(arrayPor));
                //myPor.setPrefHeight(arrayPor.size() * ROW_HEIGHT + 2);
                break;
            case ('D'):
                arrayDif.add(item);
                myDef.setItems(FXCollections.observableArrayList(arrayDif));
                //myDef.setPrefHeight(arrayDif.size() * ROW_HEIGHT + 2);
                break;
            case ('C'):
                arrayCen.add(item);
                myCen.setItems(FXCollections.observableArrayList(arrayCen));
                //myCen.setPrefHeight(arrayCen.size() * ROW_HEIGHT + 2);
                break;
            case('A'):
                arrayAtk.add(item);
                myAtk.setItems(FXCollections.observableArrayList(arrayAtk));
               // myAtk.setPrefHeight(arrayAtk.size() * ROW_HEIGHT + 2);
                break;


        }

        subSoldi(item.getCosto());
    }

    public void addSoldi(int toAdd){
        this.money = this.money+toAdd;
        moneyLabel.setText(""+ money);
    }

    public void subSoldi(int toSub){
        this.money = this.money-toSub;
        moneyLabel.setText(""+ money);
    }

    public void GoToNextTab(ActionEvent actionEvent) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(1); // select by index starting with 0
    }

    public void openFileChooser(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile!=null){
            //fileLabel.setText(selectedFile.getAbsolutePath());
            profileImage.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    RegisterController.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    public void ShowError() {
        errorUser.setVisible(true);
    }
}
