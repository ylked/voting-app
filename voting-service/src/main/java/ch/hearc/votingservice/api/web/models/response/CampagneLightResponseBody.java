package ch.hearc.votingservice.api.web.models.response;

import ch.hearc.votingservice.service.models.Campagne;
import ch.hearc.votingservice.shared.CampagneStatus;

public class CampagneLightResponseBody {

    private String nom;
    private String identifiant;
    private CampagneStatus status;

    public CampagneLightResponseBody(String nom, String identifiant, CampagneStatus status) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.status = status;
    }

    public static CampagneLightResponseBody fromCampagne(Campagne campagne){
        return new CampagneLightResponseBody(
                campagne.getNom(), campagne.getIdentifiant(),campagne.getStatus()
        );

    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public CampagneStatus getStatus() {
        return status;
    }

    public void setStatus(CampagneStatus status) {
        this.status = status;
    }
}
