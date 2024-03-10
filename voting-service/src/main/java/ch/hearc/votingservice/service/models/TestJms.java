package ch.hearc.votingservice.service.models;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TestJms {
    private String message;
    private String date;

    public TestJms(String message) {
        this.message = message;

        // source : GitHub Copilot Chat
        this.date =  ZonedDateTime.now().format(
                DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        );
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
