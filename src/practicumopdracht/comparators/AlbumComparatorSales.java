package practicumopdracht.comparators;

import practicumopdracht.models.Album;

import java.util.Comparator;

public class AlbumComparatorSales implements Comparator<Album> {

    private final boolean isAscending;

    public AlbumComparatorSales(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Album o1, Album o2) {
        int compareValue = Double.compare(o1.getSales(), o2.getSales());
        return (isAscending) ? -compareValue : compareValue;
    }
}
