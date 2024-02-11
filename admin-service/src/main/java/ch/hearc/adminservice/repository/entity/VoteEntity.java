package ch.hearc.adminservice.repository.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "vote")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifiant;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    @OneToOne
    private AutorisationEntity autorisationEntity;

    @ManyToOne
    @JoinColumn(name = "objet_id")
    private ObjetEntity objetEntity;

    public static VoteEntity newInstanceFrom(ObjetEntity objetEntity, AutorisationEntity autorisationEntity){
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setAutorisationEntity(autorisationEntity);
        voteEntity.setObjetEntity(objetEntity);
        voteEntity.setIdentifiant(UUID.randomUUID().toString());
        return voteEntity;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutorisationEntity getAutorisationEntity() {
        return autorisationEntity;
    }

    public void setAutorisationEntity(AutorisationEntity autorisationEntity) {
        this.autorisationEntity = autorisationEntity;
    }

    public ObjetEntity getObjetEntity() {
        return objetEntity;
    }

    public void setObjetEntity(ObjetEntity objetEntity) {
        this.objetEntity = objetEntity;
    }
}
