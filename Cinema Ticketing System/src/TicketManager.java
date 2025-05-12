import java.util.*;
import java.io.*;

public class TicketManager extends Thread{
    public void run(){
        
    } 

    private Cinema cinema;

    public TicketManager(Cinema cinema) {
        this.cinema = cinema;
    }

    public void displayAvailableMovies() {
        cinema.displayMoviesInHalls();
        System.out.println();
    }

    public void displayAvailableSeats(String genre) {
        Map<String, List<Movie>> halls = cinema.getHalls();
        List<Movie> movies = halls.getOrDefault(genre, null);

        if (movies != null) {
            System.out.println("------ Available Seats in " +  genre  + ": ------");
            System.out.println();
            for (Movie movie : movies) {
                System.out.println("------ " + movie.getName() + " ------");
                System.out.println("MovieId: " + movie.getMovieId());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Showtime: " + movie.getShowtime());
                System.out.println("Available Seats: " + movie.getNumSeats());
                System.out.println();
            }
            System.out.println("----------- Enjoy! -----------");
            System.out.println();
        } else {
            System.out.println("No movies found for the genre: " + genre);
            System.out.println();
        }
    }

    public synchronized  List<String>  reserveTickets(String genre, int movieId, int numTickets, String username, String email, String paymentMethod) {
        Map<String, List<Movie>> halls = cinema.getHalls();
        List<Movie> movies = halls.getOrDefault(genre, null);
        User user = new User(username, email, paymentMethod);
        Ticket tic = new Ticket(movieId, genre);
        List<String> ids = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tickets.txt", true))) {
            if (movies != null) {
                for (Movie movie : movies) {
                    if (movie.getMovieId() == movieId) {
                        if (movie.getNumSeats() >= numTickets) {
                            int totalPrice = (int) (Ticket.getTicketPrice() * numTickets);
                            for (int i = 0; i < numTickets; i++) {
                                String id = tic.idGenerator(movieId, genre) + (movie.getNumSeats() - i);
                                ids.add(id);
                                System.out.println("Ticket " + (i+1) + " id: "+ id);
                                writer.write(  id + "," +
                                            movie.getMovieId() + "," +
                                            movie.getName() + "," +
                                            movie.getGenre() + "," +
                                            Ticket.getTicketPrice() + "," +
                                            user.getUsername() + "," +
                                            user.getEmail() + "," +
                                            user.getPaymentMethod() + "," + movie.getShowtime()
                                );
                                writer.newLine();
                            }
                            System.out.println("Successfully reserved " + numTickets + " tickets for movie " + movie.getName());
                            System.out.println("Total Price: " + totalPrice);
                            System.out.println();
                            return ids;
                        } else {
                            System.out.println("Not enough seats available for movie " + movie.getName());
                            System.out.println();
                            return ids;
                        }
                    }
                }
            }
        System.out.println("Invalid genre or movie ID");
        System.out.println();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }
    public boolean checkTicketExistence(String ticketId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] ticketData = line.split(",");
                String storedTicketId = ticketData[0];
                
                if (storedTicketId.equals(ticketId)) {
                    return true; // Ticket exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false; // Ticket doesn't exist
    }
    public void cancelReservation(String ticketId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
            List<String> w5ra = new ArrayList<>();
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] ticketData = line.split(",");
                String ticketidfromTxt = ticketData[0];
                if (ticketidfromTxt.equals(ticketId)) {
                    System.out.println("Ticket with ID \"" + ticketId + "\" has been removed.");  
                    found = true;                  
                    continue;}
                w5ra.add(line);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tickets.txt"))) {
                for (String linesTicket : w5ra) {
                    writer.write(linesTicket);
                    writer.newLine();
                }
            }
            if (!found) {
                System.out.println("Ticket with ID \"" + ticketId + "\" does not exist.");
                
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}