package practicumopdracht.models;

import java.util.ArrayList;

/**
 * Model for containing data related to an Artist.
 * Can contain an collection of albums.
 */
public class Artist {
    private String name;
    private String label;
    private boolean favorited;

    private ArrayList<Album> albums;

    public Artist(String name, String label, boolean favorited) {
        this.name = name;
        this.label = label;
        this.favorited = favorited;
    }

    public void AddAlbum(Album album) {
        albums.add(album);
    }
}
