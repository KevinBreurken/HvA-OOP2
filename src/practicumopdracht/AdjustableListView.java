package practicumopdracht;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import practicumopdracht.models.Artist;

/**
 * Reusable listview used in both Artist and Album view.
 */
public class AdjustableListView extends VBox {

    private Label titleLabel;

    private ListView listView;
    private StackPane headerStackPane;

    //Buttons
    private HBox buttonHBox;
    private Button addButton;
    private Button removeButton;

    public Button getAddButton() {
        return addButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public ListView getListView() {
        return listView;
    }

    public StackPane getHeaderStackPane() {
        return headerStackPane;
    }

    public AdjustableListView(String listTitle, String addButtonText, String removeButtonText) {
        setMinWidth(200);
        setAlignment(Pos.CENTER);
        headerStackPane = new StackPane();
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

        headerStackPane.getChildren().add(titleLabel);
        getChildren().add(headerStackPane);

        //add the list view
        listView = new ListView();
        listView.setStyle("-fx-font-family: 'Arial Unicode MS'");
        VBox.setVgrow(listView, Priority.ALWAYS);
        getChildren().add(listView);
        //Add button row
        buttonHBox = new HBox();
        addButton = new Button(addButtonText);
        buttonHBox.getChildren().add(addButton);
        removeButton = new Button(removeButtonText);
        buttonHBox.getChildren().add(removeButton);
        getChildren().add(buttonHBox);
        //Determine how the buttons should be scaled.
        int btnCount = buttonHBox.getChildren().size();
        addButton.prefWidthProperty().bind(buttonHBox.widthProperty().divide(btnCount));
        removeButton.prefWidthProperty().bind(buttonHBox.widthProperty().divide(btnCount));

    }

    public void addToTop(Node node){
        this.getChildren().add(0,node);
    }

}
