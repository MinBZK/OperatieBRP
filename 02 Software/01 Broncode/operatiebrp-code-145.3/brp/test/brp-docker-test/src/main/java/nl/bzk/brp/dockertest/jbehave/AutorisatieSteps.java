/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.DockerNaam;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.steps.Steps;
import org.springframework.core.io.ClassPathResource;

/**
 * JBehave steps tbv autorisatie.
 */
public class AutorisatieSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Voegt nieuw autorisaties toe en verwijdert de oude.
     */
    @Given("leveringsautorisatie uit $bestanden")
    public void givenLeveringautorisatieUitBestanden(List<String> bestanden) {
        JBehaveState.get().autorisaties().laadAutorisaties(
                bestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), JBehaveState.get().brpDatabase());
        if (JBehaveState.get().bevat(DockerNaam.SELECTIEBLOB_DATABASE)) {
            JBehaveState.get().autorisaties().laadAutorisaties(
                    bestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), JBehaveState.get().selectieDatabase());
        }
    }

    /**
     * Voegt nieuw bijhoudingautorisaties toe en verwijdert de oude.     *
     */
    @Given("bijhoudingautorisatie uit $bestanden")
    public void givenBijhoudingautorisatieUitBestanden(List<String> bestanden) {
        JBehaveState.get().autorisaties().laadBijhoudingAutorisaties(
                bestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), JBehaveState.get().brpDatabase());
        if (JBehaveState.get().bevat(DockerNaam.SELECTIEBLOB_DATABASE)) {
            JBehaveState.get().autorisaties().laadBijhoudingAutorisaties(
                    bestanden.stream().map(ClassPathResource::new).collect(Collectors.toList()), JBehaveState.get().selectieDatabase());
        }
    }


}
