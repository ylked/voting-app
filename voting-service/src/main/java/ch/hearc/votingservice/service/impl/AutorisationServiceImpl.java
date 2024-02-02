package ch.hearc.votingservice.service.impl;

import ch.hearc.votingservice.jms.models.DemandeMessage;
import ch.hearc.votingservice.jms.models.VoteMessage;
import ch.hearc.votingservice.remote.impl.Error400Exception;
import ch.hearc.votingservice.remote.models.CampagneResponseBody;
import ch.hearc.votingservice.remote.models.ObjetResponseBody;
import ch.hearc.votingservice.repository.DemandeRepository;
import ch.hearc.votingservice.repository.VotantRepository;
import ch.hearc.votingservice.repository.models.DemandeEntity;
import ch.hearc.votingservice.repository.models.VotantEntity;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.jms.JmsProducteur;
import ch.hearc.votingservice.service.models.Autorisation;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.remote.AdminRemoteServiceClient;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorisationServiceImpl implements AutorisationService {

    @Autowired
    JmsProducteur jmsProducteur;
    @Autowired
    DemandeRepository demandeRepository;
    @Autowired
    VotantRepository votantRepository;
    @Autowired
    AdminRemoteServiceClient adminRemoteServiceClient;



    @Override
    public CreateDemandeAutorisationResult createDemandeAutorisation(Demande demande) throws JsonProcessingException {

        if(Objects.isNull(demande)){
            return CreateDemandeAutorisationResult.actionKo(demande,"The demande cant'be null");
        }
        //check if campagne exist and opened
        Boolean campagneExist;
        try{
            campagneExist = adminRemoteServiceClient.isCampagneExistAndOuverte(demande.getCampagneId());

        }catch(Error400Exception ex){
            campagneExist = Boolean.FALSE;
        }

        if(!campagneExist){
            return CreateDemandeAutorisationResult.actionKo(demande,"The campagne with id specified doesn't exist or isn't in the state OPENED");
        }

        //persistence de l'entité
        DemandeEntity demandeEntity = Demande.toEntity(demande);
        demandeRepository.save(demandeEntity);
        //envoi du message
        DemandeMessage demandeMessage = Demande.toDemandeMessage(demande);
        jmsProducteur.sendDemande(demandeMessage);

        return CreateDemandeAutorisationResult.actionOk(demande);
    }


    /**
     * Validation de l'autorisation reçue
     * Une fois validée, la demande interne liée sera effacée, et une instance de Votant sera persisté
     * @param autorisationDto l'autorisation à traitée
     * @return le résultat de l'opération
     */
    @Override
    public ValidateAutorisationResult validateAutorisation(@Valid Autorisation autorisation){

        //Recherche de la demande liée
        Optional<DemandeEntity> demandeEntity = demandeRepository.findByIdentifiant(autorisation.getDemandeId());
        //Recherche de la campagne
        Optional<CampagneResponseBody> campagne = adminRemoteServiceClient.getCampagneByIdentifiant(autorisation.getCampagneId());

        if(demandeEntity.isPresent()){

            if(campagne.isPresent()){

                Demande demande = Demande.fromEntity(demandeEntity.get());

                //Création de l'objet votant
                VotantEntity votantEntity = new VotantEntity();
                votantEntity.setIdentifiant(UUID.randomUUID().toString());
                votantEntity.setNom(demande.getNom());
                votantEntity.setPrenom(demande.getPrenom());
                votantEntity.setAutorisationCode(autorisation.getAutorisationCode());
                votantEntity.setCampagneIdentifiant(campagne.get().getIdentifiant());
                votantRepository.save(votantEntity);

                //supression de l'objet demande
                demandeRepository.delete(demandeEntity.get());

                return ValidateAutorisationResult.ok(autorisation.getDemandeId(), autorisation.getCampagneId(), "Votant successfully created for campagne identifiant: " + campagne.get().getIdentifiant());

            }else{
                DemandeEntity demandeEntityOnError = demandeEntity.get();
                demandeEntityOnError.setOnError(Boolean.TRUE);
                demandeRepository.save(demandeEntityOnError);

                return ValidateAutorisationResult.ko(autorisation.getDemandeId(), autorisation.getCampagneId(), "Campagne with identifiant: " + autorisation.getCampagneId() + " doesn't exist or is not longer OPENED");
            }

        }else{
            DemandeEntity demandeEntityOnError = demandeEntity.get();
            demandeEntityOnError.setOnError(Boolean.TRUE);
            demandeRepository.save(demandeEntityOnError);

            return ValidateAutorisationResult.ko(autorisation.getDemandeId(), autorisation.getCampagneId(), "Demande with identifiant: " + autorisation.getDemandeId() + " doesn't exist");
        }
    }

    @Override
    public ValidationVoteResult validateVoteForCampagne(String campagneIdentifiant, String autorisationCode, String objetIdentifiant) {

        //Récupération du votant avec code d'autorisation et identifiant de campagne
        Optional<VotantEntity> votantEntity = votantRepository.findByAutorisationCodeAndCampagneIdentifiant(autorisationCode,campagneIdentifiant);

        if(votantEntity.isPresent()){
            //Validation de la campagne et de l'objet
            Optional<CampagneResponseBody> campagneOptional = adminRemoteServiceClient.getCampagneByIdentifiant(campagneIdentifiant);

            if(campagneOptional.isPresent()){

                CampagneResponseBody campagne = campagneOptional.get();

                Optional<ObjetResponseBody> objetToFind = campagne.getObjets().stream().filter(objet -> {
                    return objet.getIdentifiant().equals(objetIdentifiant);
                }).findFirst();

                if (objetToFind.isPresent()){



                    return ValidationVoteResult.ok(campagneIdentifiant,autorisationCode,objetIdentifiant,"Vote successfully validated");

                }else{
                    return ValidationVoteResult.ko(campagneIdentifiant,autorisationCode,objetIdentifiant,"No objet found with objetIdentifiant provided");
                }

            }else{
                return ValidationVoteResult.ko(campagneIdentifiant,autorisationCode,objetIdentifiant,"No campagne found with campagneIdentifiant provided");
            }

        }else{
            return ValidationVoteResult.ko(campagneIdentifiant,autorisationCode,objetIdentifiant,"No votant found with campagneIdentifiant and autorisationCode provided");
        }
    }

    @Override
    public SendVoteResult sendVoteForCampagne(String campagneIdentifiant, String autorisationCode, String objetIdentifiant) {

        //Récupération du votant avec code d'autorisation et identifiant de campagne
        Optional<VotantEntity> votantEntityOptionnal = votantRepository.findByAutorisationCodeAndCampagneIdentifiant(autorisationCode,campagneIdentifiant);

        if(votantEntityOptionnal.isPresent()){

            VotantEntity votantEntity = votantEntityOptionnal.get();
            votantEntity.setVoteDone(Boolean.TRUE);
            votantRepository.save(votantEntity);

            VoteMessage voteMessage = new VoteMessage(campagneIdentifiant,objetIdentifiant,autorisationCode);
            jmsProducteur.sendVote(voteMessage);
            return SendVoteResult.ok(campagneIdentifiant,autorisationCode,"Vote successfully submited");

        }else{
            return SendVoteResult.ko(campagneIdentifiant,autorisationCode,"No votant found with campagneIdentifiant and autorisationCode provided");
        }


    }


}
