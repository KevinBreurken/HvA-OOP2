package practicumopdracht.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

public class ArtistView extends GeneralContentView {

    //Sorting
    private Button sortButton = new Button("");
    private ImageView sortImageView = new ImageView();
    private Image sortDescendingImage = MainApplication.loadImage("src/practicumopdracht/content/sorting/sort-descending.png");
    private Image sortAscendingImage = MainApplication.loadImage("src/practicumopdracht/content/sorting/sort-ascending.png");
    //Content
    private ImageView favImageView;
    private Image favImageOn = MainApplication.loadImage("src/practicumopdracht/content/fav-on-32.png");
    private Image favImageOff = MainApplication.loadImage("src/practicumopdracht/content/fav-off-32.png");
    private Label contentTitle = new Label("Artist Name");
    private Label recordlabelTitle = new Label("Label Name");
    private Button viewAlbumsButton = new Button("View Albums");
    private Button editArtistButton = new Button("Edit Artist");
    //Edit
    private Button artistEditApplyButton;
    private Button artistEditCancelButton;
    private Button changeImageButton = new Button("Change Album Image");
    //Edit - Input
    private CheckBox favoriteCheckBox;
    private TextField artistNameTextField;
    private TextField labelNameTextField;

    public ArtistView() {
        this.adjustableListBox.getTitleLabel().setText("Artist");

        sortButton.setGraphic(sortImageView);
        sortButton.setStyle("-fx-background-color: transparent;");
        sortButton.setMinSize(30, 30);
        StackPane.setAlignment(sortButton, Pos.CENTER_RIGHT);
        adjustableListBox.getHeaderStackPane().getChildren().add(sortButton);
        setSortingButtonGraphic(false);

        initContentView();
        initEditView();

        setState(ViewState.EMPTY);
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

    private void initContentView() {
        contentViewBox.setAlignment(Pos.CENTER);

        //Favorite icon
        favImageView = new ImageView();
        favImageView.setSmooth(true);
        setFavoriteDisplayState(true);

        //Artist title
        contentTitle.setWrapText(true);
        contentTitle.setTextAlignment(TextAlignment.CENTER);
        contentTitle.setAlignment(Pos.CENTER);
        contentTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 30; -fx-font-family: Broadway");

        //Label name
        recordlabelTitle.setWrapText(true);
        recordlabelTitle.setTextAlignment(TextAlignment.CENTER);
        recordlabelTitle.setAlignment(Pos.CENTER);
        recordlabelTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");

        //Button group
        VBox buttonHBox = new VBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(viewAlbumsButton, editArtistButton);
        buttonHBox.setMinWidth(200);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(favImageView,0,0);
        GridPane.setHalignment(favImageView, HPos.CENTER);
        gridPane.add(contentTitle,0,1);
        GridPane.setHalignment(contentTitle, HPos.CENTER);
        gridPane.add(recordlabelTitle,0,2);
        GridPane.setHalignment(recordlabelTitle, HPos.CENTER);
        gridPane.add(buttonHBox,0,3);
        GridPane.setHalignment(buttonHBox, HPos.CENTER);

        contentViewBox.getChildren().add(gridPane);
    }

    private void initEditView() {
        editViewBox.setAlignment(Pos.CENTER);
        //Name
        VBox artistNameHBox = UIComponents.createTextfieldGroup("Artist name:", "Type artist name here...");
        artistNameTextField = (TextField) artistNameHBox.getChildren().get(1);
        //Label
        VBox labelNameHBox = UIComponents.createTextfieldGroup("Label name:", "Type label name here...");
        labelNameTextField = (TextField) labelNameHBox.getChildren().get(1);
        //Favorite
        HBox favoriteHBox = UIComponents.createFavoriteGroup("Favorite:");
        favoriteCheckBox = (CheckBox) favoriteHBox.getChildren().get(1);
        //Change image button
        changeImageButton.setMaxWidth(150);
        VBox.setMargin(changeImageButton, new Insets(10, 0, 0, 0));

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        artistEditApplyButton = (Button) editButtonHBox.getChildren().get(0);
        artistEditCancelButton = (Button) editButtonHBox.getChildren().get(1);
        editViewBox.getChildren().addAll(artistNameHBox, labelNameHBox, favoriteHBox, changeImageButton, editButtonHBox);
    }

    public void setSortingButtonGraphic(boolean ascending) {
        Image imageToSetTo = ascending ? sortAscendingImage : sortDescendingImage;
        sortImageView.setImage(imageToSetTo);
    }

    public void setFavoriteDisplayState(boolean state) {
        favImageView.setImage(state ? favImageOn : favImageOff);
    }

    @Override
    public Parent getRoot() {
        return super.getRoot();
    }

}
