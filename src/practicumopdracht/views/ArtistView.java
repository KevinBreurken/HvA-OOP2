package practicumopdracht.views;

import javafx.geometry.Insets;
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

    //Artist Content
    StackPane artistDisplayContentPane;
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

    private void initArtistDisplay() {

        StackPane rootPane = new StackPane();
        artistDisplayContentPane = new StackPane();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        BackgroundImage bgImage = new BackgroundImage(
                loadImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);
        initArtistContentView();
        initArtistContentView();
    }

    private void initArtistContentView(){
        VBox contentVBox = new VBox();
        artistDisplayContentPane.getChildren().add(contentVBox);
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

        VBox buttonHBox = new VBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20,0,0,0));
        buttonHBox.setAlignment(Pos.CENTER);
        Button addButton = new Button("View Albums");
        buttonHBox.getChildren().add(addButton);
        Button removeButton = new Button("Edit Artist");
        buttonHBox.getChildren().add(removeButton);
        contentVBox.getChildren().add(buttonHBox);
        buttonHBox.setMinWidth(200);
    }

    private void initArtistEditView(){

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
