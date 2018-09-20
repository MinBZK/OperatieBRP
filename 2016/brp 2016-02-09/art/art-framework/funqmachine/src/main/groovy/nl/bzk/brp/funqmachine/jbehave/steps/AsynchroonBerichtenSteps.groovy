package nl.bzk.brp.funqmachine.jbehave.steps

import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext

import static nl.bzk.brp.funqmachine.processors.xml.XmlUtils.getNode
import static org.threeten.bp.temporal.ChronoUnit.SECONDS

import com.google.common.base.Predicate
import groovy.sql.GroovyRowResult
import java.util.concurrent.TimeUnit
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.informatie.WaarOnwaar
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger
import nl.bzk.brp.funqmachine.processors.FileProcessor
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.TemplateProcessor
import nl.bzk.brp.funqmachine.processors.XmlProcessor
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import nl.bzk.brp.funqmachine.verstuurder.JmsVerstuurder
import nl.bzk.brp.funqmachine.wachttijd.FluentWait
import org.jbehave.core.annotations.*
import org.jbehave.core.failures.StepFailed
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.threeten.bp.Duration
import org.w3c.dom.Document
/**
 * Stappen voor het uitvoeren van Asynchrone SOAP services.
 */
@Steps
class AsynchroonBerichtenSteps {
    static final Logger logger = LoggerFactory.getLogger(AsynchroonBerichtenSteps)

    private static final long TIMEOUT_MINUTE = 10l;

    // Het aantal POLL_INTERVAL seconden dat er gewacht moet worden op het eerste bericht.
    static final int INITIAL_STABLE_WAIT_COUNT       = 15
    // Het aantal POLL_INTERVAL seconden dat er gewacht moet worden op een volgend bericht als er al berichten ontvangen zijn.
    static final int INTER_ARRIVAL_STABLE_WAIT_COUNT =  5
    static final int POLL_INTERVAL                   =  1

    @Autowired
    ScenarioRunContext runContext

    @Autowired
    private HttpLeveringOntvanger ontvanger

    @BeforeScenario
    final void startOntvanger() {
        if (ontvanger.receivedMessages != 0){
            logger.info 'Wacht met het resetten van de ontvanger tot alle berichten ontvangen zijn'
            wachtEnHaalberichtOp(null, null, null, null)
        }

        ontvanger?.reset()
        logger.info 'ontvanger is gereset'
    }

    @AfterStories
    final void cleanUp() {
        ontvanger?.stop()

        logger.info 'ontvanger is gestopt'
    }

    /**
     * ==== Handeling nogmaals leveren
     * Simuleert het (nogmaals) leveren van een administratieve handeling die voor
     * een wijziging van een persoonslijst heeft gezorgd. Dit houdt in dat de kolom
     * `tslev` van de tabel kern.admhnd wordt leeg gemaakt, en dat de de waarde van de
     * `ID` kolom op de queue voor verwerkte administratieve handelingen wordt geplaatst.
     * De mutatielevering software verwerkt deze administratieve handeling alsof deze zo juist
     * door de bijhouding is verwerkt.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt
     *      worden door de mutatielevering software.
     * @param index in aanduiding van de hoeveel na laatste handeling opnieuw geleverd
     *      gaat worden. `1` is de een na laatste, `2` de twee na laatste, etc. Technisch
     *      gezien kan `0` voor de laatste worden opgegeven, maar je kan ook
     *      link:#laatste_handeling_nogmaals_leveren[Laatste handeling nogmaals leveren] gebruiken
     */
    @When("voor persoon \$bsn wordt de \$index na laatste handeling geleverd")
    void resetLeveringVoorPersoon(String bsn, int index) {
        this.resetLeveringEnZetOpQueueVoorPersoon(bsn, index + 1)
    }

    /**
     * ==== Laatste handeling nogmaals leveren
     * Dit is de verkorte versie van link:#handeling_nogmaals_leveren[handeling nogmaals leveren]
     * om de laatste handeling mogmaals te leveren. Deze step leest functioneler dan `0 na laatste handeling`.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt
     *      worden door de mutatielevering software.
     */
    @When("voor persoon \$bsn wordt de laatste handeling geleverd")
    void resetLaatsteLeveringVoorPersoon(String bsn) {
        this.resetLeveringEnZetOpQueueVoorPersoon(bsn, 1)
    }

    /**
     * ==== Eerste handeling nogmaals leveren
     * Dit is de verkorte versie van link:#handeling_nogmaals_leveren[handeling nogmaals leveren] om de eerste handeling
     * mogmaals te leveren. Deze step is functioneler dan nagaan wat de index van de eerste
     * handeling van een persoon is.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt
     *      worden door de mutatielevering software.
     */
    @When("voor persoon \$bsn wordt de eerste handeling geleverd")
    void resetEersteLeveringVoorPersoon(String bsn) {
        this.resetLeveringEnZetOpQueueVoorPersoon(bsn, -1)
    }

