package Controllers;

/**
 * Created by Emanuele on 21/10/2015.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import GraphicItem.ButtonCell;
import Model.Player;
import sample.RegisterMain;
import Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ControllerSecond extends BaseController {

    private RegisterMain clientApp;
    @FXML private TableView por;
    @FXML private TableView dif;
    @FXML private TableView cen;
    @FXML private TableView att;
    @FXML private TableView team;
    @FXML private Text textMoney;
    @FXML private TextField teamName;
    @FXML private TextField userNameNew;
    @FXML private PasswordField password;
    @FXML private Text textError;
    private ControllerSecond controllerSecond;

    private int portnum=0;
    private int maxport=3;
    private int difnum=0;
    private int maxdif=8;
    private int cennum=0;
    private int maxcen=8;
    private int attnum=0;
    private int maxatt=6;

    private ObservableList<Player> teamList=null;


    public void Create(ActionEvent actionEvent) {
        System.out.println("CREATE");
        String teamString = teamName.getText();
        String passwordString = password.getText();
        String userName = userNameNew.getText();
        if(team.getItems().size()==(maxcen+maxdif+maxport+maxatt) && (teamString!="" && teamString!=null) && (passwordString!= "" && passwordString!=null) && (userName!= "" && userName!=null)){
            try {
                clientApp.SendTeam(new User(passwordString,new ArrayList<Player>(team.getItems()),teamString,userName),null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            textError.setText("ERRORE, CONTROLLA");
        }
    }

    public void initView(){
        controllerSecond = this;
        teamList = FXCollections.observableList(new ArrayList<Player>());
        TableColumn name = team.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));

        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = team.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        TableColumn costo = team.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player,Integer>("costo"));
        TableColumn ruolo = team.getVisibleLeafColumn(3);
        ruolo.setCellValueFactory(new PropertyValueFactory<Player,Character>("ruolo"));
        TableColumn modify = team.getVisibleLeafColumn(4);
       modify.setCellFactory(new Callback<TableColumn, TableCell>() {
           @Override
           public TableCell call(TableColumn param) {
               return new ButtonCell(controllerSecond,team);
           }
       });

        team.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        UpdateMoneyText();

    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param clientApp
     */
    public void setMainApp(RegisterMain clientApp) {
        this.clientApp = clientApp;
    }

    public void setPor(ArrayList<Player> players){

        ObservableList<Player> mArray = FXCollections.observableArrayList(players);

        TableColumn name = por.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));
        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = por.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        TableColumn costo = por.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player,Integer>("costo"));
        por.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Ciao : " +por.getSelectionModel().getSelectedItem());
                if ((por.getSelectionModel().getSelectedItem() != null) && portnum < maxport) {
                    Player selected = (Player) por.getSelectionModel().getSelectedItem();
                    int index = por.getSelectionModel().getSelectedIndex();
                    if(clientApp.getMoney()-selected.getCosto()>0) {
                        por.getItems().remove(index);
                        AddItemToTeam(selected);
                        SubMoney(selected.getCosto());
                        por.refresh();
                        portnum++;
                    }
                } else {
                    if (portnum >= maxport) {
                        int index = por.getSelectionModel().getSelectedIndex();
                    }
            }
        };
        });

        por.setItems(mArray);


        por.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }


    public void setDef(ArrayList<Player> defensors) {
        ObservableList<Player> array = FXCollections.observableArrayList(defensors);
        TableColumn name = dif.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));
        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = dif.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        TableColumn costo = dif.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player, Integer>("costo"));

        dif.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Ciao : " + dif.getSelectionModel().getSelectedItem());
                if ((dif.getSelectionModel().getSelectedItem() != null) && difnum < maxdif) {
                    Player selected = (Player) dif.getSelectionModel().getSelectedItem();
                    int index = dif.getSelectionModel().getSelectedIndex();
                    if(clientApp.getMoney()-selected.getCosto()>0) {
                        dif.getItems().remove(index);
                        AddItemToTeam(selected);
                        SubMoney(selected.getCosto());
                        dif.refresh();
                        difnum++;
                    }
                } else {
                    if (difnum >= maxdif) {
                        int index = dif.getSelectionModel().getSelectedIndex();
                    }
                }
            };
        });

        dif.setItems(array);

    }

    public void setCen(ArrayList<Player> midfielders) {
        ObservableList<Player> array = FXCollections.observableArrayList(midfielders);
        TableColumn name = cen.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));
        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = cen.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        TableColumn costo = cen.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player, Integer>("costo"));

        cen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Ciao : " +cen.getSelectionModel().getSelectedItem());
                if ((cen.getSelectionModel().getSelectedItem() != null) && cennum < maxcen) {
                    Player selected = (Player) cen.getSelectionModel().getSelectedItem();
                    int index = cen.getSelectionModel().getSelectedIndex();
                    if(clientApp.getMoney() - selected.getCosto() > 0) {
                        cen.getItems().remove(index);
                        AddItemToTeam(selected);
                        SubMoney(selected.getCosto());
                        cen.refresh();
                        cennum++;
                    }
                } else {
                    if (cennum >= maxcen) {
                        int index = cen.getSelectionModel().getSelectedIndex();
                    }
                }
            };
        });

        cen.setItems(array);
    }

    public void setAtk(ArrayList<Player> strikers) {
        ObservableList<Player> array = FXCollections.observableArrayList(strikers);
        TableColumn name = att.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));
        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = att.getVisibleLeafColumn(1);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        TableColumn costo = att.getVisibleLeafColumn(2);
        costo.setCellValueFactory(new PropertyValueFactory<Player, Integer>("costo"));

        att.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Ciao : " +att.getSelectionModel().getSelectedItem());
                if ((por.getSelectionModel().getSelectedItem() != null) && attnum < maxatt) {
                    Player selected = (Player) att.getSelectionModel().getSelectedItem();
                    int index = att.getSelectionModel().getSelectedIndex();
                    if(clientApp.getMoney() - selected.getCosto()>0) {
                        att.getItems().remove(index);
                        AddItemToTeam(selected);
                        SubMoney(selected.getCosto());
                        att.refresh();
                        attnum++;
                    }
                } else {
                    if (attnum >= maxatt) {
                        int index = att.getSelectionModel().getSelectedIndex();
                    }
                }
            };
        });

        att.setItems(array);
    }

    public void AddItemToTeam(Player player){
        teamList.add(player);
        team.setItems(teamList);
        team.refresh();
    }

    public void SubMoney(int money){
        clientApp.setMoney(clientApp.getMoney()-money);
        UpdateMoneyText();
    }

    public void AddMoney(int money){
        clientApp.setMoney(clientApp.getMoney()+money);
        UpdateMoneyText();
    }

    public void UpdateMoneyText(){
        textMoney.setText("Money: "+ clientApp.getMoney());
    }

    public void removeItemFromTeam(Player selectedItem) {
       // Player selectedItem = (Player) team.getItems().get(index);
        System.out.println(selectedItem);
        switch (selectedItem.getRuolo()){
            case ('P'):
                por.getItems().add(selectedItem);
                portnum--;
                por.getItems().sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getCognome().compareTo(o2.getCognome());
                    }
                });
                por.refresh();
                //por.sort();
                break;
            case ('D'):
                dif.getItems().add(selectedItem);
                difnum--;
                dif.getItems().sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getCognome().compareTo(o2.getCognome());
                    }
                });
                dif.refresh();
                break;
            case ('C'):
                cen.getItems().add(selectedItem);
                cennum--;
                cen.getItems().sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getCognome().compareTo(o2.getCognome());
                    }
                });
                cen.refresh();
                //cen.sort();
                break;
            case ('A'):
                att.getItems().add(selectedItem);
                attnum--;
                att.getItems().sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getCognome().compareTo(o2.getCognome());
                    }
                });
                att.refresh();
                //att.sort();
                break;
        }

        AddMoney(selectedItem.getCosto());
        team.getItems().remove(selectedItem);
        team.refresh();
    }




}


