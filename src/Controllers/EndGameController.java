package Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Home;

/**
 * Created by Emanuele on 17/01/2016.
 */
public class EndGameController extends BaseController {
   @FXML private CubicCurve curve;
    @FXML private StackPane stackPane;
    @FXML private Text posText;


    public void startAnimation() {
        Path path = new Path();
        path.getElements().add(new MoveTo(0.00, 0.00));
        path.getElements().add(new CubicCurveTo(25.00,-50.00,75.00,-50.00,100.00,0.00));
        //path.getElements().add(new CubicCurveTo(75.00,50.00,50.00,50.00,50.00,0.00));
        path.getElements().add(new CubicCurveTo(75.00,50.00,50.00,50.00,40.00,0.00));
        //path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));

        //path.getElements().add(new LineTo(400.00-stackPane.getLayoutX(),360.00-stackPane.getLayoutY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(stackPane);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        pathTransition.play();


        stackPane.setScaleX(0);
        stackPane.setScaleY(0);

        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(6000), stackPane);
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_IN);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.playFromStart();
        //this.scaleArray.add(scaleTransition);
    }


    public void setPos(String pos) {
        posText.setText(""+pos+"°");
    }
}
