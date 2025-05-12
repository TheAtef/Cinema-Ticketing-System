import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MainUI {
    private static JTabbedPane tabby = new JTabbedPane( );
    private static JPanel CancelReservation = new JPanel(new GridBagLayout());
    private static JPanel statis = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    private static JPanel UsersAcc = new JPanel();
    private static JPanel m = new JPanel( );
    private static JPanel rt = new JPanel( );
    private static JPanel r = new JPanel( );
    // Reserve tickets variables to increase scope:
    final private static DefaultListModel pay = new DefaultListModel<>();
    private static JList payy = new JList<>(pay);
    private static List<Movie> moviesFromHall = null;
    private static Movie selectedMovie = null;
    // ------------------------
    // Review tab variables to increase scope:
   private static int rb = 0;
    // -------------------------
    //functions for Statis Tab:    
    private static void updateMoviesTable(List<Map.Entry<String, Integer>> movies) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Movie");
        model.addColumn("Count");
                
        for (Map.Entry<String, Integer> movie : movies) {
            model.addRow(new Object[] { movie.getKey(), movie.getValue() });
        }
                
        moviesTable.setModel(model);
        moviesTable.repaint();
    }
    // Method to update the showtimes table:
    private static void updateShowtimesTable(List<Map.Entry<String, Integer>> showtimes) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Showtime");
        model.addColumn("Count");
                
        for (Map.Entry<String, Integer> showtime : showtimes) {
            model.addRow(new Object[] { showtime.getKey(), showtime.getValue() });
        }
                
        showtimesTable.setModel(model);
        showtimesTable.repaint();
    }
    private static JTable moviesTable;
    private static JTable showtimesTable;
                
