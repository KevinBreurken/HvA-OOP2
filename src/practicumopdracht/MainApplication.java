package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.views.ArtistView;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }

        ArtistView.openEditPanel = false;
        ArtistView artistView = new ArtistView();
//        Shows the edit panel
        Scene scene = new Scene(artistView.getRoot());

        stage.setTitle(String.format("Practicumopdracht OOP2 - %s", Main.studentNaam));
        stage.setWidth(640);
        stage.setHeight(480);
        //set the minimal dimensions the program supports
        stage.setMinWidth(430);
        stage.setMinHeight(280);
        stage.setScene(scene);
        stage.show();

    }
}
