package setup;

import java.util.Comparator;

/**
 * Compares two balls based on their volumes
 */
public class BallSorter implements Comparator<Ball>  {
    /**
     * Compares two balls to one another
     *
     * @param n Ball to be compared to m Ball
     * @return -1 if the volume of n is less than  volume m, 0 if the volumes
     * are equal, and 1 if volume n is greater than volume m
     * n != null and m != null
     */
    @Override
    public int compare(Ball n, Ball m){
        double diff = n.getVolume() - m.getVolume();
        if (diff < 0){
            return -1;
        } else if (diff > 0){
            return 1;
        }
        return 0;
    }
}