    private void resetLeveringEnZetOpQueueVoorPersoon(String bsn, int index) {
        def admhnd = new SqlProcessor(Database.KERN).resetLevering(bsn, index)

        if (admhnd) {
            logger.info 'Handeling {} is gereset', admhnd

            GroovyRowResult row = new SqlProcessor(Database.KERN).loadAdmHnd(admhnd)

            def jms = new JmsVerstuurder()
            logger.info 'Zet handeling {} op de queue at {}', row.get("id"), row.get("tsreg")
            jms.plaatsHandelingOpQueueEnWachtTotVerwerkt(admhnd, Duration.of(120, SECONDS))
        }
    }

    /**
     * ==== Volledigbericht opzoeken in leveringen
     * Na het ontvangen van asynchrone berichten heeft de FunwMachinator deze ter beschikking, om daarop
     * nadere validatie te kunnen doen. Hiertoe is het nodig om een bericht te zoeken in deze
     * ontvangen berichten. Deze step geeft de mogelijkheid om een _volledigbericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een abonnement.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbericht
     */
    @When("volledigbericht voor leveringsautorisatie \$leveringsautorisatie wordt bekeken")
    void haalVolledigberichtOp(String leveringsautorisatie) {
        haalVolledigberichtOp(leveringsautorisatie, null)
    }

    /**
     * ==== Mutatiebericht opzoeken in leveringen
     * Na het ontvangen van asynchrone berichten heeft de FunwMachinator deze ter beschikking, om daarop
     * nadere validatie te kunnen doen. Hiertoe is het nodig om een bericht te zoeken in deze
     * ontvangen berichten. Deze step geeft de mogelijkheid om een _mutatiebericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een abonnement.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbeicht
     */
    @When("mutatiebericht voor leveringsautorisatie \$leveringsautorisatie wordt bekeken")
    void haalMutatieberichtOp(String leveringsautorisatie) {
       haalMutatieberichtOp(leveringsautorisatie, null)
    }

    /**
     * ==== Volledigbericht opzoeken in leveringen
     * Na het ontvangen van asynchrone berichten heeft de FunwMachinator deze ter beschikking, om daarop
     * nadere validatie te kunnen doen. Hiertoe is het nodig om een bericht te zoeken in deze
     * ontvangen berichten. Deze step geeft de mogelijkheid om een _volledigbericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een abonnement.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbericht
     * @param partijCode de partijCode die voorkomt in een volledigbericht
     */
    @When("volledigbericht voor partij \$partijCode en leveringsautorisatie \$leveringsautorisatie wordt bekeken")
    void haalVolledigberichtOp(String leveringsautorisatie, String partijCode) {
        def leveringBericht = vindLeveringVoor('Volledigbericht', leveringsautorisatie, null, partijCode)

        if (leveringBericht) {
            logger.debug 'mutatiebericht gevonden!'
        }
    }

    /**
     * ==== Mutatiebericht opzoeken in leveringen
     * Na het ontvangen van asynchrone berichten heeft de FunqMachinator deze ter beschikking, om daarop
     * nadere validatie te kunnen doen. Hiertoe is het nodig om een bericht te zoeken in deze
     * ontvangen berichten. Deze step geeft de mogelijkheid om een _mutatiebericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een abonnement.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een mutatiebericht
     * @param partijCode de partijCode die voorkomt in een mutatiebericht
     */
    @When("mutatiebericht voor partij \$partijCode en leveringsautorisatie \$leveringsautorisatie wordt bekeken")
    void haalMutatieberichtOp(String leveringsautorisatie, String partijCode) {
        def leveringBericht = vindLeveringVoor('Mutatiebericht', leveringsautorisatie, null, partijCode)

        if (leveringBericht) {
            logger.debug 'mutatiebericht gevonden!'
        }
    }

    private String vindNotificatieVoor(String partijCode) {
        logger.info "Zoek in {} ontvangen berichten naar Notificatiebericht voor partij >{}<", ontvanger.receivedMessages, partijCode
        def berichtIdx = ontvanger.messages.findLastIndexOf {msg ->
            def doc = XmlUtils.bouwDocument(msg)

            if (getNode('/brp:bhg_fiaNotificeerBijhoudingsplan', doc) == null) {
                return false
            }

            final Integer partijNode = getNode('//brp:stuurgegevens/brp:ontvangendePartij', doc)?.textContent?.toInteger()
            if (partijCode.toInteger() == partijNode) {
                logger.debug("Notificatiebericht gevonden voor {}", partijCode)
                return true
            }

            return false;
        }

        def result = new StepResult(StepResult.Soort.LEVERING)
        result.response = berichtIdx >= 0 ? ontvanger.messages[berichtIdx] : ''
        runContext << result

        return result.response
    }

