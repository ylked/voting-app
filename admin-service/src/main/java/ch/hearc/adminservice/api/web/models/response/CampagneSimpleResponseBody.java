package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.shared.CampagneStatus;
import ch.hearc.adminservice.service.models.Campagne;

/**
 * DTO pour le body servant à la liste des capagnes. Sans les liens avec les objets
 * Attention: les getters sont indispensable pour la sérialisation
 */
public class CampagneSimpleResponseBody {


    private String nom;
    private CampagneStatus status;
    private String identifiant;

    public String getNom() {
        return nom;
    }

    public CampagneStatus getStatus() {
        return status;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    private CampagneSimpleResponseBody(String nom, String identifiant, CampagneStatus status) {
        this.nom =nom;
        this.identifiant = identifiant;
        this.status = status;
    }


    public static CampagneSimpleResponseBody mapFromCampagne(Campagne campagne) {

        return new CampagneSimpleResponseBody(campagne.getNom(),campagne.getIdentifiant(),campagne.getStatus());
    }
}
