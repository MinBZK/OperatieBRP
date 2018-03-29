/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.cache.DalCacheController;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import nl.bzk.brp.funqmachine.configuratie.DatabaseConfig;
import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import nl.bzk.brp.funqmachine.jbehave.caches.PartijCache;
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;
import nl.bzk.brp.funqmachine.jbehave.context.DefaultScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.processors.LoggingProcessor;
import nl.bzk.brp.funqmachine.processors.ProcessorException;
import nl.bzk.brp.funqmachine.processors.SqlProcessor;
import nl.bzk.brp.funqmachine.processors.bijhouding.BijhoudingProcessor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JBehave steps voor Bijhouding. Deze steps spreken direct de bijhouding business laag aan ipv via een webservice.
 */
@Steps
public class BijhoudingSteps implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijhoudingSteps.class);

    private final DefaultScenarioRunContext runContext;
    private final BevraagbaarContextView contextView;
    private final PartijCache partijCache;

    private BijhoudingProcessor bijhoudingProcessor;
    private DalCacheController dalCacheController;
    private ApplicationContext parentApplicationContext;

    private ClassPathXmlApplicationContext context;
    private JmsTemplate jms;
    private Instant starttijdScenario;
    private Instant starttijdStory;
    private BrokerService broker;

    /**
     * Constructor voor de bijhouding steps class.
     * @param runContext de run context van JBehave
     * @param contextView de context view
     * @param partijCache de partij cache
     */
    @Inject
    public BijhoudingSteps(final DefaultScenarioRunContext runContext, final BevraagbaarContextView contextView, final PartijCache partijCache) {
        this.runContext = runContext;
        this.contextView = contextView;
        this.partijCache = partijCache;
    }

    /**
     * Post-construct method om de partij -> OIN map op te bouwen.
     * @throws Exception als het starten van de broker mislukt
     */
    @PostConstruct
    public void beforeStories() throws Exception {
        LOGGER.info("Starting BIJHOUDING context");
        context = new ClassPathXmlApplicationContext();
        context.setParent(parentApplicationContext);
        context.setConfigLocation("config/funqmachine-bijhouding-beans.xml");

        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("atomikos_unique_name", String.valueOf(new SecureRandom().nextInt() * Integer.MAX_VALUE));
        final DatabaseConfig archiveringDatabaseConfig = Omgeving.getOmgeving().getGetDatabaseConfig("ber");
        properties.put("jdbc.archivering.database.host", archiveringDatabaseConfig.getHost());
        properties.put("jdbc.archivering.database.port", archiveringDatabaseConfig.getPort());
        properties.put("jdbc.archivering.database.name", archiveringDatabaseConfig.getDatabaseName());
        properties.put("jdbc.archivering.username", archiveringDatabaseConfig.getUsername());
        properties.put("jdbc.archivering.password", archiveringDatabaseConfig.getPassword());

        propConfig.setProperties(properties);
        context.addBeanFactoryPostProcessor(propConfig);
        context.refresh();

        final ObjectSleutelService objectSleutelService = context.getBean(ObjectSleutelService.class);
        final BijhoudingService bijhoudingService = context.getBean(BijhoudingService.class);
        dalCacheController = context.getBean(DalCacheController.class);
        bijhoudingProcessor = new BijhoudingProcessor(bijhoudingService, objectSleutelService);

        startJmsConnection();
    }

    private void startJmsConnection() throws Exception {
        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Omgeving.getOmgeving().getBrokerUrl());
        jms = new JmsTemplate(factory);

        try {
            final Connection connection = factory.createConnection();
            connection.close();
        } catch (JMSException e) {
            LOGGER.info("Geen actieve JMS Broker gevonden. Er wordt er nu 1 gestart");
            LOGGER.trace("de volledige stacktrace", e);
            broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            broker.setDeleteAllMessagesOnStartup(true);
            broker.setPersistent(false);
            broker.start();
        }
        jms.setReceiveTimeout(1_000);
    }

    /**
     * After stories.
     */
    @PreDestroy
    public void afterStories() {
        LOGGER.info("Shutting down bijhouding context");
        if (broker != null && broker.isStarted()) {
            try {
                broker.stop();
            } catch (Exception e) {
                LOGGER.info("De broker kon niet netjes gestop worden", e);
            }
        }
        context.close();
    }

    /**
     * Before story.
     */
    @BeforeStory
    public void beforeStory() {
        starttijdStory = Instant.now();
        LoggingProcessor.clear();
    }

    /**
     * Reset de queues die gebruikt worden tijdens de tests.
     */
    @BeforeScenario
    public void resetQueues() {
        starttijdScenario = Instant.now();
        LOGGER.info("Resetting the queue in the broker");
        clearQueue(Omgeving.getOmgeving().getNotificatieBerichtQueue());
    }

    /**
     * After scenario.
     */
    @AfterScenario
    public void afterScenario() {
        final Instant eindtijdScenario = Instant.now();
        final Duration scenarioDuur = Duration.between(starttijdScenario, eindtijdScenario);
        final int milliseconden = Math.floorDiv(scenarioDuur.getNano(), 1_000_000);
        LOGGER.info(String.format("Scenario duurde %d seconde(n) en %d milliseconde(n)", scenarioDuur.getSeconds(), milliseconden));
        starttijdScenario = null;
    }

    /**
     * After story.
     */
    @AfterStory
    public void afterStory() {
        final Duration scenarioDuur = Duration.between(starttijdStory, Instant.now());
        final int milliseconden = Math.floorDiv(scenarioDuur.getNano(), 1_000_000);
        starttijdStory = null;
        LOGGER.info(String.format("Story duurde %d seconde(n) en %d milliseconde(n)", scenarioDuur.getSeconds(), milliseconden));
    }

    private void clearQueue(final String queueName) {
        try {
            int aantal = 0;
            Message receive = jms.receive(queueName);
            while (receive != null) {
                receive.acknowledge();
                receive = jms.receive(queueName);
                aantal++;
            }
            LOGGER.info(String.format("%d berichten verwijderd van queue %s", aantal, queueName));
        } catch (final JMSException | JmsException e) {
            LOGGER.warn("Kan de queue %s niet legen", e, queueName);
        }
    }

    /**
     * Then: Controleer het tijdstip laatste wijziging in het bijhoudingsplan.
     */
    @Then("controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen")
    public void controleerTsLaatsteWijziging() {
        bijhoudingProcessor.controleerTijdstipLaatsteWijzigingInResponse(runContext.geefLaatsteVerzoek());
    }


    /**
     * Voert megegeven update uit.
     * @param query uit te voeren query
     */
    @Given("voer enkele update uit: $query")
    public void voerEnkeleUpdateQueryUit(final String query) throws SQLException {
        SqlProcessor.getInstance().voerUpdateUit(query);
    }

    /**
     * Vervangt, bij alle betrokkenheden waarbij het opgegeven anr van de pseudo persoon is geregistreerd, door de met
     * anr opgegevens ingeschreven persoon.
     * @param anrPseudo anr van de pseudo persoon die vervangen moet worden
     * @param anrIngeschreven anr van de ingeschreven persoon
     */
    @Given("Pseudo-persoon $anrPseudo is vervangen door ingeschreven persoon $anrIngeschrevene")
    public void vervangPseudoPersoonDoorIngeschrevenPersoon(final String anrPseudo, final String anrIngeschreven) throws SQLException {
        final String query =
                String.format(
                        "update kern.betr set pers = (select p.id from kern.pers p where p.anr = '%s') where id = (select b.id from kern.betr "
                                + "b join kern.pers p on b.pers = p.id where anr = '%s')",
                        anrIngeschreven,
                        anrPseudo);
        SqlProcessor.getInstance().voerUpdateUit(query);
    }

    /**
     * Maakt alle bijhouding caches leeg.
     */
    @Given("maak bijhouding caches leeg")
    public void maakCachesLeeg()
            throws IOException, MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, MBeanException {
        if (Omgeving.getOmgeving().isDockerOmgeving()) {
            //setup jmx connectie
            final Map<String, Object> environment = new HashMap<>();
            environment.put(JMXConnector.CREDENTIALS, new String[]{"admin", "admin"});
            try (final JMXConnector jmxc = JMXConnectorFactory.connect(new JMXServiceURL(Omgeving.getOmgeving().getJmxUrl()), environment)) {
                final MBeanServerConnection serverConnection = jmxc.getMBeanServerConnection();
                final ObjectName mbeanName = new ObjectName("caches:name=AlgemeenDALCaches");
                final MBeanInfo mBeanInfo = serverConnection.getMBeanInfo(mbeanName);
                LOGGER.info("Clearing cache using bean: " + mBeanInfo.toString());
                serverConnection.invoke(mbeanName, "maakCachesLeeg", null, null);
                LOGGER.info("Successfully sent notification to clear caches.");
            }
        } else {
            dalCacheController.maakCachesLeeg();
        }
    }

    /**
     * Past van een relatie tussen twee personen de relatie id en betrokkenheid id van de andere persoon aan zodat deze
     * ids gebruikt kunnen worden als objectsleutels in een bijhoudingsbericht.
     * @param soortRelatieId het id van het soort relatie dat moet worden aangepast
     * @param bsnPersoon1 de bsn van persoon 1
     * @param bsnPersoon2 de bsn van persoon 2
     * @param relatieId de nieuwe relatie id
     * @param betrokkenheidId de nieuwe betrokkenheid id
     */
    @Given("pas laatste relatie van soort $soortRelatieId aan tussen persoon $bsnPersoon1 en persoon $bsnPersoon2 met relatie id $relatieId en "
            + "betrokkenheid id $betrokkenheidId")
    public void pasLaatsteRelatieIdEnBetrokkenheidIdAan(
            final int soortRelatieId,
            final String bsnPersoon1,
            final String bsnPersoon2,
            final int relatieId,
            final int betrokkenheidId) {
        pasRelatieIdEnBetrokkenheidIdAan(soortRelatieId, bsnPersoon1, bsnPersoon2, relatieId, betrokkenheidId, true);
    }

    /**
     * Past van een relatie tussen twee personen de relatie id en betrokkenheid id van de andere persoon aan zodat deze
     * ids gebruikt kunnen worden als objectsleutels in een bijhoudingsbericht.
     * @param soortRelatieId het id van het soort relatie dat moet worden aangepast
     * @param bsnPersoon1 de bsn van persoon 1
     * @param bsnPersoon2 de bsn van persoon 2
     * @param relatieId de nieuwe relatie id
     * @param betrokkenheidId de nieuwe betrokkenheid id
     */
    @Given("pas eerste relatie van soort $soortRelatieId aan tussen persoon $bsnPersoon1 en persoon $bsnPersoon2 met relatie id $relatieId en "
            + "betrokkenheid id $betrokkenheidId")
    public void pasEersteRelatieIdEnBetrokkenheidIdAan(
            final int soortRelatieId,
            final String bsnPersoon1,
            final String bsnPersoon2,
            final int relatieId,
            final int betrokkenheidId) {
        pasRelatieIdEnBetrokkenheidIdAan(soortRelatieId, bsnPersoon1, bsnPersoon2, relatieId, betrokkenheidId, false);
    }

    private void pasRelatieIdEnBetrokkenheidIdAan(
            final int soortRelatieId,
            final String bsnPersoon1,
            final String bsnPersoon2,
            final int relatieId,
            final int betrokkenheidId,
            final boolean isLaatste) {
        final int betrokkenheidIdIndex = 0;
        final int relatieIdIndex = 1;
        final String selectQuery =
                String.format(
                        "select b2.id as betrokkenheid2_id, r.id as relatie_id "
                                + "from kern.pers p1 "
                                + "join kern.betr b1 on b1.pers = p1.id "
                                + "join kern.relatie r on r.id = b1.relatie "
                                + "join kern.betr b2 on b2.relatie = r.id "
                                + "join kern.pers p2 on p2.id = b2.pers "
                                + "join kern.his_relatie rh on rh.relatie = r.id "
                                + "where p1.bsn = '%s' and p2.bsn = '%s' and r.srt = %d and rh.tsverval is null order by rh.tsreg %s",
                        bsnPersoon1,
                        bsnPersoon2,
                        soortRelatieId,
                        isLaatste ? "DESC" : "ASC");
        final int resultLength = 2;
        final int[] result = new int[resultLength];
        final boolean[] relatieGevonden = {false};

        SqlProcessor.getInstance().voerQueryUit(selectQuery, resultSet -> {
            if (!relatieGevonden[0]) {
                result[betrokkenheidIdIndex] = resultSet.getInt(betrokkenheidIdIndex + 1);
                result[relatieIdIndex] = resultSet.getInt(relatieIdIndex + 1);
                relatieGevonden[0] = true;
            }
        });
        if (relatieGevonden[0]) {
            LOGGER.debug(String.format("updating relatie %d en betrokkenheid %d", result[relatieIdIndex], result[betrokkenheidIdIndex]));
            // update betrokkenheid
            final String kopieBetrokkenheidQuery =
                    String.format(
                            "insert into kern.betr select %d, relatie, rol, pers, indag, indouderuitwiekindisgeboren, "
                                    + "indagouderschap, indouderheeftgezag, indagouderlijkgezag from kern.betr where id = %d",
                            betrokkenheidId,
                            result[betrokkenheidIdIndex]);
            final String updateBetrokkenheidHistorieQuery =
                    String.format("update kern.his_betr set betr = %d where betr = %d", betrokkenheidId, result[betrokkenheidIdIndex]);
            final String updateBetrokkenheidOuderHistorieQuery =
                    String.format("update kern.his_ouderouderschap set betr = %d where betr = %d", betrokkenheidId, result[betrokkenheidIdIndex]);
            final String updateBetrokkenheidOuderlijkGezagHistorieQuery =
                    String.format("update kern.his_ouderouderlijkgezag set betr = %d where betr = %d", betrokkenheidId, result[betrokkenheidIdIndex]);
            final String updateVerantwoordingConversieQuery =
                    String.format("update verconv.lo3aandouder set ouder = %d where ouder = %d", betrokkenheidId, result[betrokkenheidIdIndex]);

            final String deleteOudeBetrokkenheidQuery = String.format("delete from kern.betr where id = %d", result[betrokkenheidIdIndex]);

            // update relatie
            final String kopieRelatieQuery =
                    String.format(
                            "insert into kern.relatie select %d, srt, dataanv, gemaanv, wplnaamaanv, blplaatsaanv, "
                                    + "blregioaanv, omslocaanv, landgebiedaanv, rdneinde, dateinde, gemeinde, wplnaameinde, "
                                    + "blplaatseinde, blregioeinde, omsloceinde, landgebiedeinde from kern.relatie where id = %d",
                            relatieId,
                            result[relatieIdIndex]);
            final String updateRelatieHistorieQuery =
                    String.format("update kern.his_relatie set relatie=%d where relatie = %d", relatieId, result[relatieIdIndex]);
            final String updateBetrokkenheidQuery =
                    String.format("update kern.betr set relatie = %d where relatie = %d", relatieId, result[relatieIdIndex]);

            final String
                    updateIstRelatieQuery =
                    String.format("update ist.stapelRelatie set relatie = %d where relatie = %d ", relatieId, result[relatieIdIndex]);
            final String deleteOudeRelatieQuery = String.format("delete from kern.relatie where id = %d ", result[relatieIdIndex]);

            // execute
            SqlProcessor.getInstance().voerBatchUpdateUit(
                    kopieBetrokkenheidQuery,
                    updateBetrokkenheidHistorieQuery,
                    updateBetrokkenheidOuderHistorieQuery,
                    updateBetrokkenheidOuderlijkGezagHistorieQuery,
                    updateVerantwoordingConversieQuery,
                    deleteOudeBetrokkenheidQuery,
                    kopieRelatieQuery,
                    updateRelatieHistorieQuery,
                    updateBetrokkenheidQuery,
                    updateIstRelatieQuery,
                    deleteOudeRelatieQuery);
        } else {
            LOGGER.debug(String.format("Geen relatie gevonden voor bsn1 %s en bsn2 %s", bsnPersoon1, bsnPersoon2));
        }
    }

    /**
     * Voer een bevraging uit dmv bestand voor een partij welke ook de transporteur en ondertekenaar is.
     * @param filename naam van het bestand met daarin XML bericht
     * @param partijNaam de naam van de partij waarvoor de bijhouding uitgevoerd gaat worden
     */
    @When("voer een bevraging uit $filenaam namens partij '$partijNaam'")
    public void verstuurBevraging(final String filename, final String partijNaam) {
        verstuurBericht(filename, partijNaam, partijNaam, false, false);
    }

    /**
     * Voer een bijhouding uit dmv bestand voor een partij met verschillende transporteur en ondertekenaar.
     * @param filename naam van het bestand met daarin XML bericht
     * @param ondertekenaar de naam van de partij die als ondertekenaar optreedt
     * @param transporteur de naam van de partij die als transporteur optreedt
     */
    @When("voer een bijhouding uit $filename met ondertekenaar '$ondertekenaar', en transporteur '$transporteur'")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void verstuurBericht(final String filename, final String ondertekenaar, final String transporteur) {
        verstuurBericht(filename, ondertekenaar, transporteur, false, true);
    }

    /**
     * Voer een bijhouding uit dmv bestand voor een partij welke ook de transporteur en ondertekenaar is.
     * @param filename naam van het bestand met daarin XML bericht
     * @param partijNaam de naam van de partij waarvoor de bijhouding uitgevoerd gaat worden
     */
    @When("voer een bijhouding uit $filename namens partij '$partijNaam'")
    public void verstuurBericht(final String filename, final String partijNaam) {
        verstuurBericht(filename, partijNaam, partijNaam, false, true);
    }

    /**
     * Voer een GBA bijhouding uit dmv bestand voor een partij welke ook de transporteur en ondertekenaar is.
     * @param filename naam van het bestand met daarin XML bericht
     * @param partijNaam de naam van de partij waarvoor de bijhouding uitgevoerd gaat worden
     */
    @When("voer een GBA bijhouding uit $filename namens partij '$partijNaam'")
    public void verstuurGbaBijhouding(final String filename, final String partijNaam) {
        verstuurBericht(filename, partijNaam, partijNaam, true, true);
    }


    private void verstuurBericht(final String filename, final String ondertekenaar, final String transporteur, final boolean isGbaBijhouding,
                                 final boolean isBijhouding) {
        final Map<String, String> variabelen = new HashMap<>();
        final DatabaseSteps databaseSteps = (DatabaseSteps) context.getBean("databaseSteps");
        if (databaseSteps != null) {
            variabelen.putAll(databaseSteps.getVariabelen());
        }
        StepResult result = new StepResult(StepResult.Soort.SOAP);
        try {
            final InputStream xmlInputStream = getXmlInputStream(filename);
            final String oinTransporteur = partijCache.geefPartijOin(transporteur);
            final String oinOndertekenaar = partijCache.geefPartijOin(ondertekenaar);
            AutAutContext.get().setVerzoekBijhoudingsautorisatie(oinOndertekenaar, oinTransporteur);
            if (oinOndertekenaar == null) {
                LOGGER.warn(String.format("Er kan geen oin-ondertekenaar partij gevonden voor waar '%s'", ondertekenaar));
            }
            if (oinTransporteur == null) {
                LOGGER.warn(String.format("Er kan geen oin-transporteur partij gevonden voor waar '%s'", transporteur));
            }
            bijhoudingProcessor.verwerkenBericht(isBijhouding, isGbaBijhouding, result, xmlInputStream, oinOndertekenaar, oinTransporteur, variabelen);
        } catch (ResourceException re) {
            throw new ProcessorException(re);
        } catch (ProcessorException e) {
            if (e.negeerExceptie()) {
                LOGGER.info("Er is een fout opgetreden die de story niet blokkeerd zodat deze fout als verwachting kan worden opgegeven:\n" + e.getMessage());
                result = new StepResult(StepResult.Soort.ERROR);
                if (e.getCause() == null) {
                    result.setResponse(e.getMessage());
                } else {
                    result.setResponse(e.getCause().getMessage());
                }
            } else {
                throw e;
            }
        }
        runContext.voegStepResultaatToe(result);
    }

    /**
     * Controleert of het aantal opgegeven bijhouding notificatie berichten op de queue is gezet.
     * @param aantalBerichten aantal berichten dat op de queue verwacht wordt
     */
    @Then("staan er $aantal notificatieberichten voor bijhouders op de queue")
    @Alias("staat er $aantal notificatiebericht voor bijhouders op de queue")
    public void staatNotificatieBerichtOpBijhoudersQueue(final int aantalBerichten) throws JMSException {
        final String queue = Omgeving.getOmgeving().getNotificatieBerichtQueue();
        TextMessage bericht = (TextMessage) jms.receive(queue);
        int aantalOntvangenBerichten = 0;
        while (bericht != null) {
            bericht.acknowledge();
            final StepResult jsonResult = new StepResult(StepResult.Soort.JSON);
            jsonResult.setResponse(bericht.getText());
            runContext.voegStepResultaatToe(jsonResult);
            aantalOntvangenBerichten++;
            bericht = (TextMessage) jms.receive(queue);
        }

        assertEquals(String.format(
                "Aantal berichten op queue komt niet overeen met verwacht aantal (expected: %d, actual: %d)", aantalBerichten, aantalOntvangenBerichten),
                aantalBerichten, aantalOntvangenBerichten);
    }

    /**
     * Controleert of er eem blob is aangemaakt voor het opgegeven administratienummer.
     * @param administratienummer het administratienummer waarvoor een blob aangemaakt moet zijn
     */
    @Then("is er een blob gemaakt voor administratienummer $administratienummer")
    @Alias("is er een blob aangepast voor administratienummer $administratienummer")
    public void bestaatBlobVoorAnummer(final String administratienummer) {
        final String
                query =
                String.format("select pc.pers, pc.pershistorievollediggegevens from kern.perscache pc join kern.pers p on p.id = pc.pers where p.anr = '%s'",
                        administratienummer);
        final List<Map<String, Object>> persCacheList = SqlProcessor.getInstance().voerQueryUit(query);
        final int aantalPersCache = persCacheList.size();
        LOGGER.debug("Aantal gevonden blobs {}", aantalPersCache);
        assertEquals(String.format("Aantal blobs gevonden voor %s ongelijk aan 1 (actual: %d)", administratienummer, aantalPersCache), 1, aantalPersCache);

        final Map<String, Object> persCacheMap = persCacheList.get(0);
        final Number persId = (Number) persCacheMap.get("pers");
        final byte[] actualPersoonBytes = (byte[]) persCacheMap.get("pershistorievollediggegevens");
        StepResult stepResult = runContext.getBlobResult(persId);

        if (stepResult != null) {
            final byte[] expectedPersoonBytes = stepResult.getExpected().getBytes(Charset.defaultCharset());

            if (actualPersoonBytes.length == expectedPersoonBytes.length) {
                boolean bytesZijnOngelijkGelijk = false;
                int index = 0;
                while (!bytesZijnOngelijkGelijk && index < actualPersoonBytes.length) {
                    bytesZijnOngelijkGelijk = actualPersoonBytes[index] != expectedPersoonBytes[index];
                    index++;
                }

                assert bytesZijnOngelijkGelijk : "Blob is niet aangepast.";
            }
        } else {
            stepResult = new StepResult(StepResult.Soort.BLOB);
        }
        stepResult.setResponse(new String(actualPersoonBytes, Charset.defaultCharset()));
    }

    /**
     * Controleert of de gegeven logtekst voorkomt in de logging die het resultaat is van deze story.
     * @param logTekst de tekst die verwacht wordt in de logging
     */
    @Then("komt de tekst '$logTekst' voor in de logging")
    public void komtTekstVoorInLogging(final String logTekst) {
        assertTrue(String.format("De logTekst '%s' komt niet voor in de logging.", logTekst), LoggingProcessor.komtTekstVoorInLogging(logTekst));
    }

    /**
     * Controleert of de gegeven logtekst voorkomt in de logging die het resultaat is van deze story.
     * @param logTekst de tekst die verwacht wordt in de logging
     */
    @Then("komt de tekst '$logTekst' NIET voor in de logging")
    public void komtTekstNietVoorInLogging(final String logTekst) {
        assertFalse(String.format("De logTekst '%s' komt voor in de logging.", logTekst), LoggingProcessor.komtTekstVoorInLogging(logTekst));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.parentApplicationContext = applicationContext;
    }

    private InputStream getXmlInputStream(final String fileNaam) throws ResourceException {
        final InputStream inputStream;
        if (fileNaam.startsWith("/")) {
            inputStream = getClass().getResourceAsStream(fileNaam);
        } else {
            try {
                inputStream = new FileInputStream(new ResourceResolver(contextView).resolve(fileNaam).toFile());
            } catch (FileNotFoundException e) {
                throw new ResourceException(e);
            }
        }
        return inputStream;
    }
}
