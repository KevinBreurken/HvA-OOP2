package practicumopdracht.comparators;

import practicumopdracht.models.Artist;

public class ArtistComparatorAZ extends DirectionalComparator<Artist> {

    public ArtistComparatorAZ(boolean isAscending) {
        super(isAscending);
    }

    @Override
    public int compare(Artist o1, Artist o2) {
        int compareValue = o1.getName().compareTo(o2.getName());
        return (isAscending) ? -compareValue : compareValue;
    }
}
