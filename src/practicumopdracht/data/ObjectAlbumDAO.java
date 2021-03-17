package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;
import practicumopdracht.views.View;

import java.io.*;
import java.util.ArrayList;

public class ObjectAlbumDAO extends AlbumDAO {
    private static final String FILENAME = "src/albums.bin";

    public ObjectAlbumDAO(){
        objects = new ArrayList<>();
        checkIfFileExists(FILENAME);
    }

    @Override
    public boolean save() {
        File file = new File(FILENAME);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeInt(objects.size());
            for (Album album : objects) {
                objectOutputStream.writeObject(album);
                objectOutputStream.writeInt(MainApplication.getArtistDAO().getIDFor(album.getArtist()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }

    @Override
    public boolean load() {
        objects = new ArrayList<Album>();

        File file = new File(FILENAME);
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            int objectAmount = objectInputStream.readInt();

            for (int i = 0; i < objectAmount; i++) {
                Album album = (Album) objectInputStream.readObject();
                album.setHoortBij(MainApplication.getArtistDAO().getById(objectInputStream.readInt()));
                objects.add(album);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.load();
    }
}