    private String vindLeveringVoor(String levering, String leveringsautorisatieNaam, String soortDienst, String partijCode) {
        if (partijCode != null) {
            logger.info "Zoek in {} ontvangen berichten naar '{}' voor abo >{}< en partij >{}<", ontvanger.receivedMessages, levering, leveringsautorisatieNaam,
                    partijCode
        } else {
            logger.info "Zoek in {} ontvangen berichten naar '{}' voor abo >{}<", ontvanger.receivedMessages, levering, leveringsautorisatieNaam
        }

        def berichtIdx = ontvanger.messages.findLastIndexOf {msg ->
            def doc = XmlUtils.bouwDocument(msg)

            //check op bericht soort
            final String berichtSoortNode = getNode('//brp:parameters/brp:soortSynchronisatie', doc)?.textContent
            boolean gevonden = levering.equalsIgnoreCase(berichtSoortNode)

            final Integer berichtLeveringautorisatieId = getNode('//brp:parameters/brp:leveringsautorisatieIdentificatie', doc)?.textContent.toInteger();
            final Integer berichtDienstIdentificatieId = getNode('//brp:parameters/brp:dienstIdentificatie', doc)?.textContent.toInteger();

            List<String> logList = new LinkedList<>();
            if (gevonden && leveringsautorisatieNaam != null) {
                gevonden = AutAutContext.get().bestaatLeveringsautorisatie(berichtLeveringautorisatieId, leveringsautorisatieNaam);
                logList.add(String.format("leveringsautorisatie %s", leveringsautorisatieNaam));
            }

            if (gevonden && soortDienst != null) {
                gevonden = AutAutContext.get().bestaatDienst(berichtLeveringautorisatieId, leveringsautorisatieNaam, berichtDienstIdentificatieId, soortDienst);
                logList.add(String.format("soortdienst %s", soortDienst));
            }
            if (gevonden && partijCode != null) {
                final Integer partijNode = getNode('//brp:stuurgegevens/brp:ontvangendePartij', doc)?.textContent?.toInteger()
                gevonden = partijCode.toInteger() == partijNode;
                logList.add(String.format("partijCode %s", partijCode));
            }

            if (gevonden) {
                logger.debug("'{}' gevonden voor {}", levering, logList.toString())
            }

            return gevonden;
        }

        def result = new StepResult(StepResult.Soort.LEVERING)
        result.response = berichtIdx >= 0 ? ontvanger.messages[berichtIdx] : ''
        runContext << result

        return result.response
    }

    /**
     * ==== Geen bericht in ontvangen (synchronisatie)berichten gevonden
     * De, door de FunqMachinator, ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#mutatiebericht_opzoeken_in_leveringen[Mutatiebericht opzoeken in leveringen] en
     * link:#volledigbericht_opzoeken_in_leveringen[Volledigbericht opzoeken in leveringen]. Als er
     * geen bericht is gevonden, en dit wordt verwacht, kan dat met deze step worden gevalideerd.
     */
    @Then("is er geen synchronisatiebericht gevonden")
    void erIsGeenLeverBericht() {
        assert !getLeveringBericht()
    }

    /**
     * ==== Verwacht aantal (synchronisatie)berichten
     * De BRP levert asynchroon berichten naar aanleiding van verzoeken of
     * administratieve handelingen. De FunqMachinator ontvangt deze berichten
     * alsof het een afnemer(systeem) is. Met deze step wacht de FunqMachinator
     * totdat het aantal ontvangen berichten het aantal is dat is opgegeven.
     *
     * De ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#mutatiebericht_opzoeken_in_leveringen[Mutatiebericht opzoeken in leveringen] en
     * link:#volledigbericht_opzoeken_in_leveringen[Volledigbericht opzoeken in leveringen].
     *
     * [WARNING]
     * ====
     * Wachten op nul (`0`) berichten is meteen correct in deze step, en dan gaat
     * het scenario verder. Indien je daarop wilt valideren is het handiger om de step
     * link:#wacht_op_alle_synchronisatie_berichten[Wacht op alle (synchronisatie)berichten]
     * te gebruiken, aangezien die langer wacht voordat de conclusie "alles ontvangen" wordt getrokken.
     * ====
     *
     * @param count het aantal berichten dat door de FunqMachinator verwacht wordt.
     */
    @Then("{zijn|worden|is|wordt} er \$count bericht{en|} geleverd")
    void aantalOntvangenBerichtenIs(int count) {
        logger.info 'Validatie of {} berichten zijn ontvangen', count

        FluentWait<HttpLeveringOntvanger> wait = new FluentWait<>(ontvanger)
            .withTimeout(TIMEOUT_MINUTE, TimeUnit.MINUTES)
            .pollingEvery(3, TimeUnit.SECONDS)

        wait.until(new Predicate<HttpLeveringOntvanger>() {
            @Override
            boolean apply(final HttpLeveringOntvanger input) {
                logger.info '{} berichten van {} zijn ontvangen', input.receivedMessages, count
                return input.receivedMessages == count
            }
        })
    }

    private void wachtEnHaalberichtOp(String berichtSoort, String leveringsautorisatieNaam, String dienstSoort, String partijCode) {
        FluentWait<HttpLeveringOntvanger> wait = new FluentWait<>(ontvanger)
            .withTimeout(TIMEOUT_MINUTE, TimeUnit.MINUTES)
            .pollingEvery(POLL_INTERVAL, TimeUnit.SECONDS)

        wait.until(new Predicate<HttpLeveringOntvanger>() {
            private int prevCount = 0
            private int stable    = 0

            @Override
            boolean apply(final HttpLeveringOntvanger input) {
                int currentCount = input.receivedMessages
                int stableWaitCount = currentCount == 0 ? INITIAL_STABLE_WAIT_COUNT : INTER_ARRIVAL_STABLE_WAIT_COUNT
                if (currentCount > prevCount) {
                    prevCount = currentCount
                    stable = 0
                    logger.info '{} berichten zijn ontvangen', currentCount
                    if (leveringsautorisatieNaam != null && vindLeveringVoor(berichtSoort, leveringsautorisatieNaam, dienstSoort, partijCode)) {
                        logger.debug "${berichtSoort} gevonden!"
                        return true;
                    } else if ("Notificatiebericht".equals(berichtSoort) && vindNotificatieVoor(partijCode)) {
                        logger.debug "${berichtSoort} gevonden!"
                        return true;
                    }
                    return false
                } else if (++stable < stableWaitCount) {
                    logger.debug 'Zekerheid op alle leveringen is {}%', 100 * stable / stableWaitCount as int
                    return false
                } else {
                    logger.info 'Zekerheid op alle leveringen is 99.9%, {} berichten zijn ontvangen', input.receivedMessages
                    return true
                }
            }
        })
    }

