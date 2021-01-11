import java.util.Comparator;

public class RaterSizeComparator implements Comparator<PlainRater> {
    public int compare(PlainRater a, PlainRater b){
        return a.numRatings() - b.numRatings();
    }
}
