package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practicumopdracht.controllers.ArtistController;
import practicumopdracht.controllers.Controller;
import practicumopdracht.data.*;
import practicumopdracht.vendors.ResizeHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Starts the JavaFX application and handles general application operations.
 */
public class MainApplication extends Application {

    public static final String title = String.format("Practicumopdracht OOP2 - %s", Main.studentNaam);;

    private static Stage stage;
    private static ArtistDAO artistDAO;
    private static AlbumDAO albumDAO;
    private static ImageFileDAO imageFileDAO;

    private final int WIDTH = 640;
    private final int HEIGHT = 500;
    private final int MIN_WIDTH = 580;
    private final int MIN_HEIGHT = 500;

    public static Stage getStage() {
        return stage;
    }
    public static ArtistDAO getArtistDAO() { return artistDAO;}
    public static AlbumDAO getAlbumDAO() { return albumDAO;}
    public static ImageFileDAO getImageFileDAO() { return imageFileDAO;}

    /**
     * Switches the controller to a new controller and scene.
     * @param controller the newly applied controller.
     */
    public static void switchController(Controller controller) {
        Scene scene = new Scene(controller.getView().getRoot());

        //Source: Stylesheet from https://github.com/joffrey-bion/javafx-themes
        File f = new File("src/practicumopdracht/content/css/default.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
    }

    public static Image loadImage(String fileUrl) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            Image image = new Image(input);
            input.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image loadImage(String fileUrl, int requestedWidth, int requestedHeight, boolean preserveRatio, boolean smooth) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            Image image = new Image(input, requestedWidth, requestedHeight, preserveRatio, smooth);
            input.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }
        MainApplication.stage = stage;
        artistDAO = new BinaryArtistDAO();
        albumDAO = new ObjectAlbumDAO();
        imageFileDAO = new ImageFileDAO();
        artistDAO.load();
        albumDAO.load();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        //set the minimal dimensions the program supports
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        ArtistController artistController = new ArtistController();
        MainApplication.switchController(artistController);
        stage.show();
    }
}
