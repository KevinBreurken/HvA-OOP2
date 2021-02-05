package practicumopdracht;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class UIComponents {
    public static VBox createTextfieldGroup(String preText, String promptText) {
        VBox groupHBox = new VBox();
        groupHBox.setAlignment(Pos.BASELINE_CENTER);
        groupHBox.setPadding(new Insets(0, 0, 15, 0));

        Label preTextLabel = new Label(preText);
        preTextLabel.setWrapText(true);
        preTextLabel.setTextAlignment(TextAlignment.RIGHT);
        preTextLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");
        preTextLabel.setPadding(new Insets(0, 10, 0, 0));
        groupHBox.getChildren().add(preTextLabel);

        TextField artistName = new TextField();
        artistName.setAlignment(Pos.CENTER);
        artistName.setPromptText(promptText);
        artistName.setPrefWidth(140);
        artistName.setMaxWidth(140);
        groupHBox.getChildren().add(artistName);
        return groupHBox;
    }
}
