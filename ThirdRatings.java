import java.util.*;

public class ThirdRatings {
    private ArrayList<PlainRater> myRaters;
    private Map<String, ArrayList<Double>> MovieRating;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
        
    }
    
    public ThirdRatings(String ratingfile){
        MovieRating = new HashMap<>();
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingfile);
        for (PlainRater r: myRaters){
            for (Rating rating: r.myRatings){
                if (MovieRating.containsKey(rating.getItem())){
                    ArrayList<Double> dummy = MovieRating.get(rating.getItem());
                    dummy.add(rating.getValue());
                    MovieRating.put(rating.getItem(), dummy);
                }
                else {
                    ArrayList<Double> dummy = new ArrayList<Double>();
                    dummy.add(rating.getValue());
                    MovieRating.put(rating.getItem(), dummy);
                }
            }
        } 
    }

    public int getRaterSize(){
        return myRaters.size();
    }

    public double getAverageByID(String id, int minimalRaters){
        if (!MovieRating.containsKey(id)){
            return 0.0;
        }
        double sum = 0.0;
        int size = MovieRating.get(id).size();
        if (MovieRating.get(id).size()<minimalRaters){
            return 0.0;
        }
        for (double a: MovieRating.get(id)){
            sum += a;
        }
        return sum/size;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> MoviesAverageRating = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String m: movies){
            double average = getAverageByID(m, minimalRaters);
            if (average!=0.0){
                MoviesAverageRating.add(new Rating(m, average));
            }
        }
        return MoviesAverageRating;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> AverageRatingsByFilter = new ArrayList<Rating>();
        ArrayList<String> Ids = MovieDatabase.filterBy(filterCriteria);
        for (String id: Ids){
            double averageRating = getAverageByID(id, minimalRaters);
            if (averageRating!=0.0){
                AverageRatingsByFilter.add(new Rating(id, averageRating));
            }
        }
        return AverageRatingsByFilter;
    }
}
