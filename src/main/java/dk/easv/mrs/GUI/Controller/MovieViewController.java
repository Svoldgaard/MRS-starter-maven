package dk.easv.mrs.GUI.Controller;

// import Project
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;

// import Java
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    @FXML
    public TextField txtMovieSearch;
    @FXML
    public ListView<Movie> lstMovies;
    @FXML
    public Button btnCreatNewMovie;
    @FXML
    public TextField movieTitle;
    @FXML
    public TextField movieYear;
    @FXML
    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });


    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void onActionCreatNewMovie(ActionEvent actionEvent) {
        String title = movieTitle.getText().trim();
        String yearText = movieYear.getText().trim();

        // Validate the input
        if (title.isEmpty()) {
            showError("Title cannot be empty.");
            return;
        }

        if (yearText.isEmpty()) {
            showError("Year cannot be empty.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);  // Convert year to integer
        } catch (NumberFormatException e) {
            showError("Invalid year. Please enter a valid number.");
            return;
        }

        try {
            // Call MovieModel's createMovie method
            Movie newMovie = movieModel.createMovie(title, year);
            showSuccess("Movie created: " + newMovie.getTitle());
            // Optionally, clear the fields
            movieTitle.clear();
            movieYear.clear();
        } catch (Exception e) {
            showError("An error occurred while creating the movie.");
            e.printStackTrace();
        }
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        //alert.setHeaderText("Movie Creation Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        //alert.setHeaderText("Movie Created");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onActionUpdateMovie(ActionEvent actionEvent) {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        String title = movieTitle.getText().trim();
        String yearText = movieYear.getText().trim();
        if (title.isEmpty()) {
            showError("Title cannot be empty.");
            return;
        }
        if (yearText.isEmpty()) {
            showError("Year cannot be empty.");
   x         return;
        }
    }
    @FXML
    public void onActionDeleteMovie(ActionEvent actionEvent) {
        // Get the selected movie from the ListView
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            try {
                Movie deletedMovie = movieModel.deleteMovie(selectedMovie);

                if (deletedMovie != null) {
                    showSuccess("Movie deleted successfully: " + deletedMovie.getTitle());
                } else {
                    showError("The movie could not be deleted.");
                }
            } catch (Exception e) {
                showError("An error occurred while trying to delete the movie.");
                e.printStackTrace();
            }
        } else {
            showError("Please select a movie to delete.");
        }
    }
}
