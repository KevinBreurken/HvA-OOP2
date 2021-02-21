package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Artist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class ImageFileDAO {

    private static String directoryPath = new File("").getAbsolutePath() + "/images";
    private static String albumPrefix = "/albums";
    private static String artistPrefix = "/artists";

    private ArrayList<String> filesToRemoveOnSave;

    public void addImageToRemoveOnSave(String fileName){
        filesToRemoveOnSave.add(fileName);
    }

    public void removeQueuedImages(){
        for (int i = 0; i < filesToRemoveOnSave.size(); i++) {
            removeArtistImage(filesToRemoveOnSave.get(i));
        }
        filesToRemoveOnSave.clear();
    }

    public void removeUnsavedImages(){
        ArrayList<Artist> artists = (ArrayList<Artist>) MainApplication.getArtistDAO().getAll();
        for (int i = 0; i < artists.size(); i++) {
            if(artists.get(i).getUnsavedImageFileName() != null){
               removeArtistImage(artists.get(i).getUnsavedImageFileName());
            }
        }
    }

    public ImageFileDAO() {
        try {
            Path path = Paths.get(directoryPath + albumPrefix);
            Files.createDirectories(path);

            path = Paths.get(directoryPath + artistPrefix);
            Files.createDirectories(path);

        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
        filesToRemoveOnSave = new ArrayList<>();
    }

    public void saveArtistImage(File file) {
        try {
            Path copied = Paths.get(directoryPath + artistPrefix + "/" + file.getName());
            Path originalPath = Paths.get(file.getAbsolutePath());
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to save Image!" + e.getMessage());
        }
    }

    public void removeArtistImage(String filename) {
        try {
            System.out.println("Remove: " + directoryPath + artistPrefix + "/" + filename);
            Files.delete(Paths.get(directoryPath + artistPrefix + "/" + filename));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void removeOldArtistImage(Artist artist) {
        if (artist.getUnsavedImageFileName() != null) {
            removeArtistImage(artist.getImageFileName());
            artist.setFileName(artist.getUnsavedImageFileName());
            artist.setUnsavedImageFileName(null);
        }
    }

    public void queRemoveAllImagesOfArtist(Artist artist) {
        if (artist.getUnsavedImageFileName() != null)
            addImageToRemoveOnSave(artist.getUnsavedImageFileName());

        if(artist.getImageFileName() != null)
            addImageToRemoveOnSave(artist.getImageFileName());
    }

    public void checkForExistingUnsavedImages(Artist artist) {
        if (artist.getUnsavedImageFileName() != null)
            MainApplication.getImageFileDAO().removeArtistImage(artist.getUnsavedImageFileName());
    }

    public String getArtistPath() {
        return directoryPath + artistPrefix;
    }
}
