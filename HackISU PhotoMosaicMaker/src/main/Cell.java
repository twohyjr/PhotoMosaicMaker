package main;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;

/**
 * Created by twohyjr on 2/19/16.
 */
public class Cell implements Runnable{

    private boolean running = false;
    public Thread thread;

    private Image mainImage;
    public  Image cellImage;
    public Point cellLocation; //This is the cell location in the 2d array
    public int cellWidth, cellHeight;

    public int averageRed = 0;
    public int averageGreen = 0;
    public int averageBlue = 0;
    public int averageAlpha = 0;
    public Color averageColor = null;

    public Cell(Image mainImage,Point cellLocation,int cellWidth,int cellHeight){
        this.mainImage = mainImage;
        this.cellLocation = cellLocation;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        thread = new Thread(this);
    }

    public void start(){

        if(running){
            return;
        }
        running = true;
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    @Override
    public void run() {
        createSubImage();

    }

    public void createSubImage(){

        int posX = cellLocation.x * cellWidth;
        int posY = cellLocation.y * cellHeight;

        PixelReader reader = mainImage.getPixelReader();
        WritableImage dest = new WritableImage(cellWidth, cellHeight);
        PixelWriter writer = dest.getPixelWriter();

        int red = 0;
        int green = 0;
        int blue = 0;
        int alpha = 0;

        for(int column = 0; column < cellHeight; column++) {
            for (int row = 0; row < cellWidth; row++) {
                int argb = reader.getArgb(posX + row, posY + column);
                Color c = new Color(argb);
                red += c.getRed();
                green += c.getGreen();
                blue += c.getBlue();
                alpha += c.getAlpha();
                writer.setArgb(row, column, argb);
            }
        }

        int totalCells = cellHeight*cellWidth;
        averageRed = red/totalCells;
        averageGreen = green/totalCells;
        averageBlue = blue/totalCells;
        averageAlpha = alpha/totalCells;
        averageColor = new Color(averageRed,averageGreen,averageBlue,averageAlpha);

//        this.cellImage = dest;

    }

    public void createDisplayCell(){

        PixelReader reader = mainImage.getPixelReader();
        WritableImage dest = new WritableImage(cellWidth, cellHeight);
        PixelWriter writer = dest.getPixelWriter();

        for(int column = 0; column < cellHeight; column++) {
            for (int row = 0; row < cellWidth; row++) {
                Color color = new Color(averageRed,averageGreen,averageBlue,averageAlpha);

                writer.setArgb(row, column, color.getRGB());
            }
        }

        this.cellImage = dest;

    }



//    if(column <= widthCount && row <= heightCount) {
//        quadrentIndex = 1;
//    }else if(column >= widthCount && row <= heightCount){
//        quadrentIndex = 2;
//    }else if(column <= widthCount && row >= heightCount){
//        quadrentIndex = 3;
//    }else if(column >= widthCount && row >= heightCount){
//        quadrentIndex = 4;
//    }else{
//        quadrentIndex = 999;
//    }

}

