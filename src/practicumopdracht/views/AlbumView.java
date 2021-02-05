package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlbumView extends View {
    private final static String[] TEST_ALBUM_NAMES = new String[]{
            "Tranquility Base Hotel & Casino", "AM", "Suck it and see", "Humbug", "Favourite worst nightmare",
            "Whatever people say I am, that's what I'm not"
    };
    public static boolean openEditPanel = false;
    //Artist Content
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox;
    //Artist List
    private AdjustableListView artistListBox;
    private StackPane rootPane;

    public AlbumView() {
        this.rootHorizontalBox = new HBox();
        this.artistListBox = new AdjustableListView("Albums", "Add", "Remove");
        this.artistListBox.addTestNames(TEST_ALBUM_NAMES);
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
                MainApplication.loadImage("src/practicumopdracht/content/arcticmonkeys.jfif"
                        , 24, 24, true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);

        ComboBox albumBox = new ComboBox();
        albumBox.getItems().addAll(ArtistView.TEST_ARTIST_NAMES);
        artistListBox.addToTop(albumBox);
        albumBox.setMaxWidth(999);

        Button backButton = new Button("Back to Artist");
        backButton.setMaxWidth(999);
        artistListBox.addToTop(backButton);

        if (openEditPanel)
            initArtistEditView();
        else
            initArtistContentView();
    }

    private void initArtistEditView() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        artistDisplayContentPane.getChildren().add(gridPane);

        StackPane pane = new StackPane();
        pane.setMinSize(100,100);
        gridPane.getChildren().add(pane);

        VBox groupBox = new VBox();
        groupBox.setSpacing(5);
        pane.getChildren().add(groupBox);
        VBox artistNameHBox =  UIComponents.createTextfieldGroup("Album name:","Type album name here...");
        groupBox.getChildren().add(artistNameHBox);

        VBox wikilinkTextArea = UIComponents.createTextAreaGroup("Wiki Link:","Type wiki URL here...");
        groupBox.getChildren().add(wikilinkTextArea);

        VBox datePicker =  UIComponents.createDatepickerGroup("Release date:");
        groupBox.getChildren().add(datePicker);

        VBox intSelector =  UIComponents.createIntSelectorGroup("Rating:");
        groupBox.getChildren().add(intSelector);

        Button changeImageButton = new Button("Change Album Image");
        changeImageButton.setMaxWidth(300);
        groupBox.getChildren().add(changeImageButton);

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        groupBox.getChildren().add(editButtonHBox);
    }

    private void initArtistContentView() {
        VBox albumVBox = new VBox();
        albumVBox.setAlignment(Pos.CENTER);
        artistDisplayContentPane.getChildren().add(albumVBox);
        ImageView imageView = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/arcticmonkeys_album.jpg", 300, 300, true, true));
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        albumVBox.getChildren().add(pane);
        VBox contentVBox = new VBox();
        contentVBox.setSpacing(15);
        contentVBox.setPadding(new Insets(0, 0, 10, 0));
        Label albumTitleLabel = new Label("Tranquility Base Hotel & Casino");
        albumTitleLabel.setWrapText(true);
        albumTitleLabel.setMaxWidth(300);
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
        buttonHBox.setPadding(new Insets(0, 0, 10, 0));
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

    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
