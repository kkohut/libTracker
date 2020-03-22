package logic;

public class Session {
    private String bookTitle;
    private String date;
    private String duration;

    public Session(String bookTitle, String date, String duration) {
        this.bookTitle = bookTitle;
        this.date = date;
        this.duration = duration;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookName(String bookName) {
        this.bookTitle = bookName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
