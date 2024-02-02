package ch.hearc.votingservice.service.impl;

import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AutorisationServiceImplTest {

    @Autowired
    AutorisationService autorisationService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testNullParamter() {

        Demande demande = null;
        try {
            CreateDemandeAutorisationResult result = autorisationService.createDemandeAutorisation(demande);
            assertFalse(result.isActionOk());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void validateAutorisation() {
    }

    @Test
    void validateVoteForCampagne() {
    }

    @Test
    void sendVoteForCampagne() {
    }
}