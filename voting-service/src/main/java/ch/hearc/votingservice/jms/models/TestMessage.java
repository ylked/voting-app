package ch.hearc.votingservice.jms.models;

public class TestMessage {
    private String message;
    private String date;

    public TestMessage(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
