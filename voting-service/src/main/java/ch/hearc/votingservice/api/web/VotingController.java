package ch.hearc.votingservice.api.web;

import ch.hearc.votingservice.api.web.models.request.CreateVoteRequestBody;
import ch.hearc.votingservice.api.web.models.response.CampagneResponseBody;
import ch.hearc.votingservice.api.web.models.response.SendVoteResponseBody;
import ch.hearc.votingservice.service.AutorisationService;
import ch.hearc.votingservice.service.CampagneService;
import ch.hearc.votingservice.service.models.Vote;
import ch.hearc.votingservice.service.models.actions.SendVoteResult;
import ch.hearc.votingservice.service.models.actions.ValidationVoteResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Votes", description = "Votes API")
@RestController
public class VotingController extends AbstractValidationHandler{


    @Autowired
    AutorisationService autorisationService;
    @Autowired
    CampagneService campagneService;


    /**
     * Soumission d'un vote
     * @param createVoteRequestBody
     * @return
     */
    @Operation(
            summary = "Soumission d'un vote",
            description = "Permet de soummettre un vote pour une campagne donnée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, vote soumis avec succès")
    })
    @PostMapping("/campagne/vote")
    public ResponseEntity<SendVoteResponseBody> votePourCampagne(@RequestBody @Valid CreateVoteRequestBody createVoteRequestBody){

        Vote vote = CreateVoteRequestBody.toVote(createVoteRequestBody);
        //recherche du votant avec le code d'autorisation et l'identifiant de campagne
        ValidationVoteResult validationVoteResult = autorisationService.validateVoteForCampagne(vote);

        //Si campagne et code ok
        if(validationVoteResult.isValidationOk()){

            //Resultat de l'opération
            SendVoteResult sendVoteResult = autorisationService.sendVoteForCampagne(vote);

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
    public List<CampagneResponseBody> getCampagneOuvertes(){

        return campagneService.getCampagnesOuvertes()
                .stream()
                .map(CampagneResponseBody::fromCampagne)
                .toList();
    }


}
