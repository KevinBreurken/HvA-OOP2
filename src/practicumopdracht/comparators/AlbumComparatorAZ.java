package practicumopdracht.comparators;

import practicumopdracht.models.Album;

import java.util.Comparator;

public class AlbumComparatorAZ implements Comparator<Album> {

    private boolean isAscending = false;

    public AlbumComparatorAZ(boolean isAscending){
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Album o1, Album o2) {
        int compareName = o1.getName().compareTo(o2.getName());
        if(compareName == 0){

        }
        return (isAscending) ? -compareName : compareName;
    }
}
