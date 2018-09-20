package nl.bzk.brp.funqmachine.jbehave.steps

import com.google.common.base.Predicate
import groovy.sql.Sql
import javax.annotation.PostConstruct
import javax.sql.DataSource
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.funqmachine.datalezers.DataProcessor
import nl.bzk.brp.funqmachine.informatie.WaarOnwaar
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.jbehave.converters.FileConverter
import nl.bzk.brp.funqmachine.mapper.BerichtenMapper
import nl.bzk.brp.funqmachine.processors.FileProcessor
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.TemplateProcessor
import nl.bzk.brp.funqmachine.processors.XmlProcessor
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import nl.bzk.brp.funqmachine.verstuurder.SoapVerstuurder
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters
import nl.bzk.brp.funqmachine.wachttijd.SqlWait
import nl.bzk.brp.funqmachine.wachttijd.Wait
import org.apache.commons.beanutils.PropertyUtilsBean
import org.jbehave.core.annotations.Alias
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Named
import org.jbehave.core.annotations.Parameter
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.model.ExamplesTable
import org.jbehave.core.steps.Parameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.threeten.bp.Duration
import org.xml.sax.SAXParseException

/**
 * Stappen voor het uitvoeren van synchrone SOAP services.
 */
@Steps
class SynchroonBerichtenSteps {
    private final Logger logger = LoggerFactory.getLogger(SynchroonBerichtenSteps)

    @Autowired
    private ScenarioRunContext runContext

    @Autowired
    private DataProcessor dataProcessor

    @Autowired
    private BevraagbaarContextView contextView

    @Autowired
    private DatabaseSteps databaseSteps;

    private def soapVerstuurder = new SoapVerstuurder()

    String sjabloonFilename
    String soapEndpoint
    String soapNamespace

    private Map<String, String> partijNaamNaarCode = new HashMap<>();
    private Map<String, String> partijNaamNaarOin = new HashMap<>();

    @PostConstruct
    public void naMaak() {
        final DataSource ds = databaseSteps.geefLezenSchrijvenDataSource();
        final List<Map<String, String>> naamCodeLijst = new JdbcTemplate(ds).queryForList("select naam, code, oin from kern.partij");
        for (Map<String, String> naamCode : naamCodeLijst) {
            partijNaamNaarCode.put(naamCode.get("naam"), naamCode.get("code"))
            partijNaamNaarOin.put(naamCode.get("naam"), naamCode.get("oin"))
        }
    }

    /**
     * ==== Prepareer een administratieve handeling
     * Haalt informatie bij elkaar om een administratieve handeling te kunnen versturen naar de BRP Bijhouding.
     * Op basis van de administratieve handeling en de acties worden de berichttemplate en
     * bijbehorende default YML bestanden verzameld.
     * Hoe deze informatie wordt verwerkt staat beschreven bij link:#bijhouding[Berichten - bijhouding].
     *
     * @param beschrevenHandeling de handeling waarvoor de berichttemplate en default YML wordt opgehaald
     * @param soortActies de acties waarvoor extra (default) YML wordt verzameld om in het bericht in te vullen
     */
    @Given("administratieve handeling van type \$beschrevenHandeling , met de acties \$soortActies")
    // Met de complexe bijhouding worden er geen administratieve handelingen meer ingeschoten, maar bijhoudingsvoorstellen.
    @Alias("bijhoudingsvoorstel van type \$beschrevenHandeling , met de acties \$soortActies")
    void maakBijhoudingBericht(String beschrevenHandeling, List<String> soortActies) {
        final List<File> files = []
        final String soortBericht = BerichtenMapper.geefBerichtVoorSoortVerzoek(beschrevenHandeling)
        final String berichtMap = "/berichtdefinities/${soortBericht}"

        final File bericht = geefFile("${berichtMap}/${soortBericht}.yml")
        files.add(bericht)

        final File admHnd = geefFile("${berichtMap}/${beschrevenHandeling}/${beschrevenHandeling}.yml")
        files.add(admHnd)

        for (String soortActie : soortActies) {
            File file = geefFile("${berichtMap}/${beschrevenHandeling}/acties/${soortActie}.yml")
            files.add(file)
        }

        for (File file : files) {
            // Voeg specifieke waarden
            runContext.data = dataProcessor.process(runContext.data, file, null)
        }

        def verzoek = AutAutContext.get().getAutorisatieVoorVerzoek()
        if (verzoek != null) {
            final String partijCode = partijNaamNaarCode.get(verzoek.partijNaam)

            String partijCodeConformXsd = partijCode;
            while (partijCodeConformXsd.length() < 6) {
                partijCodeConformXsd = '0' + partijCodeConformXsd
            }
            runContext.data.put("zendendePartijCode", partijCodeConformXsd);
            runContext.data.get("stuurgegevens").put("zendendePartij", partijCodeConformXsd);
        }

        sjabloonFilename = "${berichtMap}/${soortBericht}.xml"
        soapEndpoint = BerichtenMapper.geefEndpointVoorBericht(soortBericht)
        soapNamespace = BerichtenMapper.geefNamespaceVoorBericht(soortBericht)

        assert !runContext.data.isEmpty()
    }

