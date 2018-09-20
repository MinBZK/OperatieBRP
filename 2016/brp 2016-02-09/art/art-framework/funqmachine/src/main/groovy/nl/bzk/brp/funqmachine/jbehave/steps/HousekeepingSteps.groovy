package nl.bzk.brp.funqmachine.jbehave.steps
import groovy.text.SimpleTemplateEngine
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.converters.FileConverter
import nl.bzk.brp.funqmachine.processors.HttpProcessor
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import org.jbehave.core.annotations.Alias
import org.jbehave.core.annotations.Given
import org.jbehave.core.steps.ParameterConverters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
/**
 * Pre en post processing die onafhankelijk van een scenario gedaan kan worden.
 */
@Steps
class HousekeepingSteps {
    static final Logger logger = LoggerFactory.getLogger(HousekeepingSteps)

    @Autowired
    private ScenarioRunContext runContext

    @Inject
    private JmxConnectorService jmxConnector;

    @Inject
    private DataTaalContextLader dataTaalContextLader;

    /**
     * ==== Persoon resetten
     * Zet personen in de database terug naar de bekende toestand.
     * Dit houdt in dat personen worden verwijderd middels een _cascading delete_
     * en daarna worden toegevoegd middels de SQL statements die zijn gegenereerd
     * door de test-data tools.
     *
     * @param bsns één of meerdere (komma gescheiden) burgerservicenummers van de personen
     *      die gereset moeten worden
     */
    @Given("de database {is|wordt} gereset voor de personen \$bsns")
    @Alias("de personen \$bsns {zijn|worden} gereset")
    void resetPeople(List<String> bsns) {
        logger.debug "Resetting bsns: $bsns"

        new SqlProcessor(Database.KERN).resetPersonen(bsns)
    }

    /**
     * ==== Persoon verwijderen
     * Verwijdert personen uit de database middels een _cascading delete_. Gebruik dit
     * bij voorkeur met personen die in een scenario zijn gecreeerd. De effectieve implementatie
     * doet hetzelfde als de *Reset personen* step.
     *
     * @param bsns één of meerdere (komma gescheiden) burgerservicenummers van de personen
     *      die verwijderd moeten worden
     */
    @Given("de personen \$bsns {zijn|worden} verwijderd")
    void deletePeople(List<String> bsns) {
        logger.debug "Verwijderen bsns: $bsns"

        new SqlProcessor(Database.KERN).verwijderPersonen(bsns)
    }

    /**
     * ==== Personen verwijderen
     * Verwijdert alle personen uit de database middels een truncate cascade delete.
     */
    @Given("alle personen zijn verwijderd")
    void verwijderAllePersonen() {
        logger.debug "Schoon alle personen"

        new SqlProcessor(Database.KERN).verwijderAllePersonen()
    }

    /**
     * wacht het gegeven aantal seconden
     * @param sec aantal seconden
     */
    @Given("wacht \$sec seconden")
        void wacht(String sec) {
        TimeUnit.SECONDS.sleep(Integer.parseInt(sec))
    }

    /**
     * ==== Persoon DSL uitvoeren
     * Voer een _Persoon DSL_ script uit dat een of meer gebeurtenissen beschrijft.
     *
     * @param dsl het DSL script dat moet worden uitgevoerd
     */
    @Given("de persoon beschrijvingen: \$dsl")
    void laadPersonenMetDsl(String dsl) {
        //logger.info("Laad personen met DSL {}", dsl)
        dataTaalContextLader.zetContext()
        try {
            def applicationServerTime = HttpProcessor.applicationServerTime
            logger.info("Creating PersoonDSLExecutor with {}", applicationServerTime)
            new PersoonDSLExecutor(applicationServerTime).execute(dsl)
        } catch (Exception e) {
            throw new ParameterConverters.ParameterConvertionFailed('Kan DSL niet uitvoeren', e)
        }
    }

    /**
     * Voert een _Persoon DSL_ script met daarin 1 of meerdere persoonsbeschrijvingen.
     * @param persoonbestand de bestandsnaam voor het DSL script
     */
    @Given("de persoonsbeschrijving in \$persoon")
    @Alias("de persoonsbeschrijvingen in \$persoon")
    void laadPersonenMetDslUitBestand(String persoon) {
        def persoonsbestand = new FileConverter().convertFile(persoon)
        def engine = new SimpleTemplateEngine()
        def template = engine.createTemplate(persoonsbestand).make()
        laadPersonenMetDsl(template.toString())
    }

    /**
     * ==== Extra gebeurtenissen DSL uitvoeren op een standaardpersoon
     * Voer een _Persoon DSL_ script uit een standaardpersoonbestand uit aangevuld met gebeurtenissen.
     *
     * @param persoonsbestand het bestand met het DSL script dat moet worden uitgevoerd
     * @param bsn het bsn dat aan de standaardpersoon gegeven wordt
     * @param anr het anr dat aan de standaardpersoon gegeven wordt
     * @param dsl de gebeurtenissen die toegevoegd moeten worden aan de standaardpersoon
     */
    @Given("de standaardpersoon \$persoon met bsn \$bsn en anr \$anr met extra gebeurtenissen: \$dsl")
    void laadPersonenMetDslUitBestand(String persoon, String bsn, String anr, String dsl) {
        def persoonsbestand = new FileConverter().convertFile("/standaardpersonen/$persoon")
        def engine=new SimpleTemplateEngine()
        def binding=[bsn:bsn,anr:anr,gebeurtenissen:dsl]
        def template = engine.createTemplate(persoonsbestand).make(binding)
        laadPersonenMetDsl(template.toString())
    }

    /**
     * ==== DSL uitvoeren van een standaardpersoon
     * Voer een _Persoon DSL_ script uit een standaardpersoonbestand uit.
     *
     * @param persoonsbestand het bestand met het DSL script dat moet worden uitgevoerd
     * @param bsn het bsn dat aan de standaardpersoon gegeven wordt
     * @param anr het anr dat aan de standaardpersoon gegeven wordt
     */
    @Given("de standaardpersoon \$persoon met bsn \$bsn en anr \$anr zonder extra gebeurtenissen")
    void laadPersonenMetDslUitBestand(String persoon, String bsn, String anr) {
        laadPersonenMetDslUitBestand(persoon, bsn, anr, null)
    }

    /**
     * ==== Zet indicatie dat cache ververst dient te worden
     * Zet indicatie dat cache ververst dient te worden
     */
    @Given("de cache is herladen")
    void herlaadCache(){
        runContext.setCacheHeeftActivatieRefreshNodig(true)
        jmxConnector.herlaadCaches()
    }

}
