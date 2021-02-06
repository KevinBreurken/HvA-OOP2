package practicumopdracht.models;

import java.time.LocalDate;

/**
 * Model for containing data related to an Album.
 */
public class Album {

    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 5;

    private LocalDate releaseDate;
    private String name;
    private String wikiLink;
    private double sales;
    private int rating;

    public Album(LocalDate releaseDate, String name, double sales, int rating, String wikiLink) {
        this.releaseDate = releaseDate;
        this.wikiLink = wikiLink;
        this.name = name;
        this.sales = sales;
        this.rating = rating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album:\n")
                .append(String.format("\tName: %s\n", name))
                .append(String.format("\tSales: %d\n", sales))
                .append(String.format("\tWiki: %s\n",wikiLink))
                .append(String.format("\tRelease date: %s\n",releaseDate.toString()))
                .append(String.format("\tRating: (%d/%d)\n",rating,MAX_RATING));

        return sb.toString();
    }

}
