package tbi;

import db.Insert;
import db.Update;
import logic.Book;
import logic.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Interaction {
    public static void printPossibleInput() {
        System.out.println("Press...\n"
                + "\t 'b', if you want to add a book\n"
                + "\t 's', if you want to add a session\n"
                + "\t 'e', if you want to finish a book\n"
                + "\t 'q', if you want to quit.");
    }

    /**
     * asks for title, author and number of pages and hands them to insertBook();
     * @param scanner
     * @param conn
     * @throws SQLException
     */
    public static void addingBook(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Please enter the title of your book");
        String title = scanner.nextLine();
        System.out.println("Please enter the author of your book");
        String author = scanner.nextLine();
        System.out.println("Please enter the number of pages of your book");
        int page_number = Integer.valueOf(scanner.nextLine());
        Book book = new Book(title, author, page_number);
        System.out.println(book.getTitle() + book.getAuthor() + book.getPageNumber());
        Insert.insertBook(conn, book);
    }

    /**
     * asks for title, date and duration of the session hands them to insertSession();
     * @param scanner
     * @param conn
     * @throws SQLException
     */
    public static void addingSession(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Please enter the name of your book");   //needs to be tested
        String bookTitle = scanner.nextLine();
        System.out.println("Please enter the date of your session");
        String date = scanner.nextLine();
        System.out.println("Please enter the duration of your session");
        String duration = scanner.nextLine();
        Session session = new Session(bookTitle, date, duration);
        Insert.insertSession(conn, session);
    }

    /**
     * asks for the book and the date named book was finished
     * @param scanner
     * @param conn
     */
    public static void updatingReading(Scanner scanner, Connection conn) {
        System.out.println("Please enter the name of your book");
        String bookTitle = scanner.nextLine();
        System.out.println("Please enter the date you finished the book");
        String endDate = scanner.nextLine();
        Update.endReading(conn, bookTitle, endDate);
    }
}
