import java.util.*;

public class EfficientRater implements Rater {
    public String myID;
    public Map<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(Rating r) {
        myRatings.put(r.getItem(), r);
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (myRatings.containsKey(item)){
            return myRatings.get(item).getValue();
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String id: myRatings.keySet()){
            list.add(id);
        }
        
        return list;
    }

    public void toMyString(){
        for (Map.Entry<String, Rating> map: myRatings.entrySet()){
            System.out.println(map.getValue());
        }
    }
}

