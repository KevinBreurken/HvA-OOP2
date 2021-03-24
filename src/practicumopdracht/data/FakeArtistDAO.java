package practicumopdracht.data;

import practicumopdracht.models.Artist;

import java.util.ArrayList;

public class FakeArtistDAO extends ArtistDAO {
    @Override
    public boolean load() {
        objects = new ArrayList<>();
        addOrUpdate(new Artist("Arctic Monkeys","Domino Records",true,null));
        addOrUpdate(new Artist("The Kooks","Winestone Labels",true,null));
        addOrUpdate(new Artist("Infected Mushrooms","Mushside Records",false,null));
        return super.load();
    }

    @Override
    public boolean save() {
        return super.save();
    }
}
