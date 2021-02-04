package practicumopdracht;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Reusable listview used in both Artist and Album view.
 */
public class AdjustableListView extends VBox {

    private Label titleLabel;
    private ListView listView;
    //Buttons
    private HBox buttonHBox;
    private Button addButton;
    private Button removeButton;


    public AdjustableListView(String listTitle, String addButtonText, String removeButtonText) {
        setMinWidth(200);
        setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);
        //Add title label.
        titleLabel = new Label(listTitle);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        getChildren().add(titleLabel);
        //add the list view
        listView = new ListView();
        getChildren().add(listView);
        //Add button row
        buttonHBox = new HBox();
        addButton = new Button("Add");
        buttonHBox.getChildren().add(addButton);
        removeButton = new Button("Remove");
        buttonHBox.getChildren().add(removeButton);
        getChildren().add(buttonHBox);
        //Determine how the buttons should be scaled.
        int btnCount = buttonHBox.getChildren().size();
        addButton.prefWidthProperty().bind(buttonHBox.widthProperty().divide(btnCount));
        removeButton.prefWidthProperty().bind(buttonHBox.widthProperty().divide(btnCount));
    }

    //Only for testing purposes
    public void addTestNames(String[] names) {
        for (int i = 0; i < names.length; i++) {
            listView.getItems().add(names[i]);
        }
    }
}
