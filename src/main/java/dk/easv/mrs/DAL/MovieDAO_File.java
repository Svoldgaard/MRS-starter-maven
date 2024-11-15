package dk.easv.mrs.DAL;

//import project
import dk.easv.mrs.BE.Movie;

// import java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardOpenOption.APPEND;


public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final Path moviesPath = Path.of(MOVIES_FILE);


    @Override
    public List<Movie> getAllMovies() throws IOException {
        List<String> lines = Files.readAllLines(moviesPath);
        List<Movie> movies = new ArrayList<>();

        for (String line: lines) {
            String[] separatedLine = line.split(",");

            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];
            if(separatedLine.length > 3)
            {
                for(int i = 3; i < separatedLine.length; i++)
                {
                    title += "," + separatedLine[i];
                }
            }
            Movie movie = new Movie(id, year, title);
            movies.add(movie);
        }


        return movies;

    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {

        List<String> movies = Files.readAllLines(Path.of(MOVIES_FILE));

        if (movies.size() >0) {
            String[] separatedLine = movies.get(movies.size() - 1).split(",");
            int nextId = Integer.parseInt(separatedLine[0]) + 1;
            String newMovieLine = nextId + "," + year + "," + title;
            Files.write(moviesPath, (newMovieLine + "\r\n").getBytes(), APPEND);

            return new Movie(nextId, year, title);
        }
        return null;
    }

    @Override
    public Movie updateMovie(Movie movie) throws Exception {
        List<String> movies = Files.readAllLines(moviesPath);
        List<String> updatedMovies = new ArrayList<>();

        boolean movieFound = false;

        for (String line : movies) {
            String[] separatedLine = line.split(",");
            int movieId = Integer.parseInt(separatedLine[0]);

            if (movieId == movie.getId()) {
                String updatedLine = movie.getId() + "," + movie.getYear() + "," + movie.getTitle();
                updatedMovies.add(updatedLine);
                movieFound = true;
            } else {
                updatedMovies.add(line);
            }
        }

        if (movieFound) {
            Files.write(moviesPath, updatedMovies, StandardOpenOption.TRUNCATE_EXISTING);
            return movie;
        } else {
            throw new Exception("Movie not found for update.");
        }
    }

    @Override
    public Movie deleteMovie(Movie movie) throws Exception {
        List<String> movies = Files.readAllLines(moviesPath);
        List<String> updatedMovies = new ArrayList<>(); 

        boolean movieFound = false;

        for (String line : movies) {
            String[] separatedLine = line.split(",");
            int movieId = Integer.parseInt(separatedLine[0]);
            String movieTitle = separatedLine[2];
            int movieYear = Integer.parseInt(separatedLine[1]);
            if (movieId == movie.getId() && movieTitle.equals(movie.getTitle()) && movieYear == movie.getYear()) {
                movieFound = true;
                continue;
            }
            updatedMovies.add(line);
        }

        if (movieFound) {
            Files.write(moviesPath, updatedMovies, StandardOpenOption.TRUNCATE_EXISTING);
            return movie;
        } else {
            return null;
        }
    }
}