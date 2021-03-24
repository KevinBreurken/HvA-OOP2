package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.util.Callback;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.MessageDialogBuilder;
import practicumopdracht.comparators.AlbumComparatorAZ;
import practicumopdracht.comparators.AlbumComparatorSales;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.View;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class AlbumController extends Controller {

    private final AlbumView VIEW;
    private Album currentAlbum;
    private Artist currentArtist;
    private boolean isNameSortingAscending;
    private boolean isNameSalesAscending;

    public AlbumController(Artist artist) {
        VIEW = new AlbumView();
        currentArtist = artist;
        //HEADER - SAVE/LOAD
        VIEW.getWindowHandle().getFileLoadButton().setOnAction(event -> handleFileLoadClick());
        VIEW.getWindowHandle().getFileSaveButton().setOnAction(event -> CustomWindowHandle.handleFileSaveClick());

        //ALBUM - GENERAL
        VIEW.getBackToArtistButton().setOnAction(event -> handleBackToArtistClick());
        VIEW.getAdjustableListView().getAddButton().setOnAction(event -> handleListAddClick());
        VIEW.getAdjustableListView().getRemoveButton().setOnAction(event -> handleListRemoveClick());
        VIEW.getAdjustableListView().getRemoveButton().setDisable(true);
        //Add listeners for when an item is selected in the Artist ComboBox
        VIEW.getArtistComboBox().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onComboArtistSelected(newValue));
        //Add listeners for when an item is selected in the Album ListView.
        VIEW.getAdjustableListView().getListView().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onAlbumListSelected(newValue));

        //ALBUM - SORTING
        VIEW.getAlphabetAscendingRadioButton().setOnAction(event -> onAlphabetSortingSelected(true));
        VIEW.getAlphabetDescendingRadioButton().setOnAction(event -> onAlphabetSortingSelected(false));
        VIEW.getSalesAscendingRadioButton().setOnAction(event -> onSalesSortingSelected(true));
        VIEW.getSalesDescendingRadioButton().setOnAction(event -> onSalesSortingSelected(false));

        //ALBUM - CONTENT
        VIEW.getWikiButton().setOnAction(event -> handleOpenWikiClick());
        VIEW.getEditAlbumButton().setOnAction(event -> handleEditAlbumClick());

        //ALBUM - EDIT CONTENT
        VIEW.getRatingDecreaseButton().setOnAction(event -> handleChangeRatingClick(-1));
        VIEW.getRatingIncreaseButton().setOnAction(event -> handleChangeRatingClick(1));
        VIEW.getAlbumEditApplyButton().setOnAction(event -> validateEdit());
        VIEW.getAlbumEditCancelButton().setOnAction(event -> handleAlbumEditCancelClick());

        setArtistComboBox();
        updateAlbumList();

        //Source: https://stackoverflow.com/a/36657553
        VIEW.getAdjustableListView().getListView().setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Album item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getListString() == null) {
                    setText(null);
                } else {
                    setText(item.getListString());
                }
            }
        });
    }

    private void handleFileLoadClick() {
        if (CustomWindowHandle.handleFileLoadClick())
            updateAlbumList();
    }

    private void onAlphabetSortingSelected(boolean isAscending) {
        isNameSortingAscending = isAscending;
        updateAlbumList();
    }

    private void onSalesSortingSelected(boolean isAscending) {
        isNameSalesAscending = isAscending;
        updateAlbumList();
    }

    private void onComboArtistSelected(Artist item) {
        if (item == null) //caused when selected item is removed.
            return;
        currentArtist = item;
        VIEW.setBackgroundImageByPath(MainApplication.getImageFileDAO().getArtistPath() + "/" + currentArtist.getCurrentFileName(), 50);
        updateAlbumList();
    }

    private void onAlbumListSelected(Album item) {
        if (item == null) //caused when selected item is removed.
            return;
        displayAlbum(item);
        VIEW.getAdjustableListView().getRemoveButton().setDisable(false);
    }

    private void displayAlbum(Album album) {
        currentAlbum = album;
        VIEW.getEditArtistComboBox().setValue(currentArtist);
        VIEW.getAlbumTitleLabel().setText(album.getName());
        VIEW.getSalesLabel().setText(String.format("Sales: %.0f", album.getSales()));
        VIEW.getRatingLabel().setText(String.format("Rating: (%d/%d)", album.getRating(), Album.MAX_RATING));
        VIEW.getDateLabel().setText(String.format("Release date: \n%s", album.getReleaseDate().toString()));
        VIEW.setState(View.ViewState.VIEW);
    }

    private void updateAlbumList() {
        ArrayList<Album> albums = (ArrayList<Album>) MainApplication.getAlbumDAO().getAllFor(currentArtist);
        if (albums == null)
            return;

        ListView<Album> listView = VIEW.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(albums));
        FXCollections.sort(listView.getItems(), new AlbumComparatorAZ(isNameSortingAscending).thenComparing(new AlbumComparatorSales(isNameSalesAscending)));
        if (albums.size() > 0)
            VIEW.getAdjustableListView().getListView().getSelectionModel().select(0);
        else {
            VIEW.setState(View.ViewState.EMPTY);
            VIEW.getAdjustableListView().getRemoveButton().setDisable(true);
        }
    }

    private void setArtistComboBox() {
        //Source:https://stackoverflow.com/a/40325634
        //required for setting the combobox button and content to the item.getListString();
        Callback<ListView<Artist>, ListCell<Artist>> cellFactory = new Callback<>() {

            @Override
            public ListCell<Artist> call(ListView<Artist> l) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(Artist item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getListString());
                        }
                    }
                };
            }
        };

        ComboBox<Artist> artistComboBox = VIEW.getArtistComboBox();
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        artistComboBox.setItems(FXCollections.observableList(artists));
        artistComboBox.getSelectionModel().select(currentArtist);
        artistComboBox.setMaxWidth(999);
        artistComboBox.setButtonCell(cellFactory.call(null));
        artistComboBox.setCellFactory(cellFactory);

        ComboBox<Artist> artistEditComboBox = VIEW.getEditArtistComboBox();
        artistEditComboBox.setItems(FXCollections.observableList(artists));
        artistEditComboBox.getSelectionModel().select(currentArtist);
        artistEditComboBox.setButtonCell(cellFactory.call(null));
        artistEditComboBox.setCellFactory(cellFactory);
    }

    private void validateEdit() {
        MessageDialogBuilder messageDialogBuilder = new MessageDialogBuilder();
        //Album Name
        TextField nameField = VIEW.getNameInputField();
        String albumName = nameField.getText();
        boolean albumNameValid = (albumName.length() > 0);
        if (albumNameValid) nameField.getStyleClass().removeAll("error");
        else {
            nameField.getStyleClass().add("error");
            messageDialogBuilder.append("Name: No name entered.");
        }

        //Sales amount
        TextField salesField = VIEW.getAlbumSalesTextField();
        double salesCount;
        try {
            salesCount = Double.parseDouble(salesField.getText());
            salesField.getStyleClass().removeAll("error");
        } catch (Exception e) {
            salesCount = -1;
            salesField.getStyleClass().add("error");
            messageDialogBuilder.append("Sales: Value is incorrect.");
        }

        //Wiki link:
        TextArea textArea = VIEW.getWikiLinkInputField();
        String wikiLink = textArea.getText();
        boolean wikiLinkValid = (wikiLink.contains("wiki") && !wikiLink.contains(" "));
        if (wikiLinkValid) textArea.getStyleClass().removeAll("error");
        else {
            textArea.getStyleClass().add("error");
            messageDialogBuilder.append("Wiki: Incorrect wiki link.");
        }

        //Release date:
        DatePicker datePicker = VIEW.getDateInputField();
        LocalDate pickedDate = datePicker.getValue();
        boolean dateValid = (pickedDate != null) && pickedDate.isBefore(LocalDate.now());
        if (dateValid) datePicker.getStyleClass().removeAll("error");
        else {
            datePicker.getStyleClass().add("error");
            messageDialogBuilder.append("Date: Incorrect date.");
        }

        //Rating value
        TextField ratingField = VIEW.getRatingTextField();
        int ratingCount;
        try {
            ratingCount = Integer.parseInt(ratingField.getText());
            if (ratingCount < Album.MIN_RATING || ratingCount > Album.MAX_RATING) {
                throw new Exception();
            } else {
                ratingField.getStyleClass().removeAll("error");
            }
        } catch (Exception e) {
            ratingCount = -1;
            ratingField.getStyleClass().add("error");
            messageDialogBuilder.append("Rating: Incorrect value. (0/5)");
        }

        if (messageDialogBuilder.getTotalAppendCount() == 0) {
            Artist artistCurrentlySelected = VIEW.getEditArtistComboBox().getValue();
            Album newAlbum;
            if (currentAlbum != null) {
                currentAlbum.setName(albumName);
                currentAlbum.setReleaseDate(pickedDate);
                currentAlbum.setSales(salesCount);
                currentAlbum.setRating(ratingCount);
                currentAlbum.setWikiLink(wikiLink);
                currentAlbum.setHoortBij(artistCurrentlySelected);
                newAlbum = currentAlbum;
                messageDialogBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Edited '%s'\n\n%s", newAlbum.getName(),newAlbum.toString()));
            } else {
                newAlbum = new Album(pickedDate, albumName, salesCount, ratingCount, wikiLink, artistCurrentlySelected);
                messageDialogBuilder.createAlert(Alert.AlertType.INFORMATION, String.format("Added '%s' to the list.\n\n%s", newAlbum.getName(),newAlbum.toString()));
            }
            VIEW.getArtistComboBox().setValue(artistCurrentlySelected);
            currentArtist = artistCurrentlySelected;
            applyFromEditView(newAlbum);
        } else {
            messageDialogBuilder.createAlert(Alert.AlertType.ERROR);
        }
    }

    private void applyFromEditView(Album album) {
        //Remove the currently selected Album.
        if (currentAlbum != null)
            MainApplication.getAlbumDAO().remove(currentAlbum);
        //Add the newly made Album.
        MainApplication.getAlbumDAO().addOrUpdate(album);
        updateAlbumList();
        VIEW.getAdjustableListView().getListView().getSelectionModel().select(album);
        VIEW.setState(View.ViewState.VIEW);
    }

    private void clearEditFields() {
        VIEW.getNameInputField().setText("");
        VIEW.getAlbumSalesTextField().setText("");
        VIEW.getWikiLinkInputField().setText("");
        VIEW.getDateInputField().setValue(null);
        VIEW.getRatingTextField().setText("0");
    }

    private void setEditFieldsByAlbum(Album album) {
        VIEW.getNameInputField().setText(album.getName());
        VIEW.getAlbumSalesTextField().setText("" + album.getSales());
        VIEW.getWikiLinkInputField().setText(album.getWikiLink());
        VIEW.getDateInputField().setValue(album.getReleaseDate());
        VIEW.getRatingTextField().setText("" + album.getRating());
    }


    private void handleListAddClick() {
        clearEditFields();
        currentAlbum = null;
        VIEW.setState(View.ViewState.EDIT);
    }

    private void handleListRemoveClick() {
        Alert alert = MessageDialogBuilder.createAlertTemplate(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format("Are you sure you want to remove %s?\n", currentAlbum.getName()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            MainApplication.getAlbumDAO().remove(currentAlbum);
            currentAlbum = null;
            updateAlbumList();
        }
    }

    private void handleBackToArtistClick() {
        MainApplication.switchController(new ArtistController());
    }

    private void handleEditAlbumClick() {
        setEditFieldsByAlbum(currentAlbum);
        VIEW.setState(View.ViewState.EDIT);
    }

    private void handleOpenWikiClick() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(URI.create(currentAlbum.getWikiLink()));
        } catch (IOException e) {
            MessageDialogBuilder.showPopupAlert("couldn't open browser.");
            e.printStackTrace();
        }
    }

    private void handleAlbumEditCancelClick() {
        VIEW.setState((MainApplication.getAlbumDAO().getAll().size() == 0) ? View.ViewState.EMPTY : View.ViewState.VIEW);
    }

    private void handleChangeRatingClick(int increase) {
        TextField ratingField = VIEW.getRatingTextField();
        try {
            int ratingCount = Integer.parseInt(ratingField.getText());
            ratingCount += increase;
            ratingCount = Math.max(Album.MIN_RATING, Math.min(Album.MAX_RATING, ratingCount));
            ratingField.setText("" + ratingCount);
        } catch (Exception e) {
            ratingField.setText("0");
        }
    }

    @Override
    public View getView() {
        return VIEW;
    }

}
