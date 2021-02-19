package practicumopdracht.comparators;

import practicumopdracht.models.Album;

import java.util.Comparator;

public class AlbumComparatorAZ implements Comparator<Album> {

    private final boolean isAscending;

    public AlbumComparatorAZ(boolean isAscending){
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Album o1, Album o2) {
        int compareValue = o1.getName().compareTo(o2.getName());
        return (isAscending) ? -compareValue : compareValue;
    }
}