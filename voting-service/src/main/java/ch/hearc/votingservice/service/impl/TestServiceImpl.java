package ch.hearc.votingservice.service.impl;

import ch.hearc.votingservice.jms.JmsProducteur;
import ch.hearc.votingservice.jms.models.TestMessage;
import ch.hearc.votingservice.remote.TestClient;
import ch.hearc.votingservice.service.TestService;
import ch.hearc.votingservice.service.models.TestJms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    JmsProducteur jmsProducteur;

    @Autowired
    TestClient client;

    Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public void sendTestMessage(TestJms message) {
        jmsProducteur.sendTestMessage(new TestMessage(message.getMessage(), message.getDate()));
    }

    @Override
    public void sendTestRestRequest() {
        String body = client.getGooglePage();

        logger.info("Retrieved google page : \n" + body);
    }
}
