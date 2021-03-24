package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import practicumopdracht.AdjustableListView;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.MessageDialogBuilder;
import practicumopdracht.comparators.ArtistComparatorAZ;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.io.File;
import java.util.ArrayList;

public class ArtistController extends Controller {

    private static Artist currentSelectedArtist;
    private final ArtistView VIEW;

    private boolean isListAscending = false;
    private String imagePath;
    private File selectedFile;


    public ArtistController() {
        VIEW = new ArtistView();
        //HEADER - SAVE/LOAD
        VIEW.getWindowHandle().getFileLoadButton().setOnAction(event -> handleFileLoadClick());
        VIEW.getWindowHandle().getFileSaveButton().setOnAction(event -> handleFileSaveClick());
        //ARTIST - GENERAL
        AdjustableListView<Artist> adjustableListView = VIEW.getAdjustableListView();
        adjustableListView.getAddButton().setOnAction(event -> handleListAddClick());
        adjustableListView.getRemoveButton().setOnAction(event -> handleListRemoveClick());
        adjustableListView.getRemoveButton().setDisable(true);
        //Artist - Sort
        VIEW.getSortButton().setOnAction(event -> handleSortClick());
        VIEW.setSortingButtonGraphic(isListAscending);
        //Add listeners for when an item is selected in the item list.
        adjustableListView.getListView().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onArtistListItemSelected(newValue));

        //ARTIST - CONTENT
        VIEW.getEditArtistButton().setOnAction(event -> handleEditClick());
        VIEW.getViewAlbumsButton().setOnAction(event -> handleAlbumsClick());
        //ARTIST - EDIT CONTENT
        VIEW.getArtistEditApplyButton().setOnAction(event -> handleEditApplyClick());
        VIEW.getArtistEditCancelButton().setOnAction(event -> handleEditCancelClick());
        VIEW.getChangeImageButton().setOnAction(event -> handleChangeImageClick());

        updateArtistList();
        //Sets the selection back to currentArtist when the user returns from the Album View.
        if (currentSelectedArtist != null) {
            VIEW.getAdjustableListView().getListView().getSelectionModel().select(currentSelectedArtist);
            onArtistListItemSelected(currentSelectedArtist);
        }

        //Source: https://stackoverflow.com/a/36657553
        VIEW.getAdjustableListView().getListView().setCellFactory(param -> new ListCell<>() {
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

    private void handleFileSaveClick() {
        CustomWindowHandle.handleFileSaveClick();
    }

    private void handleFileLoadClick() {
        if (CustomWindowHandle.handleFileLoadClick()) {
            updateArtistList();
        }
    }

    private void handleChangeImageClick() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        selectedFile = file;
        System.out.println(file.getName());
        imagePath = file.getName();
        VIEW.setBackgroundImageByPath(file.getAbsolutePath());
    }

    private void handleSortClick() {
        isListAscending = !isListAscending;
        VIEW.setSortingButtonGraphic(isListAscending);
        updateArtistList();
    }

    private void updateArtistList() {
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        if (artists == null)
            return;
        ListView<Artist> listView = VIEW.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(artists));
        FXCollections.sort(listView.getItems(), new ArtistComparatorAZ(isListAscending));
    }

    private void onArtistListItemSelected(Artist item) {
        if (item == null) //called when selected item is removed.
            return;
        displayArtist(item);
        VIEW.setBackgroundImageByPath(MainApplication.getImageFileDAO().getArtistPath() + "/" + item.getCurrentFileName());
        VIEW.getAdjustableListView().getRemoveButton().setDisable(false);
    }

    private void displayArtist(Artist artist) {
        currentSelectedArtist = artist;
        VIEW.setFavoriteDisplayState(artist.isFavorite());
        VIEW.getArtistDisplay().setText(artist.getName());
        VIEW.getLabelDisplay().setText(artist.getLabel());
        VIEW.setState(View.ViewState.VIEW);
    }

