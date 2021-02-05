package practicumopdracht.controllers;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.View;

public class AlbumController extends Controller {

    private Album album;
    private AlbumView view;

    public AlbumController() {
        view = new AlbumView();
        view.getBackToArtistButton().setOnAction(event -> handleBackToArtistClick());
        view.getWikiButton().setOnAction(event -> handleOpenWikiClick());
        view.getEditAlbumButton().setOnAction(event -> handleEditAlbumClick());
        view.getAdjustableListBox().getAddButton().setOnAction(event -> handleListAddClick());
        view.getAdjustableListBox().getRemoveButton().setOnAction(event -> handleListRemoveClick());
    }

    private void handleListAddClick(){
        MainApplication.showAlert("List add click");
    }

    private void handleListRemoveClick(){
        MainApplication.showAlert("List remove click");
    }

    private void handleBackToArtistClick() {
        MainApplication.switchController(new ArtistController());
    }

    private void handleEditAlbumClick(){
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleOpenWikiClick(){
        MainApplication.showAlert("Open Wiki page");
    }

    @Override
    public View getView() {
        return view;
    }

}
