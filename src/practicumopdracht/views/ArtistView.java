package practicumopdracht.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ArtistView extends View {

    private GridPane gridPane;

    public ArtistView() {
        this.gridPane = new GridPane();
        initLayout();
    }
    
    @Override
    protected void initLayout() {
        Label name = new Label("Test Album view");
        gridPane.add(name, 0, 0);
    }

    @Override
    public Parent getRoot() {
        return gridPane;
    }
}
