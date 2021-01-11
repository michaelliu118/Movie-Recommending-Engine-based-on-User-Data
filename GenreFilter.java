public class GenreFilter implements Filter {
    private String requiredGenre;

    public GenreFilter(String genre){
        requiredGenre = genre;
    }

    public boolean satisfies(String id){
        return MovieDatabase.getGenres(id).contains(requiredGenre);
    }
}
