package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.io.*;
import java.util.ArrayList;

public class ObjectAlbumDAO extends AlbumDAO {
    private static final String FILENAME = "src/albums.bin";

    public ObjectAlbumDAO(){
        objects = new ArrayList<>();
        File file = new File(FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            }
        } catch (Exception e) {
            System.out.println(e);
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
                album.setHoortBij(MainApplication.getArtistDAO().getById(album.getArtistID()));
                objects.add(album);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return super.load();
    }
}