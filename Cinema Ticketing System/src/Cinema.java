import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cinema {
    private Map<String, List<Movie>> Genrehalls;

    public Cinema() {
        Genrehalls = new HashMap<>();
    }


    public void parseMovieFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] movieDetails = line.split(",");
                if (movieDetails.length == 4) {
                    String movieName = movieDetails[0].trim();
                    String genre = movieDetails[1].trim();
                    String showtime = movieDetails[2].trim();
                    int movieId = Integer.parseInt(movieDetails[3].trim());

                    Movie movie = new Movie(movieName, genre, showtime, movieId);
                    addMovieToHall(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMovieToHall(Movie movie) {
        List<Movie> hall = Genrehalls.getOrDefault(movie.getGenre(), new ArrayList<>());
        hall.add(movie);
        Genrehalls.put(movie.getGenre(), hall);
    }

    public List<Movie> displayMoviesInHalls() {
        System.out.println("--------- Available Movies: ---------");
        System.out.println();
        List<Movie> moviesAll = new ArrayList<>();
        for (Map.Entry<String, List<Movie>> entry : Genrehalls.entrySet()) {
            String hallName = entry.getKey();
            List<Movie> movies = entry.getValue();

            System.out.println("------ "+ hallName + " ------");
            System.out.println();
            for (Movie movie : movies) {
                System.out.println(movie.getMovieId() + " - " + movie.getName() + " - "
                        + movie.getShowtime() + " - Seats: " + movie.getNumSeats());
                moviesAll.add(movie);
            }
            System.out.println();
        }
        System.out.println("----------- Enjoy! -----------");
        System.out.println();
        return moviesAll;
    }
    
    public Map<String, List<Movie>> getHalls() {
        return Genrehalls;
    }
}
