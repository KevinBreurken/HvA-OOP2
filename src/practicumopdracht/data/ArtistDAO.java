package practicumopdracht.data;

import practicumopdracht.models.Artist;

import java.util.List;

public abstract class ArtistDAO implements DAO {
    protected List<Artist> objects;
    private int addOrUpdateCallSinceLoad;

    @Override
    public boolean isEdited() {
        return (addOrUpdateCallSinceLoad != 0);
    }

    public Artist getById(int id) {
        try {
            return objects.get(id);
        } catch (IndexOutOfBoundsException e) {
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
    public List getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Object T) {
        addOrUpdateCallSinceLoad++;
        if (objects.contains(T)) {
            return;
        }
        objects.add((Artist) T);
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
