package ch.hearc.adminservice.shared.utility;

import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.ObjetRepository;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.shared.CampagneStatus;
import ch.hearc.adminservice.repository.entity.ObjetEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile(value = "init-datas")
public class DataSeeder {
    @Autowired
    ObjetRepository objetRepository;
    @Autowired
    CampagneRespository campagneRespository;

    @PostConstruct
    public void initDatas(){

        CampagneEntity campagneEntity = new CampagneEntity();
        campagneEntity.setNom("Elections fédérales 2022");
        campagneEntity.setIdentifiant(UUID.randomUUID().toString());
        campagneEntity.setStatus(CampagneStatus.CREATED);
        campagneEntity = campagneRespository.save(campagneEntity);

        ObjetEntity objetEntity = new ObjetEntity();
        objetEntity.setNom("Elisabeth Baume Schneider");
        objetEntity.setIdentifiant(UUID.randomUUID().toString());
        objetEntity.setCampagneEntity(campagneEntity);
        objetRepository.save(objetEntity);

        objetEntity = new ObjetEntity();
        objetEntity.setNom("Eva Herzog");
        objetEntity.setIdentifiant(UUID.randomUUID().toString());
        objetEntity.setCampagneEntity(campagneEntity);
        objetRepository.save(objetEntity);

        campagneEntity = new CampagneEntity();
        campagneEntity.setNom("Votation Suisse sans armée");
        campagneEntity.setIdentifiant(UUID.randomUUID().toString());
        campagneEntity.setStatus(CampagneStatus.OPENED);
        campagneRespository.save(campagneEntity);

        objetEntity = new ObjetEntity();
        objetEntity.setNom("Oui");
        objetEntity.setIdentifiant(UUID.randomUUID().toString());
        objetEntity.setCampagneEntity(campagneEntity);
        objetRepository.save(objetEntity);

        objetEntity = new ObjetEntity();
        objetEntity.setNom("Non");
        objetEntity.setIdentifiant(UUID.randomUUID().toString());
        objetEntity.setCampagneEntity(campagneEntity);
        objetRepository.save(objetEntity);

        campagneEntity = new CampagneEntity();
        campagneEntity.setNom("Augmention du prix de la vignette");
        campagneEntity.setIdentifiant(UUID.randomUUID().toString());
        campagneEntity.setStatus(CampagneStatus.CREATED);
        campagneRespository.save(campagneEntity);


    }
}
