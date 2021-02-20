package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import practicumopdracht.AdjustableListView;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

import java.io.File;

public class ArtistView extends View {

    private CustomWindowHandle windowHandle;
    //Artist Content
    private VBox artistContentBox;
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox = new HBox();
    private ImageView favImageView;
    private Image favImageOn = MainApplication.loadImage("src/practicumopdracht/content/fav-on-32.png");
    ;
    private Image favImageOff;
    private Label contentTitle;
    private Label recordlabelTitle;
    //Artist Edit
    private VBox artistEditBox;
    private Button artistEditApplyButton;
    private Button artistEditCancelButton;
    private Button changeImageButton;

    //Artist Edit - Input
    private CheckBox favoriteCheckBox;
    private TextField artistNameTextField;
    private TextField labelNameTextField;
    //Artist List
    private AdjustableListView adjustableListBox;
    private Button viewAlbumsButton;
    private Button editArtistButton;
    private Image sortDescendingImage = MainApplication.loadImage("src/practicumopdracht/content/sorting/sort-descending.png");
    private Image sortAscendingImage = MainApplication.loadImage("src/practicumopdracht/content/sorting/sort-ascending.png");
    private Button sortButton;
    private ImageView sortImageView;
    private VBox rootVerticalBox = new VBox();
    private StackPane rootPane;

    public ArtistView() {
        this.adjustableListBox = new AdjustableListView("Artist", "Add", "Remove");
        initLayout();
    }

    public CustomWindowHandle getWindowHandle() {
        return windowHandle;
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

    public Button getChangeImageButton() {
        return changeImageButton;
    }

    public Button getSortButton() {
        return sortButton;
    }

    public AdjustableListView getAdjustableListView() {
        return adjustableListBox;
    }

    public CheckBox getFavoriteCheckBox() {
        return favoriteCheckBox;
    }

    public TextField getArtistNameTextField() {
        return artistNameTextField;
    }

    public TextField getLabelNameTextField() {
        return labelNameTextField;
    }

    public Label getArtistDisplay() {
        return contentTitle;
    }

    public Label getLabelDisplay() {
        return recordlabelTitle;
    }

    public void setFavoriteDisplayState(boolean state) {
        favImageView.setImage(state ? favImageOn : favImageOff);
    }

    @Override
    protected void initLayout() {
        initArtistDisplay();

        VBox.setVgrow(rootVerticalBox, Priority.ALWAYS);
        windowHandle = new CustomWindowHandle();
        rootHorizontalBox.prefHeightProperty().bind(MainApplication.getStage().heightProperty());
        rootVerticalBox.getChildren().add(windowHandle);
        rootVerticalBox.getChildren().add(rootHorizontalBox);
        Pane bottomEdge = new Pane();
        bottomEdge.setMinHeight(10);
        rootVerticalBox.getChildren().add(bottomEdge);
        rootHorizontalBox.getChildren().add(adjustableListBox);
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);

    }

    public void setBackgroundImageByPath(String imagePath) {
        File tempFile = new File(imagePath);
        boolean exists = tempFile.exists();
        Image image;
        if(exists){
            image = MainApplication.loadImage(imagePath);
        }else {
            image = MainApplication.loadImage("src/practicumopdracht/content/default_bg.png");
        }
        BackgroundImage bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(-1, -1, false, false, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
    }

    private void initArtistDisplay() {

        rootPane = new StackPane();
        rootPane.setMinWidth(200);
        artistDisplayContentPane = new StackPane();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        setBackgroundImageByPath("src/practicumopdracht/content/default_bg.png");

        artistDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(artistDisplayContentPane);

        sortImageView = new ImageView();
        sortButton = new Button("");
        sortButton.setGraphic(sortImageView);
        sortButton.setStyle("-fx-background-color: transparent;");
        sortButton.setMinSize(30, 30);
        StackPane.setAlignment(sortButton, Pos.CENTER_RIGHT);
        adjustableListBox.getHeaderStackPane().getChildren().add(sortButton);

        initArtistEditView();
        initArtistContentView();

        setState(ViewState.EMPTY);
    }

    public void setSortingButtonGraphic(boolean ascending) {
        Image imageToSetTo = ascending ? sortAscendingImage : sortDescendingImage;
        sortImageView.setImage(imageToSetTo);
    }

    private void initArtistContentView() {
        artistContentBox = new VBox();
        artistDisplayContentPane.getChildren().add(artistContentBox);
        artistContentBox.setAlignment(Pos.CENTER);

        favImageOn = MainApplication.loadImage("src/practicumopdracht/content/fav-on-32.png");
        favImageOff = MainApplication.loadImage("src/practicumopdracht/content/fav-off-32.png");
        favImageView = new ImageView();
        favImageView.setSmooth(true);
        artistContentBox.getChildren().add(favImageView);

        //Add / Remove Button
        contentTitle = new Label("Arctic Monkeys");
        contentTitle.setWrapText(true);
        contentTitle.setTextAlignment(TextAlignment.CENTER);
        contentTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 30; -fx-font-family: Broadway");
        artistContentBox.getChildren().add(contentTitle);
        recordlabelTitle = new Label("Domino Records");
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
        artistNameTextField = (TextField) artistNameHBox.getChildren().get(1);
        artistEditBox.getChildren().add(artistNameHBox);
        VBox labelNameHBox = UIComponents.createTextfieldGroup("Label name:", "Type label name here...");
        labelNameTextField = (TextField) labelNameHBox.getChildren().get(1);
        artistEditBox.getChildren().add(labelNameHBox);
        HBox favoriteHBox = UIComponents.createFavoriteGroup("Favorite:");
        favoriteCheckBox = (CheckBox) favoriteHBox.getChildren().get(1);
        artistEditBox.getChildren().add(favoriteHBox);
        changeImageButton = new Button("Change Album Image");
        changeImageButton.setMaxWidth(150);
        VBox.setMargin(changeImageButton, new Insets(10, 0, 0, 0));
        artistEditBox.getChildren().add(changeImageButton);
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
    public void setState(ViewState state) {
        artistContentBox.setVisible(state != ViewState.EMPTY && state == ViewState.VIEW);
        artistEditBox.setVisible(state != ViewState.EMPTY && state == ViewState.EDIT);
    }
}