    /**
     * ==== Wacht op volledigbericht voor een leveringsautorisatie
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _volledigbericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een leveringsautorisatie.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbeicht
     */
    @When("het volledigbericht voor leveringsautorisatie \$leveringsautorisatie is ontvangen en wordt bekeken")
    void wachtEnHaalVolledigberichtOp(String leveringsautorisatie) {
        logger.info "wacht totdat volledigbericht voor ${leveringsautorisatie} is ontvangen"

        wachtEnHaalVolledigberichtOpZonderDienst(leveringsautorisatie, null)
    }

    /**
     * ==== Wacht op volledigbericht voor een leveringsautorisatie
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _volledigbericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een leveringsautorisatie.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbericht
     * @param partijCode de partijcode dat voorkomt in een volledigbericht
     */
    @When("het volledigbericht voor partij \$partijCode en leveringsautorisatie \$leveringsautorisatie is ontvangen en wordt bekeken")
    void wachtEnHaalVolledigberichtOpZonderDienst(String leveringsautorisatie, String partijCode) {
        if (partijCode != null) {
            logger.info "wacht totdat volledigbericht voor ${leveringsautorisatie} en partij ${partijCode} is ontvangen"
        } else {
            logger.info "wacht totdat volledigbericht voor ${leveringsautorisatie} is ontvangen"
        }

        wachtEnHaalberichtOp('Volledigbericht', leveringsautorisatie, null, partijCode)
    }

    /**
     * ==== Wacht op volledigbericht voor een leveringsautorisatie
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _volledigbericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een leveringsautorisatie.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een volledigbericht
     * @param partijCode de partijcode dat voorkomt in een volledigbericht
     * @param dienstSoort De dienst soort
     */
    @When("het volledigbericht met dienst \$dienstSoort voor partij \$partijCode en leveringsautorisatie \$leveringsautorisatie is ontvangen en wordt bekeken")
    void wachtEnHaalVolledigberichtOpMetDienst(String leveringsautorisatie, String partijCode, String dienstSoort) {
        if (partijCode != null) {
            logger.info "wacht totdat volledigbericht voor ${leveringsautorisatie} en partij ${partijCode} is ontvangen"
        } else {
            logger.info "wacht totdat volledigbericht voor ${leveringsautorisatie} is ontvangen"
        }

        wachtEnHaalberichtOp('Volledigbericht', leveringsautorisatie, dienstSoort, partijCode)
    }

    /**
     * ==== Wacht op mutatiebericht voor een leveringsautorisatie
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _mutatiebericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een leveringsautorisatie.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een mutatiebeicht
     */
    @When("het mutatiebericht voor leveringsautorisatie \$leveringsautorisatie is ontvangen en wordt bekeken")
    void wachtEnHaalMutatieberichtOp(String leveringsautorisatie) {
        wachtEnHaalMutatieberichtOp(leveringsautorisatie, null)
    }

    /**
     * ==== Wacht op mutatiebericht voor een leveringsautorisatie
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _mutatiebericht_ te kunnen
     * opzoeken dat is verstuurd naar de houder van een leveringsautorisatie.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param abonaam de abonnementnaam dat voorkomt in een mutatiebericht
     * @param partijCode de partijcode dat voorkomt in een mutatiebericht
     */
    @When("het mutatiebericht voor partij \$partijCode en leveringsautorisatie \$leveringsautorisatie is ontvangen en wordt bekeken")
    void wachtEnHaalMutatieberichtOp(String leveringsautorisatie, String partijCode) {
        if (partijCode != null) {
            logger.info "wacht totdat mutatiebericht voor ${leveringsautorisatie} en partij ${partijCode} is ontvangen"
        } else {
            logger.info "wacht totdat mutatiebericht voor ${leveringsautorisatie} is ontvangen"
        }

        wachtEnHaalberichtOp('Mutatiebericht', leveringsautorisatie, null, partijCode)
    }