// //===============================reviews==========================\\
    public static void r(){
        r.removeAll();
        r.setLayout(new BorderLayout());
        JLabel publicRevs = new JLabel("Public reviews:");
        JPanel sigPanel = new JPanel(new GridLayout(2,3,20,0));
        JLabel signLabel = new JLabel("Sign in to leave a review.");
        JLabel emptyLabel = new JLabel();
        JLabel emptyLabel2 = new JLabel();
        signLabel.setFont(new Font("Arial", Font.BOLD, 15));
        publicRevs.setFont(new Font("Arial", Font.BOLD, 15));
        publicRevs.setBorder(BorderFactory.createEmptyBorder());
        sigPanel.setBorder(BorderFactory.createEtchedBorder());
        TextField username = new TextField("Username");
        username.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
        username.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Username")) {
                    username.setText("");
                    username.setForeground(Color.BLACK);
                }
            }         
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setText("Username");
                    username.setForeground(Color.GRAY);
                }
            }
        });
        TextField email = new TextField("Email");
        email.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
        email.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (email.getText().equals("Email")) {
                    email.setText("");
                    email.setForeground(Color.BLACK);
                }
            }         
            public void focusLost(FocusEvent e) {
                if (email.getText().isEmpty()) {
                    email.setText("Email");
                    email.setForeground(Color.GRAY);
                }
            }
        });

        JButton signBtn = new JButton("Sign in");
        signBtn.setBackground(new Color(119, 154, 229));

        sigPanel.add(signLabel);
        sigPanel.add(emptyLabel);
        sigPanel.add(emptyLabel2);
        sigPanel.add(username);
        sigPanel.add(email);
        sigPanel.add(signBtn);

        JPanel revs = new JPanel(new BorderLayout());
        revs.setBorder(BorderFactory.createEtchedBorder());
        Cinema cinema = new Cinema();
        cinema.parseMovieFile("Movies.txt");
        List<Movie> movies = cinema.displayMoviesInHalls();
        final DefaultListModel<String> list = new DefaultListModel<>();
        for(Movie movie : movies){
            list.addElement(movie.getName());
        }
        final JList<String> li = new JList<>(list);
        li.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane liScroll = new JScrollPane(li);

        JFrame addRevF = new JFrame("Leave a review");
        JRadioButton rb1 = new JRadioButton("1");
        JRadioButton rb2 = new JRadioButton("2");
        JRadioButton rb3 = new JRadioButton("3");
        JRadioButton rb4 = new JRadioButton("4");
        JRadioButton rb5 = new JRadioButton("5");

        JButton done = new JButton("Done");
        done.setBackground(new Color(119, 154, 229));

        final JList<String> liAddRev = new JList<>(list);
        liAddRev.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane liAdREvScroll = new JScrollPane(liAddRev);
        JTextArea commentText = new JTextArea("Write your comment here.");
        commentText.setFont(new Font("Arial", Font.ROMAN_BASELINE, 14));
        commentText.setBorder(BorderFactory.createEtchedBorder());
        commentText.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (commentText.getText().equals("Write your comment here.")) {
                    commentText.setText("");
                    commentText.setForeground(Color.BLACK);
                }
            }         
            public void focusLost(FocusEvent e) {
                if (commentText.getText().isEmpty()) {
                    commentText.setText("Write your comment here.");
                    commentText.setForeground(Color.GRAY);
                }
            }
        });

        signBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                    String[] eachLine = line.split(",");
                    if(username.getText().strip().equals(eachLine[0].strip()) && email.getText().strip().equals(eachLine[1].strip())){
                        found = true;
                        break;
                    } else {
                        JLabel noAcc = new JLabel("Account does not exist.");
                        sigPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                        sigPanel.removeAll(); sigPanel.add(noAcc); sigPanel.updateUI();
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                sigPanel.removeAll();
                                sigPanel.setLayout(new GridLayout(2,3,20,0));
                                sigPanel.add(signLabel);
                                sigPanel.add(emptyLabel);
                                sigPanel.add(emptyLabel2);
                                sigPanel.add(username);
                                sigPanel.add(email);
                                sigPanel.add(signBtn);
                                sigPanel.updateUI();
                            }
                        });
                        timer.setRepeats(false); // Only execute once
                        timer.start();
                    }
                    }
                } catch(Exception ex){ex.getMessage();
                } finally {
                    if(found){
                    addRevF.setAlwaysOnTop(true);
                    addRevF.setSize(500, 400);
                    addRevF.setLocation(300, 0);
                    addRevF.setResizable(false);
                    addRevF.getContentPane().setBackground(Color.GRAY);
                    JPanel addRevP = new JPanel(new BorderLayout(2,2));
                    
                    JLabel rbLabel = new JLabel("Rating:");
                    
                    ButtonGroup br = new ButtonGroup();
                    br.add(rb1); br.add(rb2); br.add(rb3); br.add(rb4); br.add(rb5);
                    JPanel bg = new JPanel(new GridLayout(1,6,30,0));
                    bg.setBorder(BorderFactory.createEtchedBorder());
                    bg.add(rbLabel); bg.add(rb1); bg.add(rb2); bg.add(rb3); bg.add(rb4); bg.add(rb5);
                    addRevP.add(liAdREvScroll, BorderLayout.WEST);
                    addRevP.add(bg, BorderLayout.PAGE_START);
                    addRevP.add(commentText, BorderLayout.CENTER);
                    addRevP.add(done, BorderLayout.PAGE_END);
                    addRevF.add(addRevP); addRevP.updateUI();  
                    addRevF.setVisible(true);
                }}
            }
        });
   
      
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(rb != 0 && liAddRev.getSelectedIndex() != -1){
                    int movieId = 0;
                    String selected = liAddRev.getSelectedValue();
                    for(Movie movie : movies){
                        if(selected == movie.getName()){
                        movieId = movie.getMovieId();
                    }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reviews.txt", true))) {
                        writer.write(movieId + "," + commentText.getText() + "," + rb);
                        writer.newLine();
                        addRevF.dispose();
                        r();

                    }catch(IOException IOe){};
                    }
            }
        });

        rb1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            rb = 1;
        }});
        rb2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            rb = 2;
        }});
        rb3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            rb = 3;
        }});
        rb4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            rb = 4;
        }});
        rb5.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            rb = 5;
        }});
        
        li.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()){
            revs.removeAll();
            revs.add(publicRevs, BorderLayout.PAGE_START);
            revs.add(liScroll, BorderLayout.WEST);
            revs.updateUI();
            if(li.getSelectedIndex() != -1){
                List<String> comments = new ArrayList<>();
                List<String> stars = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader("Reviews.txt"))) {
                    String line;
                    int movieId = 0;
                    String selected = li.getSelectedValue();
                    for(Movie movie : movies){
                        if(selected == movie.getName()){
                        movieId = movie.getMovieId();
                        }
                    }
                    while ((line = reader.readLine()) != null) {
                        String[] eachLine = line.split(",");
                        if(Integer.parseInt(eachLine[0]) == movieId){
                            comments.add(eachLine[1]);
                            stars.add(eachLine[2]);
                        }
                    }
                } catch (Exception ex){ex.getMessage();}
                String data [][] = new String[stars.size()][2];
                String columns [] = {"Comment", "Stars (5)"};
                try {
                    for (int i = 0; i < stars.size(); i++) {
                        data[i][0] = comments.get(i);
                        data[i][1] = stars.get(i);
                    }
                } catch (Exception ex) {}

                JTable revsTable = new JTable(data, columns);
                revsTable.setBorder(BorderFactory.createEtchedBorder());
                revsTable.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
                revsTable.setDefaultEditor(Object.class, null);
                JScrollPane revScrollPane = new JScrollPane(revsTable);
                revScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                revs.add(revScrollPane, BorderLayout.CENTER); revs.updateUI();
            }
            }
        }
        });

        revs.add(publicRevs, BorderLayout.PAGE_START);
        revs.add(liScroll, BorderLayout.WEST);
        r.add(sigPanel, BorderLayout.PAGE_START); 
        r.add(revs, BorderLayout.CENTER); r.updateUI();
    }
        
