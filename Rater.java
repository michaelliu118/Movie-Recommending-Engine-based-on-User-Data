import java.util.ArrayList;

public interface Rater {

    public void addRating(Rating r);

    public boolean hasRating(String item);

    public String getID();

    public double getRating(String item);

    public int numRatings();

    public ArrayList<String> getItemsRated();

    public String toString();

    public void toMyString();

}
