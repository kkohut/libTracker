package logic;

public class Reading {
    private String bookTitle;
    private String dateStart;
    private String dateEnd;

    public Reading( String bookName, String dateStart) {
        this.bookTitle = bookTitle;
        this.dateStart = dateStart;
    }

    public Reading(String bookTitle, String dateStart, String dateEnd) {
        this.bookTitle = bookTitle;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookName) {
        this.bookTitle = bookTitle;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
