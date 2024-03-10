package ch.hearc.votingservice.api.web;

import ch.hearc.votingservice.service.TestService;
import ch.hearc.votingservice.service.models.TestJms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService service;

    @PostMapping("testJms")
    ResponseEntity<?> sendTestMessage(){
        service.sendTestMessage(new TestJms("It works..."));
        return ResponseEntity.ok().build();
    }

    @PostMapping("restTest")
    ResponseEntity<?> sendTestRest(){
        service.sendTestRestRequest();
        return ResponseEntity.ok().build();
    }

}
