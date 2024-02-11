package ch.hearc.adminservice.shared.utility;

import ch.hearc.adminservice.repository.*;
import ch.hearc.adminservice.repository.entity.AutorisationEntity;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.repository.entity.ObjetEntity;
import ch.hearc.adminservice.repository.entity.VoteEntity;
import ch.hearc.adminservice.shared.CampagneStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
@Profile(value = "init-datas")
public class DataSeeder {
    @Autowired
    ObjetRepository objetRepository;
    @Autowired
    CampagneRespository campagneRespository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    AutorisationRepository autorisationRepository;
    @Autowired
    DemandeRepository demandeRepository;

    @PostConstruct
    public void initDatas(){

        clearDatabase();

        generateCompleteCampagneOpen();

        generateCompleteCampagneClosed();

        generateEmptyCampagne();

    }

    private void clearDatabase(){

        demandeRepository.deleteAll();
        voteRepository.deleteAll();
        autorisationRepository.deleteAll();
        objetRepository.deleteAll();
        campagneRespository.deleteAll();


    }
    private  void generateEmptyCampagne() {
        //******** Campagne vide en status CREATED
        CampagneEntity campagneEntity = new CampagneEntity();
        campagneEntity.setNom("Elections fédérales 2025");
        String idCampagneCreated = UUID.randomUUID().toString();
        campagneEntity.setIdentifiant(idCampagneCreated);
        campagneEntity.setStatus(CampagneStatus.CREATED);
        campagneRespository.save(campagneEntity);
    }

    private void generateCompleteCampagneOpen() {
        //******** Campagne complète avec votes, autorisations et objets en status OPENED
        CampagneEntity campagneEntity = new CampagneEntity();
        campagneEntity.setNom("Elections fédérales 2022");
        String idCampagne = UUID.randomUUID().toString();
        campagneEntity.setIdentifiant(idCampagne);
        campagneEntity.setStatus(CampagneStatus.OPENED);

        ObjetEntity objetEntity1 = new ObjetEntity();
        objetEntity1.setNom("Elisabeth Baume Schneider");
        objetEntity1.setIdentifiant(UUID.randomUUID().toString());
        objetEntity1.setCampagneEntity(campagneEntity);

        ObjetEntity objetEntity2 = new ObjetEntity();
        objetEntity2.setNom("Eva Herzog");
        objetEntity2.setIdentifiant(UUID.randomUUID().toString());
        objetEntity2.setCampagneEntity(campagneEntity);

        campagneEntity.addObjet(objetEntity1);
        campagneEntity.addObjet(objetEntity2);
        campagneRespository.save(campagneEntity);

        AutorisationEntity autorisationEntity1 = new AutorisationEntity();
        autorisationEntity1.setCampagneEntity(campagneEntity);
        autorisationEntity1.setAutorisationCode(UUID.randomUUID().toString());
        autorisationEntity1.setUsed(Boolean.FALSE);
        autorisationRepository.save(autorisationEntity1);

        AutorisationEntity autorisationEntity2 = new AutorisationEntity();
        autorisationEntity2.setCampagneEntity(campagneEntity);
        autorisationEntity2.setAutorisationCode(UUID.randomUUID().toString());
        autorisationEntity2.setUsed(Boolean.FALSE);
        autorisationRepository.save(autorisationEntity2);

        VoteEntity voteEntity1 = new VoteEntity();
        voteEntity1.setAutorisationEntity(autorisationEntity1);
        voteEntity1.setObjetEntity(objetEntity1);
        voteEntity1.setIdentifiant(UUID.randomUUID().toString());
        voteRepository.save(voteEntity1);

        VoteEntity voteEntity2 = new VoteEntity();
        voteEntity2.setAutorisationEntity(autorisationEntity2);
        voteEntity2.setObjetEntity(objetEntity1);
        voteEntity2.setIdentifiant(UUID.randomUUID().toString());
        voteRepository.save(voteEntity2);

    }

    private void generateCompleteCampagneClosed() {
        //******** Campagne complète avec votes, autorisations et objets en status CLOSED
        System.out.println("***** Strating complete campagne generation *****");
        CampagneEntity campagneEntityClosed = new CampagneEntity();
        campagneEntityClosed.setNom("Pour une suisse sans armée");
        String idCampagneClosed = UUID.randomUUID().toString();
        campagneEntityClosed.setIdentifiant(idCampagneClosed);
        campagneEntityClosed.setStatus(CampagneStatus.CLOSED);

        ObjetEntity objetEntity1_1 = new ObjetEntity();
        objetEntity1_1.setNom("Oui");
        objetEntity1_1.setIdentifiant(UUID.randomUUID().toString());
        objetEntity1_1.setCampagneEntity(campagneEntityClosed);

        ObjetEntity objetEntity2_1 = new ObjetEntity();
        objetEntity2_1.setNom("Non");
        objetEntity2_1.setIdentifiant(UUID.randomUUID().toString());
        objetEntity2_1.setCampagneEntity(campagneEntityClosed);

        campagneEntityClosed.addObjet(objetEntity1_1);
        campagneEntityClosed.addObjet(objetEntity2_1);
        campagneRespository.save(campagneEntityClosed);


        //Génération aléatoire de autorisation/votes
        int nb = new Random().nextInt(5,100);

        System.out.println(nb + " votes iterations");
        IntStream.range(1,nb).forEach(iteration -> {

            System.out.println("it");
            ObjetEntity objetEntity;

            //choix aléatoire de l'objet
            if(new Random().nextInt(100) % 2 == 0){
                objetEntity = objetEntity2_1;
            }else{
                objetEntity = objetEntity1_1;
            }

            AutorisationEntity autorisationEntity1_1 = new AutorisationEntity();
            autorisationEntity1_1.setCampagneEntity(campagneEntityClosed);
            autorisationEntity1_1.setAutorisationCode(UUID.randomUUID().toString());
            autorisationEntity1_1.setUsed(Boolean.TRUE); //Directement true car vote lié
            autorisationRepository.save(autorisationEntity1_1);

            VoteEntity voteEntity1_1 = new VoteEntity();
            voteEntity1_1.setAutorisationEntity(autorisationEntity1_1);
            voteEntity1_1.setObjetEntity(objetEntity);
            voteEntity1_1.setIdentifiant(UUID.randomUUID().toString());
            voteRepository.save(voteEntity1_1);

        });
    }
}
