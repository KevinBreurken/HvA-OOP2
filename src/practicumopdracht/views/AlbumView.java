package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

public class AlbumView extends View {
    private final static String[] TEST_ALBUM_NAMES = new String[]{
            "Tranquility Base Hotel & Casino", "AM", "Suck it and see", "Humbug", "Favourite worst nightmare",
            "Whatever people say I am, that's what I'm not"
    };
    private StackPane rootPane;
    private Button backToArtistButton;
    //Album Content
    private VBox albumVBox;
    private HBox rootHorizontalBox;
    private StackPane artistDisplayContentPane;
    private Button editAlbumButton;
    private Button wikiButton;

    private Button albumEditApplyButton;
    private Button albumEditCancelButton;
    private Button changeImageButton;
    private Button ratingDecreaseButton;

    private Button ratingIncreaseButton;

    //Album Edit
    private GridPane gridPane;
    //Album List
    private AdjustableListView adjustableListBox;
    private VBox rootVerticalBox;
    private VBox contentVBox;

    public AlbumView() {
        this.rootVerticalBox = new VBox();
        this.rootHorizontalBox = new HBox();
        this.adjustableListBox = new AdjustableListView("Albums", "Add", "Remove");
        this.adjustableListBox.addTestNames(TEST_ALBUM_NAMES);
        initLayout();
    }

    public Button getRatingDecreaseButton() {
        return ratingDecreaseButton;
    }

    public Button getRatingIncreaseButton() {
        return ratingIncreaseButton;
    }

    public Button getChangeImageButton() {
        return changeImageButton;
    }

    public Button getEditAlbumButton() {
        return editAlbumButton;
    }

    public Button getWikiButton() {
        return wikiButton;
    }

    public AdjustableListView getAdjustableListBox() {
        return adjustableListBox;
    }

    public Button getBackToArtistButton() {
        return backToArtistButton;
    }

    public Button getAlbumEditApplyButton() {
        return albumEditApplyButton;
    }

    public Button getAlbumEditCancelButton() {
        return albumEditCancelButton;
    }

    @Override
    protected void initLayout() {
        initArtistDisplay();
        VBox.setVgrow(rootVerticalBox, Priority.ALWAYS);
        rootHorizontalBox.prefHeightProperty().bind(MainApplication.getStage().heightProperty());
        rootVerticalBox.getChildren().add(new CustomWindowHandle());
        rootVerticalBox.getChildren().add(rootHorizontalBox);
        Pane bottomEdge = new Pane();
        bottomEdge.setMinHeight(10);
        rootVerticalBox.getChildren().add(bottomEdge);
        rootHorizontalBox.getChildren().add(adjustableListBox);
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
        adjustableListBox.addToTop(albumBox);
        albumBox.setMaxWidth(999);

        initArtistEditView();
        initArtistContentView();
        setState(VIEW_STATE.VIEW);
    }

    private void initArtistEditView() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        artistDisplayContentPane.getChildren().add(gridPane);

        StackPane pane = new StackPane();
        pane.setMinSize(100, 100);
        gridPane.getChildren().add(pane);

        VBox groupBox = new VBox();
        groupBox.setSpacing(5);
        pane.getChildren().add(groupBox);
        VBox artistNameHBox = UIComponents.createTextfieldGroup("Album name:", "Type album name here...");
        groupBox.getChildren().add(artistNameHBox);

        VBox wikilinkTextArea = UIComponents.createTextAreaGroup("Wiki Link:", "Type wiki URL here...");
        groupBox.getChildren().add(wikilinkTextArea);

        VBox datePicker = UIComponents.createDatepickerGroup("Release date:");
        groupBox.getChildren().add(datePicker);

        VBox intSelector = UIComponents.createIntSelectorGroup("Rating:");
        groupBox.getChildren().add(intSelector);
        ratingDecreaseButton = (Button) intSelector.getChildren().get(0);
        ratingIncreaseButton = (Button) intSelector.getChildren().get(2);

        changeImageButton = new Button("Change Album Image");
        changeImageButton.setMaxWidth(300);
        groupBox.getChildren().add(changeImageButton);

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        albumEditApplyButton = (Button) editButtonHBox.getChildren().get(0);
        albumEditCancelButton = (Button) editButtonHBox.getChildren().get(1);
        groupBox.getChildren().add(editButtonHBox);
        gridPane.setVisible(false);
    }

    private void initArtistContentView() {
        albumVBox = new VBox();
        albumVBox.setAlignment(Pos.CENTER);
        artistDisplayContentPane.getChildren().add(albumVBox);
        ImageView imageView = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/arcticmonkeys_album.jpg", 300, 300, true, true));
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        albumVBox.getChildren().add(pane);
        contentVBox = new VBox();
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
        wikiButton = new Button("See Wiki");
        buttonHBox.getChildren().add(wikiButton);
        editAlbumButton = new Button("Edit");
        buttonHBox.getChildren().add(editAlbumButton);
        contentVBox.getChildren().add(buttonHBox);
        editAlbumButton.setMinWidth(50);
        wikiButton.setMinWidth(50);

        backToArtistButton = new Button("");
        backToArtistButton.setMinSize(50, 50);
        rootPane.setAlignment(Pos.TOP_LEFT);
        rootPane.getChildren().add(backToArtistButton);
        backToArtistButton.setStyle("-fx-background-color: transparent;");
        backToArtistButton.setPadding(new Insets(0, 0, 0, 10));
        backToArtistButton.setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/navigation/back.png")));

        contentVBox.setAlignment(Pos.BOTTOM_CENTER);
        albumVBox.setVisible(false);
        artistDisplayContentPane.getChildren().add(contentVBox);

    }

    @Override
    public Parent getRoot() {
        return rootVerticalBox;
    }

    @Override
    public void setState(VIEW_STATE state) {
        albumVBox.setVisible(state == VIEW_STATE.VIEW);
        contentVBox.setVisible(state == VIEW_STATE.VIEW);
        gridPane.setVisible(state == VIEW_STATE.EDIT);
    }
}
