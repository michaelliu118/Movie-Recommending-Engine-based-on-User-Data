import java.util.*;

public class FourthRatings {

    public FourthRatings() {
        // default constructor
        this("ratings.csv");
        
    }
    
    public FourthRatings(String ratingfile){
        RaterDatabase.addRatings("data/" + ratingfile); 
    }


    public double getAverageByID(String id, int minimalRaters){
        ArrayList<Rater> Raters = RaterDatabase.getRaters();
        double count = 0.0;
        double numRatings = 0.0;
        for (Rater r: Raters){
            if (r.hasRating(id)){
                count += r.getRating(id);
                numRatings += 1;
            }
        }
        if (numRatings<minimalRaters) return -1;
        return count/numRatings;
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

    //This method returns the
    private double dotProduct(Rater me, Rater r){
        ArrayList<String> MoviesRatedByMe = me.getItemsRated();
        double thedotProduct = 0;
        for (String mineMovie: MoviesRatedByMe){
            if (r.hasRating(mineMovie)){
                thedotProduct += (me.getRating(mineMovie)-5)*(r.getRating(mineMovie)-5);
            }
        }
        return thedotProduct;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> similarRaters = new ArrayList<Rating>();
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        Rater thisRater = RaterDatabase.getRater(id);
        for (Rater otherRater: raters){
            if (!otherRater.getID().equals(id)){
                double dotProduct = dotProduct(thisRater, otherRater);
                Rating RatingOfRater = new Rating(otherRater.getID(), dotProduct);
                similarRaters.add(RatingOfRater);
            }
        }
        Collections.sort(similarRaters, Collections.reverseOrder());
        return similarRaters;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> similarRaters = getSimilarities(id);
        ArrayList<Rating> satisfiedRaters = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> output = new ArrayList<Rating>();
        for (int i=0; i<=numSimilarRaters; i++){
            if (similarRaters.get(i).getValue()<=0) break;
            satisfiedRaters.add(similarRaters.get(i));
        }
        for (String movie: movies){
            int countRaters = 0;
            double sumMovieRating = 0; //Sum of the score of raters's similarity score * score given by raters to the movie
            for (Rating raterRating: satisfiedRaters){
                Rater rater = RaterDatabase.getRater(raterRating.getItem());
                if (rater.hasRating(movie)){
                    countRaters += 1;
                    sumMovieRating += raterRating.getValue()*rater.getRating(movie);
                }
            }
            if (countRaters >= minimalRaters){
                double weightedAverageMovieRating = sumMovieRating/countRaters;
                output.add(new Rating(movie, weightedAverageMovieRating));
            }
        }
        Collections.sort(output, Collections.reverseOrder());
        return output;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter (String id, int numSimilarRaters, int minimalRaters, Filter filter){
        ArrayList<Rating> similarRaters = getSimilarities(id);
        ArrayList<Rating> satisfiedRaters = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filter);
        ArrayList<Rating> output = new ArrayList<Rating>();
        for (int i=0; i<=numSimilarRaters; i++){
            if (similarRaters.get(i).getValue()<=0) break;
            satisfiedRaters.add(similarRaters.get(i));
        }
        for (String movie: movies){
            int countRaters = 0;
            double sumMovieRating = 0; //Sum of the score of raters's similarity score * score given by raters to the movie
            for (Rating raterRating: satisfiedRaters){
                Rater rater = RaterDatabase.getRater(raterRating.getItem());
                if (rater.hasRating(movie)){
                    countRaters += 1;
                    sumMovieRating += raterRating.getValue()*rater.getRating(movie);
                }
            }
            if (countRaters >= minimalRaters){
                double weightedAverageMovieRating = sumMovieRating/countRaters;
                output.add(new Rating(movie, weightedAverageMovieRating));
            }
        }
        Collections.sort(output, Collections.reverseOrder());
        return output;
    }

}
