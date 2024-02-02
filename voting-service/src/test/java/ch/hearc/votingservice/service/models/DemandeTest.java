package ch.hearc.votingservice.service.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemandeTest {

    @Test
    public void ensureThatADemandeCantHaveNullParameters(){

        Exception exception = assertThrows(NullPointerException.class, () -> {
            Demande demande = Demande.nouvelleDemande(null,null,null);
        });
    }

    @Test
    public void ensureThatANewDemandeHaveAnIdentifierDefined(){

        Demande demande = Demande.nouvelleDemande("nom","prenom","campagneId");

        assertNotNull(demande.getIdentifiant());
    }

}