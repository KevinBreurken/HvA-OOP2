package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

public class AlbumView extends GeneralContentView {

    private ComboBox artistComboBox = new ComboBox();

    //Sorting
    private RadioButton alphabetDescendingRadioButton;
    private RadioButton alphabetAscendingRadioButton;
    private RadioButton salesDescendingRadioButton;
    private RadioButton salesAscendingRadioButton;

    //Content
    private Label albumTitleLabel = new Label("Album Title");
    private Label dateLabel = new Label("00 / 00 / 0000");
    private Label salesLabel = new Label("Sales: 000000");
    private Label ratingLabel = new Label("Rating: (0/0)");
    private Button wikiButton = new Button("See Wiki");
    private Button editAlbumButton = new Button("Edit");
    private Button backToArtistButton = new Button("");

    //Edit
    private GridPane gridPane;
    private Button albumEditApplyButton;
    private Button albumEditCancelButton;
    private Button changeImageButton = new Button("Change Album Image");
    private Button ratingDecreaseButton;
    private Button ratingIncreaseButton;
    //Album Edit Input
    private TextField nameInputField;
    private TextField albumSalesTextField;
    private TextArea wikiLinkInputField;
    private DatePicker dateInputField;
    private ComboBox editArtistComboBox;
    private TextField ratingTextField;

    public AlbumView() {
        this.adjustableListBox.getTitleLabel().setText("Album");

        artistComboBox.setMaxWidth(100);
        adjustableListBox.setMaxWidth(250);
        adjustableListBox.addToTop(artistComboBox);

        initContentView();
        initEditView();
        initSortingRadioButtons();
        setState(ViewState.EDIT);
    }

    public RadioButton getAlphabetDescendingRadioButton() {
        return alphabetDescendingRadioButton;
    }

    public RadioButton getAlphabetAscendingRadioButton() {
        return alphabetAscendingRadioButton;
    }

    public RadioButton getSalesDescendingRadioButton() {
        return salesDescendingRadioButton;
    }

    public RadioButton getSalesAscendingRadioButton() {
        return salesAscendingRadioButton;
    }

    public ComboBox getEditArtistComboBox() {
        return editArtistComboBox;
    }

    public ComboBox getArtistComboBox() {
        return artistComboBox;
    }

    public Label getAlbumTitleLabel() {
        return albumTitleLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public Label getSalesLabel() {
        return salesLabel;
    }

    public Label getRatingLabel() {
        return ratingLabel;
    }

    public TextField getAlbumSalesTextField() {
        return albumSalesTextField;
    }

    public TextField getRatingTextField() {
        return ratingTextField;
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

    public Button getBackToArtistButton() {
        return backToArtistButton;
    }

    public Button getAlbumEditApplyButton() {
        return albumEditApplyButton;
    }

    public Button getAlbumEditCancelButton() {
        return albumEditCancelButton;
    }

    public TextField getNameInputField() {
        return nameInputField;
    }

    public TextArea getWikiLinkInputField() {
        return wikiLinkInputField;
    }

    public DatePicker getDateInputField() {
        return dateInputField;
    }

    private void initSortingRadioButtons() {
        StackPane headerPane = adjustableListBox.getHeaderStackPane();
        final int groupSize = 2;

        //Left Radio button group
        HBox leftRadioButtonGroup = new HBox();
        StackPane.setAlignment(leftRadioButtonGroup, Pos.CENTER_LEFT);
        headerPane.getChildren().add(leftRadioButtonGroup);
        leftRadioButtonGroup.setMaxWidth(90);
        ToggleGroup alphabetToggleGroup = new ToggleGroup();

        VBox firstRadioElement = createRadioButtonElement("src/practicumopdracht/content/sorting/sort-descending-name.png", alphabetToggleGroup);
        VBox secondRadioElement = createRadioButtonElement("src/practicumopdracht/content/sorting/sort-ascending-name.png", alphabetToggleGroup);
        leftRadioButtonGroup.getChildren().addAll(firstRadioElement, secondRadioElement);
        firstRadioElement.prefWidthProperty().bind(leftRadioButtonGroup.widthProperty().divide(groupSize));
        secondRadioElement.prefWidthProperty().bind(leftRadioButtonGroup.widthProperty().divide(groupSize));
        alphabetDescendingRadioButton = (RadioButton) firstRadioElement.getChildren().get(1);
        alphabetDescendingRadioButton.setSelected(true);
        alphabetAscendingRadioButton = (RadioButton) secondRadioElement.getChildren().get(1);

        //Right Radio button group
        HBox rightRadioButtonGroup = new HBox();
        StackPane.setAlignment(rightRadioButtonGroup, Pos.CENTER_RIGHT);
        headerPane.getChildren().add(rightRadioButtonGroup);
        rightRadioButtonGroup.setMaxWidth(90);
        ToggleGroup salesToggleGroup = new ToggleGroup();
        VBox thirdRadioElement = createRadioButtonElement("src/practicumopdracht/content/sorting/sort-descending-sales.png", salesToggleGroup);
        VBox fourthRadioElement = createRadioButtonElement("src/practicumopdracht/content/sorting/sort-ascending-sales.png", salesToggleGroup);
        rightRadioButtonGroup.getChildren().addAll(thirdRadioElement, fourthRadioElement);
        thirdRadioElement.prefWidthProperty().bind(rightRadioButtonGroup.widthProperty().divide(groupSize));
        fourthRadioElement.prefWidthProperty().bind(rightRadioButtonGroup.widthProperty().divide(groupSize));
        salesDescendingRadioButton = (RadioButton) thirdRadioElement.getChildren().get(1);
        salesDescendingRadioButton.setSelected(true);
        salesAscendingRadioButton = (RadioButton) fourthRadioElement.getChildren().get(1);
    }

    private VBox createRadioButtonElement(String imagePath, ToggleGroup group) {
        VBox vBoxElement = new VBox();
        vBoxElement.setPadding(new Insets(5, 0, 5, 0));
        Image imageIcon = MainApplication.loadImage(imagePath);
        ImageView view = new ImageView(imageIcon);
        RadioButton radioButton = new RadioButton();
        radioButton.setToggleGroup(group);
        view.setSmooth(true);
        vBoxElement.setAlignment(Pos.TOP_CENTER);
        vBoxElement.getChildren().addAll(view, radioButton);
        return vBoxElement;
    }

    private void initContentView() {
        contentViewBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/default_album.png",
                300, 300, true, true));
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
        contentViewBox.getChildren().add(pane);

        VBox contentVBox = new VBox();
        contentVBox.setAlignment(Pos.BOTTOM_CENTER);
        contentVBox.setSpacing(15);
        contentVBox.setPadding(new Insets(0, 0, 10, 0));
        pane.getChildren().add(contentVBox);

        //Album title label
        albumTitleLabel.setEffect(UIComponents.createDropShadowEffect());
        albumTitleLabel.setWrapText(true);
        albumTitleLabel.setMaxWidth(300);
        albumTitleLabel.setAlignment(Pos.CENTER);
        albumTitleLabel.setTextAlignment(TextAlignment.CENTER);
        albumTitleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");

        //Date label
        dateLabel = new Label("12 / 6 / 2018");
        dateLabel.setEffect(UIComponents.createDropShadowEffect());
        dateLabel.setWrapText(true);
        dateLabel.setTextAlignment(TextAlignment.CENTER);
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway;");

        //Sales label
        salesLabel = new Label("Sales: 257382");
        salesLabel.setEffect(UIComponents.createDropShadowEffect());
        salesLabel.setWrapText(true);
        salesLabel.setTextAlignment(TextAlignment.CENTER);
        salesLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");

        //Rating label
        ratingLabel = new Label("Rating: (4/5)");
        ratingLabel.setEffect(UIComponents.createDropShadowEffect());
        ratingLabel.setWrapText(true);
        ratingLabel.setTextAlignment(TextAlignment.CENTER);
        ratingLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");

        //Buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(0, 0, 10, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().add(wikiButton);
        buttonHBox.getChildren().add(editAlbumButton);
        editAlbumButton.setMinWidth(50);
        wikiButton.setMinWidth(50);

        //Back to artist button
        backToArtistButton = new Button("");
        backToArtistButton.setMinSize(50, 50);
        backToArtistButton.setStyle("-fx-background-color: transparent;");
        backToArtistButton.setPadding(new Insets(0, 0, 0, 10));
        backToArtistButton.setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/navigation/back.png")));
        rootPane.setAlignment(Pos.TOP_LEFT);
        rootPane.getChildren().add(backToArtistButton);

        contentVBox.getChildren().addAll(albumTitleLabel, dateLabel, salesLabel, ratingLabel, buttonHBox);
    }

