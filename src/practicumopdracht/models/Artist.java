package practicumopdracht.models;

import java.util.ArrayList;

/**
 * Model for containing data related to an Artist.
 * Can contain an collection of albums.
 */
public class Artist {
    private String name;
    private String label;
    private boolean isRetired;

    private ArrayList<Album> albums;
}
