import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String email;
    private String paymentMethod;

    public User(String username, String email, String paymentMethod) {
        this.username = username;
        this.email = email;
        this.paymentMethod = paymentMethod;
        toFile();
    }
    
    public void toFile() {
        try {
            List<String> existingUsers = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingUsers.add(line);
                }
            }
            String currentUser = toString();
            if (!existingUsers.contains(currentUser)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true))) {
                    writer.write(currentUser);
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                        } 
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return username + "," + email + "," + paymentMethod;
    }
}