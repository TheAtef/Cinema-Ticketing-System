import java .io.*;
import java.util.*;


public class Statistics {
    private static String TICKETS_FILE_PATH = "Tickets.txt";
    public static List<Map.Entry<String, Integer>> getMostRepetitiveMovies() {
        Map<String, Integer> movieCountMap = new HashMap<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] ticketInfo = line.split(",");
                String movieName = ticketInfo[2];
    
                movieCountMap.put(movieName, movieCountMap.getOrDefault(movieName, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<Map.Entry<String, Integer>> sortedMovies = new ArrayList<>(movieCountMap.entrySet());
        sortedMovies.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    
        return sortedMovies;
    }
    public static List<Map.Entry<String, Integer>> getMostFrequentShowtimes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE_PATH))) {
            Map<String, Integer> showtimeCountMap = new HashMap<>();
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] ticketInfo = line.split(",");
                String showtime = ticketInfo[8];
    
                showtimeCountMap.put(showtime, showtimeCountMap.getOrDefault(showtime, 0) + 1);
            }
    
            List<Map.Entry<String, Integer>> sortedShowtimes = new ArrayList<>(showtimeCountMap.entrySet());
            sortedShowtimes.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            
            return sortedShowtimes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}

