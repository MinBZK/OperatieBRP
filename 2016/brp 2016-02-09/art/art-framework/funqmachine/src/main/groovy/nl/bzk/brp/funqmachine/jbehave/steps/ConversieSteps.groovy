package nl.bzk.brp.funqmachine.jbehave.steps
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.converters.FileConverter
import nl.bzk.brp.funqmachine.processors.conversie.ConversieDataVuller
import org.jbehave.core.annotations.Given
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
/**
 * Stappen om Pls te converteren en te persisteren
 */
@Steps
class ConversieSteps {

    private static final Logger LOG = LoggerFactory.getLogger(ConversieSteps)

    @Autowired
    private BevraagbaarContextView contextView

    @Autowired
    private ConversieDataVuller geconverteerdeDataVuller;

    @Given("enkel initiele vulling uit bestand \$filenaam")
    void enkelInitieleVullingUitBestand(String filenaam) {
        final File file = geefFile(filenaam)
        geconverteerdeDataVuller.converteerInitieleVullingPL(file);
    }

    @Given("enkel sync uit bestand \$filenaam")
    void enkelSyncUitBestand(String filenaam) {
        final File file = geefFile(filenaam)
        geconverteerdeDataVuller.converteerSyncPL(file);
    }

    @Given("een sync uit bestand \$filenaam")
    void conversieSynchronisatie(String filenaam) {
        final File file = geefFile(filenaam)
        geconverteerdeDataVuller.converteer(file);
    }

    /**
     * Geeft de file op basis van een filenaam binnen zijn classpath.
     * @param fileNaam
     * @return de file als deze gevonden kan worden.
     */
    protected File geefFile(String fileNaam) {
        if (fileNaam.startsWith('/')) {
            return new FileConverter().convertFile(fileNaam)
        } else {
            return new ResourceResolver(contextView).resolve(fileNaam)
        }
    }
}
