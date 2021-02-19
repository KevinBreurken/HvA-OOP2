package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import practicumopdracht.AdjustableListView;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.MessageBuilder;
import practicumopdracht.comparators.ArtistComparatorAZ;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.util.ArrayList;

public class ArtistController extends Controller {

    /**
     * Reference to the artist that is selected on the Artist View, is used in other controllers as well.
     */
    private static Artist currentArtist;
    private ArtistView view;
    private boolean isListAscending = false;

    public ArtistController() {
        view = new ArtistView();
        AdjustableListView adjustableListView = view.getAdjustableListView();
        //HEADER - SAVE/LOAD
        view.getWindowHandle().getFileLoadButton().setOnAction(event -> handleFileLoadClick());
        view.getWindowHandle().getFileSaveButton().setOnAction(event -> handleFileSaveClick());
        //ARTIST - GENERAL
        adjustableListView.getAddButton().setOnAction(event -> handleListAddClick());
        adjustableListView.getRemoveButton().setOnAction(event -> handleListRemoveClick());
        adjustableListView.getRemoveButton().setDisable(true);
        //Artist - Sort
        view.getSortButton().setOnAction(event -> handleSortClick());
        view.setSortingButtonGraphic(isListAscending);
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

    private void handleFileSaveClick() {
        CustomWindowHandle.handleFileSaveClick();
    }

    private void handleFileLoadClick() {
        if (CustomWindowHandle.handleFileLoadClick()) {
            updateArtistList();
        }
    }

    private void handleSortClick() {
        isListAscending = !isListAscending;
        view.setSortingButtonGraphic(isListAscending);
        updateArtistList();
    }

    private void updateArtistList() {
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        if (artists == null)
            return;
        ListView listView = view.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(artists));
        FXCollections.sort(listView.getItems(), new ArtistComparatorAZ(isListAscending));
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
        view.setState(View.ViewState.VIEW);
    }

    private void validateEdit() {
        MessageBuilder messageBuilder = new MessageBuilder();
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
            if (currentArtist != null) {
                currentArtist.setName(artistName);
                currentArtist.setFavorited(view.getFavoriteCheckBox().isSelected());
                currentArtist.setLabel(labelName);
                newArtist = currentArtist;
                messageBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Edited '%s'", newArtist.getName()));
            } else {
                newArtist = new Artist(artistName, labelName, view.getFavoriteCheckBox().isSelected());
                messageBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Added '%s' to the list.", newArtist.getName()));
            }
            applyFromEditView(newArtist);
        } else {
            messageBuilder.createAlert(Alert.AlertType.ERROR);
        }
    }

    private void applyFromEditView(Artist artist) {
        MainApplication.getArtistDAO().addOrUpdate(artist);
        updateArtistList();
        view.getAdjustableListView().getListView().getSelectionModel().select(artist);
        view.setState(View.ViewState.VIEW);
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
        view.setState(View.ViewState.EDIT);
    }

    private void handleListAddClick() {
        clearEditFields();
        view.setState(View.ViewState.EDIT);

    }

    private void handleListRemoveClick() {
        String popupText = String.format("Are you sure you want to remove %s?", currentArtist.getName());
        if (MessageBuilder.showConfirmationAlert(popupText)) {
            MainApplication.getArtistDAO().remove(currentArtist);
            currentArtist = null;
            view.getAdjustableListView().getRemoveButton().setDisable(true);
            updateArtistList();
            view.setState(View.ViewState.EMPTY);
        }
    }

    private void handleEditCancelClick() {
        if (MainApplication.getArtistDAO().getAll().size() == 0)
            view.setState(View.ViewState.EMPTY);
        else
            view.setState(View.ViewState.VIEW);
    }

    private void handleEditApplyClick() {
        validateEdit();
    }

    @Override
    public View getView() {
        return view;
    }
}
