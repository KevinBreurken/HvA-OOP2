package practicumopdracht.comparators;

import practicumopdracht.models.Album;
import practicumopdracht.models.Artist;

import java.util.Comparator;

public class ArtistComparatorAZ implements Comparator<Artist> {

    private final boolean isAscending;

    public ArtistComparatorAZ(boolean isAscending){
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Artist o1, Artist o2) {
        int compareValue = o1.getName().compareTo(o2.getName());
        return (isAscending) ? -compareValue : compareValue;
    }
}
