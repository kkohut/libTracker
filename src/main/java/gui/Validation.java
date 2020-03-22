package gui;

import javafx.scene.control.TextField;

import java.time.LocalDate;

public class Validation {
    public static boolean validateBookData(TextField titleInput, String title, TextField authorInput, String author, TextField pageNumberInput, String pageNumber) {
        try {
            Integer.parseInt(pageNumber);
        } catch(NumberFormatException e) {
            return false;
        }
        if(title.equals("") || author.equals("") || pageNumber.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean validateSessionData(LocalDate date, String duration) {
        try {
            date.toString();
        } catch(NullPointerException e) {
            return false;
        }
        if(duration.equals("")) {
            return false;
        }
        return true;
    }
}