    /**
     * Verzoek met een bepaalde leveringautorisatie.
     * @param leveringsautorisatieNaam de naam van de leveringsautorisatie
     * @param partijNaam de naam van de partij
     * @param ondertekenaar de ondertekenaar van het request
     * @param transporteur de transporteur van het request
     */
    @Given("verzoek voor leveringsautorisatie '\$leveringsautorisatieNaam' en partij '\$partijNaam' met ondertekenaar \$ondertekenaar en transporteur \$transporteur")
    void zetVerzoekLeveringsautorisatie(String leveringsautorisatieNaam, String partijNaam, String ondertekenaar, String transporteur) {
        AutAutContext.get().setVerzoekLeveringsautorisatie(leveringsautorisatieNaam, partijNaam, ondertekenaar, transporteur)
    }

    /**
     * Verzoek met een bepaalde leveringautorisatie.
     * @param leveringsautorisatieNaam de naam van de leveringsautorisatie
     * @param partijNaam de naam van de partij
     */
    @Given("verzoek voor leveringsautorisatie '\$leveringsautorisatieNaam' en partij '\$partijNaam'")
    void zetVerzoekLeveringsautorisatie(String leveringsautorisatieNaam, String partijNaam) {
        def oin = partijNaamNaarOin.get(partijNaam);
        AutAutContext.get().setVerzoekLeveringsautorisatie(leveringsautorisatieNaam, partijNaam, oin, oin)
    }

    /**
     * Zet autorisatie voor bijhoudingsverzoeken met een specifieke ondertekenaar en transporteur.
     * @param partijNaam de naam van de partij
     * @param ondertekenaar de ondertekenaar van het request
     * @param transporteur de transporteur van het request
     */
    @Given("bijhoudingsverzoek voor partij '\$partijNaam' met ondertekenaar \$ondertekenaar en transporteur \$transporteur")
    void zetVerzoekBijhoudingsautorisatie(String partijNaam, String ondertekenaar, String transporteur) {
        AutAutContext.get().setVerzoekBijhoudingsautorisatie(partijNaam, ondertekenaar, transporteur)
    }

    /**
     * Zet autorisatie voor bijhoudingsverzoeken.
     * @param partijNaam de naam van de partij
     */
    @Given("bijhoudingsverzoek voor partij '\$partijNaam'")
    void zetVerzoekBijhoudingsautorisatie(String partijNaam) {
        def oin = partijNaamNaarOin.get(partijNaam)
        AutAutContext.get().setVerzoekBijhoudingsautorisatie(partijNaam, oin, oin)
    }

    /**
     * Doe een synchroon verzoek van een bepaald type. Zorg dat vooraf de autorisatiesteps gedaan zijn.
     * @param type het type bericht dat verzonden moet worden
     */
    @Given("verzoek van bericht \$type")
    void verzoekVanType(String type) {
        generiekVerzoek(type);
    }

    private void generiekVerzoek(String soortBericht) {
        final String berichtMap = "/berichtdefinities/${soortBericht}"
        final File file = geefFile("${berichtMap}/${soortBericht}.yml")

        // Voeg specifieke waarden
        runContext.data = dataProcessor.process(runContext.data, file, null)

        def verzoek = AutAutContext.get().getAutorisatieVoorVerzoek()
        if (verzoek != null) {

            final String partijCode = partijNaamNaarCode.get(verzoek.partijNaam)

            String partijCodeConformXsd = partijCode;
            while (partijCodeConformXsd.length() < 6) {
                partijCodeConformXsd = '0' + partijCodeConformXsd
            }
            runContext.data.put("zendendePartijCode", partijCodeConformXsd);
            runContext.data.get("stuurgegevens").put("zendendePartij", partijCodeConformXsd);
            runContext.data.put("leveringsautorisatieIdentificatieId", verzoek.leveringautorisatieId);


            if ('lvg_bvgGeefDetailsPersoon'.equals(soortBericht)) {
                runContext.data.put("dienstId", verzoek.geefBevragingDienstId());
            }
        }

        sjabloonFilename = "${berichtMap}/${soortBericht}.xml"
        soapEndpoint = BerichtenMapper.geefEndpointVoorBericht(soortBericht)
        soapNamespace = BerichtenMapper.geefNamespaceVoorBericht(soortBericht)

        assert !runContext.data.isEmpty()
    }

