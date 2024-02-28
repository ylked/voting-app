package ch.hearc.votingservice.api.web.models.response;

public class SendVoteResponseBody {

    private String message;

    public SendVoteResponseBody(String message) {
        this.message = message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
