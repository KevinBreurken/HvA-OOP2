package practicumopdracht.models;

/**
 * Model for containing data related to an Artist.
 * Can contain an collection of albums.
 */
public class Artist {
    private String name;
    private String label;
    private boolean favorited;
    private String imageFileName;
    private String unsavedImageFileName;

    public Artist(String name, String label, boolean favorited, String imagePath) {
        this.name = name;
        this.label = label;
        this.favorited = favorited;
        this.imageFileName = (imagePath != null) ? imagePath : "?";
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getUnsavedImageFileName() {
        return unsavedImageFileName;
    }

    public void setUnsavedImageFileName(String unsavedImageFileName) {
        this.unsavedImageFileName = unsavedImageFileName;
    }

    public String getCurrentFileName() {
        return (unsavedImageFileName == null) ? imageFileName : unsavedImageFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\tName: %s\n", name))
                .append(String.format("\tSales: %s\n", label))
                .append(String.format("\tFavorite: %s\n", favorited))
                .append(String.format("\tImage filename: %s\n", imageFileName));

        return sb.toString();
    }

    /**
     * Used for displaying an artist in the List View.
     *
     * @return
     */
    public String getListString() {
        StringBuilder sb = new StringBuilder();
        String favUnicodeString = favorited ? "\u2605" : "\u2606";
        sb.append(String.format("%s - %s %s", name, label, favUnicodeString));

        return sb.toString();
    }
}
