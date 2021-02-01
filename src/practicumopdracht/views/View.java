package practicumopdracht.views;

import javafx.scene.Parent;

public abstract class View {
    public abstract Parent getRoot();
    protected abstract void initLayout();
}
