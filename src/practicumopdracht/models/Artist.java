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

    public String getName() {
        return name;
    }

    public Artist(String name, String label, boolean favorited) {
        this.name = name;
        this.label = label;
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\tName: %s\n", name))
                .append(String.format("\tSales: %s\n",label))
                .append(String.format("\tFavorite: %s\n", favorited));

        return sb.toString();
    }

    /**
     * Used for displaying an artist in the List View.
     * @return
     */
    public String getListString() {
        StringBuilder sb = new StringBuilder();
        String favUnicodeString = favorited ? "\u2605" : "\u2606";
        sb.append(String.format("%s - %s %s", name,label,favUnicodeString));

        return sb.toString();
    }
}
