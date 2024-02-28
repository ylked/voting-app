package ch.hearc.votingservice.remote.impl;

import ch.hearc.votingservice.remote.AdminRemoteServiceClient;
import ch.hearc.votingservice.remote.models.CampagneResponseBody;
import ch.hearc.votingservice.remote.models.ListCampagnesResponseBody;
import ch.hearc.votingservice.shared.CampagneStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
public class AdminRemoteServiceClientImpl implements AdminRemoteServiceClient {

    @Autowired
    RestClient adminServiceClient;



    @Override
    public ListCampagnesResponseBody getCampagnesOuvertes() {
        ListCampagnesResponseBody campagnes = adminServiceClient.get()
                .uri("/campagne?status=OPENED")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return campagnes;
    }

    @Override
    public Boolean isCampagneExistAndOuverte(String campagneIdentifiant) {

        ResponseEntity<CampagneResponseBody> campagneResponse = adminServiceClient.get()
                .uri("/campagne/" + campagneIdentifiant)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new Error400Exception();
                })
                .toEntity(CampagneResponseBody.class);

        return campagneResponse.getBody().getStatus().equals(CampagneStatus.OPENED);

    }

    @Override
    public Optional<CampagneResponseBody> getCampagneByIdentifiant(String identifiant){

        ResponseEntity<CampagneResponseBody> campagneResponse = adminServiceClient.get()
                .uri("/campagne/" + identifiant)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new Error400Exception();
                })
                .toEntity(CampagneResponseBody.class);

        return Optional.of(campagneResponse.getBody());

    }
}
