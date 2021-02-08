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

    public void setHoortBij(Artist hoortBij) {
        this.hoortBij = hoortBij;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Album(LocalDate releaseDate, String name, double sales, int rating, String wikiLink, Artist hoortBij) {
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

    public double getSales() {
        return sales;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public Artist getArtist() {
        return hoortBij;
    }

    public String getListString() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album:\n")
                .append(String.format("\tName: %s\n", name))
                .append(String.format("\tSales: %f\n", sales))
                .append(String.format("\tWiki: %s\n", wikiLink))
                .append(String.format("\tRelease date: %s\n", releaseDate.toString()))
                .append(String.format("\tRating: (%d/%d)\n", rating, MAX_RATING));

        if (hoortBij != null)
            sb.append(String.format("\tArtist: %s\n", hoortBij.getName()));


        return sb.toString();
    }

}