    /**
     * ==== Gebruik specifieke data
     * Naast het gebruik van default YML data bij het opgeven van een administratie handeling of
     * verzoek, kan additionele data worden opgegeven die in de berichttemplate moet worden ingevoegd.
     * Die data kan met deze stap worden opgegeven. Indien het pad of bestandsnaam met een `/` begint, wordt
     * wordt het bestand vanaf `src/main/resources/` geopend. Indien het niet met een `/` begint, wordt
     * het bestand in het (sub)pad van het huidige `.story` bestand gezocht.
     *
     * Ondersteunde datatypes zijn: YML en CSV.
     *
     * @param file het bestand met de data
     */
    @Given("testdata uit bestand \$file")
    void testdataUitBestand(String file) {
        final File bestand = geefFile(file)
        logger.info 'Laden testdata uit bestand: {}', file
        runContext.data = dataProcessor.process(runContext.data, bestand, null)
        assert !runContext.data.isEmpty()
    }

    /**
     * ==== Gebruik specifieke data uit excel
     * Gelijk aan de ARTEngine kan ook data uit een ART-data sheet gelezen worden.
     * Hierbij wordt het excel bestand opgegeven en de kolom waaruit de data gelezen
     * moet worden. Indien het pad of bestandsnaam met een `/` begint, wordt
     * wordt het bestand vanaf `src/main/resources/` geopend. Indien het niet met een `/` begint, wordt
     * het bestand in het (sub)pad van het huidige `.story` bestand gezocht.
     *
     * @param xlsFile het excel bestand dat gelezen moet worden
     * @param column de kolom op data uit te lezen
     */
    @Given("data uit excel \$xlsFile , \$column")
    @Deprecated
    void waardesUitExcelBestand(String xlsFile, String column) {
        def processor = new DataProcessor()
        runContext.data.putAll(processor.process(runContext.data, geefFile(xlsFile), column))

        runContext.data.each { if (!it.value) it.value = ' ' }
    }

    /**
     * ==== Gebruik (extra) specifieke data
     * In een scenario is het mogelijk om nog extra waardes aan de data toe te voegen
     * of een bestaande waarde (uit een YML of excelsheet) te overschrijven.
     * Door in de tabel key-value paren op te nemen is dit te toen.
     *
     * Een voorbeeld tabel is:
     * ----
     * KEY     | VALUE
     * sleutel | 1234
     * ----
     *
     * @param table een tabel met key-value paren, zoals in het voorbeeld aangegeven
     */
    @Given("extra waardes: \$table")
    void deExtraWaardes(ExamplesTable table) {

        List<Parameters> params = table.getRowsAsParameters(true)
        int size = runContext.data.size()

        final PropertyUtilsBean propertyBean = new PropertyUtilsBean()
        params.each { p ->
            propertyBean.setNestedProperty(runContext.data, p.valueAs('SLEUTEL', String), p.valueAs('WAARDE', String))
        }

        assert runContext.data.size() <= size + params.size()
    }

    /**
     * ==== Gebruik een specifieke template
     * Indien niet gebruik wordt gemaakt van een stap voor administratieve handeling of verzoek,
     * kan ook een specifiek template worden opgegeven waarin data wordt ingevoegd. Dit wordt gebruikt
     * in combinatie met het link:#stuur_bericht_naar_expliciet_endpoint[verzenden naar een expliciet endpoint].
     *
     * @param file het template bestand dat moet worden gebruikt. Dit moet een absoluut pad zijn (beginnend met een `/`)
     *      dat wordt opgezocht vanaf `src/main/resources/`
     */
    @Given("de sjabloon \$file")
    @Deprecated
    void sjabloonBestand(String file) {
        sjabloonFilename = file;
    }

