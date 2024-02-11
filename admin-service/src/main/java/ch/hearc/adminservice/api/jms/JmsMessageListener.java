package ch.hearc.adminservice.api.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

public interface JmsMessageListener {

    void listenDemandes(TextMessage jsonMessage) throws JMSException, JsonProcessingException;


    void listenVotes(TextMessage jsonMessage) throws JMSException, JsonProcessingException;
}
