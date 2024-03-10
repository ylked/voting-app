package ch.hearc.votingservice.service;

import ch.hearc.votingservice.service.models.TestJms;

public interface TestJmsService {
    void sendTestMessage(TestJms message);
}
