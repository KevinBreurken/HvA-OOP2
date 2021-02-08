package practicumopdracht.data;

import practicumopdracht.models.Artist;

import java.util.List;

public abstract class ArtistDAO implements DAO {
    protected List<Artist> objects;

    public Artist getById(int id) {
        try {
            return objects.get(id);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public List getAll() {
        return objects;
    }

    @Override
    public void addOrUpdate(Object T) {
        for (int i = 0; i < objects.size(); i++) {
            System.out.println(objects.get(i).toString());
        }
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
        return false;
    }

    @Override
    public boolean save() {
        return false;
    }

}
