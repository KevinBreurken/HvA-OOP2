package practicumopdracht;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Custom handle for the JavaFX application. handles functionality for general window functions (minimise/close) and
 * calling load/save DAO's.
 */
public class CustomWindowHandle extends HBox {

    MenuItem fileSaveButton;
    MenuItem loadSaveButton;

    public CustomWindowHandle() {
        setOnMousePressed(pressEvent -> setOnMouseDragged(dragEvent -> {
            MainApplication.getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            MainApplication.getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));

        setPadding(new Insets(0, 0, 10, 10));
        setMinHeight(30);

        Label titleLabel = new Label(MainApplication.title);
        setAlignment(Pos.CENTER_LEFT);
        titleLabel.setPadding(new Insets(10, 10, 0, 0));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15; ");

        final Button left = new Button(MainApplication.title);
        left.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        final Button closeButton = new Button("");
        closeButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setPadding(new Insets(8, 0, 0, 0));
        closeButton.setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/navigation/close.png")));
        closeButton.setOnAction(event -> handleCloseClick());

        final Button maximiseButton = new Button("");
        maximiseButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        maximiseButton.setStyle("-fx-background-color: transparent;");
        maximiseButton.setPadding(new Insets(8, 0, 0, 0));
        maximiseButton.setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/navigation/maximise.png")));
        maximiseButton.setOnAction(event -> handleMaximiseClick());

        final Button minimiseButton = new Button("");
        minimiseButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        minimiseButton.setStyle("-fx-background-color: transparent;");
        minimiseButton.setPadding(new Insets(8, 0, 0, 0));
        minimiseButton.setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/navigation/minimise.png")));
        minimiseButton.setOnAction(event -> handleMinimiseClick());

        fileSaveButton = new MenuItem("Save Data");
        loadSaveButton = new MenuItem("Load Data");

        //Adds extra padding to the buttons left side.
        Label lbl = new Label("");
        lbl.setPrefWidth(85);
        MenuButton menuButton = new MenuButton("File", null, fileSaveButton, loadSaveButton);
        menuButton.setMinWidth(160);
        fileSaveButton.setGraphic(lbl);

        getChildren().addAll(titleLabel, spacer, menuButton, minimiseButton, maximiseButton, closeButton);
    }

    public MenuItem getFileSaveButton() {
        return fileSaveButton;
    }

    public MenuItem getFileLoadButton() {
        return loadSaveButton;
    }

    public static boolean handleFileSaveClick() {
        if (MessageBuilder.showConfirmationAlert("Do you want to save the currently loaded Artist and Album data?")) {
            boolean succes = MainApplication.getArtistDAO().save();
            succes = MainApplication.getAlbumDAO().save() || succes;
            String displayString = succes ? "Saved Data" : "Save Failed";
            MessageBuilder.showPopupAlert(displayString);
            return succes;
        }
        return false;
    }

    public static boolean handleFileLoadClick() {
        if (MessageBuilder.showConfirmationAlert("Do you want to load the currently stored Artist and Album data?")) {
            boolean succes = MainApplication.getArtistDAO().load();
            succes = MainApplication.getAlbumDAO().load() || succes;

            String displayString = succes ? "Data Loaded" : "Load Failed";
            MessageBuilder.showPopupAlert(displayString);
            return succes;
        }
        return false;
    }

    private synchronized void handleCloseClick() {
        MainApplication.onApplicationClose();
    }

    private void handleMinimiseClick() {
        MainApplication.getStage().setIconified(true);
    }

    private void handleMaximiseClick() {
        MainApplication.getStage().setMaximized(!MainApplication.getStage().isMaximized());
    }

}
