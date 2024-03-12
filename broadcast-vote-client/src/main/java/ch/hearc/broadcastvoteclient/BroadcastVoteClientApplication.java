package ch.hearc.broadcastvoteclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
public class BroadcastVoteClientApplication {

    Logger logger = LoggerFactory.getLogger(BroadcastVoteClientApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(BroadcastVoteClientApplication.class, args);
    }

    @JmsListener(destination = "${spring.activemq.vote.broadcast.topic}")
    public void receive1(String message) {
        logger.info("Received message from topic: " + message);

    }


}