    private void validateEdit() {
        MessageDialogBuilder messageDialogBuilder = new MessageDialogBuilder();
        //Album Name
        TextField nameField = VIEW.getArtistNameTextField();
        String artistName = nameField.getText();
        if (artistName.length() > 0) nameField.getStyleClass().removeAll("error");
        else {
            nameField.getStyleClass().add("error");
            messageDialogBuilder.append("Name: No name entered.");
        }

        TextField labelField = VIEW.getLabelNameTextField();
        String labelName = labelField.getText();
        if (labelName.length() > 0) labelField.getStyleClass().removeAll("error");
        else {
            labelField.getStyleClass().add("error");
            messageDialogBuilder.append("Label: No label entered.");
        }

        if (messageDialogBuilder.getTotalAppendCount() == 0) {
            Artist newArtist;
            if (currentSelectedArtist != null) {
                currentSelectedArtist.setName(artistName);
                currentSelectedArtist.setFavorite(VIEW.getFavoriteCheckBox().isSelected());
                currentSelectedArtist.setLabel(labelName);
                if (selectedFile != null) {
                    MainApplication.getImageFileDAO().checkForExistingUnsavedImages(currentSelectedArtist);
                    currentSelectedArtist.setUnsavedImageFileName(selectedFile.getName());
                }
                newArtist = currentSelectedArtist;
                messageDialogBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Edited '%s' \n\n%s", newArtist.getName(),newArtist.toString()));
            } else {
                if (selectedFile != null) {
                    imagePath = selectedFile.getName();
                }
                newArtist = new Artist(artistName, labelName, VIEW.getFavoriteCheckBox().isSelected(), null);
                newArtist.setUnsavedImageFileName(imagePath);
                messageDialogBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Added '%s' to the list. \n\n%s", newArtist.getName(),newArtist.toString()));
            }

            //Add/Image
            if (selectedFile != null) {
                MainApplication.getImageFileDAO().saveArtistImage(selectedFile);
                newArtist.setUnsavedImageFileName(selectedFile.getName());
            }
            applyFromEditView(newArtist);
        } else {
            messageDialogBuilder.createAlert(Alert.AlertType.ERROR);
        }
    }

    private void applyFromEditView(Artist artist) {
        MainApplication.getArtistDAO().addOrUpdate(artist);
        updateArtistList();
        VIEW.getAdjustableListView().getListView().getSelectionModel().select(artist);
        VIEW.setState(View.ViewState.VIEW);
    }

    private void clearEditFields() {
        VIEW.getArtistNameTextField().setText("");
        VIEW.getLabelNameTextField().setText("");
        VIEW.getFavoriteCheckBox().setSelected(false);
    }

    private void setEditFieldsByArtist(Artist artist) {
        VIEW.getArtistNameTextField().setText(artist.getName());
        VIEW.getLabelNameTextField().setText(artist.getLabel());
        VIEW.getFavoriteCheckBox().setSelected(artist.isFavorite());
    }

    private void handleAlbumsClick() {
        MainApplication.switchController(new AlbumController(currentSelectedArtist));
    }

    private void handleEditClick() {
        setEditFieldsByArtist(currentSelectedArtist);
        imagePath = "";
        selectedFile = null;
        VIEW.setState(View.ViewState.EDIT);
    }

    private void handleListAddClick() {
        clearEditFields();
        currentSelectedArtist = null;
        VIEW.setState(View.ViewState.EDIT);

    }

    private void handleListRemoveClick() {
        String popupText = String.format("Are you sure you want to remove %s?", currentSelectedArtist.getName());
        if (MessageDialogBuilder.showConfirmationAlert(popupText)) {
            MainApplication.getImageFileDAO().queueRemoveAllImagesOfArtist(currentSelectedArtist);
            MainApplication.getArtistDAO().remove(currentSelectedArtist);
            currentSelectedArtist = null;
            VIEW.getAdjustableListView().getRemoveButton().setDisable(true);
            updateArtistList();
            VIEW.setState(View.ViewState.EMPTY);
        }
    }

    private void handleEditCancelClick() {
        if (MainApplication.getArtistDAO().getAll().size() == 0)
            VIEW.setState(View.ViewState.EMPTY);
        else
            VIEW.setState(View.ViewState.VIEW);
    }

    private void handleEditApplyClick() {
        validateEdit();
    }

    @Override
    public View getView() {
        return VIEW;
    }
}
