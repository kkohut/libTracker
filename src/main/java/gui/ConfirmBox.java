package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ConfirmBox {
    static boolean answer = false;

    /**
     * asks for confirmation if user tries to exit
     * @param title
     * @param message
     * @return
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> window.close());

        HBox confirmButtons = new HBox(10);
        confirmButtons.getChildren().addAll(yesButton, noButton);
        confirmButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, confirmButtons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 15, 15, 15));

        Scene scene = new Scene(layout);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
