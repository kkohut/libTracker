package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
    /**
     * updates the reading entry of a finished book;
     * replaces the NULL value of date_end with another date
     * @param conn
     * @param bookTitle
     * @param endDate
     */
    public static void endReading(Connection conn, String bookTitle, String endDate) {
        String queryBookID = "SELECT books.id FROM books "  // get bookID
                + "WHERE books.title = '" + bookTitle + "';";
        int bookID = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryBookID);) {
            rs.next();
            bookID = rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "UPDATE reading "
                + "SET date_end = '" + endDate + "' "
                + "WHERE book_id = " + bookID + " "
                + "AND date_end IS NULL;";
        DBConnection.executeQuery(conn, sql);
    }
}
