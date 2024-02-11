package ch.hearc.adminservice.service.models.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateCampagneStatusResult {


    @JsonIgnore
    private Boolean isSuccess;

    private String message;

    private String campagneIdentifiant;

    public String getCampagneIdentifiant() {
        return campagneIdentifiant;
    }

    public static UpdateCampagneStatusResult actionOk(String identifiant, String message) {

        UpdateCampagneStatusResult result = new UpdateCampagneStatusResult();
        result.isSuccess = Boolean.TRUE;
        result.message = "Campagne successfully updated";
        result.campagneIdentifiant = identifiant;
        return result;
    }

    public static UpdateCampagneStatusResult actionKo(String identifiant,String message) {
        UpdateCampagneStatusResult result = new UpdateCampagneStatusResult();
        result.isSuccess = Boolean.FALSE;
        result.message = message;
        result.campagneIdentifiant = identifiant;
        return result;
    }

    @JsonIgnore
    public Boolean isActionOk(){
        return this.isSuccess;
    }



    public String getMessage() {
        return message;
    }
}
