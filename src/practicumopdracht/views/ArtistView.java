package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

public class ArtistView extends View {

    public final static String[] TEST_ARTIST_NAMES = new String[]{
            "Anamanaguchi", "Periphery", "Haywyre", "Maduk", "Chase & Status", "Darren Korb",
            "Paramore", "Spiritbox", "Arctic Monkeys", "The Kooks", "Minus the Bear",
            "Infected Mushrooms", "Jamiroquai", "David Maxim Micic", "Iron Maiden", "Tesseract",
            "Dua Lipa", "Dance Gavin Dance", "Carpenter Brut", "Crazy Astronaut"
    };
    public static boolean openEditPanel = false;
    //Artist Content
    private VBox artistContentBox;
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox;
    //Artist Edit
    private VBox artistEditBox;
    private Button artistEditApplyButton;
    private Button artistEditCancelButton;

    //Artist List
    private AdjustableListView adjustableListBox;
    private Button viewAlbumsButton;
    private Button editArtistButton;
    private VBox rootVerticalBox;

    public ArtistView() {
        this.rootVerticalBox = new VBox();
        this.rootHorizontalBox = new HBox();
        this.adjustableListBox = new AdjustableListView("Artist", "Add", "Remove");
        this.adjustableListBox.addTestNames(TEST_ARTIST_NAMES);
        initLayout();
    }

    public Button getArtistEditApplyButton() {
        return artistEditApplyButton;
    }

    public Button getArtistEditCancelButton() {
        return artistEditCancelButton;
    }

    public Button getViewAlbumsButton() {
        return viewAlbumsButton;
    }

    public Button getEditArtistButton() {
        return editArtistButton;
    }

    public AdjustableListView getAdjustableListBox() {
        return adjustableListBox;
    }

    @Override
    protected void initLayout() {
        initArtistDisplay();

        Pane pane = new Pane();
        pane.setMinHeight(20);
        rootVerticalBox.getChildren().add(pane);
        rootVerticalBox.getChildren().add(rootHorizontalBox);
        rootHorizontalBox.getChildren().add(adjustableListBox);
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);
    }

    private void initArtistDisplay() {

        StackPane rootPane = new StackPane();
        rootPane.setMinWidth(200);
        artistDisplayContentPane = new StackPane();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        BackgroundImage bgImage = new BackgroundImage(
                MainApplication.loadImage("src/practicumopdracht/content/arcticmonkeys.jfif"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);

        initArtistEditView();
        initArtistContentView();

        setState(VIEW_STATE.VIEW);
    }

    private void initArtistContentView() {
        artistContentBox = new VBox();
        artistDisplayContentPane.getChildren().add(artistContentBox);
        artistContentBox.setAlignment(Pos.CENTER);

        ImageView favImage = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/fav-on-32.png"));
        favImage.setSmooth(true);
        artistContentBox.getChildren().add(favImage);
        //Add / Remove Button
        Label contentTitle = new Label("Arctic Monkeys");
        contentTitle.setWrapText(true);
        contentTitle.setTextAlignment(TextAlignment.CENTER);
        contentTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 30; -fx-font-family: Broadway");
        artistContentBox.getChildren().add(contentTitle);

        Label recordlabelTitle = new Label("Domino Records");
        recordlabelTitle.setWrapText(true);
        recordlabelTitle.setTextAlignment(TextAlignment.CENTER);
        recordlabelTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        artistContentBox.getChildren().add(recordlabelTitle);

        VBox buttonHBox = new VBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        viewAlbumsButton = new Button("View Albums");
        buttonHBox.getChildren().add(viewAlbumsButton);
        editArtistButton = new Button("Edit Artist");
        buttonHBox.getChildren().add(editArtistButton);
        artistContentBox.getChildren().add(buttonHBox);
        buttonHBox.setMinWidth(200);
        artistContentBox.setVisible(false);
    }

    private void initArtistEditView() {
        artistEditBox = new VBox();
        artistDisplayContentPane.getChildren().add(artistEditBox);
        artistEditBox.setAlignment(Pos.CENTER);
        VBox artistNameHBox = UIComponents.createTextfieldGroup("Artist name:", "Type artist name here...");
        artistEditBox.getChildren().add(artistNameHBox);
        VBox labelNameHBox = UIComponents.createTextfieldGroup("Label name:", "Type label name here...");
        artistEditBox.getChildren().add(labelNameHBox);
        HBox favoriteHBox = UIComponents.createfavoriteGroup("Favorite:");
        artistEditBox.getChildren().add(favoriteHBox);

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        artistEditApplyButton = (Button) editButtonHBox.getChildren().get(0);
        artistEditCancelButton = (Button) editButtonHBox.getChildren().get(1);
        artistEditBox.getChildren().add(editButtonHBox);
        artistEditBox.setVisible(false);
    }

    @Override
    public Parent getRoot() {
        return rootVerticalBox;
    }

    @Override
    public void setState(VIEW_STATE state) {
        artistContentBox.setVisible(state == VIEW_STATE.VIEW);
        artistEditBox.setVisible(state == VIEW_STATE.EDIT);
    }
}
