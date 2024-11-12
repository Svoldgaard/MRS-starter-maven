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

    public Movie createMovie(String title, int year) throws Exception {
        Movie newMovie = movieManager.createMovie(title, year);  // Call MovieManager to create movie
        moviesToBeViewed.add(newMovie);  // Add new movie to the ObservableList for GUI updates
        return newMovie;
    }


}
