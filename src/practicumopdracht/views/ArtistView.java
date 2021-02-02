package practicumopdracht.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    private double imageX;
    private double imageY;
    private ImageView artistOverlay;

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

    private void updateArtistView() {
        double heightToUse = imageX > imageY ? imageX : imageY;
//        artistImage.setFitWidth(imageX + 100);
//        artistImage.setX(imageX);
//        artistImage.setFitHeight(heightToUse);
    }

    private void initArtistDisplay() {

        StackPane rootPane = new StackPane();
        StackPane overlayPane = new StackPane();
        VBox contentVBox = new VBox();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        BackgroundImage bgImage = new BackgroundImage(
                loadImage(),                                                 // image
                BackgroundRepeat.NO_REPEAT,                            // repeatX
                BackgroundRepeat.NO_REPEAT,                            // repeatY
                BackgroundPosition.DEFAULT,                             // position
                new BackgroundSize(-1, -1, false, false, false, true)  // size
        );

        rootPane.setBackground(new Background(bgImage));
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(overlayPane);
        overlayPane.getChildren().add(contentVBox);
        contentVBox.setAlignment(Pos.CENTER);
        //Add / Remove Button
        Label contentTitle = new Label("Arctic Monkeys");
        contentTitle.setWrapText(true);
        contentTitle.setTextAlignment(TextAlignment.CENTER);
        contentTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); -fx-font-size: 30; -fx-font-family: Broadway");
        contentVBox.getChildren().add(contentTitle);

        Label recordlabelTitle = new Label("Domino Records");
        recordlabelTitle.setWrapText(true);
        recordlabelTitle.setTextAlignment(TextAlignment.CENTER);
        recordlabelTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); -fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(recordlabelTitle);

    }

    private void initArtistList() {

        artistListVBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(artistListView, Priority.ALWAYS);
        artistListVBox.minWidth(500);

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
        artistListVBox.setMinWidth(200);
        //Determine how the buttons should be scaled.
        int btnCount = artistListButtonHBox.getChildren().size();
        addButton.prefWidthProperty().bind(artistListButtonHBox.widthProperty().divide(btnCount));
        removeButton.prefWidthProperty().bind(artistListButtonHBox.widthProperty().divide(btnCount));
    }

    private Image loadImage() {
        try {
            FileInputStream input = new FileInputStream("src/practicumopdracht/content/arcticmonkeys.jfif");
            return new Image(input);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
