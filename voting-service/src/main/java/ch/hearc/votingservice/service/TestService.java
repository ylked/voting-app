package ch.hearc.votingservice.service;

import ch.hearc.votingservice.service.models.TestJms;

public interface TestService {
    void sendTestMessage(TestJms message);

    String sendTestRestRequest();
}
