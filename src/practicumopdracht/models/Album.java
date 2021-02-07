package practicumopdracht.models;

import java.time.LocalDate;

/**
 * Model for containing data related to an Album.
 */
public class Album {

    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 5;

    private String name;
    private double sales;
    private String wikiLink;
    private LocalDate releaseDate;
    private int rating;
    private Artist hoortBij;

    public Album(LocalDate releaseDate, String name, double sales, int rating, String wikiLink,Artist hoortBij) {
        this.releaseDate = releaseDate;
        this.wikiLink = wikiLink;
        this.name = name;
        this.sales = sales;
        this.rating = rating;
        this.hoortBij = hoortBij;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return hoortBij;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album:\n")
                .append(String.format("\tName: %s\n", name))
                .append(String.format("\tSales: %f\n", sales))
                .append(String.format("\tWiki: %s\n", wikiLink))
                .append(String.format("\tRelease date: %s\n", releaseDate.toString()))
                .append(String.format("\tRating: (%d/%d)\n", rating, MAX_RATING))
                .append(String.format("\tArtist: %s\n", hoortBij.getName()));


        return sb.toString();
    }

}
