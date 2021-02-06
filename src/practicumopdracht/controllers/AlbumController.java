package practicumopdracht.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import practicumopdracht.MainApplication;
import practicumopdracht.PopupMessageBuilder;
import practicumopdracht.models.Album;
import practicumopdracht.views.AlbumView;
import practicumopdracht.views.View;

import java.time.LocalDate;

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
            Album newAlbum = new Album(pickedDate, albumName, salesCount, ratingCount, wikiLink);
            Alert alert = PopupMessageBuilder.createAlertTemplate();
            alert.setContentText(newAlbum.toString());
            alert.show();
            view.setState(View.VIEW_STATE.VIEW);
        } else {
            messageBuilder.createAlert();
        }
    }

    private void handleListAddClick() {
        MainApplication.showAlert("List add click");
    }

    private void handleListRemoveClick() {
        MainApplication.showAlert("List remove click");
    }

    private void handleBackToArtistClick() {
        MainApplication.switchController(new ArtistController());
    }

    private void handleEditAlbumClick() {
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleOpenWikiClick() {
        MainApplication.showAlert("Open Wiki page");
    }

    private void handleAlbumEditApplyClick() {
        validateEdit();
    }

    private void handleAlbumEditCancelClick() {
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
