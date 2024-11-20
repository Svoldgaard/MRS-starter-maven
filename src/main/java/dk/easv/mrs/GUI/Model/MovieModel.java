package dk.easv.mrs.GUI.Model;

// import project
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;

// import Java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;


    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public Movie createMovie(Movie newMovie) throws Exception {
        Movie movieCreated = movieManager.createMovie(newMovie);
        moviesToBeViewed.add(movieCreated);
        return movieCreated;
    }

    public Movie deleteMovie(Movie movie) throws Exception {
        Movie deleteMovie = movieManager.deleteMovie(movie);
        moviesToBeViewed.remove(movie);
        return deleteMovie;

    }

    public Movie updateMovie(Movie movie) throws Exception {
        Movie updatedMovie = movieManager.updateMovie(movie);

        for (int i = 0; i < moviesToBeViewed.size(); i++) {
            if (moviesToBeViewed.get(i).getId() == updatedMovie.getId()) {
                moviesToBeViewed.set(i, updatedMovie);
                break;
            }
        }

        return updatedMovie;
    }


}
