package ch.hearc.votingservice.api.web.models.response;

import ch.hearc.votingservice.service.models.Campagne;
import ch.hearc.votingservice.shared.CampagneStatus;

import java.util.ArrayList;
import java.util.List;

public class CampagneResponseBody {

    private String nom;
    private String identifiant;
    private CampagneStatus status;

    public List<ObjetBody> getObjets() {
        return objets;
    }

    List<ObjetBody> objets = new ArrayList<>();

    public CampagneResponseBody(String nom, String identifiant, CampagneStatus status, List<ObjetBody> objets) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.status = status;
        this.objets = objets;
    }

    public static CampagneResponseBody fromCampagne(Campagne campagne){


        return new CampagneResponseBody(
                campagne.getNom(),
                campagne.getIdentifiant(),
                campagne.getStatus(),
                campagne.getObjets().stream().map(objet -> {
                    return new ObjetBody(objet.getNom(), objet.getIdentifiant());
                }).toList()
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
class ObjetBody{

    private String nom;

    private String identifiant;

    public ObjetBody(String nom, String identifiant) {
        this.nom = nom;
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }
}
