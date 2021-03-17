package practicumopdracht.data;

import practicumopdracht.MainApplication;
import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.time.LocalDate;
import java.util.ArrayList;

public class FakeAlbumDAO extends AlbumDAO {

    @Override
    public boolean save() {
        return super.save();
    }

    @Override
    public boolean load() {
        objects = new ArrayList<>();
        Artist arctic = MainApplication.getArtistDAO().getById(0);
        addOrUpdate(new Album(LocalDate.of(2018, 12, 1), "Tranquility Base Hotel & Casino", 238742, 5, "https://en.wikipedia.org/wiki/Tranquility_Base_Hotel_%26_Casino", arctic));
        addOrUpdate(new Album(LocalDate.of(2008, 5, 3), "Humbug", 525323, 3, "https://en.wikipedia.org/wiki/Humbug_(album)", arctic));
        addOrUpdate(new Album(LocalDate.of(1998, 2, 3), "Whatever people say I am, that's what I'm not", 52323, 4, "https://en.wikipedia.org/wiki/Whatever_People_Say_I_Am,_That%27s_What_I%27m_Not", arctic));

        Artist kooks = MainApplication.getArtistDAO().getById(1);
        addOrUpdate(new Album(LocalDate.of(2008, 4, 11), "Konk", 38578, 4, "https://en.wikipedia.org/wiki/Konk_(album)", kooks));
        return super.load();
    }
}
