package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.Book;
import logic.Reading;
import logic.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class Select {
    private static HashSet<String> bookSet;
    private static ObservableList<Session> sessionsList;
    private static ObservableList<Reading> readingList;
    /**
     * returns all book titles as a TreeSet<String>
     * @param conn
     * @return
     */
    public static HashSet<String> getBooks(Connection conn) {
        bookSet = new HashSet<>();
        String sql = "SELECT title FROM books";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while(rs.next()) {
                bookSet.add(rs.getString("title"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookSet;
    }

    /**
     * returns an ObservableList<Book> with all the books in the database,
     * this list contains of title, author and page_number of those books
     * @param conn
     * @return
     */
    public static ObservableList<Book> getBooksList(Connection conn) {
        String sql = "SELECT * FROM books ORDER BY id DESC;";
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while(rs.next()) {
                booksList.add(new Book(rs.getString("title"), rs.getString("author"), rs.getInt("page_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    /**
     * returns an ObservableList<Session> with all the sessions in the database,
     * this list contains of the book title, date and duration of each session
     * @param conn
     * @return
     */
    public static ObservableList<Session> getSessionsList(Connection conn) {
        String sql = "SELECT sessions.id, books.title AS title, sessions.date AS date, sessions.duration AS duration FROM sessions JOIN books ON sessions.book_id = books.id ORDER BY sessions.id DESC;";
        ObservableList<Session> sessionsList = FXCollections.observableArrayList();
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);) {
            while(rs.next()) {
                sessionsList.add(new Session(rs.getString("title"), rs.getString("date"), rs.getString("duration")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return sessionsList;
    }

    /**
     * returns an ObservableList<Reading> with all the readings in the database,
     * this list contains of the book title, date_start and date_end of those books
     * @param conn
     * @return
     */
    public static ObservableList<Reading> getReadingList(Connection conn) {
        String sql = "SELECT books.title AS title,"
                + " date_start, date_end FROM reading JOIN books ON reading.book_id = books.id ORDER BY reading.id DESC;";
        ObservableList<Reading> readingList = FXCollections.observableArrayList();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while(rs.next()) {
                readingList.add(new Reading(rs.getString("title"), rs.getString("date_start"), rs.getString("date_end")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return readingList;
    }
}
