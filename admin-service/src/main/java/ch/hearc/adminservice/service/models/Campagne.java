package ch.hearc.adminservice.service.models;

import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.shared.CampagneStatus;

import java.util.List;
import java.util.UUID;

public class Campagne {

    private String nom;

    private CampagneStatus status;
    private String identifiant;

    private List<Objet> objets;

    /**
     * Constructeur statique permettant de crééer une nouvelle campagne
     * @param nom le nom de la campagne
     * @return une nouvelle instance de Campagne
     */
    public static Campagne nouvelleCampagne(String nom){
        return new Campagne(nom);
    }

    public List<Objet> getObjets() {
        return objets;
    }

    public void setObjets(List<Objet> objets) {
        this.objets = objets;
    }

    /**
     * Mapping d'une entité JPA vers une entité métier
     * @param campagneEntity l'entité source
     * @return l'entité métier
     */
    public static Campagne mapFromEntity(CampagneEntity campagneEntity){
        return new Campagne(
                campagneEntity.getNom(),
                campagneEntity.getIdentifiant(),
                campagneEntity.getStatus(),
                campagneEntity.getObjets().stream().map(
                        Objet::mapFromEntityWithVotes)
                    .toList());
    }


    private Campagne(String nom){
        this.nom = nom;
        this.status = CampagneStatus.CREATED;
        this.identifiant = UUID.randomUUID().toString();
    }

    private Campagne(String nom, String identifiant, CampagneStatus status) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.status = status;
    }

    private Campagne(String nom, String identifiant, CampagneStatus status, List<Objet> objets) {
        this(nom,identifiant,status);
        this.objets = objets;
    }

    public CampagneEntity mapToEntity(){
        return new CampagneEntity(
                this.identifiant, this.nom, this.status);
    }


    public String getNom() {
        return nom;
    }

    public CampagneStatus getStatus() {
        return status;
    }

    public String getIdentifiant() {
        return identifiant;
    }
}
