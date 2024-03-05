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
import ch.hearc.votingservice.service.models.Vote;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import ch.hearc.votingservice.shared.DemandeStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    Logger logger = LoggerFactory.getLogger(AutorisationServiceImpl.class);



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
     * @param autorisation l'autorisation à traitée
     * @return le résultat de l'opération
     */
    @Override
    public ValidateAutorisationResult validateAutorisation(@Valid Autorisation autorisation){

        //Recherche de la demande liée
        Optional<DemandeEntity> optionalDemandeEntity = demandeRepository.findByIdentifiant(autorisation.getDemandeId());
        //Recherche de la campagne
        Optional<CampagneResponseBody> campagne = adminRemoteServiceClient.getCampagneByIdentifiant(autorisation.getCampagneId());

        if(optionalDemandeEntity.isPresent()){

            if(campagne.isPresent()){

                Demande demande = Demande.fromEntity(optionalDemandeEntity.get());

                //Création de l'objet votant
                VotantEntity votantEntity = new VotantEntity();
                votantEntity.setIdentifiant(UUID.randomUUID().toString());
                votantEntity.setNom(demande.getNom());
                votantEntity.setPrenom(demande.getPrenom());
                votantEntity.setAutorisationCode(autorisation.getAutorisationCode());
                votantEntity.setCampagneIdentifiant(campagne.get().getIdentifiant());
                votantRepository.save(votantEntity);

                //maj de l'objet demande
                DemandeEntity demandeEntity = optionalDemandeEntity.get();
                demandeEntity.setStatus(DemandeStatus.VALIDATED);
                demandeRepository.save(demandeEntity);

                return ValidateAutorisationResult.ok(autorisation.getDemandeId(), autorisation.getCampagneId(), "Votant successfully created for campagne identifiant: " + campagne.get().getIdentifiant());

            }else{
                DemandeEntity demandeEntityOnError = optionalDemandeEntity.get();
                demandeEntityOnError.setStatus(DemandeStatus.REJECTED);
                demandeRepository.save(demandeEntityOnError);

                return ValidateAutorisationResult.ko(autorisation.getDemandeId(), autorisation.getCampagneId(), "Campagne with identifiant: " + autorisation.getCampagneId() + " doesn't exist or is not longer OPENED");
            }

        }else{

            return ValidateAutorisationResult.ko(autorisation.getDemandeId(), autorisation.getCampagneId(), "Demande with identifiant: " + autorisation.getDemandeId() + " doesn't exist");
        }
    }

    /**
     * Validation d'un vote avant soumission
     * @param vote l'instance du vote
     * @return le résultat de l'opération
     */
    @Override
    public ValidationVoteResult validateVoteForCampagne(Vote vote) {

        //Récupération du votant avec code d'autorisation et identifiant de campagne
        Optional<VotantEntity> votantEntity = votantRepository.findByAutorisationCodeAndCampagneIdentifiant(vote.getAutorisationCode(),vote.getCampagneIdentifiant());

        if(votantEntity.isPresent()){
            //Validation de la campagne et de l'objet
            Optional<CampagneResponseBody> campagneOptional = adminRemoteServiceClient.getCampagneByIdentifiant(vote.getCampagneIdentifiant());

            if(campagneOptional.isPresent()){

                CampagneResponseBody campagne = campagneOptional.get();

                Optional<ObjetResponseBody> objetToFind = campagne.getObjets().stream().filter(objet -> objet.getIdentifiant().equals(vote.getObjetIdentifiant())).findFirst();

                if (objetToFind.isPresent()){

                    return ValidationVoteResult.ok(vote,"Vote successfully validated");

                }else{
                    return ValidationVoteResult.ko(vote,"No objet found with objetIdentifiant provided");
                }

            }else{
                return ValidationVoteResult.ko(vote,"No campagne found with campagneIdentifiant provided");
            }

        }else{
            return ValidationVoteResult.ko(vote,"No votant found with campagneIdentifiant and autorisationCode provided");
        }
    }

    @Override
    public SendVoteResult sendVoteForCampagne(Vote vote) {

        //Récupération du votant avec code d'autorisation et identifiant de campagne
        Optional<VotantEntity> votantEntityOptionnal = votantRepository.findByAutorisationCodeAndCampagneIdentifiant(vote.getAutorisationCode(), vote.getCampagneIdentifiant());

        if(votantEntityOptionnal.isPresent()){

            VotantEntity votantEntity = votantEntityOptionnal.get();
            votantEntity.setVoteDone(Boolean.TRUE);
            votantRepository.save(votantEntity);

            VoteMessage voteMessage = new VoteMessage(vote.getCampagneIdentifiant(), vote.getObjetIdentifiant(), vote.getAutorisationCode());
            jmsProducteur.sendVote(voteMessage);
            return SendVoteResult.ok(vote.getCampagneIdentifiant(),vote.getAutorisationCode(),"Vote successfully submited");

        }else{
            return SendVoteResult.ko(vote.getCampagneIdentifiant(),vote.getAutorisationCode(),"No votant found with campagneIdentifiant and autorisationCode provided");
        }


    }

    @Override
    public Optional<Demande> getDemandeByIdentifiant(String identifiant) {

        Optional<DemandeEntity> demandeEntity = demandeRepository.findByIdentifiant(identifiant);

        return demandeEntity.map(Demande::fromEntity);
    }

    @Override
    public List<Demande> findAllDemandes(Optional<DemandeStatus> demandeStatus) {
        List<Demande> demandes = new ArrayList<>();

        if(demandeStatus.isPresent()){

            demandeRepository.findByStatus(demandeStatus.get()).iterator().forEachRemaining(campagneEntity -> {
                demandes.add(Demande.mapFromEntity(campagneEntity));
            });

        }else{

            demandeRepository.findAll().iterator().forEachRemaining(demandeEntity -> {
                demandes.add(Demande.mapFromEntity(demandeEntity));
            });
        }

        return demandes;

    }

    @Override
    public Boolean rejectDemandeAutorisation(String demamndeId, String reasonMessage) {

        Optional<Demande> optionalDemande = getDemandeByIdentifiant(demamndeId);

        DemandeEntity demandeEntity = demandeRepository.findByIdentifiant(optionalDemande.get().getIdentifiant()).get();
        demandeEntity.setStatus(DemandeStatus.REJECTED);
        demandeEntity.setRejectedReason(reasonMessage);

        demandeRepository.save(demandeEntity);

        return Boolean.TRUE;
    }


}
