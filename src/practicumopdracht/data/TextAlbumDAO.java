package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TextAlbumDAO extends AlbumDAO {

    private static final String FILENAME = "src/albums.txt";
    private static final String SPLIT_SEQUENCE = ",_,";

    public TextAlbumDAO() {
        objects = new ArrayList<>();
    }

    @Override
    public boolean load() {
        checkIfFileExists(FILENAME);
        try (Scanner scanner = new Scanner(new File(FILENAME))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(SPLIT_SEQUENCE);
                Artist linkedArtist = MainApplication.getArtistDAO().getById(Integer.parseInt(split[5]));
                addOrUpdate(new Album(LocalDate.parse(split[3]), split[0], Double.parseDouble(split[1]), Integer.parseInt(split[4]), split[2], linkedArtist));
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
            for (Album album : objects) {
                printWriter.print(album.getName() + SPLIT_SEQUENCE);
                printWriter.print(album.getSales() + SPLIT_SEQUENCE);
                printWriter.print(album.getWikiLink() + SPLIT_SEQUENCE);
                printWriter.print(album.getReleaseDate() + SPLIT_SEQUENCE);
                printWriter.print(album.getRating() + SPLIT_SEQUENCE);
                printWriter.println(MainApplication.getArtistDAO().getIDFor(album.getArtist()) + SPLIT_SEQUENCE);
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }
}
