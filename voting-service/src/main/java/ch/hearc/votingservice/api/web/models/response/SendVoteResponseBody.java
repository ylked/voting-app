package ch.hearc.votingservice.api.web.models.response;

public class SendVoteResponseBody {

    private String messgae;

    public SendVoteResponseBody(String messgae) {
        this.messgae = messgae;
    }



    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}
