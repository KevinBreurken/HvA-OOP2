package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import practicumopdracht.MainApplication;
import practicumopdracht.PopupMessageBuilder;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.View;

import java.time.LocalDate;
import java.util.ArrayList;

public class AlbumController extends Controller {

    private ArrayList<Album> albums;
    private AlbumView view;
    private Album currentAlbum;


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
        view.getAdjustableListView().getAddButton().setOnAction(event -> handleListAddClick());
        view.getAdjustableListView().getRemoveButton().setOnAction(event -> handleListRemoveClick());
        view.getAdjustableListView().getRemoveButton().setDisable(true);
        view.getArtistComboBox().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onComboArtistSelected((Artist) newValue);
        });

        view.getAdjustableListView().getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onAlbumListSelected((Album) newValue);
        });
        albums = new ArrayList<>();

        setArtistComboBox();
        updateList();
    }

    private void onComboArtistSelected(Artist item) {
        if (item == null) //caused when selected item is removed.
            return;
        ArtistController.setCurrentArtist(item);
        updateList();
    }

    private void onAlbumListSelected(Album item) {
        if (item == null) //caused when selected item is removed.
            return;
        displayAlbum(item);
        view.getAdjustableListView().getRemoveButton().setDisable(false);
    }

    private void displayAlbum(Album album) {
        currentAlbum = album;
        view.getAlbumTitleLabel().setText(album.getName());
        view.getSalesLabel().setText(String.format("Sales: %.0f", album.getSales()));
        view.getRatingLabel().setText(String.format("Rating: (%d/%d)", album.getRating(), Album.MAX_RATING));
        view.getDateLabel().setText(String.format("Release date: \n%s", album.getReleaseDate().toString()));
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void updateList() {
        ArrayList<Album> albums = (ArrayList<Album>) MainApplication.getAlbumDAO().getAllFor(ArtistController.getCurrentArtist());
        ListView listView = view.getAdjustableListView().getListView();
        listView.setItems(FXCollections.observableList(albums));
        //Source: https://stackoverflow.com/a/36657553
        listView.setCellFactory(param -> new ListCell<Album>() {
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
        if (albums.size() > 0)
            view.getAdjustableListView().getListView().getSelectionModel().select(0);
        else {
            view.setState(View.VIEW_STATE.EMPTY);
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
        artistComboBox.getSelectionModel().select(ArtistController.getCurrentArtist());
        artistComboBox.setMaxWidth(999);

        artistComboBox.setButtonCell(cellFactory.call(null));
        artistComboBox.setCellFactory(cellFactory);

    }

    private void validateEdit() {
        PopupMessageBuilder messageBuilder = new PopupMessageBuilder();
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
        ;

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
            Album newAlbum = new Album(pickedDate, albumName, salesCount, ratingCount, wikiLink, null);
            Alert alert = PopupMessageBuilder.createAlertTemplate();
            alert.setContentText(newAlbum.toString());
            alert.show();
            view.setState(View.VIEW_STATE.VIEW);
            albums.add(newAlbum);
        } else {
            messageBuilder.createAlert();
        }
    }

    private void clearEditFields() {
        view.getNameInputField().setText("");
        view.getAlbumSalesTextField().setText("");
        view.getWikiLinkInputField().setText("");
        view.getDateInputField().setValue(null);
        view.getRatingTextField().setText("0");
    }


    private void handleListAddClick() {
        clearEditFields();
    }

    private void handleListRemoveClick() {
        MainApplication.showAlert("List remove click");
    }

    private void handleBackToArtistClick() {
        MainApplication.switchController(new ArtistController());
    }

    private void handleEditAlbumClick() {
        clearEditFields();
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleOpenWikiClick() {
        MainApplication.showAlert("Open Wiki page");
    }

    private void handleAlbumEditApplyClick() {
        validateEdit();
    }

    private void handleAlbumEditCancelClick() {
        if (albums.size() != 0)
            view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleChangePictureClick() {
        MainApplication.showAlert("Change Picture.");
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
