package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;


/**
 * Created by twohyjr on 2/20/16.
 */
public class CellView extends ImageView {

    public Point location;

    public CellView(Image image, Point location){
        super(image);
        this.location = location;
    }
}