    /**
     * ==== Stuur bericht naar expliciet endpoint
     * Om functionaliteit zoals in de ARTEngine te ondersteunen is het mogelijk om SOAP berichten
     * naar een expliciet endpoint te sturen. Deze informatie wordt in de `Meta:` van het scenario,
     * of op story niveau vastgelegd. Deze stap wordt gebruikt samen met een link:#gebruik_een_specifieke_template[specifieke template].
     *
     * Deze stap vult de template met data voor het verzenden van het bericht.
     *
     * @param endpoint het endpoint opgegeven door middel van `Meta: @soapEndpoint` en gebruikt door de funqmachine en niet opgegeven in de step
     * @param namespace de namespace opgegeven door middel van `Meta: @soapNamespace` en gebruikt door de funqmachine en niet opgegeven in de step
     */
    @When("het bericht {is|wordt} naar endpoint verstuurd")
    @Deprecated
    void stuurHetBerichtNaar(@Named('soapEndpoint') final String endpoint, @Named('soapNamespace') final String namespace) {
        stuurEenBerichtNaarEndpoint(endpoint, namespace)
    }

    /**
     * ==== Stuur bericht naar afgeleid endpoint
     * Indien afleiding van het soapEndpoint gebeurt op basis van
     * link:#prepareer_een_administratieve_handeling[prepareer handeling] of
     * link:#prepareer_een_verzoek[prepareer verzoek], dan wordt deze stap gebruikt voor het verzenden van het SOAP bericht.
     *
     * Deze stap vult de template met data voor het verzenden van het bericht.
     */
    @When("het bericht {is|wordt} verstuurd")
    void stuurHetBericht() {
        stuurEenBerichtNaarEndpoint(soapEndpoint, soapNamespace)
    }

    private void stuurEenBerichtNaarEndpoint(String endpoint, String namespace) {
        logger.info 'Gaat een bericht sturen naar endpoint: {}', endpoint
        logger.info 'Naam van template bestand: {}', sjabloonFilename

        final TemplateProcessor templateProcessor = new TemplateProcessor()
        String requestBody = templateProcessor.verwerkTemplateBestand(sjabloonFilename, runContext.data)
        requestBody = new XmlProcessor().verwijderLegeElementen(requestBody)

        logger.info 'Eerste regel van het bericht: {}', requestBody.split('\n').first()


        final SoapParameters parameters = Environment.instance().getSoapParameters(endpoint, namespace);
        def response = soapVerstuurder.send(requestBody, parameters)

        logger.info 'Antwoord ontvangen'

        final StepResult result = new StepResult(StepResult.Soort.SOAP)
        result.request = requestBody
        result.response = response

        runContext << result
    }

    /**
     * ==== Expected met database vergelijken
     * Probeert herhaald, maar binnen een maximale tijd, te valideren dat het resultaat van een
     * sql query gelijk is aan een expected resultaat. Het sql bestand wordt als template behandeld,
     * dus waardes worden vervangen voordat de sql statements worden uitgevoerd. Ook het expected
     * bestand wordt als template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn.
     * Het resultaat wordt vertaald naar een XML representatie zodat het vergelijken op eenduidige
     * wijze kan plaats vinden. De validatie wordt gedaan door te kijken of de opgegeven xpath
     * expressie in het resultaat hetzelfde is als in het expected bestand.
     *
     * @param time tijd waarbinnen het resultaat correct moet zijn
     * @param sqlfile bestand met sql statements. Dit bestand mag een template zijn, want waardes worden ingevoegd.
     * @param expected verwacht antwoord in XML formaat
     * @param regex xpath expressie voor het vergelijken van sql-resultaat en verwacht bestand
     */
    @Then("is binnen \$time de {database|query} \$sqlfile gelijk aan \$expected voor de expressie \$regex")
    @Deprecated
    void databaseMatches(String time, String sqlfile, String expected, String regex) {
        Duration duur = Duration.parse("PT$time")

        expected = checkExpected(expected)

        StepResult result = new StepResult(StepResult.Soort.DATA)
        final TemplateProcessor templateProcessor = new TemplateProcessor()
        result.expected = templateProcessor.verwerkTemplateBestand(expected, runContext.data)
        runContext << result

        Wait<Sql> wait = new SqlWait(new SqlProcessor(Database.KERN).sql, duur.seconds)
        wait.ignoring(AssertionMisluktError.class)

        wait.until(new Predicate<Sql>() {
            @Override
            boolean apply(final Sql sql) {
                def sqlText = templateProcessor.verwerkTemplateBestand(sqlfile, runContext.data)
                result.request = sqlText

                def rows = sql.rows(sqlText)

                try {
                    result.response = XmlProcessor.toXml(rows)
                    new XmlProcessor().vergelijk(regex, result.expected, result.response)
                    return true
                } catch (SAXParseException spe) {
                    logger.debug 'DB resultaat geen valide XML: {}', spe.message
                    return false
                } catch (AssertionMisluktError ame) {
                    logger.debug 'DB resultaat niet gelijk aan expected: {}', ame.message
                    throw ame
                }
            }
        })

        logger.info 'expected en actual gelijk voor xpath: {}', regex
    }

