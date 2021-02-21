package practicumopdracht.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;

public abstract class View {

    protected HBox rootHorizontalBox = new HBox();
    protected VBox rootVerticalBox = new VBox();
    private CustomWindowHandle windowHandle = new CustomWindowHandle();

    public View() {
        rootHorizontalBox.setAlignment(Pos.TOP_RIGHT);
        rootHorizontalBox.prefHeightProperty().bind(MainApplication.getStage().heightProperty());
        VBox.setVgrow(rootVerticalBox, Priority.ALWAYS);

        Pane bottomEdge = new Pane();
        bottomEdge.setMinHeight(10);
        rootVerticalBox.getChildren().addAll(windowHandle, rootHorizontalBox, bottomEdge);
    }

    public abstract Parent getRoot();

    public abstract void setState(ViewState state);

    public CustomWindowHandle getWindowHandle() {
        return windowHandle;
    }

    public enum ViewState {
        EMPTY,
        VIEW,
        EDIT
    }

}
