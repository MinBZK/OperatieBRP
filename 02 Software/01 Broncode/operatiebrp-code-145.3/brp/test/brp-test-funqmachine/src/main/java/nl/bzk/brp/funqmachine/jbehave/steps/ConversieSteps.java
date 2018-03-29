/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.Xml;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.Configuration;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.processors.XmlProcessor;
import nl.bzk.brp.funqmachine.processors.conversie.ConversieDataVuller;
import nl.bzk.brp.funqmachine.processors.conversie.ConversieException;
import nl.bzk.brp.funqmachine.processors.encoder.BrpActieBronObjectEncoder;
import nl.bzk.brp.funqmachine.processors.encoder.BrpActieObjectEncoder;
import nl.bzk.brp.funqmachine.processors.encoder.BrpRelatieObjectEncoder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Stappen om Pls te converteren en te persisteren
 */
@Steps
public class ConversieSteps implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final ScenarioRunContext runContext;
    private final BevraagbaarContextView contextView;
    private final DatabaseSteps databaseSteps;

    private ApplicationContext parentApplicationContext;
    private ConversieDataVuller geconverteerdeDataVuller;
    private ClassPathXmlApplicationContext context;

    /**
     * Constructors voor de Conversie steps.
     * @param runContext de scenario run context
     * @param contextView de context view
     * @param databaseSteps de persoonbeeld steps
     */
    @Inject
    public ConversieSteps(final ScenarioRunContext runContext, final BevraagbaarContextView contextView, final DatabaseSteps databaseSteps) {
        this.runContext = runContext;
        this.contextView = contextView;
        this.databaseSteps = databaseSteps;
    }

    /**
     * Start de conversie spring context voordat de stories worden uitgevoerd.
     */
    @PostConstruct
    public void beforeStories() {
        LOGGER.info("Starting CONVERSIE context");
        context = new ClassPathXmlApplicationContext();
        context.setParent(parentApplicationContext);
        context.setConfigLocation("/config/funqmachine-conversie-beans.xml");

        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("atomikos_unique_name", String.valueOf(new SecureRandom().nextInt() * Integer.MAX_VALUE));
        propConfig.setProperties(properties);

        context.addBeanFactoryPostProcessor(propConfig);
        context.refresh();
        geconverteerdeDataVuller = context.getBean(ConversieDataVuller.class);
    }

    /**
     * Sluit de Conversie context nadat de stories gedaan zijn.
     */
    @PreDestroy
    public void afterStories() {
        LOGGER.info("Shutting down conversie context");
        context.close();
    }

    /**
     * Given: Het laden van een initiele vulling uit het meegegeven bestandsnaam
     * @param filenaam de bestandsnaam waar de initiele vulling in staat.
     */
    @Given("enkel initiele vulling uit bestand $filenaam")
    public void enkelInitieleVullingUitBestand(final String filenaam) {
        try {
            final Path path = geefPath(filenaam, false);
            LOGGER.info("Converteer een enkele initiele vulling uit " + filenaam);
            geconverteerdeDataVuller.converteerInitieleVullingPL(path);
            databaseSteps.logPersoonsbeelden("excel");
        } catch (IOException | ConversieException | ResourceException e) {
            throw new StepException(e);
        }
    }

    /**
     * Then: Leest de persoon met meegegven anummer uit de database en vergelijkt deze met de persoonslijst welke gevonden is in het meegeven bestandsnaam.
     * @param anummer het anummer van de persoon die uit de database gelezen moet worden
     * @param filenaam het bestandsnaam waar de verwachte persoonslijst te vinden is
     */
    @Then("lees persoon met anummer $anummer uit database en vergelijk met expected $filenaam")
    public void leesPersoonEnVergelijk(final String anummer, final String filenaam) {
        LOGGER.info("Lees persoon (anr: " + anummer + ") uit database en vergelijk met expected " + filenaam);
        final BrpPersoonslijst persoonslijst = geconverteerdeDataVuller.vraagPersoonOpUitDatabase(anummer);
        try {
            final Path pathExpected = geefPath(filenaam, true);
            persoonslijst.sorteer();

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final Writer writer = new OutputStreamWriter(baos, Charset.defaultCharset());
            Configuration xmlConfiguratie = new Configuration();
            xmlConfiguratie.registerXmlObjectFor(BrpActieBron.class, new BrpActieBronObjectEncoder());
            xmlConfiguratie.registerXmlObjectFor(BrpRelatie.class, new BrpRelatieObjectEncoder());
            xmlConfiguratie.registerXmlObjectFor(BrpActie.class, new BrpActieObjectEncoder());

            Xml.encode(xmlConfiguratie, persoonslijst, writer);
            writer.close();
            baos.close();

            final StepResult result = new StepResult(StepResult.Soort.BRP_PL);
            result.setAnummer(anummer);
            result.setResponse(new String(baos.toByteArray(), Charset.defaultCharset()));
            runContext.voegStepResultaatToe(result);
            final String expectedAsString = new String(Files.readAllBytes(pathExpected), Charset.defaultCharset()).replaceAll("[\n\r]", "");
            new XmlProcessor().vergelijk("/brpPersoonslijst", expectedAsString, result.getResponse());
        } catch (XmlException | IOException | ResourceException e) {
            throw new StepException(e);
        }

    }

    /**
     * Geeft de file op basis van een filenaam binnen zijn classpath.
     * @return de file als deze gevonden kan worden.
     */

    private Path geefPath(final String fileNaam, final boolean expected) throws ResourceException {
        if (fileNaam.startsWith("/")) {
            try {
                return Paths.get(getClass().getResource(fileNaam).toURI());
            } catch (URISyntaxException e) {
                throw new ResourceException(e);
            }
        } else {
            return new ResourceResolver(contextView).resolve(fileNaam, expected);
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.parentApplicationContext = applicationContext;
    }
}
