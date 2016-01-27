package Controllers;

import GraphicItem.BouncingImage;
import Model.User;
import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import sample.Home;
import Model.Player;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Emanuele on 28/10/2015.
 */
public class HomeController extends BaseController{

    //Titolari
    @FXML private Text Por;
    @FXML private Text Dif1;
    @FXML private Text Dif2;
    @FXML private Text Dif3;
    @FXML private Text Dif4;
    @FXML private Text Dif5;
    @FXML private Text Cen1;
    @FXML private Text Cen2;
    @FXML private Text Cen3;
    @FXML private Text Cen4;
    @FXML private Text Cen5;
    @FXML private Text Att1;
    @FXML private Text Att2;
    @FXML private Text Att3;
    @FXML private ChoiceBox choiceBox;
    @FXML private TableView Rosa;
    @FXML private Button sectionRosa;
    @FXML private Button sectionMercato;
    @FXML private Button sectionClassifica;
    @FXML private Button sectionAndamento;
    @FXML private ImageView imagePor;
    @FXML private ImageView imageDif1;
    @FXML private ImageView imageDif2;
    @FXML private ImageView imageDif3;
    @FXML private ImageView imageDif4;
    @FXML private ImageView imageDif5;
    @FXML private ImageView imageCen1;
    @FXML private ImageView imageCen2;
    @FXML private ImageView imageCen3;
    @FXML private ImageView imageCen4;
    @FXML private ImageView imageCen5;
    @FXML private ImageView imageAtt1;
    @FXML private ImageView imageAtt2;
    @FXML private ImageView imageAtt3;
    @FXML private BorderPane background;
    @FXML private Text welcomeText;
    @FXML private Button buttonSchiera;

    //Titolari
    private ArrayList<Player> players;
    //Panchina
    private ArrayList<Player> bench;
    //all
    private ArrayList<Player> team;


    //Panchina
    @FXML private ImageView imagePorRis;
    @FXML private ImageView imageDifRis;
    @FXML private ImageView imageDifRis2;
    @FXML private ImageView imageCenRis;
    @FXML private ImageView imageCenRis2;
    @FXML private ImageView imageAttRis;
    @FXML private ImageView imageAttRis2;

    @FXML private Text textPorRis;
    @FXML private Text textDifRis;
    @FXML private Text textDifRis2;
    @FXML private Text textCenRis;
    @FXML private Text textCenRis2;
    @FXML private Text textAttRis;
    @FXML private Text textAttRis2;
    @FXML private Text textTeamPorRis;
    @FXML private Text textTeamDifRis;
    @FXML private Text textTeamDifRis2;
    @FXML private Text textTeamCenRis;
    @FXML private Text textTeamCenRis2;
    @FXML private Text textTeamAttRis;
    @FXML private Text textTeamAttRis2;

    @FXML private Pane panePor;
    @FXML private Pane paneDif;
    @FXML private Pane paneDif2;
    @FXML private Pane paneCen;
    @FXML private Pane paneCen2;
    @FXML private Pane paneAtt;
    @FXML private Pane paneAtt2;

    @FXML private Text textProbForm;

    @FXML private Button buttonReset;
    @FXML private Button buttonAutoGen;


    //For module;
    private int totDif;
    private int totCen;
    private int totAtt;

    private final int porPanchina = 1;
    private final int difPanchina = 2;
    private final int cenPanchina = 2;
    private final int attPanchina = 2;

    private ArrayList<Text> textArray = new ArrayList<Text>();
    private ArrayList<Text> textPanchina = new ArrayList<Text>();
    private ArrayList<ImageView> imageArray = new ArrayList<ImageView>();
    private ArrayList<ImageView> imagePanchina = new ArrayList<ImageView>();
    private ArrayList<Text> textTeamPanchina = new ArrayList<Text>();



    //private Home homeApp;


    /*public void SetHomeApp(Home home){
        this.homeApp = home;
    }
    */

    private ArrayList<ScaleTransition> scaleArray;