// //=============================Movies=======================================\\
    public static void m(){
        m.removeAll();
        Cinema cinema = new Cinema();
        cinema.parseMovieFile("Movies.txt");
        
        m.setLayout(new BorderLayout());
        m.setBorder(BorderFactory.createEtchedBorder());
        List<Movie> movies = cinema.displayMoviesInHalls();
        List<String> movieNames = new ArrayList<>();
        List<String> movieShowtimes = new ArrayList<>();
        List<String> movieGenres = new ArrayList<>();
        List<Integer> movieSeats = new ArrayList<>();
        for(Movie movie : movies){
            movieNames.add(movie.getName());
            movieGenres.add(movie.getGenre());
            movieShowtimes.add(movie.getShowtime());
            movieSeats.add(movie.getNumSeats());
        }
        String columns [] = {"Name", "Genre", "Showtime", "Available seats"};
        String data [][] = new String[movies.size()][4];
        for (int i = 0; i < movies.size(); i++) {
        data[i][0] = movieNames.get(i);
        data[i][1] = movieGenres.get(i);
        data[i][2] = movieShowtimes.get(i);
        data[i][3] = movieSeats.get(i).toString();
        }
        
        JTable moviesTable = new JTable(data, columns);
        moviesTable.setDefaultEditor(Object.class, null);
        moviesTable.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
        JScrollPane moviesTSc = new JScrollPane(moviesTable);
        moviesTSc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel label = new JLabel("Available Movies:");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(51, 102, 204));
        label.setBorder(BorderFactory.createEtchedBorder());
        
        m.add(label, BorderLayout.PAGE_START);
        m.add(moviesTSc, BorderLayout.CENTER);
        m.updateUI();
    }

