import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<PlainRater> myRaters;
    private Map<String, ArrayList<Double>> MovieRating = new HashMap<>();
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
        
    }
    
    public SecondRatings(String moviefile, String ratingfile){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovie(moviefile);
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
    
    public int getMovieSize(){
        return myMovies.size();
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
        for (Movie m: myMovies){
            double average = getAverageByID(m.getID(), minimalRaters);
            if (average!=0.0){
                MoviesAverageRating.add(new Rating(m.getTitle(), average));
            }
        }
        return MoviesAverageRating;
    }

    public String getTile(String id){
        for(Movie m : myMovies){
            if(id.equals(m.getID())){
                return m.getTitle();
            }
        }
        return "Movie ID not found!!";
    }

    public String getID(String title){
        for(Movie m : myMovies){
            if(title.equals(m.getTitle())){
                return m.getID();
            }
        }
        return "NO SUCH TITLE.";
    }

}
