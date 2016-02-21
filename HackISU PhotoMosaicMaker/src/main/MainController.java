package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private Button loadImageButton;
    @FXML
    private Button createMosaicButton;
    @FXML
    private Pane backgroundPane;
    @FXML
    private Pane mainImagePane;
    @FXML
    private Pane mosaicImagePane;
    @FXML
    private ImageView mainImageView;
    @FXML
    private TextField rowCountField;
    @FXML
    private TextField columnCountField;
    @FXML
    private TextField cellHeightField;
    @FXML
    private TextField cellWidthField;

    @FXML
    private Pane pixelizedView;

    private Image mainImage;

    private ImageLibrary currentLibrary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Buttons Pressed Actions
    public void loadImageButtonPressed(ActionEvent event){
        System.out.println("Loading Image...");

        String imagePath = getMainImagePath();
            if(!imagePath.equals("")){
                Image img = new Image("file:" + imagePath);
                System.out.println("Image Loaded\n");
                mainImage = img;
                mosaicImagePane.getChildren().clear();
                mainImageView.setImage(img);
            }

        }
    public void createMosaicButtonPressed(ActionEvent event){
        System.out.println("Creating Mosaic...");

        int rowCount =  Integer.parseInt(rowCountField.getText());
        int columnCount = Integer.parseInt(columnCountField.getText());
        int cellWidth = Integer.parseInt(cellWidthField.getText());
        int cellHeight = Integer.parseInt(cellHeightField.getText());
        mosaicImagePane.getChildren().clear();

        ImageGrid grid = new ImageGrid(mosaicImagePane, pixelizedView,currentLibrary,mainImage, rowCount,columnCount,cellWidth,cellHeight);
        grid.createViewImage();

    }

    public void loadLibraryButtonPressed(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory...");
        File file = directoryChooser.showDialog(this.backgroundPane.getScene().getWindow());
        if(file.exists()){
            String directory = file.getAbsolutePath();
            currentLibrary = new ImageLibrary(directory);
        }

    }


    private String getMainImagePath(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image...");
        File file = fileChooser.showOpenDialog(this.backgroundPane.getScene().getWindow());
        String imagePath = file.getAbsolutePath();

        return imagePath;
    }

}




