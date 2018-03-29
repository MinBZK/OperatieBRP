/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jcabi.aspects.RetryOnFailure;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.JmsDocker;
import nl.bzk.brp.dockertest.jbehave.JBehaveState;
import nl.bzk.brp.dockertest.util.ResourceUtils;
import nl.bzk.brp.test.common.OnzekerResultaatExceptie;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.test.common.xml.XmlUtils;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Parameters;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.support.destination.JmsDestinationAccessor;
import org.w3c.dom.Document;

/**
 * Hulpklasse voor het controleren van asynchroon ontvangen berichten..
 */
public class AsynchroonberichtHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int AANTAL_ONTVANGER_ITERATIES = 20;
    private static final int WACHTTIJD_PER_ONTVANGER_ITERATIE_MILLIS = 1000;
    private static final int AANTAL_ONTVANGER_ITERATIES_NA_ONTVANGEN_BERICHT = 10;
    private static final int QUEUE_RECEIVE_TIMEOUT = 1000;

    private static final String AFNEMER_VRIJBERICHT_QUEUE = "queue://AfnemerOntvangenVrijBerichtQueue";
    private static final String AFNEMER_BERICHT_QUEUE = "queue://AfnemerOntvangenBerichtQueue";
    private static final String BIJHOUDINGSNOTIFICATIE_QUEUE = "queue://AfnemerOntvangenNotificatieBerichtQueue";
    private static final String LO3_BERICHT_QUEUE = "GbaLeveringen";

    private static final String MUTATIEBERICHT = "mutatiebericht";
    private static final String VOLLEDIGBERICHT = "volledigbericht";
    private static final String NOTIFICATIEBERICHT = "notificatiebericht";
    private static final String VRIJBERICHT = "vrijbericht";

    private final BrpOmgeving brpOmgeving;
    private final List<String> leveringBerichten = Lists.newLinkedList();
    private String geselecteerdeBericht;

    /**
     * Constructor.
     * @param brpOmgeving de omgeving die gestart is
     */
    public AsynchroonberichtHelper(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }


    public void wachtTotOntvangen(Integer verwachtAantal) throws InterruptedException {
        LOGGER.info("Ontvang asynchroon verstuurde berichten...");
        final Set<String> queueSet = bepaalRelevanteQueues();

        int iteratiesTeGaan = AANTAL_ONTVANGER_ITERATIES;
        int aantalOntvangenBerichten = 0;
        LOGGER.debug("Huidige wachttijd ontvanger is {} sec.", (WACHTTIJD_PER_ONTVANGER_ITERATIE_MILLIS * AANTAL_ONTVANGER_ITERATIES) / 1000);
        while (true) {
            if (receiveQueues(queueSet) > 0) {
                if (aantalOntvangenBerichten != leveringBerichten.size()) {
                    LOGGER.debug("Aantal ontvangen berichten is {}.", leveringBerichten.size());
                }
                aantalOntvangenBerichten = leveringBerichten.size();
            }
            if (!leveringBerichten.isEmpty() && iteratiesTeGaan > AANTAL_ONTVANGER_ITERATIES_NA_ONTVANGEN_BERICHT) {
                LOGGER.debug("Bericht(en) ontvangen, wachttijd wordt verkort.");
                LOGGER.debug("Wachttijd wordt verkort tot{} sec.",
                        (WACHTTIJD_PER_ONTVANGER_ITERATIE_MILLIS * AANTAL_ONTVANGER_ITERATIES_NA_ONTVANGEN_BERICHT) / 1000);
                iteratiesTeGaan = AANTAL_ONTVANGER_ITERATIES_NA_ONTVANGEN_BERICHT;
            }
            if (iteratiesTeGaan <= 0) {
                LOGGER.debug("Wachttijd verstreken, ontvanger wordt gestopt.");
                break;
            }
            iteratiesTeGaan--;
            TimeUnit.MILLISECONDS.sleep(WACHTTIJD_PER_ONTVANGER_ITERATIE_MILLIS);
        }
        for (String bericht : leveringBerichten) {
            ResourceUtils.schrijfBerichtNaarBestand(bericht, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                    JBehaveState.getScenarioState().getCurrectScenario(), String.format("levering-%s.xml", leveringBerichten.indexOf(bericht) + 1));
        }
        LOGGER.info("Totaal aantal berichten ontvangen = {}", leveringBerichten.size());

        if (verwachtAantal != null) {
            Assert.assertEquals(String.format("Onverwacht aantal berichten ontvangen %d ipv %d", leveringBerichten.size(), verwachtAantal),
                    verwachtAantal.intValue(), leveringBerichten.size());
        }
    }

    public void assertBerichtenVanGegevenTypeZijnOntvangen(final ExamplesTable table) {
        LOGGER.info("assertBerichtenVanGegevenTypeZijnOntvangen");
        Assert.assertFalse("Er zijn geen leveringberichten om te valideren.", leveringBerichten.isEmpty());
        final Set<String> volledigBerichten = Sets.newHashSet();
        final Set<String> mutatieBerichten = Sets.newHashSet();
        final Set<String> notificatieBerichten = Sets.newHashSet();
        final Set<String> overigeBerichten = Sets.newHashSet();
        for (final String bericht : leveringBerichten) {

            final Document document = XmlUtils.stringToDocument(bericht);
            final String soortSynchronisatie = XmlUtils.getNodeWaarde("//brp:parameters/brp:soortSynchronisatie", document);
            if (soortSynchronisatie != null && !soortSynchronisatie.isEmpty()) {
                if (soortSynchronisatie.equalsIgnoreCase(VOLLEDIGBERICHT)) {
                    volledigBerichten.add(bericht);
                } else if (soortSynchronisatie.equalsIgnoreCase(MUTATIEBERICHT)) {
                    mutatieBerichten.add(bericht);
                }
            } else {
                final String notificatieNode = XmlUtils.getNodeWaarde("//brp:bhg_sysVerwerkBijhoudingsplan", document);
                if (notificatieNode != null) {
                    notificatieBerichten.add(bericht);
                } else {
                    overigeBerichten.add(bericht);
                }
            }
        }
        Assert.assertTrue("Er zijn berichten ontvangen van een onverwacht type.", overigeBerichten.isEmpty());
        final List<Parameters> rijen = table.getRowsAsParameters();
        for (final Parameters parameters : rijen) {
            final String type = parameters.valueAs("type", String.class);
            final Integer aantal = parameters.valueAs("aantal", Integer.class);
            if (StringUtils.lowerCase(type).equals(VOLLEDIGBERICHT)) {
                Assert.assertTrue(String.format("Aantal volledigberichten klopt niet.[%d != %d].", volledigBerichten.size(), aantal),
                        volledigBerichten.size() == aantal);
            } else if (StringUtils.lowerCase(type).equals(MUTATIEBERICHT)) {
                Assert.assertTrue(String.format("Aantal mutatieberichten klopt niet.[%d != %d].", mutatieBerichten.size(), aantal),
                        mutatieBerichten.size() == aantal);
            } else if (StringUtils.lowerCase(type).equals(NOTIFICATIEBERICHT)) {
                Assert.assertTrue(String.format("Aantal notificatieberichten klopt niet.[%d != %d].", notificatieBerichten.size(), aantal),
                        notificatieBerichten.size() == aantal);
            } else {
                Assert.fail("Onbekende waarde in type kolom gedefinieerd.");
            }
        }
    }


    public void assertBekekenBerichtHeeftAantalVoorkomensVanEenGroepInLevering(final Integer aantal, final String groep) {
        LOGGER.info("assertBekekenBerichtHeeftAantalVoorkomensVanEenGroepInLevering");
        brpOmgeving.getxPathHelper().assertAantal(getGeselecteerdeBericht(), groep, aantal);
    }

    public Collection<String> geefBerichten() {
        return Collections.unmodifiableCollection(leveringBerichten);

    }

    public String getGeselecteerdeBericht() {
        Assert.assertNotNull(geselecteerdeBericht);
        return geselecteerdeBericht;
    }

    public void purge() {
        LOGGER.info("Leeg de queues");
        try {
            Arrays.asList(AFNEMER_VRIJBERICHT_QUEUE, AFNEMER_BERICHT_QUEUE, BIJHOUDINGSNOTIFICATIE_QUEUE, LO3_BERICHT_QUEUE)
                    .parallelStream().forEach(q -> receive(q, false));
            geselecteerdeBericht = null;
        } finally {
            leveringBerichten.clear();
        }
    }

    public void assertVrijberichtOntvangenVoorPartij(final String partijNaam) {
        LOGGER.debug("Assert vrij bericht ontvangen voor partij: {}", partijNaam);
        Assert.assertTrue("Geen vrije berichten ontvangen", !leveringBerichten.isEmpty());
        geselecteerdeBericht = null;
        final Set<Integer> partijen = leveringBerichten.stream().map(s ->
                Integer.parseInt(XmlUtils.getNodeWaarde("//brp:stuurgegevens/brp:ontvangendePartij", XmlUtils.stringToDocument(s))))
                .collect(Collectors.toSet());

        LOGGER.debug("Lijst ontvangende partijen: {}", partijen);

        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            final Integer partijCode = jdbcTemplate.queryForObject("select code from kern.partij where naam = ?", Integer.class, partijNaam);
            Assert.assertTrue(partijen.contains(partijCode));
        });

        if (leveringBerichten.size() > 1) {
            LOGGER.warn("Er zijn meerdere berichten ontvangen, hiervan wordt de 1e geselecteerd");
        }

        geselecteerdeBericht = leveringBerichten.get(0);
    }

    /**
     * Assert dat er geen berichten ontvangen zijn.
     */
    public void assertGeenBerichtenOntvangen() {
        LOGGER.debug("Assert geen bericht ontvangen");
        Assert.assertTrue(leveringBerichten.isEmpty());
    }

    /**
     * Assert dat er een bericht ontvangen is voor de gegeven parameters.
     * @param soortSynchronisatieParam het soort synchronisatie
     * @param partijNaamParam de naam van de partij welke het bericht ontvangt
     * @param leveringsautorisatieNaamParam de naam van de leveringsautorisatie
     */
    public void assertBerichtOntvangenVoorAutorisatieEnPartij(final String soortSynchronisatieParam,
                                                              final String partijNaamParam,
                                                              final String leveringsautorisatieNaamParam) {

        LOGGER.info("assertBerichtOntvangenVoorAutorisatieEnPartij");
        Assert.assertFalse("Geen leveringberichten", leveringBerichten.isEmpty());
        Assert.assertTrue(" De soort synchronisatie is niet correct. Kies uit volledigbericht, mutatiebericht, notificatiebericht of bericht.",
                soortSynchronisatieParam != null && (soortSynchronisatieParam.equals("bericht") || soortSynchronisatieParam.equals(VOLLEDIGBERICHT)
                        || soortSynchronisatieParam.equals(MUTATIEBERICHT) || soortSynchronisatieParam.equals(NOTIFICATIEBERICHT)
                        || soortSynchronisatieParam
                        .equals(VRIJBERICHT)));

        final PartijcodeVerzoek partijcodeVerzoek = new PartijcodeVerzoek(partijNaamParam);
        if (partijNaamParam != null) {
            brpOmgeving.brpDatabase().template().readonly(partijcodeVerzoek);
        }

        geselecteerdeBericht = null;
        for (final String bericht : leveringBerichten) {

            final Document berichtDocument = XmlUtils.stringToDocument(bericht);

            final BooleanSupplier matchLevsautorisatie = () -> {
                if (leveringsautorisatieNaamParam != null && !leveringsautorisatieNaamParam.isEmpty()) {
                    final String autorisatieNode =
                            XmlUtils.getNodeWaarde("//brp:parameters/brp:leveringsautorisatieIdentificatie", berichtDocument);
                    if (autorisatieNode != null && !autorisatieNode.isEmpty()) {
                        final int autorisatieId = Integer.parseInt(autorisatieNode);
                        return brpOmgeving.autorisaties().bestaatLeveringsautorisatie(autorisatieId, leveringsautorisatieNaamParam);
                    }
                }
                return true;
            };

            final BooleanSupplier berichtMatch = () -> {
                if (!"vrijbericht".equals(soortSynchronisatieParam)) {
                    if (NOTIFICATIEBERICHT.equals(soortSynchronisatieParam)) {
                        final String notificatieBericht = XmlUtils.getNodeWaarde("//brp:bhg_sysVerwerkBijhoudingsplan", berichtDocument);
                        return notificatieBericht != null && !notificatieBericht.isEmpty();
                    } else {
                        final String berichtSoortSynchronisatie = XmlUtils.getNodeWaarde("//brp:parameters/brp:soortSynchronisatie", berichtDocument);
                        return soortSynchronisatieParam.equalsIgnoreCase(berichtSoortSynchronisatie);
                    }
                }

                return true;
            };

            final BooleanSupplier partijNaamMatch = () -> {
                if (partijcodeVerzoek.getPartijCode() != null) {
                    final String berichtPartijCode = XmlUtils.getNodeWaarde("//brp:stuurgegevens/brp:ontvangendePartij", berichtDocument);
                    return partijcodeVerzoek.getPartijCode().equals(berichtPartijCode);
                }
                return true;
            };

            if (Lists.newArrayList(matchLevsautorisatie, berichtMatch, partijNaamMatch).stream().allMatch(BooleanSupplier::getAsBoolean)) {
                geselecteerdeBericht = bericht;
                break;
            }
        }
        Assert.assertNotNull("Geen bericht gevonden die voldoet aan alle variabelen.", geselecteerdeBericht);
    }

    private final class PartijcodeVerzoek implements Consumer<JdbcTemplate> {

        private final String partijNaamParam;
        private String partijCode;

        PartijcodeVerzoek(final String partijNaamParam) {
            this.partijNaamParam = partijNaamParam;
        }

        @Override
        public void accept(JdbcTemplate jdbcTemplate) {
            partijCode = jdbcTemplate.queryForObject("select code from kern.partij where naam = ?", String.class, partijNaamParam);
        }

        public String getPartijCode() {
            return partijCode;
        }
    }

    /**
     * Assert dat er leverberichten ontvangen zijn.
     */
    public void assertLeveringen() {
        LOGGER.info("assertLeveringen");
        Assert.assertTrue("Er zijn geen berichten geleverd", !leveringBerichten.isEmpty());
    }

    /**
     * Ontvang berichten van voorgedefinieerde queue-set.
     * @param queues set van queues
     * @return aantal berichten over queues heen
     */
    private int receiveQueues(final Set<String> queues)  {
        return (int) queues.parallelStream().map(queue -> receive(queue, true)).count();
    }

    /**
     * Controleert of er een bericht op de GBALeveringen queue is gezet.
     */
    @RetryOnFailure(delay = 2000L, types = OnzekerResultaatExceptie.class, randomize = false, attempts = 10)
    public void assertLo3Levering() throws JMSException {
        LOGGER.info("assertLo3Levering");
        brpOmgeving.routering().voerUit(jmsTemplate -> {
            final int receiveTimeout = 2500;
            jmsTemplate.setReceiveTimeout(receiveTimeout);
            final Message bericht = jmsTemplate.receive(LO3_BERICHT_QUEUE);
            if (bericht == null) {
                throw new OnzekerResultaatExceptie("Geen LO3 levering gevonden");
            }
            LOGGER.info("LO3bericht = " + bericht);
        });
    }

    /**
     * Ontvang bericht van queue.
     * @param queue naam van queue
     * @return aantal berichten ontvangen van queue
     * @throws JMSException indien ontvangst faalt
     */
    private int receive(String queue, boolean metTimeout) {
        LOGGER.debug(queue + " receive()");
        final JmsDocker routeringCentrale = brpOmgeving.geefDocker(DockerNaam.ROUTERINGCENTRALE);

        final List<String> ontvangenBerichten = Lists.newLinkedList();
        try {
            routeringCentrale.voerUit(template -> {
                template.setReceiveTimeout(metTimeout ? QUEUE_RECEIVE_TIMEOUT : JmsDestinationAccessor.RECEIVE_TIMEOUT_NO_WAIT);
                Object message;
                while (brpOmgeving.isGestart() && (message = template.receiveAndConvert(queue)) != null) {
                    LOGGER.debug("bericht ontvangen");
                    ontvangenBerichten.add((String) message);
                }
            });
        } catch (JMSException e) {
            throw new TestclientExceptie(e);
        }
        leveringBerichten.addAll(ontvangenBerichten);
        return leveringBerichten.size();
    }

    /**
     * Bepaal de relevante queues : Indien vrijberichtverzoek is enkel vrijberichtqueue relevant, anders afnemer- en/of bijhoudingsnotificatiequeue
     */
    private Set<String> bepaalRelevanteQueues() {
        final Set<String> relevanteQueues = Sets.newHashSet();
        if (brpOmgeving.verzoekService().isVrijberichtVerzoek()) {
            relevanteQueues.add(AFNEMER_VRIJBERICHT_QUEUE);
        } else {
            relevanteQueues.add(AFNEMER_BERICHT_QUEUE);
            if (brpOmgeving.verzoekService().isBijhoudingVerzoek()) {
                relevanteQueues.add(BIJHOUDINGSNOTIFICATIE_QUEUE);
            }
        }
        return relevanteQueues;
    }
}
