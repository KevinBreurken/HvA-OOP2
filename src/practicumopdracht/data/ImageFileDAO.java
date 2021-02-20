package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Artist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageFileDAO {

    private static String directoryPath = new File("").getAbsolutePath() + "/images";
    private static String albumPrefix = "/albums";
    private static String artistPrefix = "/artists";

    public ImageFileDAO() {
        try {
            Path path = Paths.get(directoryPath + albumPrefix);
            Files.createDirectories(path);

            path = Paths.get(directoryPath + artistPrefix);
            Files.createDirectories(path);

        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
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
            e.printStackTrace();
        }
    }

    public void removeOldArtistImage(Artist artist) {
        if (artist.getUnsavedImageFileName() != null) {
            removeArtistImage(artist.getImageFileName());
            artist.setFileName(artist.getUnsavedImageFileName());
            artist.setUnsavedImageFileName("");
        }
    }

    public void checkForExistingUnsavedImages(Artist artist){
        System.out.println("Check exist unsaved: " +artist.getUnsavedImageFileName());
        if (artist.getUnsavedImageFileName() != null)
            MainApplication.getImageFileDAO().removeArtistImage(artist.getUnsavedImageFileName());
    }

    public String getArtistPath() {
        return directoryPath + artistPrefix;
    }
}