    private void initEditView() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        editViewBox.getChildren().add(gridPane);

        StackPane pane = new StackPane();
        pane.setMinSize(100, 100);
        gridPane.getChildren().add(pane);

        VBox groupBox = new VBox();
        groupBox.setSpacing(5);
        pane.getChildren().add(groupBox);

        VBox comboVBox = UIComponents.createComboBoxGroup("From Artist:");
        editArtistComboBox = (ComboBox) comboVBox.getChildren().get(1);
        groupBox.getChildren().add(comboVBox);

        VBox albumNameHBox = UIComponents.createTextfieldGroup("Album name:", "Type album name here...");
        nameInputField = (TextField) albumNameHBox.getChildren().get(1);
        groupBox.getChildren().add(albumNameHBox);

        VBox albumSalesVBox = UIComponents.createTextfieldGroup("Total sales:", "Type total sales value here...");
        albumSalesTextField = (TextField) albumSalesVBox.getChildren().get(1);
        groupBox.getChildren().add(albumSalesVBox);

        VBox wikilinkTextArea = UIComponents.createTextAreaGroup("Wiki Link:", "Type wiki URL here...");
        wikiLinkInputField = (TextArea) wikilinkTextArea.getChildren().get(1);
        groupBox.getChildren().add(wikilinkTextArea);

        VBox datePicker = UIComponents.createDatepickerGroup("Release date:");
        dateInputField = (DatePicker) datePicker.getChildren().get(1);
        groupBox.getChildren().add(datePicker);

        VBox intSelector = UIComponents.createIntSelectorGroup("Rating:");
        groupBox.getChildren().add(intSelector);

        HBox rootSelector = (HBox) intSelector.getChildren().get(1);
        ratingDecreaseButton = (Button) rootSelector.getChildren().get(0);
        ratingTextField = (TextField) rootSelector.getChildren().get(1);
        ratingIncreaseButton = (Button) rootSelector.getChildren().get(2);

        changeImageButton.setMaxWidth(300);
        changeImageButton.setDisable(true);
        groupBox.getChildren().add(changeImageButton);

        //Buttons
        HBox editButtonHBox = UIComponents.createEditButtonGroup();
        albumEditApplyButton = (Button) editButtonHBox.getChildren().get(0);
        albumEditCancelButton = (Button) editButtonHBox.getChildren().get(1);
        groupBox.getChildren().add(editButtonHBox);
    }

    @Override
    public Parent getRoot() {
        return super.getRoot();
    }
}
