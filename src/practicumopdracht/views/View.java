package practicumopdracht.views;

import javafx.scene.Parent;

public abstract class View {
    public abstract Parent getRoot();

    public abstract void setState(VIEW_STATE name);

    protected abstract void initLayout();

    public static enum VIEW_STATE {
        VIEW,
        EDIT
    }
}
