package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practicumopdracht.controllers.ArtistController;
import practicumopdracht.controllers.Controller;
import practicumopdracht.vendors.ResizeHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainApplication extends Application {

    private static Stage stage;
    public static String title;

    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private final int MIN_WIDTH = 530;
    private final int MIN_HEIGHT = 350;

    public static Stage getStage() {
        return stage;
    }

    public static void switchController(Controller controller) {
        Scene scene = new Scene(controller.getView().getRoot());
        //Stylesheet from https://github.com/joffrey-bion/javafx-themes
        scene.getStylesheets().add("practicumopdracht/default.css");
        stage.setScene(scene);
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

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }
        MainApplication.stage = stage;

        title = String.format("Practicumopdracht OOP2 - %s", Main.studentNaam);
        ArtistController artistController = new ArtistController();
        System.out.println(getClass().getResource("default.css"));
        stage.initStyle(StageStyle.UNDECORATED);
        System.out.println(title);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        //set the minimal dimensions the program supports
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        MainApplication.switchController(artistController);
        ResizeHelper.addResizeListener(stage);
        stage.show();
    }

    public static void showAlert(String text){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(text);
        a.show();
    }

    public static void getTitle(){

    }
}
