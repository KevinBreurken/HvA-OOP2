package practicumopdracht.controllers;

import javafx.scene.control.Alert;
import practicumopdracht.MainApplication;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.awt.*;

public class ArtistController extends Controller{

    private Artist artist;
    private ArtistView view;

    public ArtistController(){
        view = new ArtistView();
        view.getViewAlbumsButton().setOnAction(event -> handleAlbumsClick());
        view.getEditArtistButton().setOnAction(event -> handleEditClick());
        view.getArtistEditApplyButton().setOnAction(event -> handleEditApplyClick());
        view.getArtistEditCancelButton().setOnAction(event -> handleEditCancelClick());
        view.getAdjustableListBox().getAddButton().setOnAction(event -> handleListAddClick());
        view.getAdjustableListBox().getRemoveButton().setOnAction(event -> handleListRemoveClick());
    }

    private void handleAlbumsClick(){
        MainApplication.switchController(new AlbumController());
    }

    private void handleEditClick(){
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleListAddClick(){
        MainApplication.showAlert("List add click");
    }

    private void handleListRemoveClick(){
        MainApplication.showAlert("List remove click");
    }

    private void handleEditCancelClick(){
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleEditApplyClick(){
        MainApplication.showAlert("Apply changes to Artist.");
        view.setState(View.VIEW_STATE.VIEW);
    }

    @Override
    public View getView() {
        return view;
    }
}
