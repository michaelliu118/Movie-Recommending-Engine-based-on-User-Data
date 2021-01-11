import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList<Movie> loadMovie(String filename){
        FileResource fr = new FileResource("data/" + filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> file = new ArrayList<Movie>();
        for (CSVRecord csvRecord: parser){
            ArrayList s = new ArrayList();
            for (int i=0; i<csvRecord.size(); i++){
                s.add(csvRecord.get(i));
            }
            if (s.size()<8){Movie movie = new Movie(
                s.get(0).toString(),
                s.get(1).toString(),
                s.get(2).toString(),
                s.get(3).toString());
                file.add(movie);
            }
            else {Movie movie = new Movie(
                s.get(0).toString(),
                s.get(1).toString(),
                s.get(2).toString(),
                s.get(3).toString(),
                s.get(4).toString(),
                s.get(5).toString(),
                Integer.parseInt(s.get(6).toString()),
                s.get(6).toString());
                file.add(movie);
            }
        }
        //System.out.print(file.toString());
        //System.out.print(file.size());
        return file;
    }

    public ArrayList<PlainRater> loadRaters(String filename){
        FileResource fr = new FileResource("data/" + filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<PlainRater> file = new ArrayList<PlainRater>();
        HashMap<String, ArrayList<Rating>> hm = new HashMap<String, ArrayList<Rating>>();
        for (CSVRecord csvRecord: parser){
            ArrayList s = new ArrayList();
            for (int i=0; i<csvRecord.size(); i++){
                s.add(csvRecord.get(i));
            }
            String id = s.get(0).toString();
            if (!hm.containsKey(id)){
                ArrayList<Rating> dummy = new ArrayList<Rating>();
                dummy.add(new Rating(s.get(1).toString(), Integer.parseInt(s.get(2).toString())));
                hm.put(id, dummy);
            }
            else {
                ArrayList<Rating> dummy = hm.get(id);
                dummy.add(new Rating(s.get(1).toString(), Integer.parseInt(s.get(2).toString())));
                hm.replace(id, dummy);
            }
        }
        for (Map.Entry<String, ArrayList<Rating>> entry: hm.entrySet()){
            PlainRater newPlainRater = new PlainRater(entry.getKey());
            for (Rating r: entry.getValue()){
                newPlainRater.addRating(r);
            }
            file.add(newPlainRater);
        }
        //System.out.println(file.toString());
        return file;
    }

    public void testLoadMovie(){
        ArrayList<Movie> file = loadMovie("ratedmoviesfull.csv");
        String genre = "Comedy";
        int countgenre = 0;
        int countlength = 0;
        for (Movie m: file){
            if (m.getGenres().contains(genre)){
                countgenre += 1;
            }
            if (m.getMinutes()>150){
                countlength += 1;
            }
        }
        System.out.println("The number of movie containing " + genre + "is: " + countgenre);
        System.out.println("The number of movie length greater than" + 150 + "is: " + countlength);
    }

    public void testLoadRater(){
        ArrayList<PlainRater> PlainRater = loadRaters("ratings.csv");
        System.out.println("The size of the PlainRater file is: " + PlainRater.size());
        String PlainRaterId = "193";
        String movieID = "1798709";
        int countMovieRating = 0;
        HashMap<String, Integer> uniqueMovie = new HashMap<String, Integer>();
        for (PlainRater r1: PlainRater){
            if (r1.myID.equals(PlainRaterId)){
                System.out.println("The number of ratings from "+PlainRaterId + "is: " + r1.numRatings());
                break;
            }
        }
        int maxnumber = Collections.max(PlainRater, new RaterSizeComparator()).numRatings();
        System.out.println("The max number of rating PlainRaters have individually is: " + maxnumber);

        //for (PlainRater r2: PlainRater){
            //System.out.println(r2.toString());
        //}
        for (PlainRater r3: PlainRater){
            for (Rating rating: r3.myRatings){
                if (uniqueMovie.containsKey(rating.getItem())){
                    uniqueMovie.put(rating.getItem(), uniqueMovie.get(rating.getItem())+1);
                }
                else {uniqueMovie.put(rating.getItem(), 1);
                }
                if (rating.getItem().equals(movieID)){
                    countMovieRating += 1;
                }
            }
        }
        System.out.println("The movie" + movieID + "has" + countMovieRating + "ratings.");
        System.out.println("The number of unique movies that have been rated is: "+ uniqueMovie.size());
    }
}