/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.service.VerzoekService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.springframework.core.io.ClassPathResource;

/**
 * JBehave steps tbv het doen van SOAP requests
 */
public class SOAPRequestSteps extends Steps {

    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;

    /**
     * Verzoek met een bepaalde leveringautorisatie.
     * @param leveringsautorisatieNaam de naam van de leveringsautorisatie
     * @param partijNaam de naam van de partij
     */
    @Given("verzoek voor leveringsautorisatie '$leveringsautorisatieNaam' en partij '$partijNaam'")
    public void zetVerzoekLeveringsautorisatie(final String leveringsautorisatieNaam, final String partijNaam) {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        toegangLeveringsAutorisatie = brpOmgeving.autorisaties().geefToegangLeveringsautorisatie(partijNaam, leveringsautorisatieNaam);
    }

    /**
     * Doe een synchroon verzoek van een bepaald type. Dit is een volledig XML verzoek.
     * @param bestand het bestand
     */
    @Given("xml verzoek uit bestand $bestand")
    public void givenVerzoekVanType(String bestand) {
        final ClassPathResource requestBestand = new ClassPathResource(bestand);
        final VerzoekService verzoekService = JBehaveState.get().verzoekService();
        verzoekService.maakVerzoek(requestBestand, toegangLeveringsAutorisatie);
    }

    /**
     * Doe een synchroon vrijbericht verzoek. Dit is een volledig XML verzoek.
     * @param bestand het bestand
     */
    @Given("vrijbericht verzoek voor partij $partijNaam uit bestand $bestand")
    public void givenVrijberichtVerzoek(String partijNaam, String bestand) {
        final VerzoekService verzoekService = JBehaveState.get().verzoekService();
        verzoekService.maakVrijberichtVerzoek(new ClassPathResource(bestand));
    }

    /**
     * Voer een bijhouding uit dmv bestand voor een partij welke ook de transporteur en ondertekenaar is.
     * @param bestand naam van het bestand met daarin XML bericht
     * @param partijNaam de naam van de partij waarvoor de bijhouding uitgevoerd gaat worden
     */
    @When("voer een bijhouding uit $filename namens partij '$partijNaam'")
    public void verstuurBijhouding(final String bestand, final String partijNaam) {
        final VerzoekService verzoekService = JBehaveState.get().verzoekService();
        final ClassPathResource requestBestand = new ClassPathResource(bestand);
        verzoekService.maakBijhoudingVerzoek(requestBestand, partijNaam, false);
    }
}
