package GraphicItem;

import Controllers.ClassificaController;
import Controllers.MercatoController;
import Model.Player;
import Model.PlayerVoto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Created by Emanuele on 08/12/2015.
 */
public class ListCellVoti extends ListCell <PlayerVoto> {
    private ClassificaController classificaController;

    public ListCellVoti(ClassificaController classificaController) {
        this.classificaController = classificaController;
    }

    @Override
    protected void updateItem(PlayerVoto item, boolean empty) {
        super.updateItem(item, empty);

        if(item==null){

            getStyleClass().remove("titolare");
            getStyleClass().add("riserva");
        }
        else {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(4);
            gridPane.setPadding(new Insets(0,10,0,10));
            //OLD VERSION
            /*
            gridPane.getColumnConstraints().add(new ColumnConstraints()); // column 0 is 100 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(20)); // column 1 is 50 wide
            gridPane.getColumnConstraints().add(new ColumnConstraints(70));
            */
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(60);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(20);
            //ColumnConstraints col3 = new ColumnConstraints();
            //col3.setPercentWidth(15);
            gridPane.getColumnConstraints().addAll(col1,col2);

            Text name = new Text(item.getCognome());
            gridPane.add(name,0,0);

            Circle circle = new Circle();
            Text voto = new Text((""+item.getVoto()));
            voto.setBoundsType(TextBoundsType.VISUAL);
            if(item.getVoto()>6){
                circle.setFill(Color.GREEN);
                circle.setStroke(Color.GREEN);
                circle.setRadius(12);
                voto.setFill(Color.WHITE);
            }
            if(item.getVoto()<6){
                circle.setFill(Color.RED);
                circle.setStroke(Color.RED);
                circle.setRadius(12);
                voto.setFill(Color.WHITE);
            }
            if(item.getVoto()==6.0){
                circle.setFill(Color.FORESTGREEN);
                circle.setStroke(Color.FORESTGREEN);
                circle.setRadius(12);
                voto.setFill(Color.WHITE);
            }
            if(item.getVoto()==0.0){
                circle.setFill(Color.BLACK);
                circle.setStroke(Color.BLACK);
                circle.setRadius(12);
                voto.setFill(Color.WHITE);
            }
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle,voto);


            gridPane.add(stack,1,0);
            ImageView imageView = new ImageView();

            if(item.isEntrato()){

                imageView.setImage(new Image("/Images/arrow-up.png"));
            }
            if(item.isSostituito()){
                imageView.setImage(new Image("/Images/arrow-down.png"));
            }

            imageView.setFitWidth(20);
            imageView.setFitHeight(20);

            gridPane.add(imageView,2,0);

            /*
            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.print("Clicked the player"+ item.getCognome());
                   // cl.removePlayer(item);
                }
            });
            button.setPrefWidth(60.00);
            button.setPrefHeight(10.00);
            button.setText("INFO");
            button.getStyleClass().add("infoButton");

            gridPane.add(button,3,0);
            */

            if(item.isTitolare()){
                getStyleClass().add("titolare");
                getStyleClass().remove("riserva");
            }
            else {
                getStyleClass().remove("titolare");
                getStyleClass().add("riserva");
            }

            setGraphic(gridPane);
        }
    }
}
