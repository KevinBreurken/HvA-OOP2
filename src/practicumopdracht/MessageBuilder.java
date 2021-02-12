package practicumopdracht;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import java.util.Optional;

public class MessageBuilder {

    private StringBuilder sb;
    private int totalAppendCount = 0;

    public MessageBuilder() {
        this.sb = new StringBuilder();
        sb.append("Errors found: \n\n");
    }

    public static Alert createAlertTemplate(Alert.AlertType type) {
        Alert alert = new Alert(type);
        DialogPane dialogPane = alert.getDialogPane();
        alert.getDialogPane().setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/error.png")));
        dialogPane.getChildren().get(0).setOnMousePressed(pressEvent -> {
            dialogPane.getChildren().get(0).setOnMouseDragged(dragEvent -> {
                alert.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                alert.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        alert.initStyle(StageStyle.UNDECORATED);
        dialogPane.getStylesheets().add("practicumopdracht/default.css");
        return alert;
    }

    public static boolean showConfirmationAlert(String contentText) {
        Alert alert = MessageBuilder.createAlertTemplate(Alert.AlertType.CONFIRMATION);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);

    }

    public static void showPopupAlert(String contentText) {
        Alert alert = MessageBuilder.createAlertTemplate(Alert.AlertType.INFORMATION);
        alert.setContentText(contentText);
        alert.show();
    }

    public void append(String text) {
        sb.append(text + "\n");
        totalAppendCount++;
    }

    public int getTotalAppendCount() {
        return totalAppendCount;
    }

    public void createAlert() {
        Alert alert = createAlertTemplate(Alert.AlertType.ERROR);
        alert.setContentText(sb.toString());
        alert.show();
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}