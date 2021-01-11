import java.util.*;

import javax.print.event.PrintEvent;

public class MovieRunnerAverage {

    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings();
        System.out.println("The number of movies is: " + sr.getMovieSize());
        System.out.println("The number of raters is: " + sr.getRaterSize());
        int minimalRaters = 12;
        ArrayList<Rating> satisfiedMovies = sr.getAverageRatings(minimalRaters);
        Collections.sort(satisfiedMovies, new AverageRatingComparator());
        for (Rating r: satisfiedMovies){
            System.out.println("Rating: "+ r.getValue() + " " + "Ttile: " + r.getItem());
        }
        //System.out.println("asgfadhg" + satisfiedMovies.size());
    }

    public void getAverageRatingOneMovie(){
        SecondRatings MoviesAndRatings = new SecondRatings
        ("ratedmoviesfull.csv", "ratings.csv");
        String movieRequest = "Vacation";
        String id = MoviesAndRatings.getID(movieRequest);
        if(id.equals("NO SUCH TITLE.")){
            System.out.println(id);
        }
        else{
        double aveRating = MoviesAndRatings.getAverageByID(id,1);
        System.out.println("The average rating for the movie " + movieRequest + " is " + aveRating);
        }
    }

}
