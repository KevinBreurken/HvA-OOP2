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
        view.getChangeImageButton().setOnAction(event -> handleChangePictureClick());
        view.getRatingDecreaseButton().setOnAction(event -> handleDecreaseRatingClick());
        view.getRatingIncreaseButton().setOnAction(event -> handleIncreaseRatingClick());
        view.getAlbumEditApplyButton().setOnAction(event -> handleAlbumEditApplyClick());
        view.getAlbumEditCancelButton().setOnAction(event -> handleAlbumEditCancelClick());
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

    private  void handleAlbumEditApplyClick(){
        MainApplication.showAlert("Update album settings.");
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleAlbumEditCancelClick(){
        view.setState(View.VIEW_STATE.VIEW);
    }

    private  void handleChangePictureClick(){
        MainApplication.showAlert("Change Picture.");
    }

    private void handleIncreaseRatingClick(){
        MainApplication.showAlert("Increase value.");
    }

    private void handleDecreaseRatingClick(){
        MainApplication.showAlert("Decrease value.");
    }
    @Override
    public View getView() {
        return view;
    }

}
