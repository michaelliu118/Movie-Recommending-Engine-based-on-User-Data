import java.util.*;

public class AverageRatingComparator implements Comparator<Rating>{
    public int compare(Rating p1, Rating p2) {
        if (p1.getValue() < p2.getValue()) return -1;
        if (p1.getValue() > p2.getValue()) return 1;
        return 0;
    }
}
