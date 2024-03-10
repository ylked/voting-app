package ch.hearc.votingservice.service.impl;

import ch.hearc.votingservice.jms.JmsProducteur;
import ch.hearc.votingservice.jms.models.TestMessage;
import ch.hearc.votingservice.service.TestJmsService;
import ch.hearc.votingservice.service.models.TestJms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestJmsServiceImpl implements TestJmsService {

    @Autowired
    JmsProducteur jmsProducteur;

    @Override
    public void sendTestMessage(TestJms message) {
        jmsProducteur.sendTestMessage(new TestMessage(message.getMessage(), message.getDate()));
    }
}
