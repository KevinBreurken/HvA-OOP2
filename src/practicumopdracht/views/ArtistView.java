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

public class ArtistView extends View {

    //Artist Content
    private VBox artistContentBox;
    private StackPane artistDisplayContentPane;
    private HBox rootHorizontalBox = new HBox();
    private ImageView favImageView;
    private Image favImageOn;
    private Image favImageOff;
    private Label contentTitle;
    private Label recordlabelTitle;

    //Artist Edit
    private VBox artistEditBox;
    private Button artistEditApplyButton;
    private Button artistEditCancelButton;
    //Artist Edit - Input
    private CheckBox favoriteCheckBox;
    private TextField artistNameTextField;
    private TextField labelNameTextField;

    //Artist List
    private AdjustableListView adjustableListBox;
    private Button viewAlbumsButton;
    private Button editArtistButton;
    private VBox rootVerticalBox = new VBox();

    public ArtistView() {
        this.adjustableListBox = new AdjustableListView("Artist", "Add", "Remove");
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

    public void setFavoriteDisplayState(boolean state){
        favImageView.setImage(state ? favImageOn : favImageOff);
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

        StackPane rootPane = new StackPane();
        rootPane.setMinWidth(200);
        artistDisplayContentPane = new StackPane();

        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        BackgroundImage bgImage = new BackgroundImage(
                MainApplication.loadImage("src/practicumopdracht/content/default_bg.png"),
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

        setState(VIEW_STATE.EMPTY);
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
        artistContentBox.setVisible(state != VIEW_STATE.EMPTY && state == VIEW_STATE.VIEW);
        artistEditBox.setVisible(state != VIEW_STATE.EMPTY && state == VIEW_STATE.EDIT);
    }
}
