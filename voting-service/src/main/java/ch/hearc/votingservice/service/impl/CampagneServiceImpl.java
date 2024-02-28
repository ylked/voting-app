package ch.hearc.votingservice.service.impl;

import ch.hearc.votingservice.remote.AdminRemoteServiceClient;
import ch.hearc.votingservice.remote.models.CampagneResponseBody;
import ch.hearc.votingservice.remote.models.ListCampagnesResponseBody;
import ch.hearc.votingservice.service.CampagneService;
import ch.hearc.votingservice.service.models.Campagne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampagneServiceImpl implements CampagneService {

    @Autowired
    AdminRemoteServiceClient adminRemoteServiceClient;

    @Override
    public List<Campagne> getCampagnesOuvertes() {

        ListCampagnesResponseBody campagneResponseBodies = adminRemoteServiceClient.getCampagnesOuvertes();

        return campagneResponseBodies.getCampagnes().stream().map(Campagne::fromCampagneResponsBody).toList();

    }
}
