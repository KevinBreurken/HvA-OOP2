package practicumopdracht.data;

import practicumopdracht.MessageDialogBuilder;
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
            MessageDialogBuilder.showPopupAlert(String.format("file could not be created at path: %s",fileName));
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEdited() {
        return (addOrUpdateCallSinceLoad != 0);
    }

    public List<Album> getAllFor(Artist object) {
        ArrayList<Album> newListOfType = new ArrayList<>();
        for (Album album : objects) {
            if (album.getArtist() == object)
                newListOfType.add(album);
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
    public void remove(Album album) {
        objects.remove(album);
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
