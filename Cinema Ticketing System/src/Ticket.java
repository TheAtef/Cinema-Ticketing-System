import java.io.*;
public class Ticket implements Serializable{
        private String ticketId;
        private String showTime;
        private int seatNumber;
        private static final double ticketPrice = 30_000.00;

        public Ticket(int movieId, String genre) {
            idGenerator(movieId, genre);  
            
        }

        public String idGenerator(int movieId, String genre){
            this.ticketId = movieId + genre;
            return this.ticketId;
        }

        public int getticketId() {
            return seatNumber;
        }

        public String getShowTime() {
            return showTime;
        }

        public static double getTicketPrice() {
            return ticketPrice;
        }



        public String toString() {
            return "Ticket [seatNumber=" + seatNumber + ", showTime=" + showTime + "Ticket Price= " + ticketPrice + "]";
        }
        }
