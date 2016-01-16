package Controllers;

import GraphicItem.IntegerStringConverter;
import Model.InfoClass;
import Model.Results;
import Model.User;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.*;
import org.controlsfx.control.action.Action;

import javax.management.Notification;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Emanuele on 01/12/2015.
 */
public class AndamentoController extends BaseController {

    @FXML private Text teamName;

    @FXML private Text dataNascita;

    @FXML private Text email;

    @FXML private Text username;

    @FXML private ImageView profileImageView;

    @FXML private LineChart teamChart;

    @FXML private Button sectionRosa;

    @FXML private Button sectionAndamento;

    @FXML private Button sectionClassifica;

    @FXML private Button sectionMercato;

    @FXML private Button doneUser;

    @FXML private Button doneTeam;

    @FXML private Button doneMail;

    @FXML private Button doneDate;


    @FXML private Button cancelUser;

    @FXML private Button cancelTeam;

    @FXML private Button cancelMail;

    @FXML private Button cancelDate;

    @FXML private Button modUser;

    @FXML private Button modTeam;

    @FXML private Button modMail;

    @FXML private Button modDate;

    @FXML private JFXTextField userText;

    @FXML private JFXTextField teamText;

    @FXML private JFXTextField mailText;

    @FXML private DatePicker datePicker;

    @FXML private BorderPane borderPane;



    @FXML private NumberAxis numberAxis;

    @FXML private Text textBestPlayer;
    @FXML private Text textWorstPlayer;
    @FXML private Text textMostPres;
    @FXML private Text mediaBest;
    @FXML private Text mediaWorst;
    @FXML private Text textPres;





    private Image profileImage;

    private XYChart.Series series;

    private User user;

    private boolean needToDownloadResult=true;

    File selectedFile;

    private ArrayList<Results> results;

