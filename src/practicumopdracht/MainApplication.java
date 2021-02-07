package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practicumopdracht.controllers.ArtistController;
import practicumopdracht.controllers.Controller;
import practicumopdracht.data.AlbumDAO;
import practicumopdracht.data.ArtistDAO;
import practicumopdracht.data.FakeAlbumDAO;
import practicumopdracht.data.FakeArtistDAO;
import practicumopdracht.vendors.ResizeHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainApplication extends Application {

    public static String title;

    private static Stage stage;
    private static ArtistDAO artistDAO;
    private static AlbumDAO albumDAO;

    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private final int MIN_WIDTH = 530;
    private final int MIN_HEIGHT = 450;

    public static Stage getStage() {
        return stage;
    }
    public static ArtistDAO getArtistDAO() { return artistDAO;}
    public static AlbumDAO getAlbumDAO() { return albumDAO;}

    public static void switchController(Controller controller) {
        Scene scene = new Scene(controller.getView().getRoot());
        //Stylesheet from https://github.com/joffrey-bion/javafx-themes
        scene.getStylesheets().add("practicumopdracht/default.css");
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
    }

    public static Image loadImage(String fileUrl) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            return new Image(input);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    public static Image loadImage(String fileUrl, int requestedWidth, int requestedHeight, boolean preserveRatio, boolean smooth) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            return new Image(input, requestedWidth, requestedHeight, preserveRatio, smooth);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    public static void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(text);
        a.show();
    }

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }
        MainApplication.stage = stage;
        artistDAO = new FakeArtistDAO();
        artistDAO.load();
        albumDAO = new FakeAlbumDAO();
        albumDAO.load();

        title = String.format("Practicumopdracht OOP2 - %s", Main.studentNaam);
        ArtistController artistController = new ArtistController();
        stage.initStyle(StageStyle.UNDECORATED);
        System.out.println(title);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        //set the minimal dimensions the program supports
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        MainApplication.switchController(artistController);
        stage.show();
    }
}
