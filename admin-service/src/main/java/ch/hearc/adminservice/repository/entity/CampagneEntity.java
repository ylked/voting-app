package ch.hearc.adminservice.repository.entity;

import ch.hearc.adminservice.shared.CampagneStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "campagne")
public class CampagneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String identifiant;

    public List<ObjetEntity> getObjets() {
        return objets;
    }

    public void setObjets(List<ObjetEntity> objets) {
        this.objets = objets;
    }

    @OneToMany(mappedBy = "campagneEntity")
    private List<ObjetEntity> objets;

    public CampagneEntity() {}

    public CampagneStatus getStatus() {
        return status;
    }

    public void setStatus(CampagneStatus status) {
        this.status = status;
    }

    private CampagneStatus status;

    public CampagneEntity(String identifiant, String nom, CampagneStatus status) {
        this.identifiant = identifiant;
        this.status = CampagneStatus.CREATED;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
