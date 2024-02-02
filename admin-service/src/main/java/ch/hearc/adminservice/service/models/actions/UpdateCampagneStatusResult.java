package ch.hearc.adminservice.service.models.actions;

import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;

public class UpdateCampagneStatusResult {

    private Campagne campagne;

    private Boolean isSuccess;

    private String message;

    public static UpdateCampagneStatusResult actionOk(Campagne campagne) {

        UpdateCampagneStatusResult result = new UpdateCampagneStatusResult();
        result.isSuccess = Boolean.TRUE;
        result.campagne = campagne;
        return result;
    }

    public static UpdateCampagneStatusResult actionKo(Campagne campagne, String message) {
        UpdateCampagneStatusResult result = new UpdateCampagneStatusResult();
        result.isSuccess = Boolean.FALSE;
        result.campagne = campagne;
        result.message = message;
        return result;
    }

    public Boolean isActionOk(){
        return this.isSuccess;
    }

    public Campagne getCampagne() {
        return campagne;
    }

    public String getMessage() {
        return message;
    }
}