    public void initView(User user) {
        this.user = user;
        teamName.setText(user.getTeamName());
        //dataNascita.setText(user.getDataNacita().toString());
        Date date = Date.from(user.getDataNacita().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataNascita.setText(simpleDateFormat.format(date));
        email.setText(user.getEmail());
        username.setText(user.getUserName());


        sectionRosa.setFocusTraversable(false);

        sectionClassifica.setFocusTraversable(false);

        sectionMercato.setFocusTraversable(false);

        sectionAndamento.setFocusTraversable(false);

        if(email.isVisible()) {
            mailText.setVisible(false);
            doneMail.setVisible(false);
            cancelMail.setVisible(false);
        }
        if(username.isVisible()){
            userText.setVisible(false);
            doneUser.setVisible(false);
            cancelUser.setVisible(false);
        }
        if(teamName.isVisible()){
            teamText.setVisible(false);
            doneTeam.setVisible(false);
            cancelTeam.setVisible(false);
        }
        if(dataNascita.isVisible()){
            datePicker.setVisible(false);
            doneDate.setVisible(false);
            cancelDate.setVisible(false);
        }





        if(profileImage == null){
            getHome().downloadProfileImage();
        }





        if(!isNeedToDownloadResult()){

            new Timeline(new KeyFrame(
                    javafx.util.Duration.millis(1000),
                    ae -> FillChart()))
                    .play();

        }

    }


    //Function that

    private void FillChart() {

        System.out.println("Filling Chart...!");
        //clear old data
        teamChart.getData().removeAll(series);
        //teamChart.getData().retainAll();

        series.getData().clear();
        series.getData().removeAll();
        series = new XYChart.Series();

        //Series name
        series.setName("Andamento");

        //Populating series with data
        for (int i = 0; i<results.size();i++){
            //System.out.println(""+results.get(i).getGiornata()+" "+results.get(i).getPoint());
            XYChart.Data data = new XYChart.Data<Integer,Float>(results.get(i).getGiornata(),results.get(i).getPoint());
            data.setNode(new HoveredThresholdNode(results.get(i).getGiornata(),results.get(i).getPoint()));
            series.getData().add(data);

        }

        //populate chart with data

        teamChart.getData().add(series);
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = new Image(profileImage.toURI().toString());
        profileImageView.setImage(this.profileImage);
    }

    public XYChart.Series getSeries() {
        return series;
    }

    public void setSeries(XYChart.Series series) {
        this.series = series;
    }

    public void setResults(ArrayList<Results> results) {
        this.results=results;
        teamChart.setAnimated(true);
        //TODO: Mostrare tutti gli interi su asse x
        numberAxis.setTickLabelFormatter(new IntegerStringConverter());
        series = new XYChart.Series();


        //Series name
        series.setName("Andamento");

        //It tells if main have to ask server for results
        needToDownloadResult=false;

        // Look for some error in results, sometimes there aren't some day
        for (int i = 0;i<38;i++){
            if(results.get(i).getGiornata()!=i+1){
                results.add(i,new Results(i+1,0));
            }
        }

        //Populating series with data
        for (int i = 0; i<results.size();i++){
            System.out.println(""+results.get(i).getGiornata()+" "+results.get(i).getPoint());
            XYChart.Data data = new XYChart.Data<Integer,Float>(results.get(i).getGiornata(),results.get(i).getPoint());
            data.setNode(new HoveredThresholdNode(results.get(i).getGiornata(),results.get(i).getPoint()));
            series.getData().add(data);

        }

        //populate chart with data

        teamChart.getData().add(series);
    }

    public boolean isNeedToDownloadResult() {
        return needToDownloadResult;
    }

    public void setNeedToDownloadResult(boolean needToDownloadResult) {
        this.needToDownloadResult = needToDownloadResult;
    }


    public void openFileChooser(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile!=null){
            //fileLabel.setText(selectedFile.getAbsolutePath());
            profileImageView.setImage(new Image(selectedFile.toURI().toString()));
            getHome().setSelectedFile(selectedFile);
            getHome().uploadPhoto();
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public void showModUser(ActionEvent actionEvent) {
        username.setVisible(false);
        userText.setVisible(true);
        userText.setText(username.getText());
        doneUser.setVisible(true);
        cancelUser.setVisible(true);
        //modUser.setVisible(false);
        RotateButton(modUser);
    }

    public void showModTeam(ActionEvent actionEvent) {
        teamText.setVisible(true);
        teamName.setVisible(false);
        teamText.setText(teamName.getText());
        doneTeam.setVisible(true);
        cancelTeam.setVisible(true);
       // modTeam.setVisible(false);
        RotateButton(modTeam);
    }

    public void showModEmail(ActionEvent actionEvent) {
        mailText.setVisible(true);
        email.setVisible(false);
        mailText.setText(email.getText());
        doneMail.setVisible(true);
        cancelMail.setVisible(true);
       // modMail.setVisible(false);
        RotateButton(modMail);
    }

    public void showModDate(ActionEvent actionEvent) {
        datePicker.setVisible(true);
        dataNascita.setVisible(false);
        doneDate.setVisible(true);
        cancelDate.setVisible(true);
        RotateButton(modDate);
    }

    public void onDoneUser(ActionEvent actionEvent) {
        username.setVisible(true);
        username.setText(userText.getText());
        userText.setVisible(false);
        //userText.setText(username.getText());
        doneUser.setVisible(false);
        cancelUser.setVisible(false);
        modUser.setVisible(true);
    }


    private Action SaveAll(NotificationPane notificationPane) {

        //notificationPane.hide();
        return null;
    }

    public void onDoneTeam(ActionEvent actionEvent) {
        teamText.setVisible(false);
        teamName.setVisible(true);
        teamName.setText(teamText.getText());
        doneTeam.setVisible(false);
        cancelTeam.setVisible(false);
        modTeam.setVisible(true);

    }

    public void onDoneEmail(ActionEvent actionEvent) {
        mailText.setVisible(false);
        email.setVisible(true);
        email.setText(mailText.getText());
        doneMail.setVisible(false);
        cancelMail.setVisible(false);
        modMail.setVisible(true);
    }

    public void onDoneDate(ActionEvent actionEvent) {
        datePicker.setVisible(false);
        dataNascita.setVisible(true);
        doneDate.setVisible(false);
        cancelDate.setVisible(false);
        LocalDate mydate = datePicker.getValue();
        if(mydate!=null)
            dataNascita.setText(""+mydate.getDayOfMonth()+"/"+mydate.getMonthValue()+"/"+mydate.getYear());
        modDate.setVisible(true);
    }

    public void onCancelUser(ActionEvent actionEvent) {
        username.setVisible(true);
        userText.setText(username.getText());
        userText.setVisible(false);
        //userText.setText(username.getText());
        doneUser.setVisible(false);
        cancelUser.setVisible(false);
        modUser.setVisible(true);
    }

    public void onCancelTeam(ActionEvent actionEvent) {
        teamText.setVisible(false);
        teamName.setVisible(true);
        teamText.setText(teamName.getText());
        doneTeam.setVisible(false);
        cancelTeam.setVisible(false);
        modTeam.setVisible(true);
    }

    public void onCancelMail(ActionEvent actionEvent) {
        mailText.setVisible(false);
        email.setVisible(true);
        mailText.setText(email.getText());
        doneMail.setVisible(false);
        cancelMail.setVisible(false);
        modMail.setVisible(true);
    }

    public void onCancelDate(ActionEvent actionEvent) {
        datePicker.setVisible(false);
        dataNascita.setVisible(true);
        doneDate.setVisible(false);
        cancelDate.setVisible(false);
        modDate.setVisible(true);
    }

    public void saveAll(ActionEvent actionEvent) {
        //Send to server
        if(!doneUser.isVisible() && !doneTeam.isVisible() && !doneMail.isVisible() && !doneDate.isVisible()) {
            String username = this.username.getText();
            String email = this.email.getText();
            String teamName = this.teamName.getText();
            String data = this.dataNascita.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                LocalDate localDate = (dateFormat.parse(data).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                //toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                User user = new User(localDate,email,getHome().getUser().getPassword(),getHome().getUser().getTeam(),teamName,username);
                getHome().SendModifiedDataToServer(user);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("POPUP");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione!");
            alert.setHeaderText("Devi Confermare tutte le modifiche");
            alert.setContentText("Conferma le modifiche e clicca di nuovo!");

            alert.showAndWait();
        }
    }

    public void RotateButton(Button button){
        RotateTransition rt = new RotateTransition(javafx.util.Duration.millis(1000), button);
        rt.setByAngle(1080);
        rt.setCycleCount(1);
        rt.setInterpolator(Interpolator.EASE_BOTH);
        rt.play();
        rt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                button.setVisible(false);
                switch (button.getId()){
                    case "user":
                        if(!doneUser.isVisible()){
                            button.setVisible(true);
                        }
                        break;
                    case "team":
                        if(!doneTeam.isVisible()){
                            button.setVisible(true);
                        }
                        break;
                    case "mail":
                        if(!doneMail.isVisible()){
                            button.setVisible(true);
                        }
                        break;
                    case "date":
                        if(!doneMail.isVisible()){
                            button.setVisible(true);
                        }
                        break;
                }
            }
        });
    }

