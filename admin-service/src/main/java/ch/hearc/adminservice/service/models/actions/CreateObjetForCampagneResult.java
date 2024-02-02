package ch.hearc.adminservice.service.models.actions;

import ch.hearc.adminservice.service.models.Objet;

public class CreateObjetForCampagneResult {

    private Objet objet;

    private Boolean isSuccess;

    private String message;

    public static CreateObjetForCampagneResult actionOk(Objet objet) {

        CreateObjetForCampagneResult result = new CreateObjetForCampagneResult();
        result.isSuccess = Boolean.TRUE;
        result.objet = objet;
        return result;
    }

    public static CreateObjetForCampagneResult actionKo(Objet objet, String message) {
        CreateObjetForCampagneResult result = new CreateObjetForCampagneResult();
        result.isSuccess = Boolean.FALSE;
        result.objet = objet;
        result.message = message;
        return result;
    }

    public Objet getObjet() {
        return objet;
    }

    public Boolean isActionOk(){
        return this.isSuccess;
    }


    public String getMessage() {
        return message;
    }
}