    /**
     * ==== Wacht op notificatiebericht voor een partij
     * Tijdens het ontvangen van asynchrone berichten zoeken naar een specifiek bericht.
     * Deze step geeft de mogelijkheid om een _notificatiebericht_ te kunnen
     * opzoeken dat is verstuurd naar een partij.
     *
     * Alle ontvangen berichten worden bewaard, zodat daarin kan worden gezocht
     * naar een specifiek bericht dat dan nader kan worden gevalideerd. Dit selecteren
     * kan door middel van de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en
     * link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is ontvangen en wordt bekeken].
     *
     * Het is overbodig om de stap link:#ontvangAlleBerichten[wacht tot alle berichten zijn ontvangen] uit te voeren vóór deze stap. Wel kan die stap
     * nuttig zijn om het scenario af te sluiten, zodat een volgend scenario geen 'oude' berichten ontvangt.
     *
     * @param partijCode de partijcode dat voorkomt in een notificatiebericht
     */
    @When("het notificatiebericht voor partij \$partijCode is ontvangen en wordt bekeken")
    void wachtEnHaalNotificatieberichtOp(String partijCode) {
        logger.info "wacht totdat notificatiebericht voor partij ${partijCode} is ontvangen"

        wachtEnHaalberichtOp('Notificatiebericht', null, null, partijCode)
    }

    /**
     * ==== Wacht op alle (synchronisatie)berichten
     * De BRP levert asynchroon berichten naar aanleiding van verzoeken of
     * administratieve handelingen. De FunqMachinator ontvangt deze berichten
     * alsof het een afnemer(systeem) is. Met deze step wacht de FunqMachinator,
     * en checkt regelmatig, totdat het aantal ontvangen berichten een X aantal keer niet
     * meer is gewijzigd. Dan wordt aangenomen dat er niet meer berichten komen,
     * en dat alle berichten zijn geleverd.
     *
     * Deze step zorgt ervoor dat een volgend uit te voeren scenario binnen dezelfde story geen 'oude' berichten ontvangt.
     *
     * Indien een een specifiek bericht gevalideerd moet worden, gebruik dan de steps: link:#wachtEnHaalVolledigberichtOp[het volledigbericht voor
     * leveringsautorisatie \$abonaam is ontvangen en wordt bekeken] en link:#wachtEnHaalMutatieberichtOp[het mutatiebericht voor leveringsautorisatie \$abonaam is
     * ontvangen en wordt bekeken].
     */
    @Then("wacht tot alle berichten zijn ontvangen")
    void ontvangAlleBerichten() {
        logger.info 'Wacht totdat alle berichten zijn ontvangen...'
        wachtEnHaalberichtOp(null, null, null, null)
    }

    /**
     * ==== Controleer aantal ontvangen berichten
     * Valideert dat de FunqMachinator een `x` aantal berichten heeft ontvangen.
     * Deze step kan worden gebruikt na link:#wacht_op_alle_synchronisatie_berichten[Wacht op alle (synchronisatie)berichten],
     * waarbij toch nog moet worden gecontroleerd op het aantal berichten.
     *
     * Dit is vooral handig bij het verwachte aantal 0, aangezien de _wacht op x berichten_ step hier niet op
     * kan controleren, aanzien er altijd een moment is waarop geen berichten zijn ontvangen.
     *
     * @param count het aantal berichten waarvan is verwacht dat ze zijn geleverd
     */
    @Then("is het aantal ontvangen berichten \$count")
    void valideerHetAantalOntvangenBerichten(int count) {
        assert ontvanger.receivedMessages == count : "Het aantal ontvangen berichten is $ontvanger.receivedMessages. Het verwachte aantal is $count"
    }

    /**
     * ==== Expected met synchronisatiebericht vergelijken
     * Valideert een synchronisatiebericht met een expected bestand. Het expected
     * bestand wordt als template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn.
     * De validatie wordt gedaan door te kijken of de opgegeven xpath
     * expressie in het resultaat hetzelfde is als in het expected bestand.
     *
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van synchronisatiebericht en verwacht bestand
     */
    @Then("is het synchronisatiebericht gelijk aan \$expected voor expressie \$regex")
    @Deprecated
    void synchronisatieBerichtVoldoetAanExpected(String expected, String regex) {
        isSynchronisatieBerichtBeschikbaar()

        StepResult result = runContext.findAll { it.soort == StepResult.Soort.LEVERING }.last()

        expected = new FileProcessor().geefFileOfRedirectedFile(expected)

        final TemplateProcessor templateProcessor = new TemplateProcessor()
        result.expected = templateProcessor.verwerkTemplateBestand(expected, runContext.data)
        new XmlProcessor().vergelijk(regex, result.expected, result.response)
    }

    /**
     * ==== Attribuut in (synchronisatie)bericht controleren
     * Valideert of in het synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat
     * er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     *
     * [source,xml]
     * ----
     *   &lt;adres&gt;
     *     &lt;huisnummer&gt;14&lt;/huisnummer&gt;
     *     ...
     *   &lt;/adres&gt;
     * ----
     *
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param groep de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan
     *      een keer voorkomt, geef dan de waardes in de volgorde waarin ze in het bericht staan.
     */
    @Then("heeft '\$attribuut' in '\$groep' de waarde{s|} '\$verwachteWaardes'")
    void heeftGroepenGesorteerd(final String attribuut, final String groep, final List<String> verwachteWaardes) {
        isSynchronisatieBerichtBeschikbaar()
        new XmlProcessor().waardesVanAttributenInVolgorde(this.leveringBericht, groep, attribuut, verwachteWaardes)
    }

