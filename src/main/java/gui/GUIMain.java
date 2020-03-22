package gui;

import db.Create;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import logic.Book;
import logic.Reading;
import logic.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GUIMain extends Application {
    public static final String URL = "jdbc:sqlite:src/main/resources/bookHistory.db";
    Button buttonAddBook;
    Button buttonAddSession;
    static TableView<Book> tableBooks;
    static TableView<Session> tableSessions;
    static TableView<Reading> tableReading;
    static Stage window;

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * creates a GridPane Scene which contains a TabPane with three tabs
     * and a VBox which contains buttonAddBook and buttonAddSession;
     * uses the JMetro stylesheet
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        try {
            Connection conn = DriverManager.getConnection(URL);
            Create.createTables(conn);

            window = primaryStage;
            window.setTitle("libTracker");
            window.setOnCloseRequest(e -> {
                e.consume();
                closeProgram();
            });

            buttonAddBook = new Button("Add a book");
            buttonAddBook.setMinWidth(130);
            buttonAddBook.setOnAction(e -> AlertBox.displayBookEntryWindow(conn));

            buttonAddSession = new Button("Add a session");
            buttonAddSession.setMinWidth(130);
            buttonAddSession.setOnAction(e -> AlertBox.displaySessionEntryWindow(conn));

            tableBooks = Table.createTableBooks(conn, primaryStage);
            tableSessions = Table.createTableSessions(conn, primaryStage);
            tableReading = Table.createTableReading(conn, primaryStage);

            TabPane tabPane = new TabPane();
            tabPane.setMinWidth(600);

            Tab tabSessions = new Tab("Sessions", tableSessions);
            Tab tabReading = new Tab("Reading", tableReading);
            Tab tabBooks = new Tab("Books", tableBooks);

            tabPane.getTabs().addAll(tabSessions, tabReading, tabBooks);

            GridPane layout = new GridPane();
            VBox buttonsAddBookAndSession = new VBox(10);
            buttonsAddBookAndSession.setPadding(new Insets(10, 10, 10, 10));
            buttonsAddBookAndSession.getChildren().addAll(buttonAddBook, buttonAddSession);
            GridPane.setConstraints(buttonsAddBookAndSession, 1, 0);
            GridPane.setConstraints(tabPane, 0, 0);
            layout.setVgap(10);
            layout.getChildren().addAll(buttonsAddBookAndSession, tabPane);
            layout.setPadding(new Insets(10, 10, 10, 10));

            Scene scene = new Scene(layout);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            window.setMinWidth(760);
            window.setMinHeight(460);
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeProgram() {
        boolean answer = ConfirmBox.display("Confirm action", "Are you sure you want to close the application? Unsaved work will be lost.");
        if (answer) {
            window.close();
        }
    }
}
