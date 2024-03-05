package ch.hearc.adminservice.api.web;

import ch.hearc.adminservice.api.web.models.response.*;
import ch.hearc.adminservice.api.web.models.response.DemandeResponseBody;
import ch.hearc.adminservice.api.web.models.response.ListAutorisationResponseBody;
import ch.hearc.adminservice.service.AutorisationService;
import ch.hearc.adminservice.service.models.Autorisation;
import ch.hearc.adminservice.service.models.Demande;
import ch.hearc.adminservice.service.models.RefusAutorisation;
import ch.hearc.adminservice.service.models.UpdateDemandeStatusAction;
import ch.hearc.adminservice.service.models.actions.ValidateDemandeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Autorisation", description = "Autorisation API")
@RestController
@RequestMapping("autorisation")
public class AutorisationController {

    @Autowired
    AutorisationService autorisationService;

    @GetMapping()
    @Operation(
            summary = "Récupération des autorisations",
            description = "Récupére les autorisations en cours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    public ResponseEntity<ListAutorisationResponseBody> getAutorisations(){

        List<Autorisation> autorisations = autorisationService.getAutorisations();

        ListAutorisationResponseBody listAutorisationResponseBody =
                ListAutorisationResponseBody.mapFromListAutorisation(autorisations);

        return ResponseEntity.ok((ListAutorisationResponseBody.mapFromListAutorisation(autorisations)));
    }

    @GetMapping( value = "/demandes")
    @Operation(
            summary = "Récupération des demandes d'autorisations",
            description = "Récupére les demandes d'autorisations en cours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    public ResponseEntity<List<DemandeResponseBody>> getDemandes(){

        return ResponseEntity.ok(autorisationService.getDemandes()
                .stream()
                .map(DemandeResponseBody::mapFromDemande).toList());
    }

    @PostMapping(value = "/demandes/{id}")
    @Operation(
            summary = "Autorisation/Rejet d'une demande en cours",
            description = "Autoriser ou rejeter une demande et la convertit en autorisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demande traitée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur durant le traitement"),
    })
    public ResponseEntity<?> autoriseDemandeOrRejectDemande(@PathVariable String id, @RequestParam(value = "action",required = true) UpdateDemandeStatusAction action) throws JsonProcessingException {

        if(action.equals(UpdateDemandeStatusAction.REJECTED)){

            Optional<Demande> demande = autorisationService.getDemandeByIdentifiant(id);

            if(demande.isPresent()){
                autorisationService.rejeteDemande(id, demande.get().getCampagneId());
                return ResponseEntity.ok(new RejectedDemandeResponseBody(id));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(id));
            }


        }else {
            ValidateDemandeResult actionResult = autorisationService.autoriseDemande(id);

            if(actionResult.isSuccess()){
                AutorisationDemandeResponseBody responseBody = new AutorisationDemandeResponseBody(
                        actionResult.getAutorisation().getAutorisationCode(),
                        actionResult.getAutorisation().getCampagneId());

                return ResponseEntity.ok(responseBody);

            }else{
                return ResponseEntity.badRequest().body(actionResult);
            }
        }



    }


}
