package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.ArtistView;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        if(!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }

        ArtistView artistView = new ArtistView();
        Scene scene = new Scene(artistView.getRoot());

        stage.setTitle(String.format("Practicumopdracht OOP2 - %s", Main.studentNaam));
        stage.setWidth(640);
        stage.setHeight(480);
        stage.setScene(scene);
        stage.show();

    }
}
