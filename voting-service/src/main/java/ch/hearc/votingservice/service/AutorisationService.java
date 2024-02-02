package ch.hearc.votingservice.service;

import ch.hearc.votingservice.service.models.Autorisation;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidateAutorisationResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AutorisationService {
    CreateDemandeAutorisationResult createDemandeAutorisation(Demande demande) throws JsonProcessingException;

    ValidateAutorisationResult validateAutorisation(Autorisation autorisation);

    ValidationVoteResult validateVoteForCampagne(String campagneIdentifiant, String autorisationCode, String objetIdentifiant);

    SendVoteResult sendVoteForCampagne(String campagneIdentifiant, String autorisationCode, String objetIdentifiant);
}
