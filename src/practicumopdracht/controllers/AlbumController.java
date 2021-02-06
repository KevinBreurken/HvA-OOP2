package practicumopdracht.controllers;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.vendors.ResizeHelper;
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
        ErrorMessageBuilder messageBuilder = new ErrorMessageBuilder();
        //Album Name
        TextField nameField = view.getNameInputField();
        String albumName = nameField.getText().toString();
        boolean albumNameValid = (albumName.length() > 0);
        if (albumNameValid) nameField.getStyleClass().removeAll("error");
        else nameField.getStyleClass().add("error");

        //Sales amount
        TextField salesField = view.getAlbumSalesTextField();
        double salesCount;
        try {
            salesCount = Double.parseDouble(salesField.getText());
            salesField.getStyleClass().removeAll("error");
        } catch (Exception e) {
            salesCount = -1;
            salesField.getStyleClass().add("error");
            messageBuilder.append("Salesfield is incorrect.");
        }

        //Wiki link:
        TextArea textArea = view.getWikiLinkInputField();
        String wikiLink = textArea.getText().toString();
        boolean wikiLinkValid = (wikiLink.contains("wiki") && !wikiLink.contains(" "));
        if (wikiLinkValid) textArea.getStyleClass().removeAll("error");
        else textArea.getStyleClass().add("error");

        //Release date:
        DatePicker datePicker = view.getDateInputField();
        LocalDate pickedDate = datePicker.getValue();
        boolean dateValid = (pickedDate != null) && pickedDate.isBefore(LocalDate.now());
        if (dateValid) datePicker.getStyleClass().removeAll("error");
        else datePicker.getStyleClass().add("error");

        //Rating value
        TextField ratingField = view.getRatingTextField();
        int ratingCount;
        try {
            ratingCount = Integer.parseInt(salesField.getText());
            if (ratingCount < Album.MIN_RATING || ratingCount > Album.MAX_RATING) {
                throw new Exception();
            } else {
                ratingField.getStyleClass().removeAll("error");
            }
        } catch (Exception e) {
            ratingCount = -1;
            ratingField.getStyleClass().add("error");
            messageBuilder.append("Rating is incorrect.");
        }

        if (messageBuilder.getTotalAppendCount() == 0) {
            Album newAlbum = new Album(pickedDate, albumName, salesCount, ratingCount, wikiLink);
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
//        view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleAlbumEditCancelClick() {
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleChangePictureClick() {
        MainApplication.showAlert("Change Picture.");
    }

    private void handleIncreaseRatingClick() {
        MainApplication.showAlert("Increase value.");
    }

    private void handleDecreaseRatingClick() {
        MainApplication.showAlert("Decrease value.");
    }

    @Override
    public View getView() {
        return view;
    }

    public class ErrorMessageBuilder {

        private StringBuilder sb;
        private int totalAppendCount = 0;

        public ErrorMessageBuilder() {
            this.sb = new StringBuilder();
            sb.append("Errors found: \n\n");
        }

        public void append(String text) {
            sb.append(text + "\n");
            totalAppendCount++;
        }

        public int getTotalAppendCount() {
            return totalAppendCount;
        }

        public void createAlert(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            alert.getDialogPane().setGraphic(new ImageView(MainApplication.loadImage("src/practicumopdracht/content/error.png")));
            dialogPane.getChildren().get(0).setOnMousePressed(pressEvent -> {
                dialogPane.getChildren().get(0).setOnMouseDragged(dragEvent -> {
                    alert.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    alert.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            alert.initStyle(StageStyle.UNDECORATED);
            dialogPane.getStylesheets().add("practicumopdracht/default.css");
            alert.setContentText(sb.toString());
            alert.show();
        }
        @Override
        public String toString() {
            return sb.toString();
        }
    }

}
