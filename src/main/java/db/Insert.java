package db;

import logic.Book;
import logic.Reading;
import logic.Session;

import java.sql.*;

public class Insert {
    /**
     * inserts a book entry with the title, author and page_number that are give as arguments
     * @param conn
     * @param book
     * @throws SQLException
     */
    public static void insertBook(Connection conn, Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, page_number) VALUES ("
                + "'" + book.getTitle() + "', "
                + "'" + book.getAuthor() + "', "
                + book.getPageNumber()
                + ");";
        System.out.println(sql);
        DBConnection.executeQuery(conn, sql);
    }

    /**
     * inserts a session entry with the reading_id, book_id, date and duration of the session that are give as arguments
     * if this is the first entry of the book, a new reading entry is added
     * @param conn
     * @param session
     * @throws SQLException
     */
    public static void insertSession(Connection conn, Session session) throws SQLException {
        String queryBookID = "SELECT books.id FROM books "  // get bookID
                + "WHERE books.title = '" + session.getBookTitle() + "'";
        int bookID = 0;
        int readingID = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryBookID);) {
            bookID = rs.getInt(1);
            insertReading(conn, bookID, session.getDate());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        String queryReadingID = "SELECT reading.id FROM reading " // get readingID
                + "WHERE reading.book_id = " + bookID + " AND reading.date_end IS NULL;";
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryReadingID);) {
            rs.next();
            readingID = rs.getInt("id");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO sessions (reading_ID, book_ID, date, duration) VALUES (" +
                + readingID + ", " + bookID + ", '" + session.getDate() + "', '" + session.getDuration()
                + "');";
        DBConnection.executeQuery(conn, sql);
    }

    /**
     * inserts a reading entry with the book_id, date_start and date_end
     * that are give as arguments (except date_end, which is always NULL at first)
     * @param conn
     * @param bookID
     * @throws SQLException
     */
    public static void insertReading(Connection conn, int bookID, String dateStart) throws SQLException {
        String sqlReading = "INSERT INTO reading (book_id, date_start, date_end) "
                + "SELECT " + bookID + ", '" + dateStart + "', NULL "
                + "WHERE NOT EXISTS ("
                + "SELECT reading.id FROM reading WHERE book_id = " + bookID
                + " AND date_end IS NULL);";
        DBConnection.executeQuery(conn, sqlReading);
    }
}
