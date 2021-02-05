package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;
import practicumopdracht.Main;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ArtistView extends View {

    public final static String[] TEST_ARTIST_NAMES = new String[]{
            "Anamanaguchi", "Periphery", "Haywyre", "Maduk", "Chase & Status", "Darren Korb",
            "Paramore", "Spiritbox", "Arctic Monkeys", "The Kooks", "Minus the Bear",
            "Infected Mushrooms", "Jamiroquai", "David Maxim Micic", "Iron Maiden", "Tesseract",
            "Dua Lipa", "Dance Gavin Dance", "Carpenter Brut", "Crazy Astronaut"
    };
    public static boolean openEditPanel = false;
    //Artist Content
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox;
    //Artist List
    private AdjustableListView artistListBox;

    public ArtistView() {
        this.rootHorizontalBox = new HBox();
        this.artistListBox = new AdjustableListView("Artist", "Add", "Remove");
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
        if (ArtistView.openEditPanel)
            initArtistEditView();
        else
            initArtistContentView();
    }

    private void initArtistContentView() {
        VBox contentVBox = new VBox();
        artistDisplayContentPane.getChildren().add(contentVBox);
        contentVBox.setAlignment(Pos.CENTER);

        ImageView favImage = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/fav-on-32.png"));
        favImage.setSmooth(true);
        contentVBox.getChildren().add(favImage);
        //Add / Remove Button
        Label contentTitle = new Label("Arctic Monkeys");
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
        VBox artistNameHBox =  UIComponents.createTextfieldGroup("Artist name:","Type artist name here...");
        contentVBox.getChildren().add(artistNameHBox);
        VBox labelNameHBox =  UIComponents.createTextfieldGroup("Label name:","Type label name here...");
        contentVBox.getChildren().add(labelNameHBox);
        HBox favoriteHBox = UIComponents.createfavoriteGroup("Favorite:");
        contentVBox.getChildren().add(favoriteHBox);

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        contentVBox.getChildren().add(editButtonHBox);
    }

    @Override
    public Parent getRoot() {
        return rootHorizontalBox;
    }
}
