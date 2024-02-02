package ch.hearc.adminservice.service.impl;

import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.ObjetRepository;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.shared.CampagneStatus;
import ch.hearc.adminservice.repository.entity.ObjetEntity;
import ch.hearc.adminservice.service.CampagneService;
import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;
import ch.hearc.adminservice.service.models.actions.CreateObjetForCampagneResult;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusResult;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CampagneServiceImpl implements CampagneService {

    @Autowired
    CampagneRespository campagneRespository;
    @Autowired
    ObjetRepository objetRepository;

    @Override
    public Campagne createCampagne(String nom) {

        //conversions objet metiers vers entity pour persistence
        Campagne campagne = Campagne.nouvelleCampagne(nom);
        CampagneEntity campagneEntity = Campagne.mapToEntity(campagne);

        campagneEntity = campagneRespository.save(campagneEntity);

        return Campagne.mapFromEntity(campagneEntity);

    }

    @Override
    public List<Campagne> getCampagnes(Optional<CampagneStatus> status) {

        List<Campagne> campagnes = new ArrayList<>();

        if(status.isPresent()){

            campagneRespository.findByStatus(status.get()).iterator().forEachRemaining(campagneEntity -> {
                campagnes.add(Campagne.mapFromEntity(campagneEntity));
            });

        }else{

            campagneRespository.findAll().iterator().forEachRemaining(campagneEntity -> {
                campagnes.add(Campagne.mapFromEntity(campagneEntity));
            });
        }


        return campagnes;
    }

    @Override
    public Optional<Campagne> getCampagneByIdentifiant(String identifiant) {

        Optional<CampagneEntity> optionnalEntity = campagneRespository.findByIdentifiant(identifiant);

        if(optionnalEntity.isPresent()){
            CampagneEntity campagneEntity = optionnalEntity.get();
            Campagne campagne = Campagne.mapFromEntity(campagneEntity);
            //Liste des objets
            List<Objet> objets = campagneEntity.getObjets().stream().map(Objet::mapFromEntity).toList();
            campagne.setObjets(objets);
            return Optional.of(campagne);
        }else{
            return Optional.empty();
        }

    }

    @Override
    public CreateObjetForCampagneResult createObjetForCampagne(Campagne campagne, String nom) {

        Objet objet = Objet.nouvelObjet(nom);
        ObjetEntity objetEntity = Objet.mapToEntity(objet);
        //on admet que l'identifiant déjé testé dans la couche web ets valide
        CampagneEntity campagneEntity = campagneRespository.findByIdentifiant(campagne.getIdentifiant()).get();


        if(campagneEntity.getStatus().equals(CampagneStatus.CREATED)){
            objetEntity.setCampagneEntity(campagneEntity);

            objetEntity = objetRepository.save(objetEntity);

            return CreateObjetForCampagneResult.actionOk(Objet.mapFromEntity(objetEntity));
        }else{
            return CreateObjetForCampagneResult.actionKo(Objet.mapFromEntity(objetEntity),"The campagne muss be in the status CREATED to add objet");
        }

    }

    @Override
    public Optional<Objet> getObjetByIdentifiant(String idObjet) {

        Optional<ObjetEntity> objetEntity = objetRepository.findByIdentifiant(idObjet);

        return objetEntity.map(Objet::mapFromEntity);
    }

    @Override
    public UpdateCampagneStatusResult updateCampganeStatus(Campagne campagne, UpdateCampagneStatusAction action) {

        CampagneEntity campagneEntity = campagneRespository.findByIdentifiant(campagne.getIdentifiant()).get();


        switch (action){
            case OPEN -> {
                if(campagneEntity.getStatus().equals(CampagneStatus.CREATED)){

                    //une campagne a ouvrir doit avoir au minimum 2 objets
                    if (campagneEntity.getObjets().size() < 2){
                        return UpdateCampagneStatusResult.actionKo(Campagne.mapFromEntity(campagneEntity),"The campagne muss have at least 2 objets to be OPENED");
                    }

                    campagneEntity.setStatus(CampagneStatus.OPENED);
                    campagneEntity = campagneRespository.save(campagneEntity);
                    return UpdateCampagneStatusResult.actionOk(Campagne.mapFromEntity(campagneEntity));
                }else{
                    return UpdateCampagneStatusResult.actionKo(Campagne.mapFromEntity(campagneEntity),"The campagne muss be in the status CREATED to be OPENED");
                }
            }

            case CLOSE -> {
                if(campagneEntity.getStatus().equals(CampagneStatus.OPENED)){
                    campagneEntity.setStatus(CampagneStatus.CLOSED);
                    campagneEntity = campagneRespository.save(campagneEntity);
                    return UpdateCampagneStatusResult.actionOk(Campagne.mapFromEntity(campagneEntity));
                }else{
                    return UpdateCampagneStatusResult.actionKo(Campagne.mapFromEntity(campagneEntity),"The campagne muss be in the status OPENED to be CLOSED");
                }
            }

            default -> throw new RuntimeException();
        }
    }
}
