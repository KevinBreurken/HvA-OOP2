package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Artist;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextArtistDAO extends ArtistDAO {

    private static final String FILENAME = "src/artists.txt";
    private static final String SPLIT_SEQUENCE = ",_,";

    @Override
    public boolean load() {
        checkIfFileExists(FILENAME);
        objects = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(FILENAME))) {

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] split = line.split(SPLIT_SEQUENCE);
                addOrUpdate(new Artist(split[0],split[1],Boolean.parseBoolean(split[2]),split[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return super.load();
    }

    @Override
    public boolean save() {
        try {
            PrintWriter printWriter = new PrintWriter(FILENAME);
            for (Artist artist : objects) {
                printWriter.print(artist.getName() + SPLIT_SEQUENCE);
                printWriter.print(artist.getLabel() + SPLIT_SEQUENCE);
                printWriter.print(artist.isFavorite() + SPLIT_SEQUENCE);
                printWriter.println(artist.getCurrentFileName());
                artist.setUnsavedImageFileName(null);
            }
            MainApplication.getImageFileDAO().removeQueuedImages();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }
}
