package practicumopdracht;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Optional;

public class MessageBuilder {

    private StringBuilder sb;
    private int totalAppendCount = 0;

    public MessageBuilder() {
        this.sb = new StringBuilder();
    }

    public static Alert createAlertTemplate(Alert.AlertType type) {
        Alert alert = new Alert(type);
        DialogPane dialogPane = alert.getDialogPane();
        String imagePath;
        switch (type) {
            case CONFIRMATION:
                imagePath = "src/practicumopdracht/content/dialog/confirm.png";
                break;
            case ERROR:
                imagePath = "src/practicumopdracht/content/dialog/error.png";
                break;
            case WARNING:
                imagePath = "src/practicumopdracht/content/dialog/warning.png";
                break;
            default:
                imagePath = "src/practicumopdracht/content/dialog/message.png";
                break;
        }
        alert.getDialogPane().setGraphic(new ImageView(MainApplication.loadImage(imagePath)));
        dialogPane.getChildren().get(0).setOnMousePressed(pressEvent -> {
            dialogPane.getChildren().get(0).setOnMouseDragged(dragEvent -> {
                alert.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                alert.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        alert.initStyle(StageStyle.UNDECORATED);
        //Source: Stylesheet from https://github.com/joffrey-bion/javafx-themes
        File f = new File("src/practicumopdracht/content/css/default.css");
        dialogPane.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
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

    public void createAlert(Alert.AlertType type) {
        createAlert(type, sb.toString());
    }

    public void createAlert(Alert.AlertType type, String content) {
        Alert alert = createAlertTemplate(type);
        alert.setContentText(content);
        alert.show();
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}