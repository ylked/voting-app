package ch.hearc.adminservice.service.impl;

import ch.hearc.adminservice.jms.JmsMessageProducteur;
import ch.hearc.adminservice.repository.AutorisationRepository;
import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.DemandeRepository;
import ch.hearc.adminservice.repository.entity.AutorisationEntity;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.repository.entity.DemandeEntity;
import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.models.Autorisation;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.RefusAutorisation;
import ch.hearc.adminservice.service.models.actions.ReceptionnerDemandeResult;
import ch.hearc.adminservice.service.models.actions.ValidateDemandeResult;
import ch.hearc.adminservice.shared.CampagneStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorisationServiceImpl implements AutorisationService {

    @Autowired
    DemandeRepository demandeRepository;
    @Autowired
    CampagneRespository campagneRepository;
    @Autowired
    AutorisationRepository autorisationRepository;
    @Autowired
    JmsMessageProducteur jmsMessageProducteur;

    @Override
    public List<Demande> getDemandes(){

        List<Demande> demandes = new ArrayList<>();

        demandeRepository.findAll().iterator().forEachRemaining(demande ->{
           demandes.add(new Demande(demande));
        });

        return demandes;
    }

    @Override
    public Optional<Demande> getDemandeByIdentifiant(String identifiant) {

        Optional<DemandeEntity> demandeEntity = demandeRepository.findByIdentifiant(identifiant);

        if(demandeEntity.isPresent()){
            Demande demande = new Demande(demandeEntity.get());
            return Optional.of(demande);
        }else{
            return Optional.empty();
        }

    }


    @Override
    public List<Autorisation> getAutorisations(){

        List<Autorisation> autorisations = new ArrayList<>();

        autorisationRepository.findAll().iterator().forEachRemaining(autorisationEntity -> {
            autorisations.add(Autorisation.mapFromEntity(autorisationEntity));
        });

        return autorisations;
    }

    @Override
    public ValidateDemandeResult autoriseDemande(String identifiant) throws JsonProcessingException {

        //Récupératoin de la demande stockée
        Optional<DemandeEntity> demandeEntity = demandeRepository.findByIdentifiant(identifiant);

        if(demandeEntity.isPresent()){

            //génération de l'autorisation
            Demande demande = new Demande(demandeEntity.get());
            Autorisation autorisation = Autorisation.validate(demande);
            //Récupération de la campagne
            CampagneEntity campagneEntity = campagneRepository.findByIdentifiant(demande.getCampagneId()).get();

            AutorisationEntity autorisationEntity = Autorisation.toEntity(autorisation);
            autorisationEntity.setCampagneEntity(campagneEntity);

            //Persistence de l'autorisation, suppression de la demande
            autorisationRepository.save(autorisationEntity);
            jmsMessageProducteur.sendAutorisation(Autorisation.toJmsMessage(autorisation));
            demandeRepository.delete(demandeEntity.get());
            return ValidateDemandeResult.ok("Autorisation generated", demande, autorisation);
        }else{
            return ValidateDemandeResult.ko("Demande with identifiant: " + identifiant + " doesn't exist");
        }


    }

    @Override
    public ReceptionnerDemandeResult receptionnerDemande(Demande demande) throws JsonProcessingException {

        //check si cammpagneId toujours ok (existant et openend)
        Optional<CampagneEntity> optionnalCampagne = campagneRepository.findByIdentifiant(demande.getCampagneId());

        if(optionnalCampagne.isPresent()){

            if(optionnalCampagne.get().getStatus().equals(CampagneStatus.OPENED)){
                demandeRepository.save(Demande.toDemandeEntity(demande));
                return ReceptionnerDemandeResult.ok(demande,"Demande successfully created");
            }else{
                //Refus automatique si demande pas ou plus dans etat OPENED
                RefusAutorisation refusAutorisation = new RefusAutorisation(optionnalCampagne.get().getIdentifiant(),demande.getIdentifiant(), "The campagne with the identifiant: " + optionnalCampagne.get().getIdentifiant() + " isn't in the status OPENED");
                jmsMessageProducteur.sendDeniedAutorisation(RefusAutorisation.toJmsMessage(refusAutorisation));
                return ReceptionnerDemandeResult.ko(demande,"The campagne doesnn't exist or is not opened");
            }


        }else{
            //Refus automatique si campagne n'existe pas
            RefusAutorisation refusAutorisation = new RefusAutorisation("",demande.getIdentifiant(),"The campagne with the identifiant: " + demande.getIdentifiant() + " doesnt exist" );
            jmsMessageProducteur.sendDeniedAutorisation( RefusAutorisation.toJmsMessage(refusAutorisation));
            return ReceptionnerDemandeResult.ko(demande,"The campagne doesnn't exist or is not opened");

        }
    }

    @Override
    public RefusAutorisation rejeteDemande(String identifiant, String campagneId) throws JsonProcessingException {
        RefusAutorisation refusAutorisation = new RefusAutorisation(campagneId,identifiant,"The demande  was rejected");

        DemandeEntity demandeEntity = demandeRepository.findByIdentifiant(identifiant).get();
        demandeRepository.delete(demandeEntity);
        jmsMessageProducteur.sendDeniedAutorisation(RefusAutorisation.toJmsMessage(refusAutorisation));
        return refusAutorisation;
    }


}
