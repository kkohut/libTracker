package gui;

import db.Insert;
import db.Select;
import db.Update;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import logic.Book;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);

        Label label = new Label();
        label.setText(message);

        Button saveButton = new Button("OK");
        saveButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, saveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());
    }

    /**
     * creates an AlertBox to ask for the input of the book entry
     */
    public static void displayBookEntryWindow(Connection conn) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new book");
        window.setResizable(false);

        // add titleLabel and titleInput as a HBox
        Label titleLabel = new Label("Title:");
        TextField titleInput= new TextField();
        titleInput.setPromptText("Harry Potter and the Philosopher's Stone");
        HBox titleBox = new HBox(10);
        titleBox.getChildren().addAll(titleLabel, titleInput);

        // add authorLabel and authorInput as a HBox
        Label authorLabel = new Label("Author:");
        TextField authorInput = new TextField();
        authorInput.setPromptText(("J. K. Rowling"));
        HBox authorBox = new HBox(10);
        authorBox.getChildren().addAll(authorLabel, authorInput);

        // add pageNumberLabel and pageNumberInput as a HBox
        Label pageNumberLabel = new Label("Number of pages:");
        TextField pageNumberInput = new TextField();
        pageNumberInput.setPromptText(("331"));
        HBox pageNumberBox = new HBox(10);
        pageNumberBox.getChildren().addAll(pageNumberLabel, pageNumberInput);

        // add saveButton to save the entry (and insert it into the DB)
        Button saveButton = new Button("Save entry");
        saveButton.setOnAction(e -> {
            boolean valuesValid = Validation.validateBookData(titleInput, titleInput.getText(), authorInput, authorInput.getText(), pageNumberInput, pageNumberInput.getText());
            if (!valuesValid) {
                AlertBox.display("Input invalid", "Please enter valid data");
            }
            else {
                Book book = new Book(titleInput.getText(), authorInput.getText(), Integer.parseInt(pageNumberInput.getText()));
                try {
                    Insert.insertBook(conn, book);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                window.close();
            }
        });


        // add labels and TextFields to a new GridPane
        GridPane gridpane = new GridPane();
        gridpane.setVgap(5);
        gridpane.setHgap(5);
        GridPane.setConstraints(titleLabel,0, 0);
        GridPane.setConstraints(titleInput, 1, 0);
        GridPane.setConstraints(authorLabel, 0, 1);
        GridPane.setConstraints(authorInput, 1, 1);
        GridPane.setConstraints(pageNumberLabel, 0, 2);
        GridPane.setConstraints(pageNumberInput, 1, 2);
        GridPane.setHalignment(titleLabel, HPos.RIGHT);
        GridPane.setHalignment(authorLabel, HPos.RIGHT);
        GridPane.setHalignment(pageNumberLabel, HPos.RIGHT);
        gridpane.getChildren().addAll(titleLabel, titleInput,
                authorLabel, authorInput, pageNumberLabel, pageNumberInput);

        // add titleBox, authorBox, pageNumberBox, saveButton to the VBox
        VBox layout = new VBox(10);
        layout.getChildren().addAll(gridpane, saveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        window.setScene(scene);
        window.showAndWait();
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());
    }

    /**
     * creates an AlertBox to ask for the input of the book entry
     */
    public static void displaySessionEntryWindow(Connection conn) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new session");
        window.setResizable(false);

        // add Labels, ChoiceBox, DatePicker and TextField
        Label labelBooks = new Label("Book:");
        HashSet<String> bookSet = Select.getBooks(conn);

        ChoiceBox<String> choiceBoxBooks = new ChoiceBox<>();
        choiceBoxBooks.setMaxWidth(210);
        for(String s : bookSet) {
            choiceBoxBooks.getItems().add(s);
        }
        choiceBoxBooks.getSelectionModel().selectFirst();

        Label labelDate = new Label("Date:");
        final DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(choiceBoxBooks.getMaxWidth());
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
            }
        });

        Label labelDuration = new Label("Duration:");
        TextField inputDuration = new TextField();
        inputDuration.setMaxWidth(50);
        inputDuration.setPromptText("0:00");

        CheckBox checkBoxFinished = new CheckBox("finished");

        // add saveButton to save the entry (and insert it into the DB)
        Button saveButton = new Button("Save entry");
        saveButton.setOnAction(e -> {
            boolean valuesValid = Validation.validateSessionData(datePicker.getValue(), inputDuration.getText());
            if (!valuesValid) {
                AlertBox.display("Input invalid", "Please enter valid data");
            }
            else {
                Session session = new Session(choiceBoxBooks.getValue(), datePicker.getValue().toString(), inputDuration.getText());
                try {
                    Insert.insertSession(conn, session);
                    if(checkBoxFinished.isSelected()) {
                        Update.endReading(conn, choiceBoxBooks.getValue(), datePicker.getValue().toString());
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                window.close();
            }
        });
        // add a GridPane
        GridPane gridpane = new GridPane();
        GridPane.setConstraints(labelBooks, 0, 0);
        GridPane.setConstraints(choiceBoxBooks, 1, 0);
        GridPane.setConstraints(checkBoxFinished, 2, 0);
        GridPane.setConstraints(labelDate, 0, 1);
        GridPane.setConstraints(datePicker, 1, 1);
        GridPane.setConstraints(labelDuration, 0, 2);
        GridPane.setConstraints(inputDuration, 1, 2);
        GridPane.setHalignment(labelBooks, HPos.RIGHT);
        GridPane.setHalignment(labelDate, HPos.RIGHT);
        GridPane.setHalignment(labelDuration, HPos.RIGHT);
        gridpane.getChildren().addAll(labelBooks, choiceBoxBooks, checkBoxFinished, labelDate, datePicker,labelDuration, inputDuration);
        gridpane.setVgap(5);
        gridpane.setHgap(5);

        // add titleBox, authorBox, pageNumberBox, saveButton to the VBox       // could also be gridpane
        VBox layout = new VBox(10);
        layout.getChildren().addAll(gridpane, saveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        window.setScene(scene);
        window.showAndWait();
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());
    }

}
