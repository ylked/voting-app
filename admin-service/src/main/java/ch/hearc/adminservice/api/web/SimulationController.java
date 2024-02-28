package ch.hearc.adminservice.api.web;

import ch.hearc.adminservice.repository.AutorisationRepository;
import ch.hearc.adminservice.repository.CampagneRespository;
import ch.hearc.adminservice.repository.entity.AutorisationEntity;
import ch.hearc.adminservice.repository.entity.CampagneEntity;
import ch.hearc.adminservice.repository.entity.ObjetEntity;
import ch.hearc.adminservice.service.CampagneService;
import ch.hearc.adminservice.service.VoteService;
import ch.hearc.adminservice.service.models.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping("simul")
public class SimulationController {

    Logger LOGGER = LoggerFactory.getLogger(SimulationController.class);

    @Autowired
    VoteService voteService;
    @Autowired
    CampagneService campagneService;
    @Autowired
    AutorisationRepository autorisationRepository;
    @Autowired
    CampagneRespository campagneRespository;

    @PostMapping("/bulk/votes")
    public ResponseEntity<?> generateVotesForCampagne(@RequestParam String campagneIdentifiant, @RequestParam Integer nbVotes){

        LOGGER.info("Generating vote for campagne id: " + campagneIdentifiant + ", " + nbVotes +" votes");

        Optional<CampagneEntity> optCampagneEntity = campagneRespository.findByIdentifiant(campagneIdentifiant);

        if(optCampagneEntity.isPresent()){
            int nbobjets = optCampagneEntity.get().getObjets().size();

            LOGGER.info("Starting iteration");

            //iteration pour générer les votes
            IntStream.range(0,nbVotes).forEach( it -> {
                LOGGER.info("Starting iteration: " + it);
                //autorisation
                AutorisationEntity aut = new AutorisationEntity();
                aut.setUsed(Boolean.FALSE);
                aut.setCampagneEntity(optCampagneEntity.get());
                aut.setIdentifiant(UUID.randomUUID().toString());
                aut.setAutorisationCode(UUID.randomUUID().toString());
                autorisationRepository.save(aut);

                //choix objet aléatoire
                Random random = new Random();
                int objId = new Random().nextInt(nbobjets);
                ObjetEntity obj = optCampagneEntity.get().getObjets().get(objId);

                Vote vote = Vote.nouveauVote(aut.getAutorisationCode(), campagneIdentifiant,obj.getIdentifiant());
                voteService.validateVote(vote);

                LOGGER.info("End iteration: " + it);
            });

            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }



    }
}
