package ch.hearc.adminservice.service;

import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.actions.ReceptionnerDemandeResult;
import ch.hearc.adminservice.service.models.actions.ValidateDemandeResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface AutorisationService {
    List<Demande> getDemandes();
    Optional<Demande> getDemandeByIdentifiant(String identifiant);

    ValidateDemandeResult autoriseDemande(String identifiant) throws JsonProcessingException;

    ReceptionnerDemandeResult receptionnerDemande(Demande demande) throws JsonProcessingException;
}
