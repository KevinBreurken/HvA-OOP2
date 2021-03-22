package practicumopdracht.data;

import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AlbumDAO implements DAO<Album> {
    protected List<Album> objects;
    private int addOrUpdateCallSinceLoad;

    public void checkIfFileExists(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEdited() {
        return (addOrUpdateCallSinceLoad != 0);
    }

    public List<Album> getAllFor(Artist object) {
        ArrayList<Album> newListOfType = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getArtist() == object)
                newListOfType.add(objects.get(i));
        }
        return newListOfType;
    }

    @Override
    public List<Album> getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Album album) {
        addOrUpdateCallSinceLoad++;

        if (objects.contains(album)) {
            return;
        }
        objects.add(album);
    }

    @Override
    public void remove(Album T) {
        objects.remove(T);
    }

    @Override
    public boolean load() {
        addOrUpdateCallSinceLoad = 0;
        return true;
    }

    @Override
    public boolean save() {
        addOrUpdateCallSinceLoad = 0;
        return true;
    }
}
