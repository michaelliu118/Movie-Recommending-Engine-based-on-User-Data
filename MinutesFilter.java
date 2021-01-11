public class MinutesFilter implements Filter {
    int minimum;
    int maximum;

    public MinutesFilter(int minMinutes, int maxMinutes){
        minimum = minMinutes;
        maximum = maxMinutes;
    }

    public boolean satisfies(String id){
        return (MovieDatabase.getMinutes(id)<maximum && MovieDatabase.getMinutes(id)>minimum);
    }
}