    /**
     *
     * @param expected
     * @return
     */
    @Deprecated
    protected String checkExpected(final String expected) {
        new FileProcessor().geefFileOfRedirectedFile(expected)
    }

    /**
     * ==== Expected met database vergelijken
     * Probeert herhaald, maar binnen 30 seconden, te valideren dat het resultaat van een
     * sql query gelijk is aan een expected resultaat. Het sql bestand wordt als template behandeld,
     * dus waardes worden vervangen vodat de sql statements worden uitgevoerd. Ook het expected
     * bestand wordt als template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn.
     * Het resultaat wordt vertaald naar een XML representatie zodat het vergelijken op eenduidige
     * wijze kan plaats vinden. De validatie wordt gedaan door te kijken of de opgegeven xpath
     * expressie in het resultaat hetzelfde is als in het expected bestand.
     *
     * @param sqlfile bestand met sql statements. Dit bestand mag een template zijn, want waardes worden ingevoegd.
     * @param expected verwacht antwoord in XML formaat
     * @param regex xpath expressie voor het vergelijken van sql-resultaat en verwacht bestand
     */
    @Then("is de {database|query} \$sqlfile gelijk aan \$expected voor de expressie \$regex")
    @Deprecated
    void databaseMatches(String sqlfile, String expected, String regex) {
        this.databaseMatches('60s', sqlfile, expected, regex)
    }

    /**
     * ==== Expected met SOAP Response vergelijken
     * Valideert een (synchroon) SOAP response met een expected bestand. Het expected
     * bestand wordt als template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn.
     * De validatie wordt gedaan door te kijken of de opgegeven xpath
     * expressie in het resultaat hetzelfde is als in het expected bestand.
     *
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van soap-resultaat en verwacht bestand
     */
    @Then("is het antwoordbericht gelijk aan \$expected voor expressie \$regex")
    @Deprecated
    void responseVoldoetAanExpected(String expected, String regex) {
        StepResult result = runContext.findAll { it.soort == StepResult.Soort.SOAP }.last()
        final TemplateProcessor templateProcessor = new TemplateProcessor()

        expected = checkExpected(expected)

        result.expected = templateProcessor.verwerkTemplateBestand(expected, runContext.data)
        new XmlProcessor().vergelijk(regex, result.expected, result.response)
    }

    /**
     * ==== Verwerking controleren
     * Valideert of het (synchrone) antwoordbericht de opgegeven verwerking heeft.
     *
     * @param verwerking de verwerking die wordt verwacht. Mogelijke waardes zijn: `Geslaagd` en `Foutief`
     */
    @Then("heeft het antwoordbericht verwerking \$verwerking")
    void berichtHeeftVerwerking(String verwerking) {
        logger.debug 'Valideer verwerking van antwoord'
        new XmlProcessor().xpathEvalueertNaar(verwerking, '//brp:resultaat/brp:verwerking', antwoordBericht)
    }

    /**
     * ==== Controleert xpath
     * Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param regex
     * @param waarde
     */
    @Then("heeft het antwoordbericht voor xpath \$regex de waarde \$waarde")
    void antwoordberichtHeeftXPathWaarde(String regex, String waarde) {
        new XmlProcessor().xpathEvalueertNaar(waarde, regex, antwoordBericht)
    }

    /**
     * ==== Controleert xpath
     * Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.

     * @param xpathExpressie
     * @param waarde
     */
    @Then("heeft het antwoordbericht voor xpath \$xpathExpressie de platgeslagen waarde \$waarde")
    void antwoordberichtAlsPlatteTekstVanafXPath(String xpathExpressie, String waarde) {
        new XmlProcessor().berichtAlsPlatteTekstVanafXPath(waarde, xpathExpressie, antwoordBericht);
    }

