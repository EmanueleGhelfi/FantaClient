package GraphicItem;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Created by Emanuele on 21/12/2015.
 */
public class BouncingImage extends ImageView {
    Timeline bouncer = new Timeline();
    boolean mouseIn = false;

    public BouncingImage(Image image) {
        super(image);
        System.out.println("fuori");

        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                mouseIn = true;
                System.out.println("CIAOOOOOO");
                bouncer.play();
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                mouseIn = false;
            }
        });

        // set keyframes for bouncer
        bouncer.getKeyFrames().addAll(
                makeKeyFrame(0, 0.0, 1.2, 1.0),
                makeKeyFrame(100, 0.0, 1.0, 1.2),
                makeKeyFrame(300, -20.0, 1.0, 1.0),
                makeKeyFrame(500, 0.0, 1.0, 1.2),
                makeKeyFrame(600, 0.0, 1.2, 1.0)
        );



        // start play the animation
//              bouncer.setCycleCount(Animation.INDEFINITE);
        bouncer.setCycleCount(1);
//              bouncer.play();
        bouncer.setOnFinished(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                if (mouseIn) {
                    bouncer.play();
                } else {
                    setScaleX(1.0);
                }
            }

        });

        bouncer.play();
    }

    private KeyFrame makeKeyFrame(int d, double y, double sx, double sy) {
        return new KeyFrame(
                new Duration(d),
                new KeyValue(translateYProperty(), y),
                new KeyValue(scaleXProperty(), sx),
                new KeyValue(scaleYProperty(), sy)
        );
    }
}
