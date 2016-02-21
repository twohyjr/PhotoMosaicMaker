package main;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.awt.*;

/**
 * Created by twohyjr on 2/19/16.
 */
public class ImageGrid{

    public Image mainImage = null;
    public int rowCount,columnCount,cellWidth,cellHeight = 0;
    public int maxCells = 0;
    private Pane mosaicPane;
    private Cell[][] cellGrid;
    private ImageLibrary compareLibrary;
    private boolean pixelize =false;
    private Pane pixilizedView;

    public ImageGrid(Pane mosaicImagePane,Pane pixilizedView, ImageLibrary library, Image mainImage, int rowCount, int columnCount,int cellWidth, int cellHeight){
        this.mainImage = mainImage;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.maxCells = rowCount*columnCount;
        this.mosaicPane = mosaicImagePane;
        this.compareLibrary = library;
        cellGrid = new Cell[columnCount][rowCount];
        this.pixilizedView = pixilizedView;

//        System.out.println("Row Count:    " + this.rowCount);
//        System.out.println("Column Count: "  + this.columnCount);
//        System.out.println("Cell Width:   " + this.cellWidth);
//        System.out.println("Cell Height:  " +this.cellHeight);
//        System.out.println("Max Cells:    " + this.maxCells);



    }



    public void createViewImage(){



        for(int y = 0; y < rowCount; y++){
            for(int x = 0; x < columnCount; x++){
                Point point = new Point(x,y);
                Cell cell = new Cell(mainImage,point,cellWidth,cellHeight);
                cellGrid[point.x][point.y] = cell;
                cell.createSubImage();
                cell.createDisplayCell();


                    CompImage best = compareLibrary.compImages.get(0);
                    Image bestImage = best.image;


                    for (CompImage compImage : compareLibrary.compImages) {

                        CompImage tempBest = best;
                        best = getBestImageMatch(cell.averageColor, tempBest, compImage);
                        bestImage = best.image;

                    }

                    displayImages(mosaicPane,bestImage,cell.cellLocation,cellWidth,cellHeight);
                    displayImages(pixilizedView,cell);

            }
        }
        System.out.println("Mosaic Created...");

    }

    public CompImage getBestImageMatch(Color cellColor, CompImage image1, CompImage image2){
        double distance1 = ColourDistance(cellColor, image1.averageColor);
        double distance2 = ColourDistance(cellColor, image2.averageColor);

        if(distance1 <= distance2){
            return image1;
        }else{
            return image2;
        }

    }

    public void displayImages(Pane pane, Image image,Point location, int cellWidth, int cellHeight){

        int posX = location.x * cellWidth;
        int posY = location.y * cellHeight;
        CellView imageView = new CellView(image,location);
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        imageView.relocate(posX,posY);
//        imageView.setEffect(new DropShadow(2, javafx.scene.paint.Color.BLACK));
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: "+imageView.location.x + " Y:" +imageView.location.y);

                imageView.setImage(mainImage);
            }
        });
        pane.getChildren().addAll(imageView);

    }

    public void displayImages(Pane pane, Cell cell){
        int posX = cell.cellLocation.x * cellWidth;
        int posY = cell.cellLocation.y * cellHeight;

        CellView imageView = new CellView(cell.cellImage,cell.cellLocation);
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        imageView.relocate(posX,posY);
//        imageView.setEffect(new DropShadow(2, javafx.scene.paint.Color.BLACK));
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {


                System.out.println("X: "+imageView.location.x + " Y:" +imageView.location.y);
                imageView.setImage(mainImage);
            }
        });
        pane.getChildren().addAll(imageView);
    }


    public double ColourDistance(Color c1, Color c2){
        double rmean = ( c1.getRed() + c2.getRed() )/2;
        int r = c1.getRed() - c2.getRed();
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean/256;
        double weightG = 4.0;
        double weightB = 2 + (255-rmean)/256;
        return Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b);
    }



}
