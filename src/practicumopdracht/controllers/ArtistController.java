package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import practicumopdracht.AdjustableListView;
import practicumopdracht.MainApplication;
import practicumopdracht.PopupMessageBuilder;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.util.ArrayList;

public class ArtistController extends Controller {

    private static Artist currentArtist;
    private ArtistView view;

    public ArtistController() {
        view = new ArtistView();
        view.getViewAlbumsButton().setOnAction(event -> handleAlbumsClick());
        view.getEditArtistButton().setOnAction(event -> handleEditClick());
        view.getArtistEditApplyButton().setOnAction(event -> handleEditApplyClick());
        view.getArtistEditCancelButton().setOnAction(event -> handleEditCancelClick());
        AdjustableListView adjustableListView = view.getAdjustableListView();
        adjustableListView.getAddButton().setOnAction(event -> handleListAddClick());
        adjustableListView.getRemoveButton().setOnAction(event -> handleListRemoveClick());
        adjustableListView.getRemoveButton().setDisable(true);
        adjustableListView.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onListItemSelected((Artist) newValue);
        });

        updateList();
    }

    public static Artist getCurrentArtist() {
        return currentArtist;
    }

    private void updateList() {
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        ListView listView = view.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(artists));
        //Source: https://stackoverflow.com/a/36657553
        listView.setCellFactory(param -> new ListCell<Artist>() {
            @Override
            protected void updateItem(Artist item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getListString() == null) {
                    setText(null);
                } else {
                    setText(item.getListString());
                }
            }
        });
    }

    private void onListItemSelected(Artist item) {
        if (item == null) //caused when selected item is removed.
            return;
        displayArtist(item);
        view.getAdjustableListView().getRemoveButton().setDisable(false);
    }

    private void displayArtist(Artist artist) {
        currentArtist = artist;
        view.setFavoriteDisplayState(artist.isFavorited());
        view.getArtistDisplay().setText(artist.getName());
        view.getLabelDisplay().setText(artist.getLabel());
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void validateEdit() {
        PopupMessageBuilder messageBuilder = new PopupMessageBuilder();
        //Album Name
        TextField nameField = view.getArtistNameTextField();
        String artistName = nameField.getText();
        if (artistName.length() > 0) nameField.getStyleClass().removeAll("error");
        else {
            nameField.getStyleClass().add("error");
            messageBuilder.append("Name: No name entered.");
        }

        TextField labelField = view.getLabelNameTextField();
        String labelName = labelField.getText();
        if (labelName.length() > 0) labelField.getStyleClass().removeAll("error");
        else {
            labelField.getStyleClass().add("error");
            messageBuilder.append("Label: No label entered.");
        }

        if (messageBuilder.getTotalAppendCount() == 0) {
            Artist newArtist = new Artist(artistName, labelName, view.getFavoriteCheckBox().isSelected());
            Alert alert = PopupMessageBuilder.createAlertTemplate();
            alert.setContentText(newArtist.toString());
            alert.show();
            applyFromEditView(newArtist);
        } else {
            messageBuilder.createAlert();
        }
    }

    private void applyFromEditView(Artist artist) {
        //Remove the currently selected Artist.
        if (currentArtist != null)
            MainApplication.getArtistDAO().remove(currentArtist);
        //Add the newly made Artist.
        MainApplication.getArtistDAO().addOrUpdate(artist);
        updateList();
        view.getAdjustableListView().getListView().getSelectionModel().select(artist);
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void clearEditFields() {
        view.getArtistNameTextField().setText("");
        view.getLabelNameTextField().setText("");
        view.getFavoriteCheckBox().setSelected(false);
    }

    private void setEditFieldsByArtist(Artist artist) {
        view.getArtistNameTextField().setText(artist.getName());
        view.getLabelNameTextField().setText(artist.getLabel());
        view.getFavoriteCheckBox().setSelected(artist.isFavorited());
    }

    private void handleAlbumsClick() {
        MainApplication.switchController(new AlbumController());
    }

    private void handleEditClick() {
        setEditFieldsByArtist(currentArtist);
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleListAddClick() {
        clearEditFields();
        view.setState(View.VIEW_STATE.EDIT);

    }

    private void handleListRemoveClick() {
        MainApplication.getArtistDAO().remove(currentArtist);
        currentArtist = null;
        view.getAdjustableListView().getRemoveButton().setDisable(true);
        updateList();
        view.setState(View.VIEW_STATE.EMPTY);
    }

    private void handleEditCancelClick() {
        if (MainApplication.getArtistDAO().getAll().size() == 0)
            view.setState(View.VIEW_STATE.EMPTY);
        else
            view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleEditApplyClick() {
        validateEdit();
    }

    @Override
    public View getView() {
        return view;
    }
}