    private void isSynchronisatieBerichtBeschikbaar() {
        def bericht = getLeveringBericht()

        if (!bericht) {
            logger.error 'Er is geen synchronisatiebericht om te valideren'
            throw new StepFailed("Er is geen synchronisatiebericht om te valideren", new AssertionMisluktError('geen synchronisatiebericht opgevraagd'))
        }
    }

    /**
     * ==== Controleert xpath
     * Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param xpathExpressie
     */
    @Then("heeft het bericht geen kinderen voor xpath \$xpathExpressie")
    void synchronisatieberichtHeeftGeenKinderenVoorXPath(String xpathExpressie) {
        new XmlProcessor().berichtHeeftGeenKinderenVoorXPath(xpathExpressie, getLeveringBericht())
    }

    /**
     * ==== Controleert xpath
     * Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param regex
     * @param waarde
     */
    @Then("heeft het bericht voor xpath \$regex de waarde \$waarde")
    void synchronisatieberichtHeeftXPathWaarde(String regex, String waarde) {
        logger.debug 'Valideer verwerking van antwoord'
        new XmlProcessor().xpathEvalueertNaar(waarde, regex, getLeveringBericht())
    }

    /**
     * ==== Controleert xpath
     * Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.
     *
     * @param xpathExpressie
     * @param waarde
     */
    @Then("heeft het bericht voor xpath \$xpathExpressie de platgeslagen waarde \$waarde")
    void synchronisatieberichtAlsPlatteTekstVanafXPath(String xpathExpressie, String waarde) {
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().berichtAlsPlatteTekstVanafXPath(waarde, xpathExpressie, getLeveringBericht());
    }

    /**
     * === Controleert xpath
     * Controleert of gegeven xpath evalueert naar een node uit het xml leveringbericht
     *
     * @param xpathExpressie
     */
    @Then("is er voor xpath \$xpath een node aanwezig in het levering bericht")
    void antwoordberichtHeeftGegevenNodeVoorXpath(String xpath){
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().xpathBestaat(xpath, getLeveringBericht())
    }

    /**
     * === Controleert xpath
     * Controleert of er voor gegeven xpath geen node aanwezig is in het xml leveringbericht
     *
     * @param xpathExpressie
     */
    @Then("is er voor xpath \$xpath geen node aanwezig in het levering bericht")
    void antwoordberichtHeeftGeenNodeVoorXpath(String xpath){
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().xpathBestaatNiet(xpath, getLeveringBericht())
    }

    /**
     * ==== Attributen in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat
     * er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     *
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut
     * en waardes worden gebruikt in plaats van de step die slechts een attribuut controleert.
     *
     * De tabel voor deze step ziet er als volgt uit:
     *
     * ----
     * groep | attribuut  | verwachteWaardes
     * adres | huisnummer | 14, 99
     * ----
     *
     * @param sorteringRegels de tabel met te valideren
     */
    @Then("hebben de attributen in de groepen de volgende waarde{s|}: \$sorteringRegels")
    void heeftGroepenGesorteerdInLeveringTabel(final List<SorteringRegel> sorteringRegels) {
        isSynchronisatieBerichtBeschikbaar()

        sorteringRegels.each {
            sorteringRegel ->
                new XmlProcessor().waardesVanAttributenInVolgorde(this.leveringBericht, sorteringRegel.groep, sorteringRegel.attribuut, sorteringRegel.verwachteWaardes)
        }
    }

    /**
     * ==== Groepen in een bericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het bericht \$aantal groep{en|} '\$groep'")
    void heeftAantalVoorkomensVanEenGroepInLevering(Integer aantal, String groep) {
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().heeftAantalVoorkomensVanEenGroep(aantal, groep, getLeveringBericht())
    }

    /**
     * ==== Groepen in een antwoordbericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht '\$groep' \$aanwezig")
    void isGroepInResponseAanwezig(final String groep, final String aanwezig) {
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().heeftVoorkomenVanEenGroep(aanwezig, groep, getLeveringBericht())
    }

    /**
     * ==== Attribuut waardes in (synchronisatie)bericht controleren
     * Valideert of in het synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     *
     * [source,xml]
     * ----
     *   &lt;adres&gt;
     *     &lt;huisnummer&gt;14&lt;/huisnummer&gt;
     *     ...
     *   &lt;/adres&gt;
     * ----
     *
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param nummer geeft aan welk voorkomen gevalideerd moet worden
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan
     *      een keer voorkomt, geef dan de waardes in de volgorde waarin ze in het bericht staan.
     */
    @Then("heeft '\$attribuut' in '\$voorkomen' nummer \$nummer de waarde '\$verwachteWaarde'")
    void heeftAttribuutInVoorkomenWaarde(final String attribuut, final String voorkomen, final Integer nummer, final String verwachteWaarde) {
        isSynchronisatieBerichtBeschikbaar()

        heeftVoorkomenAttribuut(voorkomen, nummer, attribuut, verwachteWaarde)
    }

