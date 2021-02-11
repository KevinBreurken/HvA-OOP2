package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import practicumopdracht.AdjustableListView;
import practicumopdracht.MainApplication;
import practicumopdracht.PopupMessageBuilder;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.util.ArrayList;
import java.util.Optional;

public class ArtistController extends Controller {

    /**
     * Reference to the artist that is selected on the Artist View, is used in other controllers as well.
     */
    private static Artist currentArtist;
    private ArtistView view;

    public ArtistController() {
        view = new ArtistView();
        AdjustableListView adjustableListView = view.getAdjustableListView();
        //ARTIST - GENERAL
        adjustableListView.getAddButton().setOnAction(event -> handleListAddClick());
        adjustableListView.getRemoveButton().setOnAction(event -> handleListRemoveClick());
        adjustableListView.getRemoveButton().setDisable(true);
        //Add listeners for when an item is selected in the item list.
        adjustableListView.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onArtistListItemSelected((Artist) newValue);
        });

        //ARTIST - CONTENT
        view.getEditArtistButton().setOnAction(event -> handleEditClick());
        view.getViewAlbumsButton().setOnAction(event -> handleAlbumsClick());
        //ARTIST - EDIT CONTENT
        view.getArtistEditApplyButton().setOnAction(event -> handleEditApplyClick());
        view.getArtistEditCancelButton().setOnAction(event -> handleEditCancelClick());

        updateArtistList();
        //Sets the selection back to currentArtist when the user returns from the Album View.
        if (currentArtist != null) {
            view.getAdjustableListView().getListView().getSelectionModel().select(currentArtist);
            onArtistListItemSelected(currentArtist);
        }
    }

    public static Artist getCurrentArtist() {
        return currentArtist;
    }

    public static void setCurrentArtist(Artist artist) {
        currentArtist = artist;
    }

    private void updateArtistList() {
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

    private void onArtistListItemSelected(Artist item) {
        if (item == null) //called when selected item is removed.
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
            Artist newArtist;
            if(currentArtist != null) {
                currentArtist.setName(artistName);
                currentArtist.setFavorited(view.getFavoriteCheckBox().isSelected());
                currentArtist.setLabel(labelName);
                newArtist = currentArtist;
            }else {
                newArtist = new Artist(artistName, labelName, view.getFavoriteCheckBox().isSelected());
            }
            Alert alert = PopupMessageBuilder.createAlertTemplate(Alert.AlertType.ERROR);
            alert.setContentText(newArtist.toString());
            alert.show();
            applyFromEditView(newArtist);
        } else {
            messageBuilder.createAlert();
        }
    }

    private void applyFromEditView(Artist artist) {
        MainApplication.getArtistDAO().addOrUpdate(artist);
        updateArtistList();
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
        Alert alert = PopupMessageBuilder.createAlertTemplate(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format("Are you sure you want to remove %s?", currentArtist.getName()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            MainApplication.getArtistDAO().remove(currentArtist);
            currentArtist = null;
            view.getAdjustableListView().getRemoveButton().setDisable(true);
            updateArtistList();
            view.setState(View.VIEW_STATE.EMPTY);
        }
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
