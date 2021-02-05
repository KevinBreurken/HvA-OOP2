package practicumopdracht.models;

import java.time.LocalDate;

/**
 * Model for containing data related to an Album.
 */
public class Album {
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
        return "Album{" +
                "releaseDate=" + releaseDate +
                ", name='" + name + '\'' +
                ", sales=" + sales +
                ", rating=" + rating +
                '}';
    }
}
