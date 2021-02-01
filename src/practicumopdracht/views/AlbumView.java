package practicumopdracht.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class AlbumView extends View {

    private GridPane gridPane;

    public AlbumView() {
        this.gridPane = new GridPane();
        initLayout();
    }

    protected void initLayout() {
        Label name = new Label("Test Album view");
        gridPane.add(name,0,0);
    }

    @Override
    public Parent getRoot() {
        return gridPane;
    }
}
