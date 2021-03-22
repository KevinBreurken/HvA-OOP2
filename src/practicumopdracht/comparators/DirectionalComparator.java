package practicumopdracht.comparators;

import java.util.Comparator;

/**
 * Abstract comparator used to flip the desired result by the given boolean state.
 */
public abstract class DirectionalComparator<T> implements Comparator<T> {

    protected boolean isAscending;

    public DirectionalComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }
}