    public void initView(){

        sectionRosa.setFocusTraversable(false);

        sectionClassifica.setFocusTraversable(false);

        sectionMercato.setFocusTraversable(false);

        sectionAndamento.setFocusTraversable(false);

        buttonAutoGen.setFocusTraversable(false);

        buttonSchiera.setFocusTraversable(false);

        buttonReset.setFocusTraversable(false);


        scaleArray = new ArrayList<>();

        //Init array of text and image
        textArray = new ArrayList<Text>();
        textArray.add(Por);
        textArray.add(Dif1);
        textArray.add(Dif2);
        textArray.add(Dif3);
        textArray.add(Dif4);
        textArray.add(Dif5);
        textArray.add(Cen1);
        textArray.add(Cen2);
        textArray.add(Cen3);
        textArray.add(Cen4);
        textArray.add(Cen5);
        textArray.add(Att1);
        textArray.add(Att2);
        textArray.add(Att3);

        imageArray = new ArrayList<ImageView>();
        imageArray.add(imagePor);
        imageArray.add(imageDif1);
        imageArray.add(imageDif2);
        imageArray.add(imageDif3);
        imageArray.add(imageDif4);
        imageArray.add(imageDif5);
        imageArray.add(imageCen1);
        imageArray.add(imageCen2);
        imageArray.add(imageCen3);
        imageArray.add(imageCen4);
        imageArray.add(imageCen5);
        imageArray.add(imageAtt1);
        imageArray.add(imageAtt2);
        imageArray.add(imageAtt3);

        textPanchina = new ArrayList<>();
        textPanchina.add(textPorRis);
        textPanchina.add(textDifRis);
        textPanchina.add(textDifRis2);
        textPanchina.add(textCenRis);
        textPanchina.add(textCenRis2);
        textPanchina.add(textAttRis);
        textPanchina.add(textAttRis2);

        textTeamPanchina = new ArrayList<>();
        textTeamPanchina.add(textTeamPorRis);
        textTeamPanchina.add(textTeamDifRis);
        textTeamPanchina.add(textTeamDifRis2);
        textTeamPanchina.add(textTeamCenRis);
        textTeamPanchina.add(textTeamCenRis2);
        textTeamPanchina.add(textTeamAttRis);
        textTeamPanchina.add(textTeamAttRis2);

        imagePanchina = new ArrayList<>();
        imagePanchina.add(imagePorRis);
        imagePanchina.add(imageDifRis);
        imagePanchina.add(imageDifRis2);
        imagePanchina.add(imageCenRis);
        imagePanchina.add(imageCenRis2);
        imagePanchina.add(imageAttRis);
        imagePanchina.add(imageAttRis2);




        players = new ArrayList<Player>();
        bench = new ArrayList<Player>();
        //imagePor.setImage(new Image("/Images/atalanta.png"));
        //Init module Box
        choiceBox.setItems(FXCollections.observableArrayList("4-4-2","4-3-3","3-4-3","3-5-2","4-5-1"));

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch (observable.getValue().intValue()){
                    case 0:
                        Show442();
                        break;
                    case 1:
                        Show433();
                        break;
                    case 2:
                        Show343();
                        break;
                    case 3:
                        Show352();
                        break;
                    case 4:
                        Show451();
                        break;
                }
            }
        });

        choiceBox.getSelectionModel().selectFirst();

        //Init team and table
        //Rosa.setItems(FXCollections.observableArrayList(team));
        TableColumn name = Rosa.getVisibleLeafColumn(0);
        name.setCellValueFactory(new PropertyValueFactory<Player,String>("cognome"));

        name.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        TableColumn squadra = Rosa.getVisibleLeafColumn(2);
        squadra.setCellValueFactory(new PropertyValueFactory<Player,String>("squadra"));
        /*TableColumn costo = Rosa.getVisibleLeafColumn(1);
        costo.setCellValueFactory(new PropertyValueFactory<Player,Integer>("costo"));
        */
        TableColumn ruolo = Rosa.getVisibleLeafColumn(1);
        ruolo.setCellValueFactory(new PropertyValueFactory<Player,Character>("ruolo"));

        Por.setVisible(true);

        //imagePor = new BouncingImage(new Image("/Images/spacer.gif"));
        initDragandDrop();

        initToolbar();

        //getHome().showEndPopup();


    }

    private void initToolbar() {

    }

    public void clearPlayer(){
        players.clear();
    }

    private void Show451() {
        imagePor.setImage(new Image("/Images/spacer.gif"));
        Dif2.setVisible(true);
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setVisible(true);
        Dif2.setText("Dif2");
        Dif1.setVisible(true);
        Dif1.setText("Dif1");
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setVisible(true);
        Dif3.setVisible(true);
        Dif3.setText("Dif3");
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setVisible(true);
        Dif4.setVisible(true);
        Dif4.setText("Dif4");
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setVisible(true);
        Cen1.setVisible(true);
        Cen1.setText("Cen1");
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setVisible(true);
        Cen2.setVisible(true);
        Cen2.setText("Cen2");
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setVisible(true);
        Cen3.setVisible(true);
        Cen3.setText("Cen3");
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setVisible(true);
        Cen4.setVisible(true);
        Cen4.setText("Cen4");
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setVisible(true);
        Cen5.setVisible(true);
        Cen5.setText("Cen5");
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setVisible(true);
        Att3.setVisible(true);
        Att3.setText("Att3");
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setVisible(true);


        Att2.setVisible(false);
        Att2.setText("Att2");
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setVisible(false);
        Dif5.setVisible(false);
        Dif5.setText("Dif5");
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setVisible(false);
        Att1.setVisible(false);
        Att1.setText("Att1");
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setVisible(false);
        Por.setText("Por");
        players.clear();
    }

    private void Show352() {
        imagePor.setImage(new Image("/Images/spacer.gif"));
        Dif2.setVisible(true);
        Dif2.setText("Dif2");
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setVisible(true);
        Dif5.setVisible(true);
        Dif5.setText("Dif5");
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setVisible(true);
        Dif3.setVisible(true);
        Dif3.setText("Dif3");
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setVisible(true);
        //Dif4.setVisible(true);
        //Dif4.setText("Dif4");
        Cen1.setVisible(true);
        Cen1.setText("Cen1");
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setVisible(true);
        Cen2.setVisible(true);
        Cen2.setText("Cen2");
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setVisible(true);
        Cen3.setVisible(true);
        Cen3.setText("Cen3");
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setVisible(true);
        Cen4.setVisible(true);
        Cen4.setText("Cen4");
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setVisible(true);
        Cen5.setVisible(true);
        Cen5.setText("Cen4");
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setVisible(true);
        Att1.setVisible(true);
        Att1.setText("Att1");
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setVisible(true);
        Att2.setVisible(true);
        Att2.setText("Att2");
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setVisible(true);

        Dif1.setVisible(false);
        Dif1.setText("Dif1");
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setVisible(false);
        Dif4.setVisible(false);
        Dif4.setText("Dif4");
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setVisible(false);
        Att3.setVisible(false);
        Att3.setText("Att3");
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setVisible(false);
        Por.setText("Por");
        players.clear();
    }

    private void Show343() {
        imagePor.setImage(new Image("/Images/spacer.gif"));
        Dif2.setVisible(true);
        Dif2.setText("Dif2");
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setVisible(true);
        Dif5.setVisible(true);
        Dif5.setText("Dif5");
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setVisible(true);
        Dif3.setVisible(true);
        Dif3.setText("Dif3");
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setVisible(true);
        //Dif4.setVisible(true);
        //Dif4.setText("Dif4");
        Cen1.setVisible(true);
        Cen1.setText("Cen1");
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setVisible(true);
        Cen2.setVisible(true);
        Cen2.setText("Cen2");
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setVisible(true);
        Cen3.setVisible(true);
        Cen3.setText("Cen3");
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setVisible(true);
        Cen4.setVisible(true);
        Cen4.setText("Cen4");
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setVisible(true);
        Att3.setVisible(true);
        Att3.setText("Att3");
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setVisible(true);
        Att1.setVisible(true);
        Att1.setText("Att1");
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setVisible(true);
        Att2.setVisible(true);
        Att2.setText("Att2");
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setVisible(true);

        Dif4.setVisible(false);
        Dif4.setText("Dif4");
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setVisible(false);
        Cen5.setVisible(false);
        Cen5.setText("Cen5");
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setVisible(false);
        Dif1.setVisible(false);
        Dif1.setText("false");
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setVisible(false);
        Por.setText("Por");
        players.clear();
    }

    private void initDragandDrop() {

       Rosa.setOnDragDone(new EventHandler<DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               imagePor.getStyleClass().remove("ImageZoom");
               imageDif1.getStyleClass().remove("ImageZoom");
               imageDif2.getStyleClass().remove("ImageZoom");
               imageDif3.getStyleClass().remove("ImageZoom");
               imageDif4.getStyleClass().remove("ImageZoom");
               imageDif5.getStyleClass().remove("ImageZoom");
               imageCen1.getStyleClass().remove("ImageZoom");
               imageCen2.getStyleClass().remove("ImageZoom");
               imageCen3.getStyleClass().remove("ImageZoom");
               imageCen4.getStyleClass().remove("ImageZoom");
               imageCen5.getStyleClass().remove("ImageZoom");
               imageAtt1.getStyleClass().remove("ImageZoom");
               imageAtt2.getStyleClass().remove("ImageZoom");
               imageAtt3.getStyleClass().remove("ImageZoom");
               stopTransition();

           }
       });

        Rosa.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = (Player) Rosa.getSelectionModel().getSelectedItem();
                if(selected!=null) {
                    Dragboard db = Rosa.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    System.out.println(selected.getCognome());
                    Gson gson = new Gson();
                    content.putString(gson.toJson(selected));
                    switch (selected.getRuolo()) {
                        case ('P'):
                            imagePor.getStyleClass().add("ImageZoom");
                            PulseTransition(imagePor);
                            PulseTransition(panePor);
                            break;
                        case ('D'):
                            imageDif1.getStyleClass().add("ImageZoom");
                            imageDif2.getStyleClass().add("ImageZoom");
                            imageDif3.getStyleClass().add("ImageZoom");
                            imageDif4.getStyleClass().add("ImageZoom");
                            imageDif5.getStyleClass().add("ImageZoom");
                            PulseTransition(imageDif1);
                            PulseTransition(imageDif2);
                            PulseTransition(imageDif3);
                            PulseTransition(imageDif4);
                            PulseTransition(imageDif5);
                            PulseTransition(paneDif);
                            PulseTransition(paneDif2);
                            break;
                        case ('C'):
                            imageCen1.getStyleClass().add("ImageZoom");
                            imageCen2.getStyleClass().add("ImageZoom");
                            imageCen3.getStyleClass().add("ImageZoom");
                            imageCen4.getStyleClass().add("ImageZoom");
                            imageCen5.getStyleClass().add("ImageZoom");
                            PulseTransition(imageCen1);
                            PulseTransition(imageCen2);
                            PulseTransition(imageCen3);
                            PulseTransition(imageCen4);
                            PulseTransition(imageCen5);
                            PulseTransition(paneCen);
                            PulseTransition(paneCen2);
                            break;
                        case ('A'):
                            imageAtt1.getStyleClass().add("ImageZoom");
                            imageAtt2.getStyleClass().add("ImageZoom");
                            imageAtt3.getStyleClass().add("ImageZoom");
                            PulseTransition(imageAtt1);
                            PulseTransition(imageAtt2);
                            PulseTransition(imageAtt3);
                            PulseTransition(paneAtt);
                            PulseTransition(paneAtt2);
                            break;
                    }
                    db.setContent(content);
                    event.consume();
                }
            }
        });

        Por.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOver(event, 'P');
            }
        });

        imagePor.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                /*
                Dragboard db = event.getDragboard();
                if(event.getDragboard().hasString()){
                    Player player;
                    Gson gson = new Gson();
                    player = gson.fromJson(event.getDragboard().getString(),Player.class);
                    if(player.getRuolo()=='P'){
                        // Por.setText(player.getCognome());
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
                */
                OnDragOver(event,'P');
            }
        });

        Por.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

               OnDragDropped(event,'P',"P",Por,imagePor,"Por");
            }
        });

        imagePor.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'P', "P", Por, imagePor, "Por");
            }
        });

        Dif1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        imageDif1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        Dif1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D1",Dif1,imageDif1,"Dif1");
            }
        });

        imageDif1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D1",Dif1,imageDif1,"Dif1");
            }
        });

        Dif2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

              OnDragOver(event,'D');
            }
        });

        Dif2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D2",Dif2,imageDif2,"Dif2");
            }
        });

        imageDif2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        imageDif2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D2",Dif2,imageDif2,"Dif2");
            }
        });

        Dif3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        Dif3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D3",Dif3,imageDif3,"Dif3");
            }
        });


        imageDif3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        imageDif3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event,'D',"D3",Dif3,imageDif3,"Dif3");
            }
        });

        Dif4.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        Dif4.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'D', "D4", Dif4, imageDif4, "Dif4");
            }
        });

        imageDif4.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        imageDif4.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'D', "D4", Dif4, imageDif4, "Dif4");
            }
        });

        Dif5.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        Dif5.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'D', "D5", Dif5, imageDif5, "Dif5");
            }
        });

        imageDif5.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'D');
            }
        });

        imageDif5.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'D', "D5", Dif5, imageDif5, "Dif5");
            }
        });

        Cen1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        Cen1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C1", Cen1, imageCen1, "Cen1");
            }
        });

        imageCen1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        imageCen1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C1", Cen1, imageCen1, "Cen1");
            }
        });

        Cen2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        Cen2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C2", Cen2, imageCen2, "Cen2");
            }
        });

        imageCen2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOver(event,'C');
            }
        });

        imageCen2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C2", Cen2, imageCen2, "Cen2");
            }
        });

        Cen3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        Cen3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C3", Cen3, imageCen3, "Cen3");
            }
        });


        imageCen3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        imageCen3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C3", Cen3, imageCen3, "Cen3");
            }
        });

        Cen4.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        Cen4.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C4", Cen4, imageCen4, "Cen4");
            }
        });

        imageCen4.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        imageCen4.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C4", Cen4, imageCen4, "Cen4");
            }
        });

        Cen5.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        Cen5.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C5", Cen5, imageCen5, "Cen5");
            }
        });

        imageCen5.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event,'C');
            }
        });

        imageCen5.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'C', "C5", Cen5, imageCen5, "Cen5");
            }
        });

        Att1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        Att1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A1", Att1, imageAtt1, "Att1");
            }
        });

        Att2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        Att2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A2", Att2, imageAtt2, "Att2");
            }
        });

        Att3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        Att3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A3", Att3, imageAtt3, "Att3");
            }
        });

        imageAtt1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        imageAtt1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A1", Att1, imageAtt1, "Att1");
            }
        });

        imageAtt2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        imageAtt2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A2", Att2, imageAtt2, "Att2");
            }
        });

        imageAtt3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOver(event, 'A');
            }
        });

        imageAtt3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragDropped(event, 'A', "A3", Att3, imageAtt3, "Att3");
            }
        });

        panePor.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event,'P');
            }
            });

        panePor.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                OnDragDroppedBench(event, 'P', "PP", textPorRis,textTeamPorRis,imagePorRis);
            }
        });

        paneDif.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event, 'D');
            }
        });

        paneDif.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragDroppedBench(event,'D',"PD1",textDifRis,textTeamDifRis,imageDifRis);
            }
        });


        paneDif2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event, 'D');
            }
        });

        paneDif2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragDroppedBench(event,'D',"PD2",textDifRis2,textTeamDifRis2,imageDifRis2);
            }
        });

        paneCen.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event,'C');
            }
        });

        paneCen.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragDroppedBench(event,'C',"PC1",textCenRis,textTeamCenRis,imageCenRis);
            }
        });


        paneCen2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target

                OnDragOverBench(event,'C');
            }
        });

        paneCen2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragDroppedBench(event,'C',"PC2",textCenRis2,textTeamCenRis2,imageCenRis2);
            }
        });

        paneAtt.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event,'A');
            }
        });

        paneAtt.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragDroppedBench(event,'A',"PA1",textAttRis,textTeamAttRis,imageAttRis);
            }
        });

        paneAtt2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
                OnDragOverBench(event,'A');
            }
        });

        paneAtt2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //Data is dragged over the target
               OnDragDroppedBench(event,'A',"PA2",textAttRis2,textTeamAttRis2,imageAttRis2);
            }
        });

        panePor.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getRuolo()=='P'){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                    AddToDragboard(panePor,selected);
                    event.consume();
                }
            }
        });

        paneDif.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PD1")){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                    AddToDragboard(paneDif,selected);
                    event.consume();
                }
                else {
                    System.out.println("Null");
                }
            }
        });

        paneDif2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PD2")){
                        selected = (Player) bench.get(i);
                    }
                }
                System.out.println("selected "+selected.toString());
                if(selected!=null) {
                    AddToDragboard(paneDif2,selected);
                    event.consume();
                }
            }
        });

        paneCen.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PC1")){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                    AddToDragboard(paneCen,selected);
                    event.consume();
                }
            }
        });

        paneCen2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PC2")){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                    AddToDragboard(paneCen2,selected);
                    event.consume();
                }
            }
        });

        paneAtt.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PA1")){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                    AddToDragboard(paneAtt,selected);
                    event.consume();
                }
            }
        });

        paneAtt2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Drag detected, start drag and drop gesture
                Player selected = null;
                for (int i = 0; i< bench.size(); i++){
                    if(bench.get(i).getPos().equals("PA2")){
                        selected = (Player) bench.get(i);
                    }
                }
                if(selected!=null) {
                   AddToDragboard(paneAtt2,selected);
                    event.consume();
                }
            }
        });

        for(int i = 0;i<imageArray.size();i++){
            final int finalI = i;
            imageArray.get(i).setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Player selected = null;
                    int first = imageArray.get(finalI).getImage().impl_getUrl().lastIndexOf('/');
                    int last =  imageArray.get(finalI).getImage().impl_getUrl().lastIndexOf('.');
                    String exactTeam = imageArray.get(finalI).getImage().impl_getUrl().substring(first+1,last);
                    for(int j = 0; j < players.size();j++){
                        if(textArray.get(finalI).getText().equals(players.get(j).getCognome()) && exactTeam.equals(players.get(j).getSquadra())){
                            selected=players.get(j);
                        }
                    }

                    if(selected!=null){
                        AddToDragboard(panePor,selected);
                        event.consume();
                    }
                }
            });
        }

    }

    private void OnDragDroppedBench(DragEvent event, char role, String position, Text textPlayer, Text textTeam, ImageView image) {
        //Data is dragged over the target
        System.out.println("DROP");
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (event.getDragboard().hasString()) {
            Player player;
            Gson gson = new Gson();
            player = gson.fromJson(event.getDragboard().getString(), Player.class);
            boolean present = false;
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getCognome().equals(player.getCognome())) {
                    present = true;
                }
            }
            if (player.getRuolo() == role && !present) {
                System.out.println("OKPOSSO");
                String name = textPlayer.getText();
                //Dif1.setText(player.getCognome());
                if (textPlayer.getText().equals("Panchinaro")) {
                    //add position in bench and add player to bench
                    player.setPos(position); //Portiere Panchina
                    if (bench.contains(player)) {
                        //cerco player in bench e cancello
                        SearchPlayerAndRemove(player);

                    } else {
                        bench.add(player);
                    }
                } else {
                    System.out.println("Sono in panchina");
                    for (int i = 0; i < bench.size(); i++) {
                        if (bench.get(i).getRuolo() == role && bench.get(i).getCognome().equals(name)) {
                            SearchAndChange(player, bench.get(i), position);
                            //bench.remove(i);
                            //add position in bench and add player to bench
                            /*
                            player.setPos(position);
                            bench.add(i, player);
                            */
                        }
                    }
                }
                textPlayer.setText(player.getCognome());
                textTeam.setText(capitalize(player.getSquadra()));
                success = true;
                image.setImage(new Image("/Images/" + player.getSquadra() + ".png"));
                // event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            } else {
                if (player.getRuolo() == role && present) {
                    String name = textPlayer.getText();
                    boolean presentInBench=false;
                    for (int i = 0; i < bench.size(); i++) {
                        if (bench.get(i).getRuolo() == role && bench.get(i).getCognome().equals(name)) {
                            ChangeBenchAndPlayer(bench.get(i), player);
                            presentInBench=true;
                        }
                    }
                    if(!presentInBench){
                        for(int i = 0;i<textArray.size();i++){
                            int first = imageArray.get(i).getImage().impl_getUrl().lastIndexOf('/');
                            int last =  imageArray.get(i).getImage().impl_getUrl().lastIndexOf('.');
                            String exactTeam = imageArray.get(i).getImage().impl_getUrl().substring(first+1,last);
                            if(textArray.get(i).getText().equals(player.getCognome())&& exactTeam.equals(player.getSquadra())){
                                textArray.get(i).setText(imageArray.get(i).getId());
                                imageArray.get(i).setImage(new Image("/Images/spacer.gif"));
                                players.remove(player);
                            }
                        }
                    }
                    player.setPos(position);
                    bench.add(player);
                    textPlayer.setText(player.getCognome());
                    textTeam.setText(capitalize(player.getSquadra()));
                    success = true;
                    image.setImage(new Image("/Images/" + player.getSquadra() + ".png"));
                }
            }
            event.setDropCompleted(success);
            event.consume();
        }
    }

    private void ChangeBenchAndPlayer(Player playerToTeam, Player playerToBench) {
        for(int i = 0;i<textArray.size();i++){
            int first = imageArray.get(i).getImage().impl_getUrl().lastIndexOf('/');
            int last =  imageArray.get(i).getImage().impl_getUrl().lastIndexOf('.');
            String exactTeam = imageArray.get(i).getImage().impl_getUrl().substring(first+1,last);
            System.out.println("URL: "+ exactTeam);
            if(textArray.get(i).getText().equals(playerToBench.getCognome()) && exactTeam.toLowerCase().equals(playerToBench.getSquadra()) ){
                textArray.get(i).setText(playerToTeam.getCognome());
                imageArray.get(i).setImage(new Image("/Images/" + playerToTeam.getSquadra() + ".png"));
                playerToTeam.setPos(playerToBench.getPos());
                players.remove(playerToBench);
                players.add(playerToTeam);
                bench.remove(playerToTeam);
            }
        }

    }

    private void SearchAndChange(Player player, Player playerInBench, String position) {
        System.out.println("SEARCH AND CHANGE");
        boolean present = false;
        int index=0;
        for (int i = 0; i < bench.size(); i++) {
            if (bench.get(i).getId() == player.getId()) {
                present = true;
                index=i;
            }
        }
        if (present) {
            System.out.println("panchina contiene il gioc");
            for (int i = 0; i < textPanchina.size(); i++) {
                if (textPanchina.get(i).getText().equals(bench.get(index).getCognome()) && textTeamPanchina.get(i).getText().toLowerCase().equals(bench.get(index).getSquadra())) {
                    textPanchina.get(i).setText(playerInBench.getCognome());
                    textTeamPanchina.get(i).setText(capitalize(playerInBench.getSquadra()));
                    imagePanchina.get(i).setImage(new Image("/Images/" + playerInBench.getSquadra() + ".png"));
                    playerInBench.setPos(bench.get(index).getPos());
                    System.out.println("position " +position);
                    bench.get(index).setPos(position);
                }
            }
        }
        else {
            bench.remove(playerInBench);
            player.setPos(position);
            bench.add(player);

        }
    }

    private void SearchPlayerAndRemove(Player player) {
        System.out.println("SEARCH AND REMOVE");

        for (int i = 0; i < textPanchina.size();i++){
            if(textPanchina.get(i).getText().equals(player.getCognome()) && textTeamPanchina.get(i).getText().toLowerCase().equals(player.getSquadra())){
                clearBench(textPanchina.get(i),textTeamPanchina.get(i),imagePanchina.get(i));
            }
        }

        bench.remove(player);
        bench.add(player);
    }

    private void OnDragOverBench(DragEvent event, char role) {
        Dragboard db = event.getDragboard();
        if(event.getDragboard().hasString()){
            Player player;
            Gson gson = new Gson();
            player = gson.fromJson(event.getDragboard().getString(),Player.class);
            //Search in the team for a player with the same name and role, if true you can't drop
            //boolean presentInTeam = false;


            for (int i = 0; i<players.size(); i++){
                if(players.get(i).getCognome().equals(player.getCognome())){
                    //presentInTeam=true;
                }
            }

            boolean presentInTeam = false;
            if(player.getRuolo()==role && !presentInTeam){
                // Por.setText(player.getCognome());
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }
    }

    private void AddToDragboard(Pane pane, Player selected) {
        Dragboard db = Rosa.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        System.out.println(selected.getCognome());
        Gson gson = new Gson();
        content.putString(gson.toJson(selected));
        switch (selected.getRuolo()) {
            case ('P'):
                imagePor.getStyleClass().add("ImageZoom");
                PulseTransition(imagePor);
                PulseTransition(panePor);
                break;
            case ('D'):
                imageDif1.getStyleClass().add("ImageZoom");
                imageDif2.getStyleClass().add("ImageZoom");
                imageDif3.getStyleClass().add("ImageZoom");
                imageDif4.getStyleClass().add("ImageZoom");
                imageDif5.getStyleClass().add("ImageZoom");
                PulseTransition(imageDif1);
                PulseTransition(imageDif2);
                PulseTransition(imageDif3);
                PulseTransition(imageDif4);
                PulseTransition(imageDif5);
                PulseTransition(paneDif);
                PulseTransition(paneDif2);
                break;
            case ('C'):
                imageCen1.getStyleClass().add("ImageZoom");
                imageCen2.getStyleClass().add("ImageZoom");
                imageCen3.getStyleClass().add("ImageZoom");
                imageCen4.getStyleClass().add("ImageZoom");
                imageCen5.getStyleClass().add("ImageZoom");
                PulseTransition(imageCen1);
                PulseTransition(imageCen2);
                PulseTransition(imageCen3);
                PulseTransition(imageCen4);
                PulseTransition(imageCen5);
                PulseTransition(paneCen);
                PulseTransition(paneCen2);
                break;
            case ('A'):
                imageAtt1.getStyleClass().add("ImageZoom");
                imageAtt2.getStyleClass().add("ImageZoom");
                imageAtt3.getStyleClass().add("ImageZoom");
                PulseTransition(imageAtt1);
                PulseTransition(imageAtt2);
                PulseTransition(imageAtt3);
                PulseTransition(paneAtt);
                PulseTransition(paneAtt2);
                break;


        }
        db.setContent(content);
    }

    private void stopTransition() {
        for(int i = 0; i< scaleArray.size();i++){
            scaleArray.get(i).stop();
            scaleArray.get(i).setToX(1);
            scaleArray.get(i).setToY(1);
            scaleArray.get(i).setCycleCount(1);
            scaleArray.get(i).play();
        }
        scaleArray.clear();
    }

    private void PulseTransition(Node node) {
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(500),node);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(node.getScaleX());
        scaleTransition.setFromY(node.getScaleY());
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.playFromStart();
        this.scaleArray.add(scaleTransition);
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public void Show442(){
        imagePor.setImage(new Image("/Images/spacer.gif"));
        Dif1.setVisible(true);
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setVisible(true);
        Dif1.setText("Dif1");
        Dif2.setVisible(true);
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setVisible(true);
        Dif2.setText("Dif2");
        Dif3.setVisible(true);
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setVisible(true);
        Dif3.setText("Dif3");
        Dif4.setVisible(true);
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setVisible(true);
        Dif4.setText("Dif4");
        Cen1.setVisible(true);
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setVisible(true);
        Cen1.setText("Cen1");
        Cen2.setVisible(true);
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setVisible(true);
        Cen2.setText("Cen2");
        Cen3.setVisible(true);
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setVisible(true);
        Cen3.setText("Cen3");
        Cen4.setVisible(true);
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setVisible(true);
        Cen4.setText("Cen4");
        Att1.setVisible(true);
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setVisible(true);
        Att1.setText("Att1");
        Att2.setVisible(true);
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setVisible(true);
        Att2.setText("Att2");

        Dif5.setVisible(false);
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setVisible(false);
        Dif5.setText("Dif5");
        Cen5.setVisible(false);
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setVisible(false);
        Cen5.setText("Cen5");
        Att3.setVisible(false);
        Att3.setText("Att3");
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setVisible(false);
        //Att3.setVisible(false);
        //Att3.setVisible(false);
        Por.setText("Por");
        players.clear();

    }

    public void Show433(){
        imagePor.setImage(new Image("/Images/spacer.gif"));
        Dif1.setVisible(true);
        Dif1.setText("Dif1");
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setVisible(true);
        Dif2.setVisible(true);
        Dif2.setText("Dif2");
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setVisible(true);
        Dif3.setVisible(true);
        Dif3.setText("Dif3");
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setVisible(true);
        Dif4.setVisible(true);
        Dif4.setText("Dif4");
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setVisible(true);
        Cen5.setVisible(true);
        Cen5.setText("Cen5");
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setVisible(true);
        Cen2.setVisible(true);
        Cen2.setText("Cen2");
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setVisible(true);
        Cen3.setVisible(true);
        Cen3.setText("Cen3");
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setVisible(true);
        Att3.setVisible(true);
        Att3.setText("Att3");
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setVisible(true);
        Att1.setVisible(true);
        Att1.setText("Att1");
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setVisible(true);
        Att2.setVisible(true);
        Att2.setText("Att2");
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setVisible(true);

        Dif5.setVisible(false);
        Dif5.setText("Dif5");
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setVisible(false);
        Cen1.setVisible(false);
        Cen1.setText("Cen1");
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setVisible(false);
        Cen4.setVisible(false);
        Cen4.setText("Cen4");
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setVisible(false);
        Por.setText("Por");
        players.clear();
    }



    public void initTable(ArrayList<Player> team) {

        int contDif=0;
        int contCen=0;
        int contAtt=0;
        this.team = team;
        ArrayList<Player> por = new ArrayList<>();
        ArrayList<Player> dif = new ArrayList<>();
        ArrayList <Player> cen = new ArrayList<>();
        ArrayList <Player> att = new ArrayList<>();



        for (int i = 0; i< team.size();i++){

            if(team.get(i).getPos()!=null) {
                if (team.get(i).getPos().equals("D1") || team.get(i).getPos().equals("D2") || team.get(i).getPos().equals("D3") || team.get(i).getPos().equals("D4") || team.get(i).getPos().equals("D5")) {
                    contDif++;
                }
                if (team.get(i).getPos().equals("C1") || team.get(i).getPos().equals("C2") || team.get(i).getPos().equals("C3") || team.get(i).getPos().equals("C4") || team.get(i).getPos().equals("C5")) {
                    contCen++;
                }
                if (team.get(i).getPos().equals("A1") || team.get(i).getPos().equals("A2") || team.get(i).getPos().equals("A3")) {
                    contAtt++;
                }
            }

            switch (team.get(i).getRuolo()){
                case 'P':
                    por.add(team.get(i));
                    break;
                case 'D':
                    dif.add(team.get(i));
                    break;
                case 'C':
                    cen.add(team.get(i));
                    break;
                case 'A':
                    att.add(team.get(i));
                    break;
            }
            //team.get(i).setSquadra(capitalize(team.get(i).getSquadra()));
        }

        team.clear();
        team.addAll(por);
        team.addAll(dif);
        team.addAll(cen);
        team.addAll(att);

        Rosa.setItems(FXCollections.observableArrayList(team));

        System.out.println("Ecco il modulo: "+ contDif +"-" +contCen + "-"+contAtt);
        //TODO: Improve
        if((contDif==4 || contDif==3 || contDif==5)&& (contCen==3 || contCen==4 || contCen==5) && (contAtt== 3 || contAtt==2 || contAtt==1))
            SelectModule(contDif,contCen,contAtt,team);

        }

    //Select the correct module
    private void SelectModule(int contDif, int contCen, int contAtt, ArrayList<Player> team) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                choiceBox.getSelectionModel().select(""+contDif+"-"+contCen+"-"+contAtt+"");
                initLastTeam(team);
                //imagePor = new BouncingImage(new Image("/Images/spacer.gif"));
            }
        });
    }

    private void initLastTeam(ArrayList<Player> team) {
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i).getPos() != null && team.get(i).getPos() != "0") {
                switch (team.get(i).getPos()) {
                    case "P":
                        players.add(team.get(i));
                        Por.setText(team.get(i).getCognome());
                        imagePor.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        //imagePor.setImage(new Image("/res/Images/"+ team.get(i).getSquadra() + ".png"));
                       // Image image = new Image(getClass().getResourceAsStream("/Images/"+ team.get(i).getSquadra() + ".png"));
                        //imagePor.setImage(image);
                        break;
                    case "D1":
                        players.add(team.get(i));
                        Dif1.setText(team.get(i).getCognome());
                        imageDif1.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "D2":
                        players.add(team.get(i));
                        Dif2.setText(team.get(i).getCognome());
                        imageDif2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "D3":
                        players.add(team.get(i));
                        Dif3.setText(team.get(i).getCognome());
                        imageDif3.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "D4":
                        players.add(team.get(i));
                        Dif4.setText(team.get(i).getCognome());
                        imageDif4.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "D5":
                        players.add(team.get(i));
                        Dif5.setText(team.get(i).getCognome());
                        imageDif5.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "C1":
                        players.add(team.get(i));
                        Cen1.setText(team.get(i).getCognome());
                        imageCen1.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "C2":
                        players.add(team.get(i));
                        Cen2.setText(team.get(i).getCognome());
                        imageCen2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "C3":
                        players.add(team.get(i));
                        Cen3.setText(team.get(i).getCognome());
                        imageCen3.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "C4":
                        players.add(team.get(i));
                        Cen4.setText(team.get(i).getCognome());
                        imageCen4.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "C5":
                        players.add(team.get(i));
                        Cen5.setText(team.get(i).getCognome());
                        imageCen5.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "A1":
                        players.add(team.get(i));
                        Att1.setText(team.get(i).getCognome());
                        imageAtt1.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "A2":
                        players.add(team.get(i));
                        Att2.setText(team.get(i).getCognome());
                        imageAtt2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "A3":
                        players.add(team.get(i));
                        Att3.setText(team.get(i).getCognome());
                        imageAtt3.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PP":
                        bench.add(team.get(i));
                        textPorRis.setText(team.get(i).getCognome());
                        textTeamPorRis.setText(capitalize(team.get(i).getSquadra()));
                        imagePorRis.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PD1":
                        bench.add(team.get(i));
                        textDifRis.setText(team.get(i).getCognome());
                        textTeamDifRis.setText(capitalize(team.get(i).getSquadra()));
                        imageDifRis.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PD2":
                        bench.add(team.get(i));
                        textDifRis2.setText(team.get(i).getCognome());
                        textTeamDifRis2.setText(capitalize(team.get(i).getSquadra()));
                        imageDifRis2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PC1":
                        bench.add(team.get(i));
                        textCenRis.setText(team.get(i).getCognome());
                        textTeamCenRis.setText(capitalize(team.get(i).getSquadra()));
                        imageCenRis.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PC2":
                        bench.add(team.get(i));
                        textCenRis2.setText(team.get(i).getCognome());
                        textTeamCenRis2.setText(capitalize(team.get(i).getSquadra()));
                        imageCenRis2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PA1":
                        bench.add(team.get(i));
                        textAttRis.setText(team.get(i).getCognome());
                        textTeamAttRis.setText(capitalize(team.get(i).getSquadra()));
                        imageAttRis.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;
                    case "PA2":
                        bench.add(team.get(i));
                        textAttRis2.setText(team.get(i).getCognome());
                        textTeamAttRis2.setText(capitalize(team.get(i).getSquadra()));
                        imageAttRis2.setImage(new Image("/Images/" + team.get(i).getSquadra() + ".png"));
                        break;

                }

            }
        }

        System.out.println("Lunghezza :"+players.size());
        System.out.println(players.toString());


    }


    /*********************************************** Send Team to Server *******************************************************/

    public void Schiera(ActionEvent actionEvent) {
        if(canSendTeam()) {
            System.out.println("" + players.size());
            getHome().sendTeam(players,bench);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Formazione schierata per la prossima giornata!");

            alert.showAndWait();
        }
        else {
            //Show error popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Attenzione, la formazione non è completa!");
            alert.setContentText(null);

            alert.showAndWait();
        }
    }

    // tells if the users can send team, if the team is correct
    private boolean canSendTeam() {
        if(players.size()==11 && bench.size()== 7)
            return true;
        else return false;
    }

    public void clearAll() {
        imagePor.setImage(new Image("/Images/spacer.gif"));
        imageDif1.setImage(new Image("/Images/spacer.gif"));
        imageDif2.setImage(new Image("/Images/spacer.gif"));
        imageDif3.setImage(new Image("/Images/spacer.gif"));
        imageDif4.setImage(new Image("/Images/spacer.gif"));
        imageDif5.setImage(new Image("/Images/spacer.gif"));
        imageCen1.setImage(new Image("/Images/spacer.gif"));
        imageCen2.setImage(new Image("/Images/spacer.gif"));
        imageCen3.setImage(new Image("/Images/spacer.gif"));
        imageCen4.setImage(new Image("/Images/spacer.gif"));
        imageCen5.setImage(new Image("/Images/spacer.gif"));
        imageAtt1.setImage(new Image("/Images/spacer.gif"));
        imageAtt2.setImage(new Image("/Images/spacer.gif"));
        imageAtt3.setImage(new Image("/Images/spacer.gif"));
        Dif1.setText("Dif1");
        Dif2.setText("Dif2");
        Dif3.setText("Dif3");
        Dif4.setText("Dif4");
        Cen1.setText("Cen1");
        Cen2.setText("Cen2");
        Cen3.setText("Cen3");
        Cen4.setText("Cen4");
        Att1.setText("Att1");
        Att2.setText("Att2");

        Dif5.setText("Dif5");
        Cen5.setText("Cen5");
        Att3.setText("Att3");
        Por.setText("Por");
        players.clear();

        //clear all bench
        clearBench(textPorRis,textTeamPorRis,imagePorRis);
        clearBench(textDifRis,textTeamDifRis,imageDifRis);
        clearBench(textDifRis2,textTeamDifRis2,imageDifRis2);
        clearBench(textCenRis,textTeamCenRis,imageCenRis);
        clearBench(textCenRis2,textTeamCenRis2,imageCenRis2);
        clearBench(textAttRis,textTeamAttRis,imageAttRis);
        clearBench(textAttRis2,textTeamAttRis2,imageAttRis2);

        bench.clear();
    }

    public void initWelcomeText() {
        welcomeText.setText("Benvenuto, "+getHome().getUser().getUserName()+"! Ecco la tua squadra: "+getHome().getUser().getTeamName()+"!");
    }


    //Enable send button if today is before date. date is the date of the next game
    public void EnableSendButton(String date) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateOfTheNextDay = format.parse(date);
            //TODO: rimettere
            /*if(today.after(dateOfTheNextDay)){
                buttonSchiera.setDisable(true);
            }
            */

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Reset text and Image, text1 is the name, text2 is the team, image is the image of the player
    public void clearBench(Text text1,Text text2, ImageView image){
        text1.setText("Panchinaro");
        text2.setText("Squadra");
        image.setImage(new Image("/Images/spacer.gif"));
    }

    //  code is the code of the role
    public void OnDragOver(DragEvent event,char code){
        Dragboard db = event.getDragboard();
        if(event.getDragboard().hasString()){
            Player player;
            Gson gson = new Gson();
            player = gson.fromJson(event.getDragboard().getString(),Player.class);
            //Search in the team for a player with the same name and role, if true you can't drop
            boolean present = false;
            for (int i = 0; i<players.size(); i++){
                if(players.get(i).getCognome().equals(player.getCognome()) && player.getId()==players.get(i).getId()){
                    present=true;
                }
            }
            if(player.getRuolo()==code && !present){
                // Por.setText(player.getCognome());
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }
    }

    // role is the code of the role and position is the correct position, placeholder is the text in the UI
    public void OnDragDropped(DragEvent event, char role, String position, Text text, ImageView image, String placeHolder){
        Dragboard db = event.getDragboard();
        boolean success=false;
        if(event.getDragboard().hasString()) {
            Player player;
            Gson gson = new Gson();
            player = gson.fromJson(event.getDragboard().getString(), Player.class);
            boolean present = false;
            for (int i = 0; i<players.size(); i++){
                if(players.get(i).getCognome().equals(player.getCognome()) && player.getId()==players.get(i).getId()){
                    present=true;
                }
            }
            if(player.getRuolo()==role && !present){
                String name = text.getText();
                //remove from the bench if is present in the bench
                
                if(text.getText().equals(placeHolder)){
                    player.setPos(position);
                    RemoveFromBench(player,role);
                    players.add(player);
                }
                else {
                    boolean presentInTeam = false;
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getRuolo() == role && players.get(i).getCognome().equals(name)) {
                            ChangePlayers(player,players.get(i));
                            presentInTeam=true;
                            players.remove(i);
                            player.setPos(position);
                            players.add(i, player);
                        }
                    }
                    if(!presentInTeam){
                        player.setPos(position);
                        players.add(player);
                    }
                }
                text.setText(player.getCognome());
                success=true;
                image.setImage(new Image("/Images/"+player.getSquadra()+".png"));
                // event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }


    /**
     *
     *
     * change playerToInsert and oldPlayer
     * @param playerToInsert is the player selected by DragAndDrop
     * @param oldPlayer is the old player in that position
     */
    private void ChangePlayers(Player playerToInsert, Player oldPlayer) {
        for(int i = 0; i < textPanchina.size();i++){
            if(textPanchina.get(i).getText().equals(playerToInsert.getCognome()) && textTeamPanchina.get(i).getText().toLowerCase().equals(playerToInsert.getSquadra())){
                textPanchina.get(i).setText(oldPlayer.getCognome());
                textTeamPanchina.get(i).setText(capitalize(oldPlayer.getSquadra()));
                imagePanchina.get(i).setImage(new Image("/Images/"+oldPlayer.getSquadra()+".png"));
                oldPlayer.setPos(playerToInsert.getPos());
                bench.remove(playerToInsert);
                bench.add(oldPlayer);
            }
        }
    }

    //Remove seleted player from bench and call clearbench
    private void RemoveFromBench(Player player, char role) {
        switch (role){
            case 'P':
                if(textPorRis.getText().equals(player.getCognome())){
                    clearBench(textPorRis,textTeamPorRis,imagePorRis);
                }
                break;
            case 'D':
                if(textDifRis.getText().equals(player.getCognome())){
                    clearBench(textDifRis,textTeamDifRis,imageDifRis);
                }
                else {
                    if(textDifRis2.getText().equals(player.getCognome())){
                        clearBench(textDifRis2,textTeamDifRis2,imageDifRis2);
                    }
                }
                break;
            case 'C':
                if(textCenRis.getText().equals(player.getCognome())){
                    clearBench(textCenRis,textTeamCenRis,imageCenRis);
                }
                else {
                    if(textCenRis2.getText().equals(player.getCognome())){
                        clearBench(textCenRis2,textTeamCenRis2,imageCenRis2);
                    }
                }
                break;
            case 'A':
                if(textAttRis.getText().equals(player.getCognome())){
                    clearBench(textAttRis,textTeamAttRis,imageAttRis);
                }
                else {
                    if(textAttRis2.getText().equals(player.getCognome())){
                        clearBench(textAttRis2,textTeamAttRis2,imageAttRis2);
                    }
                }
                break;
        }

        for(int i = 0; i< bench.size(); i++){
            if(bench.get(i).getCognome().equals(player.getCognome())){
                bench.remove(i);
            }
        }
    }

    public void resetTeam(ActionEvent actionEvent) {
        clearAll();
    }

    public void showProbForm(Event event) {
        String url = "http://www.gazzetta.it/Calcio/prob_form/";

        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void autogenTeam(ActionEvent actionEvent) {
        players.clear();
        bench.clear();
        int selectedModule = choiceBox.getSelectionModel().getSelectedIndex();
        switch (selectedModule){
            case 0:
                //Show442();
                totDif = 4;
                totCen = 4;
                totAtt = 2;
                break;
            case 1:
                totDif = 4;
                totCen = 3;
                totAtt = 3;
                break;
            case 2:
                totDif = 3;
                totCen = 4;
                totAtt = 3;
                break;
            case 3:
                totDif = 3;
                totCen = 5;
                totAtt = 2;
                break;
            case 4:
                totDif = 4;
                totCen = 5;
                totAtt = 1;
                break;
        }





        int max=0;
        int pos=0;
        int posInArray=0;

        ArrayList<Player> tempTeam = new ArrayList<>();
        tempTeam.addAll(team);

        //Search por
        //cerco gioc con costo max
        for (int j = 0; j<tempTeam.size();j++){
            if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='P'){
                max = tempTeam.get(j).getCosto();
                pos=j;
            }
        }

        //lo inserisco nella formazione e lo rimuovo dal team
        while(posInArray<textArray.size() && !textArray.get(posInArray).isVisible()){
            posInArray++;
        }

        //Set text and image
        textArray.get(posInArray).setText(tempTeam.get(pos).getCognome());
        imageArray.get(posInArray).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
        tempTeam.get(pos).setPos("P");
        players.add(tempTeam.get(pos));

        //remove player from temp team
        tempTeam.remove(pos);

        posInArray++;


        for(int i = 0; i< totDif; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='D'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //lo inserisco nella formazione e lo rimuovo dal team
            while(posInArray<textArray.size() && !textArray.get(posInArray).isVisible()){
                posInArray++;
            }

            //Set text and image
            textArray.get(posInArray).setText(tempTeam.get(pos).getCognome());
            imageArray.get(posInArray).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            //tempTeam.get(pos).setPos("D"+(i+1));
            tempTeam.get(pos).setPos("D"+((posInArray-1)%5+1));
            System.out.println(tempTeam.get(pos).getPos());
            players.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInArray++;
        }

        for(int i = 0; i< totCen; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='C'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //lo inserisco nella formazione e lo rimuovo dal team
            while(posInArray<textArray.size() && !textArray.get(posInArray).isVisible()){
                posInArray++;
            }

            //Set text and image
            textArray.get(posInArray).setText(tempTeam.get(pos).getCognome());
            imageArray.get(posInArray).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            //tempTeam.get(pos).setPos("C"+(i+1));
            tempTeam.get(pos).setPos("C"+((posInArray-1)%5+1));
            System.out.println(tempTeam.get(pos).getPos());
            players.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInArray++;
        }

        for(int i = 0; i< totAtt; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='A'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //lo inserisco nella formazione e lo rimuovo dal team
            while(posInArray<textArray.size() && !textArray.get(posInArray).isVisible()){
                posInArray++;
            }

            //Set text and image
            textArray.get(posInArray).setText(tempTeam.get(pos).getCognome());
            imageArray.get(posInArray).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            //tempTeam.get(pos).setPos("A"+(i+1));
            tempTeam.get(pos).setPos("A"+((posInArray-1)%5+1));
            System.out.println(tempTeam.get(pos).getPos());
            players.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInArray++;
        }

        int posInPanchina = 0;
        //per pos panchina
        int trovati = 0;
        //Generate Panchina
        for(int i = 0; i< porPanchina; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='P'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //Set text and image
            textPanchina.get(posInPanchina).setText(tempTeam.get(pos).getCognome());
            textTeamPanchina.get(posInPanchina).setText(capitalize(tempTeam.get(pos).getSquadra()));
            imagePanchina.get(posInPanchina).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            if(trovati==0){
                tempTeam.get(pos).setPos("PP");
            }
            else {
            }
            bench.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInPanchina++;
            trovati++;
        }

        trovati=0;
        for(int i = 0; i< difPanchina; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='D'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //Set text and image
            textPanchina.get(posInPanchina).setText(tempTeam.get(pos).getCognome());
            textTeamPanchina.get(posInPanchina).setText(capitalize(tempTeam.get(pos).getSquadra()));
            imagePanchina.get(posInPanchina).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            if(trovati==0){
                tempTeam.get(pos).setPos("PD1");
            }
            else {
                tempTeam.get(pos).setPos(("PD2"));
            }
            bench.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInPanchina++;
            trovati++;
        }

        trovati=0;
        for(int i = 0; i< cenPanchina; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='C'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //Set text and image
            textPanchina.get(posInPanchina).setText(tempTeam.get(pos).getCognome());
            textTeamPanchina.get(posInPanchina).setText(capitalize(tempTeam.get(pos).getSquadra()));
            imagePanchina.get(posInPanchina).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            if(trovati==0){
                tempTeam.get(pos).setPos("PC1");
            }
            else {
                tempTeam.get(pos).setPos(("PC2"));
            }
            bench.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInPanchina++;
            trovati++;
        }

        trovati=0;
        for(int i = 0; i< attPanchina; i++){
            max=0;
            pos=0;
            //cerco gioc con costo max
            for (int j = 0; j<tempTeam.size();j++){
                if(tempTeam.get(j).getCosto()>max && tempTeam.get(j).getRuolo()=='A'){
                    max = tempTeam.get(j).getCosto();
                    pos=j;
                }
            }

            //Set text and image
            textPanchina.get(posInPanchina).setText(tempTeam.get(pos).getCognome());
            textTeamPanchina.get(posInPanchina).setText(capitalize(tempTeam.get(pos).getSquadra()));
            imagePanchina.get(posInPanchina).setImage(new Image("/Images/"+tempTeam.get(pos).getSquadra()+".png"));
            if(trovati==0){
                tempTeam.get(pos).setPos("PA1");
            }
            else {
                tempTeam.get(pos).setPos(("PA2"));
            }
            bench.add(tempTeam.get(pos));

            //remove player from temp team
            tempTeam.remove(pos);

            posInPanchina++;
            trovati++;
        }



    }

    public void showUnderline(Event event) {
        textProbForm.setUnderline(true);
    }

    public void hideUnderline(Event event) {
        textProbForm.setUnderline(false);
    }

    public void scaleTransition(Event event) {
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(200), (Node)event.getTarget());
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(((Node)event.getTarget()).getScaleX());
        scaleTransition.setFromY(((Node)event.getTarget()).getScaleY());
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.playFromStart();
    }

    public void scaleTrasitionOut(Event event) {
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(200), (Node)event.getTarget());
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(((Node)event.getTarget()).getScaleX());
        scaleTransition.setFromY(((Node)event.getTarget()).getScaleY());
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.playFromStart();
    }

    public void ApplyScaleTransition(ImageView image){
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(200), image);
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(image.getScaleX());
        scaleTransition.setFromY(image.getScaleY());
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.playFromStart();
    }

    public void RemoveScaleTransition(ImageView image){
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(200), image);
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setFromX(image.getScaleX());
        scaleTransition.setFromY(image.getScaleY());
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.playFromStart();
    }

    @Override
    public void goToRosa(Event event) {
        //Does nothing
    }
}

