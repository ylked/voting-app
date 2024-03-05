package ch.hearc.votingservice.service;

import ch.hearc.votingservice.service.models.Autorisation;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.service.models.Vote;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import ch.hearc.votingservice.shared.DemandeStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface AutorisationService {
    CreateDemandeAutorisationResult createDemandeAutorisation(Demande demande) throws JsonProcessingException;

    ValidateAutorisationResult validateAutorisation(Autorisation autorisation);

    ValidationVoteResult validateVoteForCampagne(Vote vote);

    SendVoteResult sendVoteForCampagne(Vote vote);

    Optional<Demande> getDemandeByIdentifiant(String identifiant);

    List<Demande> findAllDemandes(Optional<DemandeStatus> demandeStatus);



    Boolean rejectDemandeAutorisation(String demamndeId, String reasonMessage);
}
