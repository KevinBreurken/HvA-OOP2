package practicumopdracht.data;

import practicumopdracht.models.Artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ArtistDAO implements DAO {
    protected List<Artist> objects;
    private int addOrUpdateCallSinceLoad;

    public void checkIfFileExists(String fileName){
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

    public Artist getById(int id) {
        try {
            return objects.get(id);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getIDFor(Artist artist) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) == artist)
                return i;
        }
        return -1;
    }

    @Override
    public List<Artist> getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Object T) {
        Artist artistObject = (Artist) T;
        addOrUpdateCallSinceLoad++;

        if (objects.contains(artistObject)) {
            return;
        }
        objects.add(artistObject);
    }

    @Override
    public void remove(Object T) {
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