    /**
     * ==== Waardes van attributen in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut
     * en waardes worden gebruikt in plaats van de step die slechts een attribuut controleert.
     *
     * De tabel voor deze step ziet er als volgt uit:
     *
     * ----
     * groep | nummer | attribuut  | verwachteWaarde
     * adres | 2      | huisnummer | 14
     * ----
     *
     * @param attribuutRegels de tabel met te valideren waardes
     */
    @Then("hebben attributen in voorkomens de volgende waarde{s|}: \$attribuutRegels")
    void hebbenAttributenWaardesInTabel(final List<VoorkomenAttribuutRegel> attribuutRegels) {
        isSynchronisatieBerichtBeschikbaar()

        attribuutRegels.each {regel ->
            heeftVoorkomenAttribuut(regel.groep, regel.nummer, regel.attribuut, regel.verwachteWaarde)
        }
    }

    private void heeftVoorkomenAttribuut(String groep, Integer nummer, String attr, String verwachteWaarde) {
        final String expr = """//brp:$groep"""
        def node = XmlUtils.getNodes(expr, XmlUtils.bouwDocument(this.leveringBericht)).item(nummer - 1)

        node = node.childNodes.find {
            it.nodeName == "brp:$attr" as String
        }

        final String werkelijkeWaarde = node?.textContent
        assert werkelijkeWaarde == verwachteWaarde:
            "Waarde van $attr in de groep $groep[$nummer] is niet correct. Verwacht was '$verwachteWaarde'. Werkelijk was '$werkelijkeWaarde'."
    }

    /**
     * ==== Attribuut aanwezigheid in (synchronisatie)bericht controleren
     * Valideert of in het synchronisatiebericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     *
     * [source,xml]
     * ----
     *   &lt;adres&gt;
     *     &lt;huisnummer&gt;14&lt;/huisnummer&gt;
     *     ...
     *   &lt;/adres&gt;
     * ----
     *
     * @param attribuut het attribuut (in XML een element) waarvan de aanwezigheid worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param nummer geeft aan welk voorkomen gevalideerd moet worden
     * @param aanwezig de verwachte aanwezigheid van het gegeven attribuut
     */
    @Then("is de aanwezigheid van '\$attribuut' in '\$voorkomen' nummer \$nummer \$aanwezig")
    void isAttribuutInVookomenAanwezig(final String attribuut, final String voorkomen, final Integer nummer, final String aanwezig) {
        isSynchronisatieBerichtBeschikbaar()

        isVoorkomenAttribuutAanwezig(voorkomen, nummer, attribuut, WaarOnwaar.isWaar(aanwezig))
    }

    /**
     * ==== Aanwezigheid van attributen in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut
     * en aanwezigheid worden gebruikt in plaats van de step die slechts een attribuut controleert.
     *
     * De tabel voor deze step ziet er als volgt uit:
     *
     * ----
     * groep | nummer | attribuut  | aanwezig
     * adres | 2      | huisnummer | true
     * adres | 2      | huisletter | false
     * ----
     *
     * @param attribuutRegels de tabel met te valideren aanwezigheid
     */
    @Then("hebben attributen in voorkomens de volgende aanwezigheid: \$attribuutRegels")
    void zijnAttributenAanwezigInTabel(final List<VoorkomenAttribuutAanwezigRegel> attribuutRegels) {
        isSynchronisatieBerichtBeschikbaar()

        attribuutRegels.each {regel ->
            isVoorkomenAttribuutAanwezig(regel.groep, regel.nummer, regel.attribuut, regel.isAanwezig())
        }
    }

    private void isVoorkomenAttribuutAanwezig(String groep, Integer nummer, String attr, boolean aanwezig) {
        final String expr = """//brp:$groep"""
        def node = XmlUtils.getNodes(expr, XmlUtils.bouwDocument(this.leveringBericht)).item(nummer - 1)

        node = node?.childNodes.find {
            it.nodeName == "brp:$attr" as String
        }

        assert (node != null) == aanwezig :
            "Aanwezigheid van $attr in de groep $groep[$nummer] is niet correct. Verwacht was '$aanwezig'. Werkelijk was '${node != null}'."
    }

    /**
     * ==== Waarde van het xml attribuut "verwerkingssoort" in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht de verwerkingssoort van een groep een verwachte waarde heeft.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * @param nummer geeft aan welk voorkomen gevalideerd moet worden
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param waarde de verwachte waarde van de verwerkingssoort
     */
    @Then("is de verwerkingssoort van groep \$groep in voorkomen \$voorkomen, \$verwerkingssoort")
    void heeftGroepVerwerkingssoort(final String groep, final Integer voorkomen, final String verwerkingssoort) {
        isSynchronisatieBerichtBeschikbaar()

        new XmlProcessor().waardeVanXmlAttribuut(getLeveringBericht(), groep, voorkomen - 1, "brp:verwerkingssoort", verwerkingssoort)
    }

    /**
     * ==== Waardes van het xml attribuut "verwerkingssoort" in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut
     * en waardes worden gebruikt in plaats van de step die slechts een attribuut controleert.
     *
     * De tabel voor deze step ziet er als volgt uit:
     *
     * ----
     * groep                | nummer                 | verwerkingssoort
     * persoon              | 1                      | Wijziging
     * ----
     *
     * @param verwerkingssoortRegels de tabel met te valideren
     */
    @Then("hebben verwerkingssoorten in voorkomens de volgende waardes: \$verwerkingssoortRegels")
    void heeftGroepenVerwerkingssoort(final List<VerwerkingssoortRegel> verwerkingssoortRegels) {
        isSynchronisatieBerichtBeschikbaar()

        verwerkingssoortRegels.each {
            verwerkingssoortRegel ->
                new XmlProcessor().waardeVanXmlAttribuut(this.leveringBericht, verwerkingssoortRegel.groep, verwerkingssoortRegel.nummer - 1,
                    "brp:verwerkingssoort", verwerkingssoortRegel.verwerkingssoort)
        }
    }

