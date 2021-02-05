package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.ArtistView;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        if (!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }


        ArtistView.openEditPanel = false; //Show / Hides the edit panel on the Artist View.
        ArtistView artistView = new ArtistView();
        AlbumView albumView = new AlbumView();

        Scene scene = new Scene(albumView.getRoot());

        stage.setTitle(String.format("Practicumopdracht OOP2 - %s", Main.studentNaam));
        stage.setWidth(640);
        stage.setHeight(480);
        //set the minimal dimensions the program supports
        stage.setMinWidth(530);
        stage.setMinHeight(330);
        stage.setScene(scene);
        stage.show();

    }
}
