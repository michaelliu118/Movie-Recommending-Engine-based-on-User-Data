import java.util.*;

public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate(){
        YearAfterFilter yearFilter = new YearAfterFilter(2000);
        MinutesFilter minutesFilter = new MinutesFilter(100,200);
        Random randomGenerator = new Random();
        AllFilters all = new AllFilters();
        all.addFilter(yearFilter);
        all.addFilter(minutesFilter);
        ArrayList<String> Movies = MovieDatabase.filterBy(all);
        ArrayList<String> RandomlySelectedMovies = new ArrayList<String>(); 
        //Select 20 movies as specified by the instruction to be good enough
        for(int i=0; i<20 ;i++){
            int index = randomGenerator.nextInt(Movies.size());
            RandomlySelectedMovies.add(Movies.get(index));
        }
         return RandomlySelectedMovies;
     }

     public void printRecommendationsFor(String webRaterID){
        FourthRatings database = new FourthRatings("ratings.csv");
        MovieDatabase MovDat = new MovieDatabase();
        MovDat.initialize("ratedmoviesfull.csv");
        RaterDatabase RatDat = new RaterDatabase();
        RatDat.initialize("ratings.csv");
        ArrayList<Rating> recommendations = database.getSimilarRatings(webRaterID,4,2);
        ArrayList<Rating> finalRecommendations = new ArrayList<Rating>();
        if(recommendations.size()==0){
            System.out.println("No movie found given the input preferences. Please adjust your preferences and try again.");
        }
        //The follow code serves to print only the top 10 recommeded movies for better viewing purpose
        int range = Math.min(10, recommendations.size());
        for (int i=0; i<range; i++){
            finalRecommendations.add(recommendations.get(i));
        }
        System.out.println("<h2>The recommended movies and their rating scores are as below:</h2>");
        System.out.println("<style>th{color:blue}img{  border: 1px solid #ddd;border-radius: 4px;padding: 5px;width: 150px;}table{border: 1px solid black;}</style>");
        System.out.println("<table style="+"width:90%"+">");
        System.out.println("<tr><th>Movies</th><th>Ratings</th></tr>");
        for(Rating movie : finalRecommendations){
            String movieTitle = MovieDatabase.getTitle(movie.getItem()); 
            double ratingscore = movie.getValue();
            String poster = MovieDatabase.getPoster(movie.getItem());
            System.out.println("<tr>");
            System.out.println("<td>"+movieTitle+"</td>"+"<td>"+ratingscore+"</td>"+"<td>"+"<img src="+poster+">"+"</td>");
            System.out.println("</tr>");
        }
        System.out.println("</table>");
     }
}
