package practicumopdracht.views;

import javafx.scene.Parent;

public abstract class View {
    public abstract Parent getRoot();

    public abstract void setState(ViewState name);

    protected abstract void initLayout();

    public enum ViewState {
        EMPTY,
        VIEW,
        EDIT
    }

}
