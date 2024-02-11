package ch.hearc.adminservice.api.web.models.response;

import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.shared.CampagneStatus;

import java.util.ArrayList;
import java.util.List;
/**
 * DTO pour la réponse de l'API Rest qui retourne les résultats de la campagne
 */
public class CampagneResultsResponseBody {

    private CampagneResultBody campagne;
    private List<ObjetResultBody> objets = new ArrayList<>();

    public CampagneResultBody getCampagne() {
        return campagne;
    }

    public List<ObjetResultBody> getObjets() {
        return objets;
    }



    public  CampagneResultsResponseBody (Campagne campagne){


        objets = campagne.getObjets().stream().map(objet -> {
            return new ObjetResultBody(objet.getIdentifiant(),objet.getNom(),objet.getVotes());
        }).toList();

        Integer totalVotes = objets.stream().map(ObjetResultBody::getVotes)
                .reduce(0, Integer::sum);

        this.campagne = new CampagneResultBody(campagne.getIdentifiant(), campagne.getNom(),campagne.getStatus(),totalVotes);


    }
    class ObjetResultBody{

        private String identifiant;
        private String nom;

        private Integer votes;

        public String getIdentifiant() {
            return identifiant;
        }

        public String getNom() {
            return nom;
        }

        public Integer getVotes() {
            return votes;
        }

        public ObjetResultBody(String identifiant, String nom, Integer votes) {
            this.identifiant = identifiant;
            this.nom = nom;
            this.votes = votes;
        }
    }

    class CampagneResultBody {
        private String identifiant;
        private String nom;

        private Integer totalVotes;

        private CampagneStatus status;

        public CampagneStatus getStatus() {
            return status;
        }

        public Integer getTotalVotes() {
            return totalVotes;
        }

        public String getIdentifiant() {
            return identifiant;
        }

        public String getNom() {
            return nom;
        }

        public CampagneResultBody(String identifiant, String nom, CampagneStatus status, Integer totalVotes) {
            this.identifiant = identifiant;
            this.nom = nom;
            this.totalVotes = totalVotes;
            this.status = status;
        }
    }
}