    /**
     * ==== Controleert xpath
     * Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param xpathExpressie
     */
    @Then("heeft het antwoordbericht geen kinderen voor xpath \$xpathExpressie")
    void antwoordberichtHeeftGeenKinderenVoorXPath(String xpathExpressie) {
        new XmlProcessor().berichtHeeftGeenKinderenVoorXPath(xpathExpressie, antwoordBericht)
    }

    /**
     * ==== Controleert xpath
     * Controleert of gegeven xpath evalueert naar een node uit het xml antwoordbericht
     *
     * @param xpathExpressie
     */
    @Then("is er voor xpath \$xpath een node aanwezig in het antwoord bericht")
    void antwoordberichtHeeftGegevenNodeVoorXpath(String xpath) {
        new XmlProcessor().xpathBestaat(xpath, antwoordBericht)
    }

    /**
     * === Controleert xpath
     * Controleert of er voor gegeven xpath geen node aanwezig is in het xml antwoordbericht
     *
     * @param xpathExpressie
     */
    @Then("is er voor xpath \$xpath geen node aanwezig in het antwoord bericht")
    void antwoordberichtHeeftGeenNodeVoorXpath(String xpath) {
        new XmlProcessor().xpathBestaatNiet(xpath, antwoordBericht)
    }

    /**
     * ==== Antwoord is een soapFault
     * Valideert of het antwoord een SoapFault is.
     */
    @Then("is het antwoordbericht een soapfault")
    void antwoordIsEenSoapFault() {
        new XmlProcessor().xpathBestaat('//soap:Fault', antwoordBericht)
    }

    /**
     * ==== Aantal meldingen controleren
     * Valideert of het (synchrone) antwoordbericht het aantal opgegeven meldingen bevat.
     *
     * @param count het aantal verwachte meldingen in het bericht
     */
    @Then("heeft het antwoordbericht \$count melding{en|}")
    void berichtHeeftAantalMeldingen(int count) {
        logger.debug 'Valideer meldingen antwoord'
        new XmlProcessor().xpathEvalueertNaar(count as String, 'count(//brp:meldingen/brp:melding)', antwoordBericht)
    }

    /**
     * ==== Specifiek meldingen controleren
     * Valideert of de opgegeven meldingen in het (synchrone) antwoordbericht voorkomen. Voor de duidelijkheid
     * kan de meldingtekst worden opgegegeven in de tabel, maar deze wordt niet meegenomen in
     * de validatie van het bericht.
     *
     * De tabel heeft de volgende header:
     * ----
     * CODE    | MELDING
     * ALG0001 | De algememe foutmelding
     * ----
     *
     * @param meldingen tabel met meldingcodes en meldingsteksten waarvan wordt gekeken of deze in het bericht staan
     */
    @Then("heeft het antwoordbericht de meldingen: \$meldingen")
    void berichtHeeftMeldingen(List<MeldingRow> meldingen) {
        XmlProcessor processor = new XmlProcessor()
        meldingen.each { melding ->
            processor.xpathBestaat("//brp:melding/brp:regelCode[text()='${melding.code}']", antwoordBericht)
            processor.xpathBestaat("//brp:melding/brp:melding[text()='${melding.melding}']", antwoordBericht)
        }
    }

