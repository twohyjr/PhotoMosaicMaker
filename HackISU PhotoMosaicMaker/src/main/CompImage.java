package main;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.util.Random;

/**
 * Created by twohyjr on 2/20/16.
 */
public class CompImage {

    public static final int MAX_SAMPLES = 100;

    public  Image image;
    public int imageWidth,imageHeight;
    public int averageRed = 0;
    public int averageGreen = 0;
    public int averageBlue = 0;
    public int averageAlpha = 0;
    public Color averageColor = null;


    public CompImage(MainController controller,Image image, int imageWidth, int imageHeight){
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        getPicAverageColors();
    }

    public void getPicAverageColors(){
        PixelReader reader = image.getPixelReader();
        WritableImage dest = new WritableImage(imageWidth, imageHeight);
        PixelWriter writer = dest.getPixelWriter();
        Random rand = new Random();

        int red = 0;
        int green = 0;
        int blue = 0;
        int alpha = 0;

        for(int i = 0; i < MAX_SAMPLES; i++){

            int row = rand.nextInt(imageWidth);
            int column = rand.nextInt(imageHeight);

            int argb = reader.getArgb(row, column);
            Color c = new Color(argb);
            red += c.getRed();
            green += c.getGreen();
            blue += c.getBlue();
            alpha += c.getAlpha();
        }

        int totalCells = MAX_SAMPLES;
        averageRed = red/totalCells;
        averageGreen = green/totalCells;
        averageBlue = blue/totalCells;
        averageAlpha = alpha/totalCells;

        averageColor = new Color(averageRed,averageGreen,averageBlue,averageAlpha);

//        System.out.println("Red: " + averageRed);
//        System.out.println("Green: " + averageGreen);
//        System.out.println("Blue: " + averageBlue);
//        System.out.println("Alpha: " + averageAlpha);
    }



}
