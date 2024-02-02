package ch.hearc.votingservice.api.web.models.response;

public class CreateDemandeResponseBody {

    private String messgae;

    public CreateDemandeResponseBody(String messgae) {
        this.messgae = messgae;
    }



    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}
