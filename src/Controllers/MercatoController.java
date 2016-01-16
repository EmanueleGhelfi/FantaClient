package Controllers;

import Model.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import GraphicItem.ButtonCellCompra;
import GraphicItem.ListCellCustom;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Emanuele on 01/12/2015.
 */
public class MercatoController extends BaseController {
    @FXML
    ListView listPor;

    @FXML
    ListView listDef;

    @FXML
    ListView listCen;

    @FXML
    ListView listAtk;

    @FXML
    TableView tablePor;


    @FXML
    TableView tableDef;

    @FXML
    TableView tableCen;

    @FXML
    TableView tableAtk;

    @FXML
    Label moneyLabel;

    @FXML private Button sectionRosa;

    @FXML private Button sectionAndamento;

    @FXML private Button sectionClassifica;

    @FXML private Button sectionMercato;


    private ArrayList<Player> allPor;
    private ArrayList<Player> allDef;
    private ArrayList<Player> allCen;
    private ArrayList<Player> allAtk;

    private ArrayList<Player> myPor;
    private ArrayList<Player> myDef;
    private ArrayList<Player> myCen;
    private ArrayList<Player> myAtk;

    private ArrayList<Player> myTeam;

    private MercatoController mercatoController = this;

    private int money;

    //tells if the user has modified his team
    private boolean hasModified= false;

    public void initView(){

        sectionRosa.setFocusTraversable(false);

        sectionClassifica.setFocusTraversable(false);

        sectionMercato.setFocusTraversable(false);

        sectionAndamento.setFocusTraversable(false);
        if(myTeam==null){

            myTeam=getHome().getTeam();
            myPor=new ArrayList<>();
            myCen=new ArrayList<>();
            myDef=new ArrayList<>();
            myAtk=new ArrayList<>();

            for (int i =0 ; i< myTeam.size(); i++){
                switch (myTeam.get(i).getRuolo()){
                    case 'P':
                        myPor.add(myTeam.get(i));
                        break;
                    case 'D':
                        myDef.add(myTeam.get(i));
                        break;
                    case 'C':
                        myCen.add(myTeam.get(i));
                        break;
                    case 'A':
                        myAtk.add(myTeam.get(i));
                        break;
                }
            }

            //calcolaSoldi();
            this.money=getHome().getUser().getSoldi();
            moneyLabel.setText("Soldi: "+getHome().getUser().getSoldi());

            initList();

            if(allPor!=null) {
                initTable();
            }
            else {
                getHome().getAllPlayers();
            }


        }


    }

    private void calcolaSoldi() {
        int soldi=0;
        for (int i = 0; i<myPor.size();i++){
            soldi = soldi + myPor.get(i).getCosto();
        }
        for (int i = 0; i<myDef.size();i++){
            soldi = soldi + myDef.get(i).getCosto();
        }
        for (int i = 0; i<myCen.size();i++){
            soldi = soldi + myCen.get(i).getCosto();
        }
        for (int i = 0; i<myAtk.size();i++){
            soldi = soldi + myAtk.get(i).getCosto();
        }
        this.money = 250 - soldi;
    }

