package practicumopdracht;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Class containing static functions to create custom UI Components.
 */
public class UIComponents {

    public static DropShadow createDropShadowEffect(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0, 0, 0));
        return dropShadow;
    }

    private static VBox createComponentContainer(String preText,Insets insets){
        VBox componentVBox = new VBox();
        componentVBox.setAlignment(Pos.BASELINE_CENTER);
        componentVBox.setPadding(insets);

        Label preTextLabel = new Label(preText);
        preTextLabel.setEffect(createDropShadowEffect());
        preTextLabel.setWrapText(true);
        preTextLabel.setTextAlignment(TextAlignment.RIGHT);
        preTextLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");
        componentVBox.getChildren().add(preTextLabel);

        return componentVBox;
    }


    public static VBox createComboBoxGroup(String preText) {
        VBox groupVBox = createComponentContainer(preText,new Insets(0, 0, 15, 0));

        ComboBox comboBox = new ComboBox();
        comboBox.setMaxWidth(250);
        groupVBox.getChildren().add(comboBox);

        return groupVBox;
    }

    public static VBox createTextfieldGroup(String preText, String promptText) {
        VBox groupVBox = createComponentContainer(preText,new Insets(0, 0, 15, 0));

        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setPromptText(promptText);
        textField.setPrefWidth(140);
        textField.setMaxWidth(140);
        groupVBox.getChildren().add(textField);

        return groupVBox;
    }

    public static VBox createTextAreaGroup(String preText, String promptText) {
        VBox groupVBox = createComponentContainer(preText,new Insets(-10, 0, 20, 0));
        groupVBox.setMaxHeight(0);

        TextArea textArea = new TextArea();
        VBox.setMargin(textArea,new Insets(0,0,-18,0));
        textArea.setPromptText(promptText);
        textArea.setPrefWidth(140);
        textArea.setMaxWidth(140);
        textArea.setMinHeight(40);
        groupVBox.getChildren().add(textArea);

        return groupVBox;
    }

    public static VBox createDatepickerGroup(String preText) {
        VBox groupVBox = createComponentContainer(preText,new Insets(0, 0, 0, 0));

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(140);
        datePicker.setMaxWidth(140);
        groupVBox.getChildren().add(datePicker);

        return groupVBox;
    }

    public static VBox createIntSelectorGroup(String preText) {
        VBox groupHBox =  createComponentContainer(preText,new Insets(5, 0, 0, 0));

        //Create the horizontal button group.
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(5, 0, 10, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        //Create the remove button.
        Button removeButton = new Button("-");
        buttonHBox.getChildren().add(removeButton);
        removeButton.setMinSize(30,10);
        removeButton.setMaxHeight(20);
        //Create the rating value display.
        TextField valueLabel = new TextField("0");
        valueLabel.setEffect(createDropShadowEffect());
        valueLabel.setMaxWidth(30);
//        valueLabel.setWrapText(true);
        valueLabel.setAlignment(Pos.CENTER);
        valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");
        buttonHBox.getChildren().add(valueLabel);
        //Create the add button.
        Button saveButton = new Button("+");
        buttonHBox.getChildren().add(saveButton);
        saveButton.setMinSize(30,10);
        saveButton.setMaxHeight(20);

        groupHBox.getChildren().add(buttonHBox);
        return groupHBox;
    }

    public static HBox createEditButtonGroup(){
        HBox buttonHBox = new HBox();

        buttonHBox.setSpacing(10);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER);
        Button removeButton = new Button("Save");
        buttonHBox.getChildren().add(removeButton);
        Button saveButton = new Button("Cancel");
        buttonHBox.getChildren().add(saveButton);
        saveButton.setMinWidth(50);
        removeButton.setMinWidth(50);
        return buttonHBox;
    }

    public static HBox createFavoriteGroup(String preText) {
        HBox favoriteGroupHBox = new HBox();
        favoriteGroupHBox.setAlignment(Pos.BASELINE_CENTER);
        favoriteGroupHBox.setPadding(new Insets(10, 0, 0, 50));

        Label preTextLabel = new Label(preText);
        preTextLabel.setEffect(createDropShadowEffect());
        preTextLabel.setPadding(new Insets(0, 1, 0, 0));
        preTextLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: rgba(255,255,255,1);");

        preTextLabel.setTextAlignment(TextAlignment.RIGHT);
        favoriteGroupHBox.getChildren().add(preTextLabel);

        CheckBox favoriteCheckbox = new CheckBox();
        favoriteCheckbox.setAlignment(Pos.CENTER_LEFT);
        favoriteCheckbox.setPadding(new Insets(0, 0, 0, 10));
        favoriteCheckbox.setPrefWidth(80);
        favoriteCheckbox.setMaxWidth(80);
        favoriteGroupHBox.getChildren().add(favoriteCheckbox);

        return favoriteGroupHBox;
    }
}
