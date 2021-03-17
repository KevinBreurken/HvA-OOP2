package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.util.Callback;
import practicumopdracht.CustomWindowHandle;
import practicumopdracht.MainApplication;
import practicumopdracht.MessageBuilder;
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

    private AlbumView view;
    private Album currentAlbum;
    private Artist currentArtist;
    private boolean isNameSortingAscending;
    private boolean isNameSalesAscending;

    public AlbumController(Artist artist) {
        view = new AlbumView();
        currentArtist = artist;
        //HEADER - SAVE/LOAD
        view.getWindowHandle().getFileLoadButton().setOnAction(event -> handleFileLoadClick());
        view.getWindowHandle().getFileSaveButton().setOnAction(event -> handleFileSaveClick());

        //ALBUM - GENERAL
        view.getBackToArtistButton().setOnAction(event -> handleBackToArtistClick());
        view.getAdjustableListView().getAddButton().setOnAction(event -> handleListAddClick());
        view.getAdjustableListView().getRemoveButton().setOnAction(event -> handleListRemoveClick());
        view.getAdjustableListView().getRemoveButton().setDisable(true);
        //Add listeners for when an item is selected in the Artist ComboBox
        view.getArtistComboBox().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onComboArtistSelected((Artist) newValue);
        });
        //Add listeners for when an item is selected in the Album ListView.
        view.getAdjustableListView().getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onAlbumListSelected((Album) newValue);
        });

        //ALBUM - SORTING
        view.getAlphabetAscendingRadioButton().setOnAction(event -> onAlphabetSortingSelected(true));
        view.getAlphabetDescendingRadioButton().setOnAction(event -> onAlphabetSortingSelected(false));
        view.getSalesAscendingRadioButton().setOnAction(event -> onSalesSortingSelected(true));
        view.getSalesDescendingRadioButton().setOnAction(event -> onSalesSortingSelected(false));


        //ALBUM - CONTENT
        view.getWikiButton().setOnAction(event -> handleOpenWikiClick());
        view.getEditAlbumButton().setOnAction(event -> handleEditAlbumClick());

        //ALBUM - EDIT CONTENT
        view.getRatingDecreaseButton().setOnAction(event -> handleDecreaseRatingClick());
        view.getRatingIncreaseButton().setOnAction(event -> handleIncreaseRatingClick());
        view.getAlbumEditApplyButton().setOnAction(event -> handleAlbumEditApplyClick());
        view.getAlbumEditCancelButton().setOnAction(event -> handleAlbumEditCancelClick());
        view.getChangeImageButton().setOnAction(event -> handleChangePictureClick());

        setArtistComboBox();
        updateAlbumList();

        //Source: https://stackoverflow.com/a/36657553
        view.getAdjustableListView().getListView().setCellFactory(param -> new ListCell<Album>() {
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

    private void handleFileSaveClick() {
        CustomWindowHandle.handleFileSaveClick();
    }

    private void handleFileLoadClick() {
        if (CustomWindowHandle.handleFileLoadClick()) {
            updateAlbumList();
        }
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
        view.setBackgroundImageByPath(MainApplication.getImageFileDAO().getArtistPath() + "/" + currentArtist.getCurrentFileName(),50);
        System.out.println("asd");
        updateAlbumList();
    }

    private void onAlbumListSelected(Album item) {
        if (item == null) //caused when selected item is removed.
            return;
        displayAlbum(item);
        view.getAdjustableListView().getRemoveButton().setDisable(false);
    }

    private void displayAlbum(Album album) {
        currentAlbum = album;
        view.getEditArtistComboBox().setValue(currentArtist);
        view.getAlbumTitleLabel().setText(album.getName());
        view.getSalesLabel().setText(String.format("Sales: %.0f", album.getSales()));
        view.getRatingLabel().setText(String.format("Rating: (%d/%d)", album.getRating(), Album.MAX_RATING));
        view.getDateLabel().setText(String.format("Release date: \n%s", album.getReleaseDate().toString()));
        view.setState(View.ViewState.VIEW);
    }

    private void updateAlbumList() {
        ArrayList<Album> albums = (ArrayList<Album>) MainApplication.getAlbumDAO().getAllFor(currentArtist);
        if (albums == null)
            return;

        ListView listView = view.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(albums));
        FXCollections.sort(listView.getItems(), new AlbumComparatorAZ(isNameSortingAscending).thenComparing(new AlbumComparatorSales(isNameSalesAscending)));
        if (albums.size() > 0)
            view.getAdjustableListView().getListView().getSelectionModel().select(0);
        else {
            view.setState(View.ViewState.EMPTY);
            view.getAdjustableListView().getRemoveButton().setDisable(true);
        }

    }

    private void setArtistComboBox() {

        //Source:https://stackoverflow.com/a/40325634
        //required for setting the combobox button and content to the item.getListString();
        Callback<ListView<Artist>, ListCell<Artist>> cellFactory = new Callback<ListView<Artist>, ListCell<Artist>>() {

            @Override
            public ListCell<Artist> call(ListView<Artist> l) {
                return new ListCell<Artist>() {

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

        ComboBox artistComboBox = view.getArtistComboBox();
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        artistComboBox.setItems(FXCollections.observableList(artists));
        artistComboBox.getSelectionModel().select(currentArtist);
        artistComboBox.setMaxWidth(999);
        artistComboBox.setButtonCell(cellFactory.call(null));
        artistComboBox.setCellFactory(cellFactory);

        ComboBox artistEditComboBox = view.getEditArtistComboBox();
        artistEditComboBox.setItems(FXCollections.observableList(artists));
        artistEditComboBox.getSelectionModel().select(currentArtist);
        artistEditComboBox.setButtonCell(cellFactory.call(null));
        artistEditComboBox.setCellFactory(cellFactory);
    }

    private void validateEdit() {
        MessageBuilder messageBuilder = new MessageBuilder();
        //Album Name
        TextField nameField = view.getNameInputField();
        String albumName = nameField.getText().toString();
        boolean albumNameValid = (albumName.length() > 0);
        if (albumNameValid) nameField.getStyleClass().removeAll("error");
        else {
            nameField.getStyleClass().add("error");
            messageBuilder.append("Name: No name entered.");
        }

        //Sales amount
        TextField salesField = view.getAlbumSalesTextField();
        double salesCount;
        try {
            salesCount = Double.parseDouble(salesField.getText());
            salesField.getStyleClass().removeAll("error");
        } catch (Exception e) {
            salesCount = -1;
            salesField.getStyleClass().add("error");
            messageBuilder.append("Sales: Value is incorrect.");
        }

        //Wiki link:
        TextArea textArea = view.getWikiLinkInputField();
        String wikiLink = textArea.getText().toString();
        boolean wikiLinkValid = (wikiLink.contains("wiki") && !wikiLink.contains(" "));
        if (wikiLinkValid) textArea.getStyleClass().removeAll("error");
        else {
            textArea.getStyleClass().add("error");
            messageBuilder.append("Wiki: Incorrect wiki link.");
        }

        //Release date:
        DatePicker datePicker = view.getDateInputField();
        LocalDate pickedDate = datePicker.getValue();
        boolean dateValid = (pickedDate != null) && pickedDate.isBefore(LocalDate.now());
        if (dateValid) datePicker.getStyleClass().removeAll("error");
        else {
            datePicker.getStyleClass().add("error");
            messageBuilder.append("Date: Incorrect date.");
        }

        //Rating value
        TextField ratingField = view.getRatingTextField();
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
            messageBuilder.append("Rating: Incorrect value. (0/5)");
        }

        if (messageBuilder.getTotalAppendCount() == 0) {
            Artist artistCurrentlySelected = (Artist) view.getEditArtistComboBox().getValue();
            Album newAlbum;
            if (currentAlbum != null) {
                currentAlbum.setName(albumName);
                currentAlbum.setReleaseDate(pickedDate);
                currentAlbum.setSales(salesCount);
                currentAlbum.setRating(ratingCount);
                currentAlbum.setWikiLink(wikiLink);
                currentAlbum.setHoortBij(artistCurrentlySelected);
                newAlbum = currentAlbum;
            } else {
                newAlbum = new Album(pickedDate, albumName, salesCount, ratingCount, wikiLink, artistCurrentlySelected);
            }
            messageBuilder.createAlert(Alert.AlertType.INFORMATION);
            view.getArtistComboBox().setValue(artistCurrentlySelected);
            currentArtist = artistCurrentlySelected;
            applyFromEditView(newAlbum);
        } else {
            messageBuilder.createAlert(Alert.AlertType.ERROR);
        }
    }

    private void applyFromEditView(Album album) {
        //Remove the currently selected Album.
        if (currentAlbum != null)
            MainApplication.getAlbumDAO().remove(currentAlbum);
        //Add the newly made Album.
        MainApplication.getAlbumDAO().addOrUpdate(album);
        updateAlbumList();
        view.getAdjustableListView().getListView().getSelectionModel().select(album);
        view.setState(View.ViewState.VIEW);
    }

    private void clearEditFields() {
        view.getNameInputField().setText("");
        view.getAlbumSalesTextField().setText("");
        view.getWikiLinkInputField().setText("");
        view.getDateInputField().setValue(null);
        view.getRatingTextField().setText("0");
    }

    private void setEditFieldsByAlbum(Album album) {
        view.getNameInputField().setText(album.getName());
        view.getAlbumSalesTextField().setText("" + album.getSales());
        view.getWikiLinkInputField().setText(album.getWikiLink());
        view.getDateInputField().setValue(album.getReleaseDate());
        view.getRatingTextField().setText("" + album.getRating());
    }


    private void handleListAddClick() {
        clearEditFields();
        currentAlbum = null;
        view.setState(View.ViewState.EDIT);
    }

    private void handleListRemoveClick() {
        Alert alert = MessageBuilder.createAlertTemplate(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format("Are you sure you want to remove %s?\n", currentAlbum.getName(), ""));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
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
        view.setState(View.ViewState.EDIT);
    }

    private void handleOpenWikiClick() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(URI.create(currentAlbum.getWikiLink()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAlbumEditApplyClick() {
        validateEdit();
    }

    private void handleAlbumEditCancelClick() {
        if (MainApplication.getAlbumDAO().getAll().size() == 0)
            view.setState(View.ViewState.EMPTY);
        else
            view.setState(View.ViewState.VIEW);
    }

    private void handleChangePictureClick() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Change Picture.");
        a.show();
    }

    private void handleIncreaseRatingClick() {
        TextField ratingField = view.getRatingTextField();
        try {
            int ratingCount = Integer.parseInt(ratingField.getText());
            if (ratingCount < Album.MAX_RATING)
                ratingCount++;

            ratingField.setText("" + ratingCount);
        } catch (Exception e) {
            ratingField.setText("0");
        }
    }

    private void handleDecreaseRatingClick() {
        TextField ratingField = view.getRatingTextField();
        try {
            int ratingCount = Integer.parseInt(ratingField.getText());
            if (ratingCount > Album.MIN_RATING)
                ratingCount--;

            ratingField.setText("" + ratingCount);
        } catch (Exception e) {
            ratingField.setText("0");
        }
    }

    @Override
    public View getView() {
        return view;
    }

}
