package tbi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import db.Create;
import db.Insert;
import db.Update;
import logic.Book;
import logic.Session;

public class TBIMain {
    public static final String URL = "jdbc:sqlite:src/main/resources/bookHistory.db";

    /**
     * main Method of the text based interface;
     * first asks for a character:
     *  'b' -> add a book to the database
     *  's' -> add a session (and if needed, a reading entry) to the database
     *  'e' -> add a date_end to a reading entry, entry is needed to signal that the book is finished
     *  'q' -> quit the program
     *  after each transaction (except q), it asks for another character
     * @param args
     */
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL);
            Create.createTables(conn);
            Scanner scanner = new Scanner(System.in);
            boolean inUse = true;
            System.out.println("With this application, you can track your reading data.");
            while(inUse) {
                Interaction.printPossibleInput();
                char action = scanner.nextLine().charAt(0);
                if(action == 'b') {
                    Interaction.addingBook(scanner, conn);
                    System.out.println("New book added");
                }
                else if(action == 's') {
                    Interaction.addingSession(scanner, conn);
                    System.out.println("New session added\n");
                }
                else if(action == 'e') {
                    Interaction.updatingReading(scanner, conn);
                }
                else if(action == 'q') {
                    inUse = false;
                    scanner.close();
                }
                else {
                    System.out.println("Please enter a valid character.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
