package practicumopdracht.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import practicumopdracht.MainApplication;
import practicumopdracht.PopupMessageBuilder;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;
import practicumopdracht.views.ArtistView;
import practicumopdracht.views.View;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ArtistController extends Controller{

    private ArrayList<Artist> artist;
    private ArtistView view;

    public ArtistController(){
        view = new ArtistView();
        view.getViewAlbumsButton().setOnAction(event -> handleAlbumsClick());
        view.getEditArtistButton().setOnAction(event -> handleEditClick());
        view.getArtistEditApplyButton().setOnAction(event -> handleEditApplyClick());
        view.getArtistEditCancelButton().setOnAction(event -> handleEditCancelClick());
        view.getAdjustableListBox().getAddButton().setOnAction(event -> handleListAddClick());
        view.getAdjustableListBox().getRemoveButton().setOnAction(event -> handleListRemoveClick());
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
            Artist newArtist = new Artist(artistName,labelName,false);
            Alert alert = PopupMessageBuilder.createAlertTemplate();
            alert.setContentText(newArtist.toString());
            alert.show();
            view.setState(View.VIEW_STATE.VIEW);
            artist.add(newArtist);
        } else {
            messageBuilder.createAlert();
        }
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
            Artist newArtist = new Artist(artistName,labelName,false);
            Alert alert = PopupMessageBuilder.createAlertTemplate();
            alert.setContentText(newArtist.toString());
            alert.show();
            view.setState(View.VIEW_STATE.VIEW);
            artist.add(newArtist);
        } else {
            messageBuilder.createAlert();
        }
    }

    private void handleAlbumsClick(){
        MainApplication.switchController(new AlbumController());
    }

    private void handleEditClick(){
        view.setState(View.VIEW_STATE.EDIT);
    }

    private void handleListAddClick(){
        MainApplication.showAlert("List add click");
    }

    private void handleListRemoveClick(){
        MainApplication.showAlert("List remove click");
    }

    private void handleEditCancelClick(){
        view.setState(View.VIEW_STATE.VIEW);
    }

    private void handleEditApplyClick(){
        validateEdit();
    }

    @Override
    public View getView() {
        return view;
    }
}
