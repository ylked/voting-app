package ch.hearc.votingservice.api.web;

import ch.hearc.votingservice.api.web.models.request.CreateVoteRequestBody;
import ch.hearc.votingservice.api.web.models.request.CreateDemandeRequestBody;
import ch.hearc.votingservice.api.web.models.response.CampagneLightResponseBody;
import ch.hearc.votingservice.api.web.models.response.SendVoteResponseBody;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.CampagneService;
import ch.hearc.votingservice.service.models.Demande;
import ch.hearc.votingservice.service.models.Campagne;
import ch.hearc.votingservice.service.models.actions.CreateDemandeAutorisationResult;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VotingController {



    @Autowired
    AutorisationService autorisationService;
    @Autowired
    CampagneService campagneService;


    /**
     * Soumission d'un vote
     * @param createVoteRequestBody
     * @return
     */
    @PostMapping("/campagne/vote")
    public ResponseEntity<SendVoteResponseBody> votePourCampagne(@RequestBody @Valid CreateVoteRequestBody createVoteRequestBody){

        //recherche du votant avec le code d'autorisation et l'identifiant de campagne
        ValidationVoteResult validationVoteResult = autorisationService.validateVoteForCampagne(createVoteRequestBody.getCampagneIdentifiant(), createVoteRequestBody.getAutorisationCode(),createVoteRequestBody.getObjetIdentifiant());

        //Si campagne et code ok
        if(validationVoteResult.isValidationOk()){

            //Resultat de l'opération
            SendVoteResult sendVoteResult = autorisationService.sendVoteForCampagne(createVoteRequestBody.getCampagneIdentifiant(), createVoteRequestBody.getAutorisationCode(),createVoteRequestBody.getObjetIdentifiant());

            if(sendVoteResult.isSuccess()){

                return ResponseEntity.ok(new SendVoteResponseBody("Vote successfully submited"));
            }else{
                return ResponseEntity.badRequest().body(new SendVoteResponseBody(sendVoteResult.getMessage()));
            }

        }else{
            return ResponseEntity.badRequest().body(new SendVoteResponseBody(validationVoteResult.getMessage()));
        }

    }

    /**
     * Retounre la liste des campagnes en état OPENED (c.à.d les campagnes ouvertes aux votes)
     * @return l aliste des campagnes ouvertes
     */
    @GetMapping("/campagnes")
    public List<CampagneLightResponseBody> getCampagneOuvertes(){

        return campagneService.getCampagnesOuvertes()
                .stream()
                .map(CampagneLightResponseBody::fromCampagne)
                .toList();
    }

    /**
     * Créer une demande d'autorisation de vote pour une campagne
     * @return une autorisation de vote
     */
    @PostMapping("/demandes")
    public ResponseEntity<?> demandeAutorisationPourCampagne(@RequestBody @Valid CreateDemandeRequestBody demandeBody){

        //Converion du body en demande métier
        Demande demande = CreateDemandeRequestBody.toDemande(demandeBody);

        try {
            CreateDemandeAutorisationResult result = autorisationService.createDemandeAutorisation(demande);

            if(result.isActionOk()){
                return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(result.getDemande().getIdentifiant()).toUri()).build();
            }else{
                return ResponseEntity.badRequest().body(result);
            }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode gérant les exceptions de validation issues de l'annotation @Valid
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
