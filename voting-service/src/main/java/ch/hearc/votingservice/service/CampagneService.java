package ch.hearc.votingservice.service;

import ch.hearc.votingservice.service.models.Campagne;

import java.util.List;

public interface CampagneService {
    List<Campagne> getCampagnesOuvertes();
}
