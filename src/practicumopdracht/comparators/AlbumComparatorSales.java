package practicumopdracht.comparators;

import practicumopdracht.models.Album;

public class AlbumComparatorSales extends DirectionalComparator<Album> {

    public AlbumComparatorSales(boolean isAscending) {
        super(isAscending);
    }

    @Override
    public int compare(Album o1, Album o2) {
        int compareValue = Double.compare(o1.getSales(), o2.getSales());
        return (isAscending) ? -compareValue : compareValue;
    }
}