    /**
     * ==== Attribuut in bericht controleren
     * Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat
     * er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een bevraging antwoordbericht:
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
    @Then("heeft in het antwoordbericht '\$attribuut' in '\$groep' de waarde{s|} '\$verwachteWaardes'")
    void heeftGroepenGesorteerdInResponse(final String attribuut, final String groep, final List<String> verwachteWaardes) {
        new XmlProcessor().waardesVanAttributenInVolgorde(antwoordBericht, groep, attribuut, verwachteWaardes)
    }

    /**
     * ==== Attributen in bericht controleren
     * Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde
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
    @Then("hebben in het antwoordbericht de attributen in de groepen de volgende waarde{s|}: \$sorteringRegels")
    void heeftGroepenGesorteerdInResponseTabel(final List<SorteringRegel> sorteringRegels) {
        sorteringRegels.each {
            sorteringRegel ->
                new XmlProcessor().waardesVanAttributenInVolgorde(antwoordBericht, sorteringRegel.groep, sorteringRegel.attribuut, sorteringRegel.verwachteWaardes)
        }
    }

    /**
     * ==== Groepen in een antwoordbericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht \$aantal groep{en|} '\$groep'")
    void heeftAantalVoorkomensVanEenGroepInResponse(Integer aantal, String groep) {
        new XmlProcessor().heeftAantalVoorkomensVanEenGroep(aantal, groep, antwoordBericht)
    }

    /**
     * ==== Groepen in een antwoordbericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht \$aantal attribuut{en|} '\$attribuut'")
    void heeftAantalVoorkomensVanEenAttribuutInResponse(Integer aantal, String attribuut) {
        new XmlProcessor().heeftAantalVoorkomensVanEenAttribuut(aantal, attribuut, antwoordBericht)
    }

    /**
     * ==== Archivering van synchrone berichten controleren
     * Controleert of er een bericht gearchiveerd is in de 'ber.ber'-tabel voor de richting en het referentienummer dat meegegeven is.
     *
     * @param richting de richting van het bericht ('ingaand' of 'uitgaand')
     * @param referentienr het referentienummer van het bericht
     */
    @Then("\$richting bericht is gearchiveerd met referentienummer \$referentienr")
    void isBerichtGearchiveerd(String richting, String referentienr) {
        def isGearchiveerd = new SqlProcessor(Database.BER).isSynchroonBerichtGearchiveerd(richting, referentienr)
        assert isGearchiveerd
    }

    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is gezet
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is gezet voor de opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param abonnementnaam adonnementnaam waarvoor een afnemerindicatie is geplaatst
     */

    @Then("is er voor persoon met bsn \$bsn en leveringautorisatie \$leveringautorisatie een afnemerindicatie geplaatst")
    void IsAfnemerIndicatieGeplaatst(String bsn, String leveringautorisatienaam) {
        def IsGeplaatst = new SqlProcessor(Database.KERN).AfnemerIndicatieGeplaatst(bsn, leveringautorisatienaam)


        assert IsGeplaatst.size() == 1

    }

    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is verwijderd
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is verwijderd voor het opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param abonnementnaam adonnementnaam waarvoor een afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn \$bsn en leveringautorisatie \$leveringautorisatie geen afnemerindicatie aanwezig")
    void IsAfnemerIndicatieVerwijderd(String bsn, String leveringautorisatienaam) {
        def IsVerwijderd = new SqlProcessor(Database.KERN).AfnemerIndicatieGeplaatst(bsn, leveringautorisatienaam)


        assert IsVerwijderd.size() == 0
    }

    /**
     * Klasse voor vertaling van {@link ExamplesTable} met sorteringen.
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
     * Klasse voor vertaling van {@link ExamplesTable} met meldingen.
     */
    @AsParameters
    public static class MeldingRow {
        @Parameter(name = "CODE")
        String code
        @Parameter(name = "MELDING")
        String melding
    }

    /**
     * Klasse voor vertaling van {@link ExamplesTable} met extra properties.
     */
    @AsParameters
    public static class PropertyRow {
        @Parameter(name = "SLEUTEL")
        String sleutel
        @Parameter(name = "WAARDE")
        String waarde
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

    private String getAntwoordBericht() {
        def berichten = runContext.findAll { StepResult.Soort.SOAP == it.soort }

        if (!berichten.isEmpty()) {
            return berichten.last().response
        }

        return null
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
    @Then("is in antwoordbericht de aanwezigheid van '\$attribuut' in '\$voorkomen' nummer \$nummer \$aanwezig")
    void isAttribuutInVookomenAanwezig(final String attribuut, final String voorkomen, final Integer nummer, final String aanwezig) {
        isVoorkomenAttribuutAanwezig(voorkomen, nummer, attribuut, WaarOnwaar.isWaar(aanwezig))
    }

    private void isVoorkomenAttribuutAanwezig(String groep, Integer nummer, String attr, boolean aanwezig) {
        final String expr = """//brp:$groep"""
        def node = XmlUtils.getNodes(expr, XmlUtils.bouwDocument(this.antwoordBericht)).item(nummer - 1)

        node = node?.childNodes.find {
            it.nodeName == "brp:$attr" as String
        }

        assert (node != null) == aanwezig:
            "Aanwezigheid van $attr in de groep $groep[$nummer] is niet correct. Verwacht was '$aanwezig'. Werkelijk was '${node != null}'."
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
        new XmlProcessor().heeftVoorkomenVanEenGroep(aanwezig, groep, antwoordBericht)
    }
}
