package gui;

import db.Select;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Book;
import logic.Reading;
import logic.Session;

import java.sql.Connection;

public class Table {
    private static TableView<Book> tableBooks;
    private static TableView<Session> tableSessions;
    private static TableView<Reading> tableReading;

    /**
     * returns a TableView<Session> with a columnTitle, a columnDate and a columnDuration
     * @param conn
     * @param window
     * @return
     */
    public static TableView<Session> createTableSessions(Connection conn, Stage window) {
        TableColumn<Session, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMaxWidth(390);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<Session, String> columnDate = new TableColumn<>("Date");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Session, String> columnDuration = new TableColumn<>("Duration");
        columnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnDuration.setMinWidth(150);

        tableSessions = new TableView<>();
        tableSessions.setItems(Select.getSessionsList(conn));
        tableSessions.getColumns().addAll(columnTitle, columnDate, columnDuration);

        return tableSessions;
    }

    /**
     * returns a TableView<Book> with a columnTitle, a columnAuthor and a columnPageNumber
     * @param conn
     * @param window
     * @return
     */
    public static TableView<Book> createTableBooks(Connection conn, Stage window) {
        TableColumn<Book, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMaxWidth(390);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> columnAuthor = new TableColumn<>("Author");
        columnAuthor.setMaxWidth(140);
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> columnPageNumber = new TableColumn<>("Pages");
        columnPageNumber.setMinWidth(100);
        columnPageNumber.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));

        tableBooks = new TableView<>();
        tableBooks.setItems(Select.getBooksList(conn));
        tableBooks.getColumns().addAll(columnTitle, columnAuthor, columnPageNumber);

        return tableBooks;
    }

    /**
     * returns a TableView<Book> with a columnTitle, a columnDateStart and a columnDateEnd
     * @param conn
     * @param window
     * @return
     */
    public static TableView<Reading> createTableReading(Connection conn, Stage window) {
        TableColumn<Reading, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setMaxWidth(390);
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<Reading, String> columnDateStart = new TableColumn<>("Started");
        columnDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));

        TableColumn<Reading, String> columnDateEnd = new TableColumn<>("Finished");
        columnDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));

        tableReading = new TableView<>();
        tableReading.setItems(Select.getReadingList(conn));
        tableReading.getColumns().addAll(columnTitle, columnDateStart, columnDateEnd);

        return tableReading;
    }
}
