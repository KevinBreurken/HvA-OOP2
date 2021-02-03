package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    public static boolean openEditPanel = false;
    //Artist Content
    StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox;
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
        artistListTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

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
                loadImage("src/practicumopdracht/content/kooks.jpeg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);
        if (ArtistView.openEditPanel)
            initArtistEditView();
        else
            initArtistContentView();
    }

    private void initArtistContentView() {
        VBox contentVBox = new VBox();
        artistDisplayContentPane.getChildren().add(contentVBox);
        contentVBox.setAlignment(Pos.CENTER);

        ImageView favImage = new ImageView(loadImage("src/practicumopdracht/content/fav-on-32.png"));
        favImage.setSmooth(true);
        contentVBox.getChildren().add(favImage);
        //Add / Remove Button
        Label contentTitle = new Label("The Kooks");
        contentTitle.setWrapText(true);
        contentTitle.setTextAlignment(TextAlignment.CENTER);
        contentTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 30; -fx-font-family: Broadway");
        contentVBox.getChildren().add(contentTitle);

        Label recordlabelTitle = new Label("Domino Records");
        recordlabelTitle.setWrapText(true);
        recordlabelTitle.setTextAlignment(TextAlignment.CENTER);
        recordlabelTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(recordlabelTitle);

        VBox buttonHBox = new VBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        Button addButton = new Button("View Albums");
        buttonHBox.getChildren().add(addButton);
        Button removeButton = new Button("Edit Artist");
        buttonHBox.getChildren().add(removeButton);
        contentVBox.getChildren().add(buttonHBox);
        buttonHBox.setMinWidth(200);

    }

    private void initArtistEditView() {
        VBox contentVBox = new VBox();
        artistDisplayContentPane.getChildren().add(contentVBox);
        contentVBox.setAlignment(Pos.CENTER);

        VBox artistNameHBox = createTextfieldGroup("Artist name:", "Type artist name here...");
        contentVBox.getChildren().add(artistNameHBox);
        VBox labelNameHBox = createTextfieldGroup("Label name:", "Type label name here...");
        contentVBox.getChildren().add(labelNameHBox);
        HBox favoriteHBox = createfavoriteGroup("Is favorite:");
        contentVBox.getChildren().add(favoriteHBox);

        //Buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        Button removeButton = new Button("Save");
        buttonHBox.getChildren().add(removeButton);
        Button saveButton = new Button("Cancel");
        buttonHBox.getChildren().add(saveButton);
        contentVBox.getChildren().add(buttonHBox);
        saveButton.setMinWidth(50);
        removeButton.setMinWidth(50);

    }

    private Image loadImage(String fileUrl) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            return new Image(input);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    private VBox createTextfieldGroup(String preText, String promptText) {
        VBox groupHBox = new VBox();
        groupHBox.setAlignment(Pos.BASELINE_CENTER);
        groupHBox.setPadding(new Insets(0, 0, 15, 0));

        Label preTextLabel = new Label(preText);
        preTextLabel.setWrapText(true);
        preTextLabel.setTextAlignment(TextAlignment.RIGHT);
        preTextLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");
        preTextLabel.setPadding(new Insets(0, 10, 0, 0));
        groupHBox.getChildren().add(preTextLabel);

        TextField artistName = new TextField();
        artistName.setAlignment(Pos.CENTER);
        artistName.setPromptText(promptText);
        artistName.setPrefWidth(120);
        artistName.setMaxWidth(120);
        groupHBox.getChildren().add(artistName);
        return groupHBox;
    }

    private HBox createfavoriteGroup(String preText) {
        HBox favoriteGroupHBox = new HBox();
        favoriteGroupHBox.setAlignment(Pos.BASELINE_CENTER);
        favoriteGroupHBox.setPadding(new Insets(10, 0, 0, 50));

        Label preTextLabel = new Label(preText);
        preTextLabel.setWrapText(true);
        preTextLabel.setPadding(new Insets(0, 1, 0, 0));
        preTextLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");

        preTextLabel.setTextAlignment(TextAlignment.RIGHT);
        favoriteGroupHBox.getChildren().add(preTextLabel);

        CheckBox favoriteCheckbox = new CheckBox();
        favoriteCheckbox.setAlignment(Pos.CENTER_LEFT);
        favoriteCheckbox.setPadding(new Insets(0, 0, 0, 10));
        favoriteCheckbox.setPrefWidth(80);
        favoriteCheckbox.setMaxWidth(80);
        favoriteGroupHBox.getChildren().add(favoriteCheckbox);

        return favoriteGroupHBox;
    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
