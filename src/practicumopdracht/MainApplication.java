package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.ArtistView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainApplication extends Application {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }

        MainApplication.stage = stage;
        ArtistView.openEditPanel = true; //Show / Hides the edit panel on the Artist View.
        ArtistView artistView = new ArtistView();
        AlbumView.openEditPanel = true;  //Show / Hides the edit panel on the Album View.
        AlbumView albumView = new AlbumView();

        Scene scene = new Scene(artistView.getRoot());
        //Stylesheet from https://github.com/joffrey-bion/javafx-themes
        scene.getStylesheets().add(getClass().getResource("default.css").toString());
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(String.format("Practicumopdracht OOP2 - %s", Main.studentNaam));
        stage.setWidth(640);
        stage.setHeight(480);
        //set the minimal dimensions the program supports
        stage.setMinWidth(530);
        stage.setMinHeight(350);
        stage.setScene(scene);
        stage.show();

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
}
