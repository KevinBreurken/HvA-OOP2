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
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.UIComponents;

public class AlbumView extends View {

    private CustomWindowHandle windowHandle;
    private StackPane rootPane;
    private HBox rootHorizontalBox;
    private VBox rootVerticalBox;
    //Album Content
    private StackPane albumDisplayContentPane;
    private VBox albumVBox;
    private Button backToArtistButton;
    private Button editAlbumButton;
    private Button wikiButton;
    private VBox contentVBox;
    private Label albumTitleLabel;
    private Label dateLabel;
    private Label salesLabel;
    private Label ratingLabel;
    //Album Edit
    private GridPane gridPane;
    private Button albumEditApplyButton;
    private Button albumEditCancelButton;
    private Button changeImageButton;
    private Button ratingDecreaseButton;
    private Button ratingIncreaseButton;
    //Album Edit Input
    private TextField nameInputField;
    private TextField albumSalesTextField;
    private TextArea wikiLinkInputField;
    private DatePicker dateInputField;
    private ComboBox editArtistComboBox;
    private TextField ratingTextField;
    //Album List
    private AdjustableListView adjustableListBox;
    private ComboBox artistComboBox;
    //Sorting
    private RadioButton alphabetDescendingRadioButton;
    private RadioButton alphabetAscendingRadioButton;
    private RadioButton salesDescendingRadioButton;
    private RadioButton salesAscendingRadioButton;

    public AlbumView() {
        this.rootVerticalBox = new VBox();
        this.rootHorizontalBox = new HBox();
        this.adjustableListBox = new AdjustableListView("Albums", "Add", "Remove");

        initLayout();
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

    public CustomWindowHandle getWindowHandle() {
        return windowHandle;
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

    public AdjustableListView getAdjustableListView() {
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

    public TextField getNameInputField() {
        return nameInputField;
    }

    public TextArea getWikiLinkInputField() {
        return wikiLinkInputField;
    }

    public DatePicker getDateInputField() {
        return dateInputField;
    }

    @Override
    protected void initLayout() {
        windowHandle = new CustomWindowHandle();
        initAlbumDisplay();
        rootHorizontalBox.prefHeightProperty().bind(MainApplication.getStage().heightProperty());
        rootVerticalBox.getChildren().add(windowHandle);
        rootVerticalBox.getChildren().add(rootHorizontalBox);
        Pane bottomEdge = new Pane();
        bottomEdge.setMinHeight(10);
        rootVerticalBox.getChildren().add(bottomEdge);
        rootHorizontalBox.getChildren().add(adjustableListBox);
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);

    }

    private void initAlbumDisplay() {
        rootPane = new StackPane();
        albumDisplayContentPane = new StackPane();

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
        albumDisplayContentPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        rootHorizontalBox.getChildren().add(rootPane);
        rootPane.getChildren().add(albumDisplayContentPane);

        artistComboBox = new ComboBox();
        artistComboBox.setMaxWidth(100);
        adjustableListBox.setMaxWidth(250);
        adjustableListBox.addToTop(artistComboBox);

        initAlbumEditView();
        initAlbumContentView();
        initSortingRadioButtons();
        setState(ViewState.EMPTY);
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
        VBox firstRadioElement = createRadioButtonElement("src/practicumopdracht/content/sort-descending-name.png", alphabetToggleGroup);
        VBox secondRadioElement = createRadioButtonElement("src/practicumopdracht/content/sort-ascending-name.png", alphabetToggleGroup);
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
        VBox thirdRadioElement = createRadioButtonElement("src/practicumopdracht/content/sort-descending-sales.png", salesToggleGroup);
        VBox fourthRadioElement = createRadioButtonElement("src/practicumopdracht/content/sort-ascending-sales.png", salesToggleGroup);
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

    private void initAlbumEditView() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        albumDisplayContentPane.getChildren().add(gridPane);

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
        System.out.println(ratingTextField.getText());
        ratingIncreaseButton = (Button) rootSelector.getChildren().get(2);

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

    private void initAlbumContentView() {
        albumVBox = new VBox();
        albumVBox.setAlignment(Pos.CENTER);
        albumDisplayContentPane.getChildren().add(albumVBox);
        ImageView imageView = new ImageView(MainApplication.loadImage("src/practicumopdracht/content/default_album.png", 300, 300, true, true));
        imageView.setPreserveRatio(true);

        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        albumVBox.getChildren().add(pane);
        contentVBox = new VBox();
        contentVBox.setSpacing(15);
        contentVBox.setPadding(new Insets(0, 0, 10, 0));
        albumTitleLabel = new Label("Tranquility Base Hotel & Casino");
        albumTitleLabel.setEffect(UIComponents.createDropShadowEffect());
        albumTitleLabel.setWrapText(true);
        albumTitleLabel.setMaxWidth(300);
        albumTitleLabel.setAlignment(Pos.CENTER);
        albumTitleLabel.setTextAlignment(TextAlignment.CENTER);
        albumTitleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(albumTitleLabel);

        dateLabel = new Label("12 / 6 / 2018");
        dateLabel.setEffect(UIComponents.createDropShadowEffect());
        dateLabel.setWrapText(true);
        dateLabel.setTextAlignment(TextAlignment.CENTER);
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway;");
        contentVBox.getChildren().add(dateLabel);

        salesLabel = new Label("Sales: 257382");
        salesLabel.setEffect(UIComponents.createDropShadowEffect());
        salesLabel.setWrapText(true);
        salesLabel.setTextAlignment(TextAlignment.CENTER);
        salesLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1); " +
                "-fx-font-size: 20; -fx-font-family: Broadway");
        contentVBox.getChildren().add(salesLabel);

        ratingLabel = new Label("Rating: (4/5)");
        ratingLabel.setEffect(UIComponents.createDropShadowEffect());
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
        albumDisplayContentPane.getChildren().add(contentVBox);

    }

    @Override
    public Parent getRoot() {
        return rootVerticalBox;
    }

    @Override
    public void setState(ViewState state) {
        albumVBox.setVisible(state != ViewState.EMPTY && state == ViewState.VIEW);
        contentVBox.setVisible(state != ViewState.EMPTY && state == ViewState.VIEW);
        gridPane.setVisible(state != ViewState.EMPTY && state == ViewState.EDIT);
    }
}