/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.jbehave.caches.PartijCache;
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.processors.XmlProcessor;
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

/**
 * Stappen voor het uitvoeren van synchrone berichten.
 */
@Steps
public class SynchroonBerichtenSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ScenarioRunContext runContext;
    private final PartijCache partijCache;

    /**
     * Constructor.
     * @param runContext de scenario run context
     * @param partijCache de partij cache
     */
    @Inject
    public SynchroonBerichtenSteps(final ScenarioRunContext runContext, final PartijCache partijCache) {
        this.runContext = runContext;
        this.partijCache = partijCache;
    }

    /**
     * Given: een bijhoudingsverzoek voor de meegegeven partij, ondertekenaar en transporteur.
     * @param partijNaam de naam van de partij
     * @param ondertekenaar de ondertekenaar van het request
     * @param transporteur de transporteur van het request
     */
    @Given("bijhoudingsverzoek voor partij '$partijNaam' met ondertekenaar $ondertekenaar en transporteur $transporteur")
    public void zetVerzoekBijhoudingsautorisatie(final String partijNaam, final String ondertekenaar, final String transporteur) {
        AutAutContext.get().setVerzoekBijhoudingsautorisatie(ondertekenaar, transporteur);
    }

    /**
     * Given: een bijhoudingsverzoek voor een partij.
     * @param partijNaam de naam van de partij
     */
    @Given("bijhoudingsverzoek voor partij '$partijNaam'")
    public void zetVerzoekBijhoudingsautorisatie(final String partijNaam) {
        final String oin = partijCache.geefPartijOin(partijNaam);
        AutAutContext.get().setVerzoekBijhoudingsautorisatie(oin, oin);
    }

    /**
     * Then: is het antwoord bericht gelijk aan de opgegeven expected voor de meegegeven expressie.
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van soap-resultaat en verwacht antwoord
     */
    @Then("is het antwoordbericht gelijk aan $expected voor expressie $regex")
    public void responseVoldoetAanExpected(final String expected, final String regex) {
        try {
            LOGGER.info("is het antwoorbericht gelijk aan expected met expressie");
            StepResult result = runContext.geefLaatsteVerzoek();
            final Path expectedFile = getExpectedResource(expected);
            final String expectedContent = new String(Files.readAllBytes(expectedFile), Charset.defaultCharset());
            result.setExpected(expectedContent);
            new XmlProcessor().vergelijk(regex, result.getExpected(), result.getResponse());
        } catch (final URISyntaxException | IOException e) {
            throw new StepException(e);
        }
    }

    /**
     * ThenL is het foutief antwoordbericht gelijk aan de opgegeven expected.
     * @param expected het verwachte antwoord
     */
    @Then("is het foutief antwoordbericht gelijk aan $expected")
    public void responseVoldoetAanExpected(final String expected) {
        try {
            LOGGER.info("is het foutief antwoordbericht gelijk aan expected");
            final List<StepResult> lijst = runContext.geefErrorResultaten();
            final Path expectedFile = getExpectedResource(expected);
            final String expectedContent = new String(Files.readAllBytes(expectedFile), Charset.defaultCharset());
            final String ontvangenContent;

            if (!lijst.isEmpty()) {
                StepResult result = lijst.get(lijst.size() - 1);
                result.setExpected(expectedContent);
                ontvangenContent = result.getResponse();
                if (!expectedContent.equals(ontvangenContent)) {
                    throw new AssertionMisluktError("Expected komt niet overeen met verkregen resultaat" + expectedContent);
                }
            } else {
                final StepResult stepResult = runContext.geefLaatsteVerzoek();
                ontvangenContent = stepResult.getResponse();
                stepResult.setExpected(expectedContent);
                new XmlProcessor().vergelijk("/", expectedContent, ontvangenContent);
            }
        } catch (final URISyntaxException | IOException e) {
            throw new StepException(e);
        }
    }

    private Path getExpectedResource(final String expected) throws URISyntaxException, IOException {
        final URL urlResource = this.getClass().getResource(expected);
        if (urlResource == null) {
            throw new IOException(String.format("Kan opgegeven bestand %s niet vinden", expected));
        }
        return Paths.get(this.getClass().getResource(expected).toURI());
    }

    /**
     * Then: heeft het antwoordbericht de verwerking status.
     * @param verwerking de verwerking die wordt verwacht. Mogelijke waardes zijn: `Geslaagd` en `Foutief`
     */
    @Then("heeft het antwoordbericht verwerking $verwerking")
    public void berichtHeeftVerwerking(String verwerking) {
        LOGGER.debug("Valideer verwerking van antwoord");
        new XmlProcessor().controleerVerwerkingInAntwoordBericht(verwerking, runContext.geefLaatsteVerzoek().getResponse());
    }
}
