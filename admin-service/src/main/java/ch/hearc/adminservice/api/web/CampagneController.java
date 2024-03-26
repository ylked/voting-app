package ch.hearc.adminservice.api.web;


import ch.hearc.adminservice.api.web.models.request.CreateCampagneRequestBody;
import ch.hearc.adminservice.api.web.models.request.CreateObjetRequestBody;
import ch.hearc.adminservice.api.web.models.response.*;
import ch.hearc.adminservice.service.CampagneService;
import ch.hearc.adminservice.service.models.Campagne;
import ch.hearc.adminservice.service.models.UpdateCampagneStatusAction;
import ch.hearc.adminservice.service.models.actions.CreateObjetForCampagneResult;
import ch.hearc.adminservice.service.models.actions.GetCampagneResultsAction;
import ch.hearc.adminservice.service.models.actions.UpdateCampagneStatusResult;
import ch.hearc.adminservice.shared.CampagneStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Controlleur REST permettant de manipuler des campagnes de votes et les éléments liés
 */
@Tag(name = "Campagne", description = "Campagne API")
@RestController
@RequestMapping("campagne")
public class CampagneController {

    @Autowired
    CampagneService campagneService;


    /**
     * Création d'une campagne de vote
     *
     * @param body le corsp de la requête contenant les éléments permettant de crééer une campagne
     * @return ResponseEntity contenant le header Location pointatnt vers la ressources
     */
    @Operation(
            summary = "Création d'une campagne de vote",
            description = "Crééer une campagne de vote avec le status CREATED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    @PostMapping
    public ResponseEntity<?> createCampagne(@RequestBody @Valid CreateCampagneRequestBody body) {

        Campagne campagne = campagneService.createCampagne(body.getNom());

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(campagne.getIdentifiant())
                        .toUri()).body(new CampagneCreatedResponseBody(
                campagne.getIdentifiant(), campagne.getDateCreation().toString()));
    }

    /**
     * Retourne la liste des campagnes simple, sans les objets liés
     *
     * @return une ResponseEntity avec la liste des campagnes dans le body
     */
    @Operation(
            summary = "Retourne la liste des campagnes de votes",
            description = "Lister les campagnes de votes quelques soit le status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, retroune la liste des campagnes",
                    content = {@Content(schema = @Schema(implementation = ListCampagnesResponseBody.class),
                            mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<ListCampagnesResponseBody> getCampagnes(
            @Parameter(description = "Filtrer par status", required = false)
            @RequestParam(required = false, name = "status") Optional<CampagneStatus> status) {

        List<Campagne> campagnes = campagneService.getCampagnes(status);

        ListCampagnesResponseBody responseBody = ListCampagnesResponseBody.mapFromListCampagne(campagnes);

        return ResponseEntity.ok(responseBody);
    }

    @Operation(
            summary = "Retourne le détail d'une campagne de vote",
            description = "Retourne le détail d'une campagne de vote via son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, détail d'une campagne",
                    content = {@Content(schema = @Schema(implementation = CampagneResponseBody.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "NotFound, la campagne n'a pas été trouvée",
                    content = {@Content(schema = @Schema(implementation = NoEntityFoundResponseBody.class),
                            mediaType = "application/json")}),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCampagneByIdentifiant(
            @PathVariable("id")
            @Parameter(name = "id", description = "Identifiant de la campagne", example = "sadhjas-121212-2132kmkljk") String identifiant) {

        Optional<Campagne> campagne = campagneService.getCampagneByIdentifiant(identifiant);

        if (campagne.isPresent()) {
            return ResponseEntity.ok(CampagneResponseBody.mapFromCampagne(campagne.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }
    }


    @Operation(
            summary = "Mise à jour du status d'une camoagne",
            description = "Permet de mettre à jour le status d'une campagne")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, la campagne à été mise à jour",
                    content = {@Content(schema = @Schema(implementation = UpdateCampagneStatusResult.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "NotFound, la campagne n'a pas été trouvée ",
                    content = {@Content(schema = @Schema(implementation = NoEntityFoundResponseBody.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Erreur technique ",
                    content = {@Content(schema = @Schema(implementation = UpdateCampagneStatusResult.class),
                            mediaType = "application/json")}),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampagneStatus(
            @PathVariable("id") String identifiant,
            @Parameter(description = "L'action à appliquer pour changer le status", required = true)
            @RequestParam(value = "action", required = true) UpdateCampagneStatusAction action) {

        Optional<Campagne> optionalCampagne = campagneService.getCampagneByIdentifiant(identifiant);

        if (optionalCampagne.isPresent()) {
            UpdateCampagneStatusResult resultAction = campagneService.updateCampganeStatus(optionalCampagne.get(), action);

            if (resultAction.isActionOk()) {
                return ResponseEntity.ok(resultAction);
            } else {
                return ResponseEntity.badRequest().body(resultAction);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(identifiant));
        }

    }

    @Operation(
            summary = "Ajoute un objet à une campagne de vote",
            description = "Ajoute un objet à une campagne de vote, la campagne doit être dans le status CREATED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created, l'objet à été créé"),
            @ApiResponse(responseCode = "404", description = "NotFound, la campagne n'a pas été trouvée",
                    content = {@Content(schema = @Schema(implementation = NoEntityFoundResponseBody.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "BadRequest, si erreur",
                    content = {@Content(schema = @Schema(implementation = CreateObjetForCampagneResult.class),
                            mediaType = "application/json")}),
    })
    @PostMapping("/{id}/objet")
    public ResponseEntity<?> addObjetsToCampagne(@RequestBody @Valid CreateObjetRequestBody body, @PathVariable("id") String campagneIdentifiant) {

        //check if campgane exist by identifiant
        Optional<Campagne> campagne = campagneService.getCampagneByIdentifiant(campagneIdentifiant);

        if (campagne.isPresent()) {
            CreateObjetForCampagneResult actionResult = campagneService.createObjetForCampagne(campagne.get(), body.getNom());

            if (actionResult.isActionOk()) {
                return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/campagne/{id}")
                                .buildAndExpand(campagneIdentifiant).toUri()).body(
                        new ObjetCreatedResponseBody(campagneIdentifiant, actionResult.getObjet().getIdentifiant()));
            } else {

                return ResponseEntity.badRequest().body(actionResult);
            }


        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(campagneIdentifiant));
        }
    }

    @Operation(
            summary = "Retourne les résultats d'une campagne de vote",
            description = "Retourne les résultats des votes pour une campagem, hors de l'état CREATED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, résutats retrounée"),
            @ApiResponse(responseCode = "404", description = "NotFound, la campagne n'a pas été trouvée",
                    content = {@Content(schema = @Schema(implementation = NoEntityFoundResponseBody.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "BadRequest, si erreur, ou pas de campagne ouverte",
                    content = {@Content(schema = @Schema(implementation = NoCampagneOpenedResponseBody.class),
                            mediaType = "application/json")}),
    })
    @GetMapping("/{id}/results")
    public ResponseEntity<?> getCampagneResults(@PathVariable("id") String campagneIdentifiant) {

        GetCampagneResultsAction result = campagneService.getResultForCampagne(campagneIdentifiant);

        if (result.isCampagneExist()) {

            if (!result.InStatusCreated()) {
                return ResponseEntity.ok(new CampagneResultsResponseBody(result.getCampagne()));
            } else {
                return ResponseEntity.badRequest().body(new NoCampagneOpenedResponseBody(campagneIdentifiant));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoEntityFoundResponseBody(campagneIdentifiant));
        }
    }


}
