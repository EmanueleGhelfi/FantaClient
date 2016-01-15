package Controllers;

import GraphicItem.ListCellCustom;
import GraphicItem.ListCellVoti;
import Model.Player;
import Model.PlayerVoto;
import Model.SimpleTeam;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import sample.Home;

import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Emanuele on 09/11/2015.
 */
public class ClassificaController extends BaseController {

    //private Home home;
    private ArrayList<SimpleTeam> teams;
    private ArrayList<SimpleTeam> lastDayTeams;
     @FXML
    TableView classifica;

    @FXML
    TableView lastDayTable;

    @FXML
    ChoiceBox cbGiornate;

    @FXML
    ListView lvVoti;

    @FXML
    ListView lvPanchina;

    @FXML
    Label labelVoti;

    @FXML
    Label labelTot;

    @FXML private Button sectionRosa;

    @FXML private Button sectionAndamento;

    @FXML private Button sectionClassifica;

    @FXML private Button sectionMercato;



    /*public void SetHomeApp(Home homeapp) {
        this.home = homeapp;
    }
    */

    private ClassificaController classificaController;

    public void initView() {
        this.classificaController = this;

        sectionRosa.setFocusTraversable(false);

        sectionClassifica.setFocusTraversable(false);

        sectionMercato.setFocusTraversable(false);

        sectionAndamento.setFocusTraversable(false);

        if(this.teams==null) {
            lvVoti.setCellFactory(new Callback<ListView, ListCell>() {
                @Override
                public ListCell call(ListView param) {
                    return new ListCellVoti(classificaController);
                }
            });
            lvPanchina.setCellFactory(new Callback<ListView, ListCell>() {
                @Override
                public ListCell call(ListView param) {
                    return new ListCellVoti(classificaController);
                }
            });
            askHomeForClassifica();
        }
        else {
            if(getHome().isNeedToSyncClassification()){
                askHomeForClassifica();
            }
        }
    }

    private void askHomeForClassifica() {
        getHome().askForClassifica();
    }

    public void initClassifica(TableView tableView, ArrayList<SimpleTeam> simpleTeams) {

        //this.teams = getHome().getTeams();


        //Init team and table
        TableColumn pos = tableView.getVisibleLeafColumn(0);
        pos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SimpleTeam, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SimpleTeam,String> param) {
                return new ReadOnlyObjectWrapper<String>(tableView.getItems().indexOf(param.getValue())+1+"");
            }
        });
        pos.setSortable(false);

        TableColumn squadra = tableView.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<SimpleTeam,String>("teamName"));

        TableColumn username = tableView.getVisibleLeafColumn(2);
        username.setCellValueFactory(new PropertyValueFactory<SimpleTeam,String>("utente"));

        TableColumn punteggio = tableView.getVisibleLeafColumn(3);
        punteggio.setCellValueFactory(new PropertyValueFactory<Player,Integer>("punteggio"));

        initTable(tableView,simpleTeams);
    }


    public void initTable(TableView tableView, ArrayList<SimpleTeam> teams) {
        tableView.setItems(FXCollections.observableArrayList(teams));
        tableView.setRowFactory(new Callback<TableView<SimpleTeam>, TableRow<SimpleTeam>>() {
            @Override
            public TableRow call(TableView param) {
                final TableRow<SimpleTeam> row = new TableRow<SimpleTeam>(){
                    @Override
                    protected void updateItem(SimpleTeam item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null && item.getUtente().equals(getHome().getUserName())){
                            getStyleClass().add("highlightRow");
                        }
                        else {
                            getStyleClass().remove("highlightRow");
                        }
                    }
                };
                return row;
            }
        });
    }

    public void initComboBoxVoti(int giornateInt) {
            ArrayList giornate = new ArrayList<Integer>();
            for (int i = giornateInt; i > 0; i--) {
                giornate.add(i);
            }
            cbGiornate.setItems(FXCollections.observableArrayList(giornate));
            cbGiornate.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    System.out.println("" + observable.getValue().intValue());
                    getHome().askForVoti((Integer) giornate.get(observable.getValue().intValue()));
                    labelVoti.setText("Analisi Prestazionale per la giornata " + (Integer) giornate.get(observable.getValue().intValue()));
                }
            });

            cbGiornate.getSelectionModel().selectFirst();
            labelVoti.setText("Analisi Prestazionale per la giornata " + giornateInt);
    }

    public void changeLvVoti(String giornate, ArrayList<PlayerVoto> votiArray) {
        ArrayList<PlayerVoto> newArray = new ArrayList<>();
        ArrayList<PlayerVoto> por = new ArrayList<>();
        ArrayList<PlayerVoto> dif = new ArrayList<>();
        ArrayList<PlayerVoto> cen = new ArrayList<>();
        ArrayList<PlayerVoto> att = new ArrayList<>();
        ArrayList<PlayerVoto> riserve = new ArrayList<>();

        float tot=0;
        for(int i =0; i< votiArray.size();i++){
            if(votiArray.get(i).isTitolare()){
                //tot = tot+votiArray.get(i).getVoto();
                switch (votiArray.get(i).getRuolo()){
                    case ('P'):
                        por.add(votiArray.get(i));
                        break;
                    case ('D'):
                        dif.add(votiArray.get(i));
                        break;
                    case ('A'):
                        att.add(votiArray.get(i));
                        break;
                    case ('C'):
                        cen.add(votiArray.get(i));
                        break;
                }

                if(!votiArray.get(i).isSostituito()){
                    System.out.println("Non sotituito : "+votiArray.get(i).getCognome()+" voto:"+votiArray.get(i).getVoto());
                    tot = tot+votiArray.get(i).getVoto();
                }
            }
            else {
                if(votiArray.get(i).isEntrato()){
                    System.out.println("Entrato : "+votiArray.get(i).getCognome()+ " voto:"+votiArray.get(i).getVoto());
                    tot = tot+votiArray.get(i).getVoto();
                }
                riserve.add(votiArray.get(i));
            }

        }

        newArray.addAll(por);
        newArray.addAll(dif);
        newArray.addAll(cen);
        newArray.addAll(att);
        //newArray.addAll(riserve);

        lvVoti.setItems(FXCollections.observableArrayList(newArray));
        lvPanchina.setItems(FXCollections.observableArrayList(riserve));
        labelTot.setText("Totale: "+tot);

    }

    //TODO: parte della classifica relativa a ultima giornata.
    public void initLastDayList() {
    }

    public void CallInitClassifica() {
        initClassifica(classifica,teams);
    }

    public void setTeams(ArrayList<SimpleTeam> teams) {
        this.teams = teams;
    }

    public void setLastDayTeams(ArrayList<SimpleTeam> lastDayTeams) {
        this.lastDayTeams = lastDayTeams;
    }

    public void CallInitLastDayList() {
        initClassifica(lastDayTable,lastDayTeams);
    }
}
