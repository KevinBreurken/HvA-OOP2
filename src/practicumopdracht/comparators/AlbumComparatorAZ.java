package practicumopdracht.comparators;

import practicumopdracht.models.Album;

public class AlbumComparatorAZ extends DirectionalComparator<Album> {

    public AlbumComparatorAZ(boolean isAscending) {
        super(isAscending);
    }

    @Override
    public int compare(Album o1, Album o2) {
        int compareValue = o1.getName().compareTo(o2.getName());
        return (this.isAscending) ? -compareValue : compareValue;
    }
}
