package setup;

import java.util.Comparator;

public class BallSorter implements Comparator<Ball>  {
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
