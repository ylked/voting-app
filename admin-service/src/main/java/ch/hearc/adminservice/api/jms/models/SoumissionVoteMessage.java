package ch.hearc.adminservice.api.jms.models;

import ch.hearc.adminservice.service.models.Vote;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SoumissionVoteMessage {

    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String campagneIdentifiant;
    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String objetIdentifiant;
    @NotEmpty
    @NotNull
    @Size(min = 3)
    private String autorisationCode;

    public static Vote toVote(SoumissionVoteMessage soumissionVoteMessage) {
        return Vote.nouveauVote(soumissionVoteMessage.autorisationCode, soumissionVoteMessage.campagneIdentifiant, soumissionVoteMessage.objetIdentifiant);
    }

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public String getObjetIdentifiant() {
        return objetIdentifiant;
    }

    public String getAutorisationCode() {
        return autorisationCode;
    }
}
