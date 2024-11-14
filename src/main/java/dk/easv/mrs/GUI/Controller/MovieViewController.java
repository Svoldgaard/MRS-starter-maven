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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstMovies.setItems(movieModel.getObservableMovies());


        lstMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                movieTitle.setText(newValue.getTitle());
                movieYear.setText(String.valueOf(newValue.getYear()));
            }
        });

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
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showError("Invalid year. Please enter a valid number.");
            return;
        }

        try {
            Movie newMovie = movieModel.createMovie(title, year);
            showSuccess("Movie created: " + newMovie.getTitle());
            movieTitle.clear();
            movieYear.clear();
        } catch (Exception e) {
            showError("An error occurred while creating the movie.");
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onActionUpdateMovie(ActionEvent actionEvent) {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            showError("Please select a movie to update.");
            return;
        }

        String title = movieTitle.getText().trim();
        String yearText = movieYear.getText().trim();

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
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showError("Invalid year. Please enter a valid number.");
            return;
        }

        selectedMovie.setTitle(title);
        selectedMovie.setYear(year);

        try {
            Movie updatedMovie = movieModel.updateMovie(selectedMovie);
            showSuccess("Movie updated: " + updatedMovie.getTitle());

            movieTitle.clear();
            movieYear.clear();
        } catch (Exception e) {
            showError("An error occurred while updating the movie.");
            e.printStackTrace();
        }
    }
    @FXML
    public void onActionDeleteMovie(ActionEvent actionEvent) {
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
