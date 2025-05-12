import java.io.BufferedReader;
import java.io.FileReader;

class Movie {
    private String name;
    private String genre;
    private String showtime;
    private int movieId;
    private final int numSeats = 16;
    public Movie(String name, String genre, String showtime, int movieId) {
        this.name = name;
        this.genre = genre;
        this.showtime = showtime;
        this.movieId = movieId;
    }

    public int getTakenSeatsIO(){
        try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] eachLine = line.split("\\p{Lu}");
                String movieSeatTaken = eachLine[0];
                if (Integer.parseInt(movieSeatTaken) == this.movieId) {
                i++;}
            }
            return i;
        }catch(Exception e){e.getMessage();}
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getShowtime() {
        return showtime;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getNumSeats() {
        return (numSeats - getTakenSeatsIO());
    }

}
