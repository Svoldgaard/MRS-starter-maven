package dk.easv.mrs.DAL;

//import project
import dk.easv.mrs.BE.Movie;

// import java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;


public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static final Path moviesPath = Path.of(MOVIES_FILE);



    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
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

        /*if (!movies.isEmpty()) {
            String[] separatedLine = movies.get(movies.size() - 1).split(",");
            int nextId = Integer.parseInt(separatedLine[0]) + 1;
            String newMovieLine = nextId + "," + year + "," + title;
            Files.write(MOVIES_FILE, (newMovieLine + "\r\n").getBytes(), APPEND);

            return new Movie(nextId, year, title);
        }      */
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
    }
}