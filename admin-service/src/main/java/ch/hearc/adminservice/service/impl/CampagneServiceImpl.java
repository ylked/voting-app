package ch.hearc.adminservice.service.impl;

import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.ObjetRepository;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.repository.entity.ObjetEntity;
import ch.hearc.adminservice.service.CampagneService;
import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.Objet;
import ch.hearc.adminservice.service.models.UpdateCampagneStatusAction;
import ch.hearc.adminservice.service.models.actions.CreateObjetForCampagneResult;
import ch.hearc.adminservice.service.models.actions.GetCampagneResultsAction;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusResult;
import ch.hearc.adminservice.shared.CampagneStatus;
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

        CampagneEntity campagneEntity = campagne.mapToEntity();

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
            Campagne campagne = Campagne.mapFromEntity(optionnalEntity.get());
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

        //TODO check campagnePresence
        switch (action){
            case OPEN -> {
                if(campagneEntity.getStatus().equals(CampagneStatus.CREATED)){

                    //une campagne a ouvrir doit avoir au minimum 2 objets
                    if (campagneEntity.getObjets().size() < 2){
                        return UpdateCampagneStatusResult.actionKo(campagneEntity.getIdentifiant(),"The campagne muss have at least 2 objets to be OPENED");
                    }

                    campagneEntity.setStatus(CampagneStatus.OPENED);
                    campagneEntity = campagneRespository.save(campagneEntity);
                    return UpdateCampagneStatusResult.actionOk(campagneEntity.getIdentifiant(),"Campagne successfully updates to status OPENED");
                }else{
                    return UpdateCampagneStatusResult.actionKo(campagneEntity.getIdentifiant(),"The campagne muss be in the status CREATED to be OPENED");
                }
            }

            case CLOSE -> {
                if(campagneEntity.getStatus().equals(CampagneStatus.OPENED)){
                    campagneEntity.setStatus(CampagneStatus.CLOSED);
                    campagneEntity = campagneRespository.save(campagneEntity);
                    return UpdateCampagneStatusResult.actionOk(campagneEntity.getIdentifiant(), "Campagne successfully updates to status CLOSED");
                }else{
                    return UpdateCampagneStatusResult.actionKo(campagneEntity.getIdentifiant(),"The campagne muss be in the status OPENED to be CLOSED");
                }
            }

            default -> throw new RuntimeException();
        }
    }

    @Override
    public GetCampagneResultsAction getResultForCampagne(String campagneIdentifiant) {

        Optional<CampagneEntity> optionalCampagneEntity = campagneRespository.findByIdentifiant(campagneIdentifiant);
        //check si existe
        if(optionalCampagneEntity.isPresent()){

            //check si ouverte ou closes
            CampagneEntity campagneEntity = optionalCampagneEntity.get();

            if(campagneEntity.getStatus().equals(CampagneStatus.CLOSED) ||
                    campagneEntity.getStatus().equals(CampagneStatus.OPENED)){

                Campagne campagne = Campagne.mapFromEntity(campagneEntity);

                return GetCampagneResultsAction.ok(campagne);
            }else{
                return GetCampagneResultsAction.campagneInStatusCreated(campagneIdentifiant);
            }
        }else{
            return GetCampagneResultsAction.campagneNotExist(campagneIdentifiant);
        }
    }
}
