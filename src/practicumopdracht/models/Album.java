package practicumopdracht.models;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        return "Album{" +
                "releaseDate=" + releaseDate +
                ", name='" + name + '\'' +
                ", sales=" + sales +
                ", rating=" + rating +
                '}';
    }

}
