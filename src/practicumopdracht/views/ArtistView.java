package practicumopdracht.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ArtistView extends View {

    private final static String[] TEST_ARTIST_NAMES = new String[]{
            "Anamanaguchi", "Periphery", "Haywyre", "Maduk", "Chase & Status", "Darren Korb",
            "Paramore", "Spiritbox", "Arctic Monkeys", "The Kooks", "Minus the Bear",
            "Infected Mushrooms", "Jamiroquai", "David Maxim Micic", "Iron Maiden", "Tesseract",
            "Dua Lipa", "Dance Gavin Dance", "Carpenter Brut", "Crazy Astronaut"
    };

    private HBox rootHorizontalBox;

    //Artist Display

    //Artist List
    private VBox artistListVBox;
    private ListView artistListView;
    private HBox artistListButtonHBox;

    public ArtistView() {
        this.rootHorizontalBox = new HBox();
        this.artistListView = new ListView();
        this.artistListVBox = new VBox();
        this.artistListButtonHBox = new HBox();
        initLayout();
    }

    @Override
    protected void initLayout() {
        initArtistList();
        initArtistDisplay();

        rootHorizontalBox.getChildren().add(artistListVBox);
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);

    }

    private void initArtistDisplay() {

    }

    private void initArtistList() {
        artistListVBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(artistListView, Priority.ALWAYS);

        Label artistListTitle = new Label("Artists");
        artistListTitle.setStyle("-fx-font-weight: bold;");
        artistListVBox.getChildren().add(artistListTitle);
        artistListVBox.getChildren().add(artistListView);
        //for testing purposes
        for (int i = 0; i < TEST_ARTIST_NAMES.length; i++) {
            artistListView.getItems().add(TEST_ARTIST_NAMES[i]);
        }
        //Add / Remove Button
        Button addButton = new Button("Add");
        artistListButtonHBox.getChildren().add(addButton);
        Button removeButton = new Button("Remove");
        artistListButtonHBox.getChildren().add(removeButton);
        artistListVBox.getChildren().add(artistListButtonHBox);
        //Determine how the buttons should be scaled.
        int btnCount = artistListButtonHBox.getChildren().size();
        addButton.prefWidthProperty().bind(artistListButtonHBox.widthProperty().divide(btnCount));
        removeButton.prefWidthProperty().bind(artistListButtonHBox.widthProperty().divide(btnCount));
    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