    public void setInfo(InfoClass info) {
        textBestPlayer.setText(info.getBestPlayer());
        mediaBest.setText(info.getMediabest()+"");
        textWorstPlayer.setText(info.getWorstPlayer());
        mediaWorst.setText(info.getMediaWorst()+"");
        textMostPres.setText(info.getMostPresPlayer());
        textPres.setText(info.getPres()+"");
    }

    public void ShowErrorPopup() {
        //Reset all info
        username.setText(user.getUserName());
        email.setText(user.getEmail());
        Date date = Date.from(user.getDataNacita().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataNascita.setText(simpleDateFormat.format(date));
        teamName.setText(user.getTeamName());

        //show popup
        System.out.println("POPUP");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE!");
        alert.setHeaderText("Impossibile modificare i dati");
        alert.setContentText("Errore nell'inserimento dei nuovi dati!");
        alert.showAndWait();
        System.out.println("FINE POP");
    }


    /** a node which displays a value on hover, but is otherwise empty */
    class HoveredThresholdNode extends StackPane {
        HoveredThresholdNode(int giornata, float point) {
            setPrefSize(10, 10);
            setLayoutY(getLayoutY()+10);
            setStyle("-fx-text-alignment: center;");

            final Label label = createDataThresholdLabel(giornata, point);

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    //setCursor(Cursor.NONE);
                    //toFront();
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    //setCursor(Cursor.CROSSHAIR);
                }
            });
        }

        private Label createDataThresholdLabel(int giornata, float point) {
            final Label label = new Label(point + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 10; -fx-font-weight: bold; -fx-text-alignment: center;");

            label.setTextFill(Paint.valueOf("#2e6da4"));


            label.setMinSize(40, 40);
            return label;
        }



    }
}
