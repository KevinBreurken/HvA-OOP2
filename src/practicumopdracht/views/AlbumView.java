package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlbumView extends View {

    private final static String[] TEST_ARTIST_NAMES = new String[]{
            "Tranquility Base Hotel & Casino", "AM", "Suck it and see", "Humbug", "Favourite worst nightmare",
            "Whatever people say I am, that's what I'm not"
    };
    //Artist Content
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox;
    //Artist List
    private AdjustableListView artistListBox;
    private StackPane rootPane;

    public AlbumView() {
        this.rootHorizontalBox = new HBox();
        this.artistListBox = new AdjustableListView("Albums", "Add", "Remove");
        this.artistListBox.addTestNames(TEST_ARTIST_NAMES);
        initLayout();
    }

    @Override
    protected void initLayout() {

        initArtistDisplay();

        rootHorizontalBox.getChildren().add(artistListBox);
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);
    }

    private void initArtistDisplay() {

        rootPane = new StackPane();
        artistDisplayContentPane = new StackPane();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        BackgroundImage bgImage = new BackgroundImage(
                loadImage("src/practicumopdracht/content/arcticmonkeys.jfif"
                        , 50, 50, true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);
        initArtistContentView();
    }

    private void initArtistContentView() {
        VBox albumVBox = new VBox();
        albumVBox.setAlignment(Pos.CENTER);
        artistDisplayContentPane.getChildren().add(albumVBox);
        ImageView imageView = new ImageView(loadImage("src/practicumopdracht/content/arcticmonkeys_album.jpg", 300, 300, true, true));
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        albumVBox.getChildren().add(pane);
        VBox contentVBox = new VBox();
        contentVBox.setSpacing(20);
        contentVBox.setPadding(new Insets(0,0,10,0));
        Label albumTitleLabel = new Label("Tranquility Base Hotel & Casino");
        albumTitleLabel.setWrapText(true);
        albumTitleLabel.setTextAlignment(TextAlignment.CENTER);
        albumTitleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(albumTitleLabel);

        Label dateLabel = new Label("12 / 6 / 2018");
        dateLabel.setWrapText(true);
        dateLabel.setTextAlignment(TextAlignment.CENTER);
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway;");
        contentVBox.getChildren().add(dateLabel);

        Label salesLabel = new Label("Sales: 257382");
        salesLabel.setWrapText(true);
        salesLabel.setTextAlignment(TextAlignment.CENTER);
        salesLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(salesLabel);

        Label ratingLabel = new Label("Rating: (4/5)");
        ratingLabel.setWrapText(true);
        ratingLabel.setTextAlignment(TextAlignment.CENTER);
        ratingLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(ratingLabel);

        //Buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(0, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        Button removeButton = new Button("See Wiki");
        buttonHBox.getChildren().add(removeButton);
        Button saveButton = new Button("Edit");
        buttonHBox.getChildren().add(saveButton);
        contentVBox.getChildren().add(buttonHBox);
        saveButton.setMinWidth(50);
        removeButton.setMinWidth(50);

        contentVBox.setAlignment(Pos.BOTTOM_CENTER);
        artistDisplayContentPane.getChildren().add(contentVBox);
//
//        VBox buttonHBox = new VBox();
//        buttonHBox.setSpacing(10);
//        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
//        buttonHBox.setAlignment(Pos.CENTER);
//        Button addButton = new Button("View Albums");
//        buttonHBox.getChildren().add(addButton);
//        Button removeButton = new Button("Edit Artist");
//        buttonHBox.getChildren().add(removeButton);
//        contentVBox.getChildren().add(buttonHBox);
//        buttonHBox.setMinWidth(200);

    }

    private Image loadImage(String fileUrl, int requestedWidth, int requestedHeight, boolean preserveRatio, boolean smooth) {
        try {
            FileInputStream input = new FileInputStream(fileUrl);
            return new Image(input, requestedWidth, requestedHeight, preserveRatio, smooth);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