    public void initTable() {
        //Init team and table

        //init Por Table
        /*
        TableColumn nome = tableMercato.getVisibleLeafColumn(0);
        nome.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));

        TableColumn squadra = tableMercato.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));

        TableColumn costo = tableMercato.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player,Integer>("costo"));
        */

        //initTable(teams);
        FillTableWithArray(tablePor, allPor);
        FillTableWithArray(tableDef, allDef);
        FillTableWithArray(tableCen, allCen);
        FillTableWithArray(tableAtk, allAtk);

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
                return new ButtonCellCompra(mercatoController,table);
            }
        });

        table.setItems(FXCollections.observableArrayList(players));
    }

    private void initList() {
        listPor.setItems(FXCollections.observableArrayList(myPor));
        listDef.setItems(FXCollections.observableArrayList(myDef));
        listCen.setItems(FXCollections.observableArrayList(myCen));
        listAtk.setItems(FXCollections.observableArrayList(myAtk));

        listPor.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellCustom(mercatoController);
            }
        });

        listDef.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellCustom(mercatoController);
            }
        });

        listCen.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellCustom(mercatoController);
            }
        });

        listAtk.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellCustom(mercatoController);
            }
        });

                /*
         * Each row in a ListView should be 24px tall.  Also, we have to add an extra
         * two px to account for the borders of the ListView.
         */
        final int ROW_HEIGHT = 29;
        // This sets the initial height of the ListView:
        listPor.setPrefHeight(myPor.size() * ROW_HEIGHT + 2);
        listDef.setPrefHeight(myDef.size() * ROW_HEIGHT + 2);
        listCen.setPrefHeight(myCen.size() * ROW_HEIGHT + 2);
        listAtk.setPrefHeight(myAtk.size() * ROW_HEIGHT + 2);
    }

    public ArrayList<Player> getAllAtk() {
        return allAtk;
    }

    public void setAllAtk(ArrayList<Player> allAtk) {
        this.allAtk = allAtk;
    }

    public ArrayList<Player> getAllCen() {
        return allCen;
    }

    public void setAllCen(ArrayList<Player> allCen) {
        this.allCen = allCen;
    }

    public ArrayList<Player> getAllDef() {
        return allDef;
    }

    public void setAllDef(ArrayList<Player> allDef) {
        this.allDef = allDef;
    }

    public ArrayList<Player> getAllPor() {
        return allPor;
    }

    public void setAllPor(ArrayList<Player> allPor) {
        this.allPor = allPor;
    }

    public ArrayList<Player> getMyAtk() {
        return myAtk;
    }

    public void setMyAtk(ArrayList<Player> myAtk) {
        this.myAtk = myAtk;
    }

    public ArrayList<Player> getMyCen() {
        return myCen;
    }

    public void setMyCen(ArrayList<Player> myCen) {
        this.myCen = myCen;
    }

    public ArrayList<Player> getMyDef() {
        return myDef;
    }

    public void setMyDef(ArrayList<Player> myDef) {
        this.myDef = myDef;
    }

    public ArrayList<Player> getMyPor() {
        return myPor;
    }

    public void setMyPor(ArrayList<Player> myPor) {
        this.myPor = myPor;
    }

    public ArrayList<Player> getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(ArrayList<Player> myTeam) {
        this.myTeam = myTeam;
    }

    @Override
    public void OnCompra(Player item) {
        ArrayList players = new ArrayList();
        boolean allow= true;
        switch (item.getRuolo()){
            case ('P'):
                if(myPor.size()<=2 && !myPor.contains(item)) {
                    allow = true;
                }
                else allow=false;

                break;

            case ('D'):
                if(myDef.size()<=7 && !myDef.contains(item))
                    allow=true;
                else allow=false;

                break;

            case ('C'):
                if(myCen.size()<=7 && !myCen.contains(item))
                    allow=true;
                else allow=false;

                break;

            case ('A'):
                if(myAtk.size()<=5 && !myAtk.contains(item))
                    allow=true;
                else allow=false;

                break;

        }

        if(item.getCosto()<=money && allow){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Vuoi davvero comprare "+item.getCognome()+" per "+item.getCosto()+" fantamilioni?");
            alert.setContentText("Sei davvero sicuro?");
            this.hasModified=true;

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
        final int ROW_HEIGHT = 29;
        switch (item.getRuolo()){
            case ('P'):
                myPor.add(item);
                listPor.setItems(FXCollections.observableArrayList(myPor));
                listPor.setPrefHeight(myPor.size() * ROW_HEIGHT + 2);
                break;
            case ('D'):
                myDef.add(item);
                listDef.setItems(FXCollections.observableArrayList(myDef));
                listDef.setPrefHeight(myDef.size() * ROW_HEIGHT + 2);
                break;
            case ('C'):
                myCen.add(item);
                listCen.setItems(FXCollections.observableArrayList(myCen));
                listCen.setPrefHeight(myCen.size() * ROW_HEIGHT + 2);
                break;
            case('A'):
                myAtk.add(item);
                listAtk.setItems(FXCollections.observableArrayList(myAtk));
                listAtk.setPrefHeight(myAtk.size() * ROW_HEIGHT + 2);
                break;


        }

        subSoldi(item.getCosto());
    }


    public void removePlayer(Player item) {
        this.hasModified=true;
        final int ROW_HEIGHT = 29;
            switch (item.getRuolo()){
                case ('P'):
                    myPor.remove(item);
                    listPor.setItems(FXCollections.observableArrayList(myPor));
                    listPor.setPrefHeight(myPor.size() * ROW_HEIGHT + 2);
                    break;
                case ('D'):
                    myDef.remove(item);
                    listDef.setItems(FXCollections.observableArrayList(myDef));
                    listDef.setPrefHeight(myDef.size() * ROW_HEIGHT + 2);
                    break;
                case ('C'):
                    myCen.remove(item);
                    listCen.setItems(FXCollections.observableArrayList(myCen));
                    listCen.setPrefHeight(myCen.size() * ROW_HEIGHT + 2);
                    break;
                case('A'):
                    myAtk.remove(item);
                    listAtk.setItems(FXCollections.observableArrayList(myAtk));
                    listAtk.setPrefHeight(myAtk.size() * ROW_HEIGHT + 2);
                    break;


        }

        addSoldi(item.getCosto());
    }

    public void addSoldi(int toAdd){
        this.money = this.money+toAdd;
        moneyLabel.setText("Fantamilioni: "+ money);
    }

    public void subSoldi(int toSub){
        this.money = this.money-toSub;
        moneyLabel.setText("Fantamilioni: "+ money);
    }

    public void SendTeamToServer(ActionEvent actionEvent) {



        if(canSendTeam()) {
            hasModified=false;
            this.myTeam.clear();
            this.myTeam.addAll(myPor);
            this.myTeam.addAll(myDef);
            this.myTeam.addAll(myCen);
            this.myTeam.addAll(myAtk);
            getHome().setTeam(myTeam);
            getHome().getUser().setSoldi(money);
            getHome().sendModifiedTeamToServer();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Formazione inviata con successo al server!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione!");
            alert.setHeaderText("Impossibile inviare la formazione al server");
            alert.setContentText("La formazione potrebbe non essere completa!");

            alert.showAndWait();
        }
    }

    private boolean canSendTeam() {
        if(myPor.size()+myCen.size()+myDef.size()+myAtk.size()==25){
            return true;
        }
        else return false;
    }

    @Override
    public void goToAndamento(ActionEvent actionEvent) {
        if(hasModified){
            //Show the alert
            if(ShowAlert()){
                super.goToAndamento(actionEvent);
            }
            else {
                // Users remain in current section
            }
        }
        else {
            super.goToAndamento(actionEvent);
        }
    }

    private boolean ShowAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Stai lasciando la sezione mercato senza salvare le modifiche effettuate!");
        alert.setContentText("Continuando le tue modifiche non verranno effettuate, vuoi continuare?");
        ButtonType buttonTypeOne = new ButtonType("Lascia senza salvare");
        ButtonType buttonTypeTwo = new ButtonType("Annulla");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            // ... user chose "Lascia senza salvare"
            ResetAll();
            hasModified=false;
            return true;

        } else if (result.get() == buttonTypeTwo) {
            // ... user chose "Annulla"
            return false;
        }

        return false;
    }

    private void ResetAll() {
        myPor=new ArrayList<>();
        myCen=new ArrayList<>();
        myDef=new ArrayList<>();
        myAtk=new ArrayList<>();

        for (int i =0 ; i< myTeam.size(); i++){
            switch (myTeam.get(i).getRuolo()){
                case 'P':
                    myPor.add(myTeam.get(i));
                    break;
                case 'D':
                    myDef.add(myTeam.get(i));
                    break;
                case 'C':
                    myCen.add(myTeam.get(i));
                    break;
                case 'A':
                    myAtk.add(myTeam.get(i));
                    break;
            }
        }

        calcolaSoldi();
        moneyLabel.setText("Soldi: "+money);

        initList();
    }

    @Override
    public void goToClassifica(ActionEvent actionEvent) {
        if(hasModified){
            //Show the alert
            if(ShowAlert()){
                super.goToClassifica(actionEvent);
            }
            else {
                // Users remain in current section
            }

        }
        else {
            super.goToClassifica(actionEvent);
        }
    }

    @Override
    public void goToMercato(ActionEvent actionEvent) {
        //Do nothing, it's the same section
    }

    @Override
    public void goToRosa(ActionEvent actionEvent) {
        if(hasModified){
            //Show the alert
            if(ShowAlert()){
                super.goToRosa(actionEvent);
            }
            else {
                // Users remain in current section
            }

        }
        else {
            super.goToRosa(actionEvent);
        }
    }
}
