public class DirectorsFilter implements Filter {
    private String[] myDirectors;

    public DirectorsFilter(String Directors){
        myDirectors = Directors.split(",");
    }

    public boolean satisfies(String id){
        for (String director: myDirectors){
            if (MovieDatabase.getDirector(id).contains(director)){
                return true;
            }
        }
        return false;
    }

}