    /**
     * ==== Verantwoording correct
     * Valideert of de acties in het `&lt;verantwoording&gt;` deel van het bericht
     * ook voorkomen bij de groepen van de persoon(en) in het bericht. Indien er
     * inconsistentie is, dus acties bij een persoon die niet in de verantwoording zijn
     * opgenomen, of vice versa, zal dit worden gemeld.
     */
    @Then("verantwoording acties staan in persoon")
    void alleVerantwoordingActiesInPersoon() {
        isSynchronisatieBerichtBeschikbaar()

        final Document doc = XmlUtils.bouwDocument(this.leveringBericht)
        final List<String> verantwActies = XmlUtils.getNodes("//*[@brp:objecttype='Actie']", doc).collect {
            it.attributes.getNamedItem('brp:objectSleutel').nodeValue
        }.unique().sort()

        final List<String> acties = XmlUtils.getNodes("//brp:actieInhoud | //brp:actieVerval | //brp:actieAanpassingGeldigheid", doc).collect {
            it.textContent
        }.unique().sort()

        assert verantwActies == acties
    }

    /**
     * ==== Expected met synchronisatiebericht vergelijken
     * Valideert of een synchronisatiebericht voldoet aan een verwacht resultaat.
     *
     * @param expected het bestand met het verwachte resultaat
     * @param regex xpath expressie voor het vergelijken van synchronisatiebericht en verwacht bestand
     */
    @Then("is het bericht gelijk aan \$expected voor expressie \$regex")
    @Deprecated
    void leveringVoldoetAanExpected(String expected, String regex) {
        this.synchronisatieBerichtVoldoetAanExpected(expected, regex)
    }

    /**
     * ==== XSD-valide bericht
     * Valideert of een synchronisatiebericht correct is volgend de XSD's van
     * het koppelvlak. De versie van het koppelvlak dat wordt gebruikt staat in de
     * _dependencies_ van de FunqMachinator.
     */
    @Then("is het bericht xsd-valide")
    void leveringVoldoetAanXsd() {
        isSynchronisatieBerichtBeschikbaar()

        XmlUtils.valideerOutputTegenSchema(this.leveringBericht ?: '', 'master.xsd')
    }

    /**
     * ==== Archivering van synchronisatieberichten controleren
     * Controleert of het synchronisatiebericht dat is opgehaald, ook is gearchiveerd in de 'ber.ber'-tabel.
     */
    @Then("is het bericht gearchiveerd")
    void leveringIsGearchiveerd() {
        isSynchronisatieBerichtBeschikbaar()
        final Document doc = XmlUtils.bouwDocument(this.leveringBericht)
        def referentienummer = getNode("//brp:stuurgegevens/brp:referentienummer", doc).getTextContent()

        logger.info "Archivering voor leveringbericht wordt bekeken met referentienummer: ${referentienummer}"

        assert new SqlProcessor(Database.BER).isLeveringBerichtGearchiveerd(referentienummer)
    }

    public String getLeveringBericht() {
        def leveringen = runContext.findAll { StepResult.Soort.LEVERING == it.soort}

        if (!leveringen.isEmpty()) {
            return leveringen.last().response
        }

        return null
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met voorkomen / attribuut aanduiding.
     */
    @AsParameters
    public static class VoorkomenAttribuutRegel {
        @Parameter(name = "groep")
        String  groep
        @Parameter(name = "nummer")
        Integer nummer
        @Parameter(name = "attribuut")
        String  attribuut
        @Parameter(name = "verwachteWaarde")
        String  verwachteWaarde
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met voorkomen / attribuut aanwezig.
     */
    @AsParameters
    public static class VoorkomenAttribuutAanwezigRegel {
        @Parameter(name = "groep")
        String  groep
        @Parameter(name = "nummer")
        Integer nummer
        @Parameter(name = "attribuut")
        String  attribuut
        @Parameter(name = "aanwezig")
        String aanwezig

        boolean isAanwezig() {
            return WaarOnwaar.isWaar(aanwezig)
        }
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met sorteringen.
     */
    @AsParameters
    public static class SorteringRegel {
        @Parameter(name = "groep")
        String groep
        @Parameter(name = "attribuut")
        String attribuut
        @Parameter(name = "verwachteWaardes")
        String verwachteWaardes

        List<String> getVerwachteWaardes() {
            Arrays.asList(verwachteWaardes.split(','))
        }
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
     */
    @AsParameters
    public static class VerwerkingssoortRegel {
        @Parameter(name = "groep")
        String  groep
        @Parameter(name = "nummer")
        Integer nummer
        @Parameter(name = "verwerkingssoort")
        String  verwerkingssoort
    }
}

