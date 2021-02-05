package practicumopdracht;

import com.sun.glass.ui.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class CustomWindowHandle extends HBox {

    public CustomWindowHandle() {
        setMinHeight(30);
        setOnMousePressed(pressEvent -> {
            setOnMouseDragged(dragEvent -> {
                MainApplication.getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                MainApplication.getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        System.out.println(MainApplication.title);
        Label titleLabel = new Label(MainApplication.title);
        setAlignment(Pos.CENTER_LEFT);
        titleLabel.setPadding(new Insets(10, 10, 0, 0));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
//        getChildren().add(titleLabel);

        setPadding(new Insets(0, 0, 10, 10));

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

        getChildren().addAll(titleLabel, spacer, minimiseButton, maximiseButton, closeButton);
    }

    private void handleCloseClick(){
        Platform.exit();
    }

    private void handleMinimiseClick(){
        MainApplication.getStage().setIconified(true);
    }

    private void handleMaximiseClick(){
        MainApplication.getStage().setMaximized(!MainApplication.getStage().isMaximized());
    }
}
