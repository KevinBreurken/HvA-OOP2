package practicumopdracht;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
        //Add title label.
        titleLabel = new Label(listTitle);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        //Allows the window to be dragged by the label as well.
        titleLabel.setOnMousePressed(pressEvent -> {
            titleLabel.setOnMouseDragged(dragEvent -> {
                MainApplication.getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                MainApplication.getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        getChildren().add(titleLabel);
        //add the list view
        listView = new ListView();
        VBox.setVgrow(listView, Priority.ALWAYS);
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

        //Disables vertical scroll view by setting the cell width
        //Solution from: https://stackoverflow.com/a/37131860
        listView.setCellFactory(param -> new ListCell<String>() {
            {
                prefWidthProperty().bind(listView.widthProperty().subtract(2));
                setMaxWidth(Control.USE_PREF_SIZE);
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null && !empty) {
                    setText(item);
                } else {
                    setText(null);
                }
            }
        });
    }

    public void addToTop(Node node){
        this.getChildren().add(0,node);
    }

    //Only for testing purposes
    public void addTestNames(String[] names) {
        for (int i = 0; i < names.length; i++) {
            listView.getItems().add(names[i]);
        }
    }
}
