package practicumopdracht.views;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import practicumopdracht.AdjustableListView;
import practicumopdracht.MainApplication;

import java.io.File;

/**
 * View containing elements used by both Artist and Album view.
 */
public abstract class GeneralContentView extends View {

    protected AdjustableListView adjustableListBox = new AdjustableListView("Add", "Remove");
    protected VBox contentViewBox = new VBox();
    protected VBox editViewBox = new VBox();

    private StackPane rootPane = new StackPane();
    private StackPane artistDarkenOverlay = new StackPane();

    public GeneralContentView() {
        rootHorizontalBox.getChildren().add(adjustableListBox);

        rootPane.setMinWidth(200);
        HBox.setHgrow(rootPane, Priority.ALWAYS);
        VBox.setVgrow(rootPane, Priority.ALWAYS);
        rootHorizontalBox.getChildren().add(0, rootPane);

        artistDarkenOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        rootPane.getChildren().add(artistDarkenOverlay);

        setBackgroundImageByPath("src/practicumopdracht/content/default_bg.jpeg");

        artistDarkenOverlay.getChildren().add(contentViewBox);
        artistDarkenOverlay.getChildren().add(editViewBox);
    }

    public AdjustableListView getAdjustableListView() {
        return adjustableListBox;
    }

    public void setBackgroundImageByPath(String imagePath) {
        File tempFile = new File(imagePath);
        boolean exists = tempFile.exists();
        Image image;
        if (exists) {
            image = MainApplication.loadImage(imagePath);
        } else {
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

    @Override
    public void setState(ViewState state) {
        contentViewBox.setVisible(state != ViewState.EMPTY && state == ViewState.VIEW);
        editViewBox.setVisible(state != ViewState.EMPTY && state == ViewState.EDIT);
    }

    @Override
    public Parent getRoot() {
        return rootVerticalBox;
    }

}