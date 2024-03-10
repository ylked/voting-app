package ch.hearc.votingservice.remote.impl;

import ch.hearc.votingservice.remote.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TestClientImpl implements TestClient {

    @Autowired
    RestClient client;

    @Override
    public String getGooglePage() {
        final String URI = "http://google.ch";
        return client
                .get()
                .uri(URI)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}
