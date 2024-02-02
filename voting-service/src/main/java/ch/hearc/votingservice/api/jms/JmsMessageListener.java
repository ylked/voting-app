package ch.hearc.votingservice.api.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

public interface JmsMessageListener {
    
    void listenRefusDemande(TextMessage jsonMessage) throws JMSException, JsonProcessingException;

    void listenAutorisation(TextMessage jsonMessage) throws JMSException, JsonProcessingException;
}
