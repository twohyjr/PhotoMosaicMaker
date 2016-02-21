package main;

import javafx.scene.image.Image;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by twohyjr on 2/20/16.
 */
public class ImageLibrary implements Runnable{

    private boolean running = false;
    public Thread thread;
    public LinkedList<Image> library = new LinkedList<Image>();
    public LinkedList<CompImage> compImages = new LinkedList<CompImage>();
    private String directory = "";
    boolean rename = false;

    public ImageLibrary(String directory){
        this.directory = directory;
        renameImages();
        start();
    }

    public void start(){
        if(running){
            return;
        }
        thread = new Thread(this);
        running = true;
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    public void renameImages(){
        if(rename) {
            File dir = new File(directory);

            if (dir.isDirectory()) { // make sure it's a directory
                int count = 0;
                for (final File f : dir.listFiles()) {
                    try {
                        File newfile = new File(directory + "/" + count + ".jpg");
                        count++;
                        if (f.renameTo(newfile)) {

                        } else {
                            System.out.println("Rename failed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void createImageLibrary(){
        for(int i = 1; i < 100; i++){
            Image img = new Image("file:" + directory + "/" + i + ".jpg");
            if(img.getWidth() != 0){
                library.add(img);
            }
        }
    }

    public void createCompareList(){
        for(int i = 0; i < library.size();i++){
            int width = (int)library.get(i).getWidth();
            int height = (int) library.get(i).getHeight();
            CompImage img = new CompImage(library.get(i),width,height);
            if(img != null){
                compImages.add(img);
            }

//            System.out.println(img.averageWavelength);
        }
    }

    @Override
    public void run() {
        System.out.println("Loading Library...");
        createImageLibrary();
        System.out.println("Library Loaded...");
        System.out.println("Creating Comparable Images...");
        createCompareList();
        System.out.println("Comparable Images Loaded...");
    }


}
