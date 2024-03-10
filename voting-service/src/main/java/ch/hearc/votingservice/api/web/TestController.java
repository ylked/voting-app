package ch.hearc.votingservice.api.web;

import ch.hearc.votingservice.service.TestJmsService;
import ch.hearc.votingservice.service.models.TestJms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jmsTest")
public class TestController {
    @Autowired
    private TestJmsService service;

    @PostMapping
    ResponseEntity<?> sendTestMessage(){
        service.sendTestMessage(new TestJms("It works..."));
        return ResponseEntity.ok().build();
    }
}