// //===========================reserve tickets=========================\\
    public static void rt(){
        Cinema cinema = new Cinema();
        cinema.parseMovieFile("Movies.txt");
        TicketManager tm = new TicketManager(cinema);

        JButton againBtn = new JButton("Go back");
        againBtn.setBackground(new Color(119, 154, 229));

        JButton selectSeat = new JButton("Select seat/s");
        
        rt.setLayout(new BorderLayout());
        final DefaultListModel<String> l1 = new DefaultListModel<>();
        final DefaultListModel<String> l2 = new DefaultListModel<>();
        for(Map.Entry<String, List<Movie>> entry : cinema.getHalls().entrySet()){
        l1.addElement(entry.getKey());
        }

        final JList<String> list1 = new JList<>(l1);
        final JList<String> list2 = new JList<>(l2);
        JPanel listsPanel = new JPanel(new GridLayout(1, 2));
        JPanel info = new JPanel(null);
        JLabel ll1 = new JLabel("Select a genre:");
        ll1.setFont(new Font("Arial", Font.BOLD, 15));
        ll1.setBounds(10, 0, 150, 30);
        JButton reserve = new JButton("Reserve");
        reserve.setBackground(new Color(119, 154, 229));
        reserve.setFont(new Font("Arial", Font.BOLD, 15));
        reserve.setBounds(260, 370, 100, 50);
        selectSeat.setBounds(260, 310, 100, 50);
        JScrollPane list1Scroll = new JScrollPane(list1);
        JScrollPane list2Scroll = new JScrollPane(list2);
        list1Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        list2Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        list1.setFont(new Font("Arial", Font.PLAIN, 14));
        list1Scroll.setBounds(10,30,130,150);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userLabel.setBounds(20, 20, 150, 20);
        JTextField username = new JTextField();
        username.setBounds(20, 40, 150, 20);
        info.add(userLabel); info.add(username);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 13));
        emailLabel.setBounds(20, 160, 150, 20);
        JTextField email = new JTextField();
        email.setBounds(20, 180, 150, 20);
        info.add(emailLabel); info.add(email);

        JLabel numLabel = new JLabel("Number of tickets:");
        numLabel.setFont(new Font("Arial", Font.BOLD, 13));
        numLabel.setBounds(510, 20, 150, 20);
        JTextField numTickets = new JTextField();
        numTickets.setFont(new Font("Arial", Font.ITALIC, 11));
        numTickets.setBounds(510, 40, 150, 20);
        info.add(numLabel); info.add(numTickets);

        JLabel payLabel = new JLabel("Payment method:");
        payLabel.setFont(new Font("Arial", Font.BOLD, 13));
        payLabel.setBounds(510, 160, 150, 20);
        pay.addElement("Cash");
        pay.addElement("Paypal");
        pay.addElement("Credit Card");
        payy.setFont(new Font("Arial", Font.PLAIN, 12));
        payy.setBounds(510, 180, 150, 50);
        info.add(payLabel); info.add(payy);

        JLabel offer = new JLabel("<html>Buy five or more tickets<br/>and get a 20% discount!!</html>", SwingConstants.CENTER);
        offer.setBorder(BorderFactory.createTitledBorder(null, "Christmas Special Offer:", javax.swing.border.
        TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.
        TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 13), new Color(51, 102, 204)));
        offer.setBounds(200, 80, 300, 70);
        offer.setFont(new Font("Arial", Font.PLAIN, 14));
        offer.setBackground(Color.WHITE);
        info.add(offer);

        listsPanel.add(list1Scroll); listsPanel.add(list2Scroll);
        rt.add(reserve, BorderLayout.PAGE_END); rt.add(info, BorderLayout.CENTER);
        rt.add(listsPanel, BorderLayout.PAGE_START);
        listsPanel.updateUI();
        info.updateUI();
        rt.updateUI();
        
        list1.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()){
            list2.clearSelection();
            listsPanel.removeAll();
            l2.clear();
            
            listsPanel.add(list1Scroll);
            listsPanel.add(list2Scroll);
            listsPanel.updateUI();
            String data = list1.getSelectedValue();
            for(Map.Entry<String, List<Movie>> entry : cinema.getHalls().entrySet()){
                if(entry.getKey() == data){
                    moviesFromHall = entry.getValue();
                }

            }
            for(Movie movie : moviesFromHall){
                selectedMovie = movie;
                l2.addElement(movie.getName());
            }

            JLabel ll2 = new JLabel("Select a movie: ");
            ll2.setFont(new Font("Arial", Font.BOLD, 15));
            ll2.setBounds(160, 0, 150, 30);
            list2.setFont(new Font("Arial", Font.PLAIN, 14));
            list2Scroll.setBounds(160, 30, 200, 150);
            listsPanel.removeAll();
            listsPanel.add(list1Scroll);
            listsPanel.add(list2Scroll);            
            listsPanel.updateUI();
            rt.updateUI();
            list2.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()){
                    String selected = list2.getSelectedValue();
                    for(Movie movie : moviesFromHall){
                        if(selected == movie.getName()){
                            selectedMovie = movie;
                            break;
                        }
                    }
                    numTickets.setText("Available: " + selectedMovie.getNumSeats());
                    numTickets.setForeground(Color.BLACK);
                    numTickets.addFocusListener(new FocusListener() {
                        public void focusGained(FocusEvent e) {
                            if (numTickets.getText().equals("Available: " + selectedMovie.getNumSeats())) {
                                numTickets.setText("");
                            }
                        }         
                        public void focusLost(FocusEvent e) {
                            if (numTickets.getText().isEmpty()) {
                                numTickets.setText("Available: " + selectedMovie.getNumSeats());
                                numTickets.setForeground(Color.GRAY);
                            }
                        }
                    });
                    }
                }
            });
        }}
        });

        reserve.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent a) {
            if (username.getText().length() == 0){
                JLabel invalidUsername = new JLabel("Invalid username.");
                invalidUsername.setFont(new Font("Arial", Font.ITALIC, 8));
                invalidUsername.setForeground(Color.RED);
                invalidUsername.setBounds(180, 40, 150, 20);
                info.add(invalidUsername); rt.updateUI();
            }
            if(!email.getText().contains("@")){
                JLabel invalidEmail = new JLabel("Invalid email.");
                invalidEmail.setFont(new Font("Arial", Font.ITALIC, 8));
                invalidEmail.setForeground(Color.RED);
                invalidEmail.setBounds(180, 180, 150, 20);
                info.add(invalidEmail); rt.updateUI();
            }
            if(selectedMovie.getNumSeats() < Integer.parseInt(numTickets.getText())) {
                JLabel noSeats = new JLabel("Sorry, only " + selectedMovie.getNumSeats() + " available.");
                noSeats.setFont(new Font("Arial", Font.ITALIC, 8));
                noSeats.setForeground(Color.RED);
                noSeats.setBounds(670, 35, 150, 20);
                info.add(noSeats); rt.updateUI();
            }
            if(Integer.parseInt(numTickets.getText()) <= 0) {
                JLabel zeroSeats = new JLabel("Invalid number.");
                zeroSeats.setFont(new Font("Arial", Font.ITALIC, 8));
                zeroSeats.setForeground(Color.RED);
                zeroSeats.setBounds(670, 45, 150, 20);
                info.add(zeroSeats); rt.updateUI();
            }
            if(payy.getSelectedIndex() == -1){
                JLabel noPay = new JLabel("Must select.");
                noPay.setFont(new Font("Arial", Font.ITALIC, 8));
                noPay.setForeground(Color.RED);
                noPay.setBounds(670, 200, 150, 50);
                info.add(noPay); rt.updateUI();
            }
            if (selectedMovie.getNumSeats() >= Integer.parseInt(numTickets.getText()) &&
                Integer.parseInt(numTickets.getText()) != 0 &&
                email.getText().contains("@") && username.getText().length() > 0 &&
                payy.getSelectedIndex() != -1) {
                List<String> ids = tm.reserveTickets(selectedMovie.getGenre(), selectedMovie.getMovieId(),
                    Integer.parseInt(numTickets.getText()), username.getText(), email.getText(), payy.getSelectedValue().toString());
                rt.removeAll();
                rt.setLayout(new BorderLayout());
                JLabel doneLabel = new JLabel("Successfully reserved " + numTickets.getText() +
                    " tickets for movie: " + selectedMovie.getName());
                JLabel priceLabel = new JLabel("Total price: " + ids.size() * 30_000);
                String columns [] = {"Ticket","Ticket ID"};
                String data [][] = new String[ids.size()][columns.length];
                for (int i = 0; i < ids.size(); i++) {
                    data[i][0] = Integer.toString(i + 1);
                    data[i][1] = ids.get(i);
                }

                JTable idTable = new JTable(data, columns);
                idTable.setDefaultEditor(Object.class, null);
                idTable.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
                JScrollPane idTableSc = new JScrollPane(idTable);
                idTableSc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                rt.add(doneLabel, BorderLayout.PAGE_START); rt.add(idTableSc, BorderLayout.CENTER);                  rt.add(priceLabel, BorderLayout.PAGE_END); rt.add(againBtn, BorderLayout.EAST); rt.updateUI(); m();
                rt.updateUI();
                }
            }
        });

        againBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent a) {
            rt.removeAll(); rt.updateUI();
            rt();
        }});
    }

    //===========================================Manage User accounts====================================================\\
    public static void user(){
        UsersAcc.setBackground(new Color(240, 240, 240));
        UsersAcc.setBorder(BorderFactory.createEtchedBorder());
        JTextField userNamefield = new JTextField("Username", 10);
        userNamefield.setBackground(new Color(255, 255, 255));
        userNamefield.setFont(new Font("Arial", Font.PLAIN, 14));
        userNamefield.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        userNamefield.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (userNamefield.getText().equals("Username")) {
                    userNamefield.setText("");
                    userNamefield.setForeground(Color.BLACK);
                }
            }         
            public void focusLost(FocusEvent e) {
                if (userNamefield.getText().isEmpty()) {
                    userNamefield.setText("Username");
                    userNamefield.setForeground(Color.GRAY);
                }
            }
        });

        JTextField emaField = new JTextField("Email", 10);
        emaField.setBackground(new Color(255, 255, 255));
        emaField.setFont(new Font("Arial", Font.PLAIN, 14));
        emaField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        emaField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (emaField.getText().equals("Email")) {
                    emaField.setText("");
                    emaField.setForeground(Color.BLACK);
                }
            }    
            public void focusLost(FocusEvent e) {
                if (emaField.getText().isEmpty()) {
                    emaField.setText("Email");
                    emaField.setForeground(Color.GRAY);
                }
            }
        });

        JButton CheckUserDetails = new JButton("Check My Tickets ");
        CheckUserDetails.setBackground(new Color(119, 154, 229));
        CheckUserDetails.setFont(new Font("Arial", Font.BOLD, 14));
        //###############The Table##################\\
        String[][] outputData = new String[100][8];
        String[] columnNames = {"Ticket ID", "Mo.ID", "M.Name", "Genre Hall", "Ticket Price", "Payment Method"};
        DefaultTableModel tableModel = new DefaultTableModel(outputData, columnNames);
        JTable detailTable = new JTable(tableModel);
        detailTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        detailTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        detailTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        detailTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        detailTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        detailTable.setDefaultEditor(Object.class, null);
        detailTable.setFont(new Font("Arial", Font.ROMAN_BASELINE, 13));
        //###############The Table##################\\

        // Action listener for the button
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.TRAILING, 30, 0));
        JButton DeleteMyaccount = new JButton("Delete My Account ");
        CheckUserDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
                    String userN = userNamefield.getText();
                    String emailUser = emaField.getText();
                    String Deta;
                    int x = 0;
                    boolean hasTickets = false;
                    // Clear old information in the table
                    tableModel.setRowCount(0);
                    while ((Deta = reader.readLine()) != null) {
                        String[] UserData = Deta.split(",");
                        String username = UserData[5];
                        String userEmail = UserData[6];
                        if (userN.equals(username) && emailUser.equals(userEmail)) {
                            for (int i = 0; i < 8; i++) {
                                if(i == 5 || i == 6){}
                                else if (i<5){outputData[x][i] = UserData[i];}
                                else {outputData[x][i-2] = UserData[i];}
                            }
                            x++;
                            hasTickets = true;
                        }
                    }
                    if (hasTickets) {
                        for (int i = x - 1; i >= 0; i--) {
                            tableModel.insertRow(0, outputData[i]);
                        }
                        JLabel signed = new JLabel("Signed in successfully.");
                        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
                        panel1.removeAll(); panel1.add(signed); panel1.updateUI();
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                panel1.removeAll();
                                panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 0));
                                panel1.add(userNamefield);
                                panel1.add(emaField);
                                panel1.add(CheckUserDetails);
                                panel1.add(DeleteMyaccount);
                                panel1.updateUI();
                            }
                        });
                        timer.setRepeats(false); // Only execute once
                        timer.start();
                    } else {
                        JLabel noAcc = new JLabel("Account does not exist.");
                        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
                        panel1.removeAll(); panel1.add(noAcc); panel1.updateUI();
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                panel1.removeAll();
                                panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 0));
                                panel1.add(userNamefield);
                                panel1.add(emaField);
                                panel1.add(CheckUserDetails);
                                panel1.add(DeleteMyaccount);
                                panel1.updateUI();
                            }
                        });
                        timer.setRepeats(false); // Only execute once
                        timer.start();
                    }
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
        //Button to delete account 
        DeleteMyaccount.setBackground(new Color(119, 154, 229));
        DeleteMyaccount.setFont(new Font("Arial", Font.BOLD, 14));
        DeleteMyaccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userNamefield.getText();
                String email = emaField.getText();
                // Delete user from Users file
                File usersFile = new File("Users.txt");
                File tempUsersFile = new File("tempUsers.txt");
                boolean found = false;
                
                try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] userInfo = line.split(",");
                        String existingUsername = userInfo[0];
                        String existingEmail = userInfo[1];
                        if(existingUsername.equals(username) && existingEmail.equals(email)){

                            JFrame consentFrame = new JFrame("Warning");
                            consentFrame.setAlwaysOnTop(true);
                            consentFrame.setSize(350, 160);
                            consentFrame.setResizable(false);
                            consentFrame.setLocation(500, 100);
                            JPanel consentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            JLabel consentLabel = new JLabel("Are you sure you want to delete your account?");
                            consentLabel.setFont(new Font("Arial", Font.BOLD, 13));
                            consentLabel.setForeground(Color.RED);
                            consentLabel.setPreferredSize(new Dimension(300, 30));
                            JLabel warningLabel = new JLabel("Note: all your data will be lost forever,");
                            JLabel warningLabel2 = new JLabel("and all your tickets will get cancelled.");
                            warningLabel.setFont(new Font("Arial", Font.BOLD, 11));
                            warningLabel2.setFont(new Font("Arial", Font.BOLD, 11));
                            JButton yesBtn = new JButton("Yes");
                            JButton noBtn = new JButton("No");
                            yesBtn.setBackground(new Color(119, 154, 229));
                            noBtn.setBackground(new Color(119, 154, 229));
                            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            btnPanel.add(noBtn);
                            btnPanel.add(yesBtn);
                            consentPanel.add(consentLabel);
                            consentPanel.add(warningLabel);
                            consentPanel.add(warningLabel2);
                            consentPanel.add(btnPanel); consentPanel.updateUI();
                            consentFrame.add(consentPanel);
                            consentFrame.setVisible(true);
                            yesBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent a) {
                                    try (BufferedReader reader = new BufferedReader(new FileReader(usersFile));
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempUsersFile))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        String[] userInfo = line.split(",");
                                        String existingUsername = userInfo[0];
                                        String existingEmail = userInfo[1];
                                        if (!existingUsername.equals(username) || !existingEmail.equals(email)) {
                                            writer.write(line);
                                            writer.newLine();
                                        }
                                    }
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                    usersFile.delete();
                                    tempUsersFile.renameTo(usersFile);
                                    File ticketsFile = new File("Tickets.txt");
                                    File tempTicketsFile = new File("tempTickets.txt");
                                    try (BufferedReader reader = new BufferedReader(new FileReader(ticketsFile));
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempTicketsFile))) {
                                        String line;
                                        while ((line = reader.readLine()) != null) {
                                            String[] ticketInfo = line.split(",");
                                            String existingUsername = ticketInfo[5];
                                            String existingEmail = ticketInfo[6];
                                            if (!existingUsername.equals(username) || !existingEmail.equals(email)) {
                                                writer.write(line);
                                                writer.newLine();
                                            }
                                            JLabel accountDeleted = new JLabel("Account has been deleted.");
                                            panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
                                            panel1.removeAll(); panel1.add(accountDeleted); panel1.updateUI();
                                            Timer timer = new Timer(2000, new ActionListener() {
                                                public void actionPerformed(ActionEvent evt) {
                                                    panel1.removeAll();
                                                    panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 0));
                                                    panel1.add(userNamefield);
                                                    panel1.add(emaField);
                                                    panel1.add(CheckUserDetails);
                                                    panel1.add(DeleteMyaccount);
                                                    panel1.updateUI();
                                                }
                                            });
                                            timer.setRepeats(false); // Only execute once
                                            timer.start();
                                        }
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                    ticketsFile.delete();
                                    tempTicketsFile.renameTo(ticketsFile);
                                    consentFrame.dispose();
                                }
                            });

                            noBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent a) {
                                    consentFrame.dispose();
                            }});
                            found = true;
                            break;
                        }
                    }
                } catch (Exception ex) {ex.getStackTrace();}
                if (!found){
                    JLabel noAcc = new JLabel("Account does not exist.");
                        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
                        panel1.removeAll(); panel1.add(noAcc); panel1.updateUI();
                        Timer timer = new Timer(2000, new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                panel1.removeAll();
                                panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 0));
                                panel1.add(userNamefield);
                                panel1.add(emaField);
                                panel1.add(CheckUserDetails);
                                panel1.add(DeleteMyaccount);
                                panel1.updateUI();
                            }
                        });
                        timer.setRepeats(false); // Only execute once
                        timer.start();
                } 
            }
        });

        
        
        panel1.add(userNamefield);
        panel1.add(emaField);
        panel1.add(CheckUserDetails);
        panel1.add(DeleteMyaccount);
        JScrollPane sp = new JScrollPane(detailTable);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        UsersAcc.setLayout(new BorderLayout());
        UsersAcc.add(panel1, BorderLayout.PAGE_START);
        UsersAcc.add(sp, BorderLayout.CENTER);
        UsersAcc.updateUI();
    }
                
    //===========================================cancel reservation======================================================\\
    public static void cancelres(){                 
        TicketManager ticketManager = new TicketManager(null);
        JPanel contentPane = new JPanel();
                    
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
                    
        JLabel ticketIdLabel = new JLabel("Ticket ID:");
        ticketIdLabel.setFont(new Font("Arial", Font.BOLD, 30));
        ticketIdLabel.setForeground(new Color(51, 102, 204));
        ticketIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPane.add(ticketIdLabel);
        contentPane.add(Box.createVerticalStrut(10)); // Add vertical space
        
        JTextField ticketIdField = new JTextField(10);
        ticketIdField.setFont(new Font("Arial", Font.PLAIN, 30));
        ticketIdField.setPreferredSize(new Dimension(170, 30));
        ticketIdField.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(ticketIdField);
        contentPane.add(Box.createVerticalStrut(10)); // Add vertical space
        
        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 30));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setBackground(new Color(119, 154, 229));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(cancelButton);
        contentPane.add(Box.createVerticalStrut(10)); // Add vertical space
        
        JLabel outputLabel = new JLabel();
        outputLabel.setFont(new Font("Arial", Font.BOLD, 15));
        outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(outputLabel);
                    
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ticketId = ticketIdField.getText();
                boolean existsBeforeCancellation = ticketManager.checkTicketExistence(ticketId);
                ticketManager.cancelReservation(ticketId);

                if (existsBeforeCancellation) {
                    outputLabel.setText("Reservation canceled for ticket ID: " + ticketId);
                } else {
                    outputLabel.setText("Ticket with ID " + ticketId + " doesn't exist.");
                }
            }
        });
                    
        Border linn = new EmptyBorder(150, 0, 150, 0);
        contentPane.setBorder(linn);
        CancelReservation.add(contentPane);
        CancelReservation.updateUI();
    }
        
    //=========================================Statistic============================\\
        public static void stati(){
            JButton popularMoviesButton = new JButton("Show Popular Movies");
            popularMoviesButton.setBackground(new Color(119, 154, 229));
            popularMoviesButton.setFont(new Font("Arial", Font.BOLD, 15));
            

            JButton frequentShowtimesButton = new JButton("Show Frequent Showtimes");
            frequentShowtimesButton.setBackground(new Color(119, 154, 229));
            frequentShowtimesButton.setFont(new Font("Arial", Font.BOLD, 15));
            JLabel mm = new JLabel();
            mm.setText("Movies And number of People");
            mm.setFont(new Font("Arial", Font.BOLD, 20));
            moviesTable = new JTable();
            moviesTable.setFont(new Font("Arial", Font.BOLD,12));
            moviesTable.setBackground(new Color(211, 211, 211));

            JLabel gg = new JLabel();
            gg.setText("Showtimes  And number of People");
            gg.setFont(new Font("Arial", Font.BOLD, 20));
            showtimesTable = new JTable();
            showtimesTable.setFont(new Font("Arial", Font.BOLD,12));
            showtimesTable.setBackground(new Color(211, 211, 211));
            
            popularMoviesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    List<Map.Entry<String, Integer>> mostPopularMovies = Statistics.getMostRepetitiveMovies();
                    updateMoviesTable(mostPopularMovies);
                }
            });
            
            frequentShowtimesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    List<Map.Entry<String, Integer>> mostFrequentShowtimes = Statistics.getMostFrequentShowtimes();
                    updateShowtimesTable(mostFrequentShowtimes);
                }
            });
            statis.setLayout(new BoxLayout(statis, BoxLayout.Y_AXIS)); 
            statis.add(popularMoviesButton); 
            statis.add(mm);
            statis.add(Box.createVerticalStrut(20)); 
            statis.add(moviesTable);
            statis.add(Box.createVerticalStrut(20)); 
            statis.add(frequentShowtimesButton);
            statis.add(gg);
            statis.add(Box.createVerticalStrut(20));
            statis.add(showtimesTable);
            statis.updateUI();
        }

        public static void main(String[] args) {
            // create a JFrame to hold everything
            JFrame f = new JFrame("TabbedPaneFrame");
        
            tabby.addTab("Movies", m);
            tabby.addTab("Reserve Tickets", rt);
            tabby.addTab("CancelReservation", CancelReservation);
            tabby.addTab("Account Manager", UsersAcc);
            tabby.addTab("Review", r);
            tabby.addTab("Popularity", statis);
            
            
            // // Movies tab:
            m();
            // // Reserve tickets tab:
            rt();
            // // Reviews tab
            r();
            user();
            cancelres();
            stati();


            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 500);
            f.setLocation(300, 0);
            f.getContentPane().setBackground(Color.GRAY);
    
            tabby.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            f.getContentPane( ).add(tabby, BorderLayout.CENTER);
            f.setVisible(true);
            f.setResizable(false);
            f.setVisible(true);

        }
    } 