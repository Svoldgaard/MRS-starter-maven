package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.util.List;

public interface IMovieDataAccess {

    List<Movie> getAllMovies() throws Exception;

    Movie createMovie(String title, int year) throws Exception;

    Movie updateMovie(Movie movie) throws Exception;

    Movie deleteMovie(Movie movie) throws Exception;

}
