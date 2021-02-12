package practicumopdracht.data;

import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.util.ArrayList;
import java.util.List;

public abstract class AlbumDAO implements DAO {
    protected List<Album> objects;
    private int addOrUpdateCallSinceLoad;

    @Override
    public boolean isEdited() {
        return (addOrUpdateCallSinceLoad != 0);
    }

    public List<Album> getAllFor(Artist object){
        ArrayList<Album> newListOfType = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getArtist() == object)
                newListOfType.add(objects.get(i));
        }
        return newListOfType;
    }

    @Override
    public List getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Object T) {
        addOrUpdateCallSinceLoad++;
        if(objects.contains(T))
            return;

        objects.add((Album) T);
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
