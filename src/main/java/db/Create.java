package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Create {
    public static final String URL = "jdbc:sqlite:src/main/resources/bookHistory.db";
    /**
     * creates a new .db Database in the /src/main/resources directory of the project
     * and prints the driver name and a success message
     * @param fileName
     */
    public static Connection createNewDatabase(String fileName) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try {
            if(conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database " + fileName + " has been created.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * creates the 'books' table
     * -> for INSERTS, title, author and the number of pages is needed
     * @param conn
     */
    public static void createTableBooks(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " author TEXT NOT NULL,\n"
                + " page_number INTEGER NOT NULL"
                + " );";
        DBConnection.executeQuery(conn, sql);
    }

    /**
     * creates the 'reading' table
     * -> for INSERTS, book_id, date_start and date_end (can be NULL) is needed
     * @param conn
     */
    public static void createTableReading(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS reading (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " book_id INTEGER NOT NULL REFERENCES books(id),\n"
                + " date_start TEXT NOT NULL,\n"
                + " date_end TEXT \n"
                + ");";
        DBConnection.executeQuery(conn, sql);
    }
    /**
     * creates the 'sessions' table
     * -> for INSERTS, reading_id, book_id, date and the duration of the session is needed
     * @param conn
     */
    public static void createTableSessions(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS sessions (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "reading_id INTEGER NOT NULL REFERENCES reading(id),\n"
                + "book_id INTEGER NOT NULL REFERENCES books(id),\n"
                + "date TEXT NOT NULL,\n"
                + "duration TEXT NOT NULL\n"
                +  ");";
        DBConnection.executeQuery(conn, sql);
    }

    /**
     * adds the books, reading and sessions tables to the database
     * @param conn
     * @return
     * @throws SQLException
     */
    public static Connection createTables(Connection conn) throws SQLException {
        createTableBooks(conn);
        createTableReading(conn);
        createTableSessions(conn);
        return conn;
    }
}
