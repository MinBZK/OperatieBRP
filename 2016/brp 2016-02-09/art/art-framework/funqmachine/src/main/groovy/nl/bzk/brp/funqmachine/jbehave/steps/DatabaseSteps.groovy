package nl.bzk.brp.funqmachine.jbehave.steps

import groovy.sql.GroovyRowResult
import junit.framework.AssertionFailedError
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.processors.XmlProcessor
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLAssert

import javax.sql.DataSource
import nl.bzk.brp.datataal.leveringautorisatie.AutAutDSL
import nl.bzk.brp.datataal.leveringautorisatie.ToegangLeveringautorisatieDsl
import nl.bzk.brp.funqmachine.DataSourceConfiguration
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.informatie.WaarOnwaar
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext
import nl.bzk.brp.funqmachine.ontvanger.LeveringOntvanger
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie
import nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Parameter
import org.jbehave.core.annotations.Then
import org.jbehave.core.model.ExamplesTable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils

@Steps
class DatabaseSteps {
    static final Logger logger = LoggerFactory.getLogger(DatabaseSteps)

    final AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);

    @Autowired
    private LeveringOntvanger leveringOntvanger;

    @Autowired
    private ScenarioRunContext runContext

    public ApplicationContext getApplicationContext() {
        return ap
    }

    /**
     * Reset de gehele database met een script gegenereerd in art-testdata
     *
     */
    @Given("de gehele database is gereset")
    void resetDeHeleDatabase() {

        logger.info "Starting reset of complete database"

        final DataSource dataSource = geefLezenSchrijvenDataSource()
        final Resource resource = new ClassPathResource("art-testdata.sql")

        JdbcTestUtils.executeSqlScript(new JdbcTemplate(dataSource), resource, false)

        logger.info "Finished reset of complete database"
    }


    /**
     * ==== Autofiat wijzigen.
     * Zet voor een gemeente autofiat aan of uit.
     *
     * @param gemeente de gemeente waarvoor autofiat gewijzigd moet worden.
     * @param aanUit de {@link nl.bzk.brp.funqmachine.informatie.WaarOnwaar} waarde die aangeeft of autofiat aan- of uitgezet moet worden.
     */
    @Given("voor gemeente \$gemeente autofiat \$aanuit staat")
    void zetAutofiatAanOfUit(String gemeente, String aanUit) {
        boolean aan = WaarOnwaar.isWaar(aanUit)

        String query = "update kern.partij" +
            " set indautofiat = :aan" +
            " where id =" +
            " (select partij" +
            " from kern.gem" +
            " where naam = :gemeente)"

        logger.info "Zet autofiat op ${aan} voor gemeente ${gemeente}"

        new SqlProcessor(Database.KERN).update(query, [gemeente:gemeente,
                                                       aan:aan])
    }

    /**
     * ==== Aanwezigheid van een actuele betrokkenheid bij een relatie controleren
     * Verifieert voor een persoon de actuele betrokkenheid bij een relatie.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan de betrokkenheid/relatie gecontroleerd moet worden.
     * @param betrokkenheidSoort de soort betrokkenheid (bijv. PARTNER)
     * @param relatieSoort de soort relatie (bijv. HUWELIJK)
     */
    // LET OP we gebruiken het woordje 'wel' voor het onderscheid met de 'niet'-step.
    //        Als je het woordje 'wel' weglaat (en de niet-variant staat in deze klasse ná de wel-variant) ontstaat er een conflict.
    //        JBehave interpreteert dan de waarde '$bsn niet' als '$bsn' en gebruikt dan onterecht de wel-variant.
    @Then("is in de database de persoon met bsn \$bsn wel als \$betrokkenheidSoort betrokken bij een \$relatieSoort")
    void persoonBetrokkenBijRelatie(Integer bsn, String betrokkenheidSoort, String relatieSoort) {
        assert geefVanPersoonAantalBetrokkenhedenBijRelatie(bsn, betrokkenheidSoort, relatieSoort) == 1
    }

    /**
     * ==== Afwezigheid van een actuele betrokkenheid bij een relatie controleren
     * Verifieert voor een persoon de afwezigheid actuele betrokkenheid bij een relatie.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan de betrokkenheid/relatie gecontroleerd moet worden.
     * @param betrokkenheidSoort de soort betrokkenheid (bijv. PARTNER)
     * @param relatieSoort de soort relatie (bijv. HUWELIJK)
     */
    @Then("is in de database de persoon met bsn \$bsn niet als \$betrokkenheidSoort betrokken bij een \$relatieSoort")
    void persoonNietBetrokkenBijRelatie(Integer bsn, String betrokkenheidSoort, String relatieSoort) {
        assert geefVanPersoonAantalBetrokkenhedenBijRelatie(bsn, betrokkenheidSoort, relatieSoort) == 0
    }

    int geefVanPersoonAantalBetrokkenhedenBijRelatie(Integer bsn, String betrokkenheidSoort, String relatieSoort) {
        String query = "Select b.relatie from kern.pers p, kern.betr b, kern.relatie r" +
            " where p.id=b.pers" +
            " and r.id=b.relatie" +
            " and p.bsn=:bsn" +
            " and b.rol=:betrokkenheidSoort" +
            " and r.srt=:relatieSoort" +
            " and r.dateinde IS NULL"

        logger.info "Zoekt persoon met bsn ${bsn} die als ${betrokkenheidSoort} betrokken is bij een ${relatieSoort}"

        return new SqlProcessor(Database.KERN).geefAantal(query, [bsn:bsn,
                                                                  betrokkenheidSoort:SoortBetrokkenheid.valueOf(betrokkenheidSoort).ordinal(),
                                                                  relatieSoort: SoortRelatie.valueOf(relatieSoort).ordinal()])
    }

    DataSource geefLezenSchrijvenDataSource() {
        return (DataSource) ap.getBean("lezenSchrijvenDataSource");
    }

    /**
     * Vergelijk xml in database aan xml in bestand.
     *
     * @param referentie
     * @param soort
     */
    @Then("is de data van crossreferentie \$crossreferentie en srt \$soort gelijk aan antwoord bericht")
    void givenQueryCrossDataCompareToResult(String crossreferentie, String soort) {

        def query = "select ber.data from ber.ber where crossreferentienr='r?' and srt = 's?'"
                .replace("r?", crossreferentie).replace("s?", soort)
        def databaseResult = SqlProcessor(Database.BER).firstRow(query).getAt(0)

        Diff myDiff = new Diff(antwoordBericht, databaseResult)

        try {
            XMLAssert.assertXMLEqual(myDiff, true)
        } catch (AssertionFailedError e) {
            //AssertionFailedError is geen subklasse van Exceptie, daarom wrappen we hier
            throw new AssertionMisluktError("XML niet gelijk op xpath: $xpath", e);
        }
    }

    /**
     *
     * @return
     */
    private String getAntwoordBericht() {
        def berichten = runContext.findAll { StepResult.Soort.SOAP == it.soort }

        if (!berichten.isEmpty()) {
            return berichten.last().response
        }

        return null
    }

    /**
     * Vergelijk xml in database aan xml in bestand.
     *
     * @param referentie
     * @param soort
     */
    @Then("is de data van referentie \$referentie en srt \$soort gelijk aan inkomend bericht")
    void givenQueryDataCompareToResult(String referentie, String soort) {

        def query = "select ber.data from ber.ber where referentienr='r?' and srt = 's?'"
                .replace("r?", referentie).replace("s?", soort)
        def databaseResult = SqlProcessor(Database.BER).firstRow(query).getAt(0)

        Diff myDiff = new Diff(antwoordBericht, databaseResult)

        try {
            XMLAssert.assertXMLEqual(myDiff, true)
        } catch (AssertionFailedError e) {
            //AssertionFailedError is geen subklasse van Exceptie, daarom wrappen we hier
            throw new AssertionMisluktError("XML niet gelijk op xpath: $xpath", e);
        }
    }

    /**
     * givenLeveringautorisatieUitBestand
     *
     * @param bestanden
     */
    @Given("leveringsautorisatie uit \$bestanden")
    void givenLeveringautorisatieUitBestanden(List<String> bestanden) {
        logger.info "Laad leveringautorisaties ${bestanden}"

        final DataSource dataSource = geefLezenSchrijvenDataSource();

        //niet zo netjes
        if (AutAutDSL.IDENTITEIT_GROEPEN == null) {
            AutAutDSL.IDENTITEIT_GROEPEN = new HashSet(new JdbcTemplate(dataSource).
                    queryForList("select naam from kern.element where elementnaam = 'Identiteit'", String.class))
        }

        AutAutContext.get().reset();
        //leeg alle autaut tabellen
        new JdbcTemplate(dataSource).batchUpdate(

                "delete from autaut.toeganglevsautorisatie",
                "TRUNCATE autaut.levsautorisatie CASCADE",
                "update kern.perscache set afnemerindicatiechecksum = null, afnemerindicatiegegevens = null",
        );

        //voer nieuwe autaut schema's toe
        String totaleSqlString = '';
        for (String bestand : bestanden) {
            def resultaat = AutAutDSL.voerLeveringautorisatiesOp(new ClassPathResource(bestand));
            for (ToegangLeveringautorisatieDsl tla : resultaat.toegangLeveringautorisatieDsls) {
                AutAutContext.get().voegToegangleveringsautorisatieToe(tla);
            }
            String sqlString = resultaat.toSql().replaceAll("<afleverpunt>", leveringOntvanger.getUrl().toURI().toString())
            totaleSqlString += sqlString;

        }
        def resource = new ByteArrayResource(totaleSqlString.getBytes())
        JdbcTestUtils.executeSqlScript(new JdbcTemplate(dataSource), resource, false);

        //refresh caches
        new JmxConnector().herlaadCaches()
    }

    /**
     * givenBijhoudingautorisatieUitBestand
     *
     * @param bestanden
     */
    @Given("bijhoudingautorisatie uit \$bestanden")
    void givenBijhoudingautorisatieUitBestanden(List<String> bestanden) {
        logger.info "Laad bijhoudingautorisaties ${bestanden}"

        final DataSource dataSource = geefLezenSchrijvenDataSource();

        //niet zo netjes
        if (AutAutDSL.IDENTITEIT_GROEPEN == null) {
            AutAutDSL.IDENTITEIT_GROEPEN = new HashSet(new JdbcTemplate(dataSource).
                    queryForList("select naam from kern.element where elementnaam = 'Identiteit'", String.class))
        }

        AutAutContext.get().resetAutorisatieVoorVerzoek();
        //leeg alle autaut tabellen
        new JdbcTemplate(dataSource).batchUpdate(
            "delete from autaut.bijhautorisatiesrtadmhnd",
            "delete from autaut.toegangbijhautorisatie"
        );

        //voer nieuwe autaut schema's toe
        for (String bestand : bestanden) {
            List<String> regels = vervangVandaag(new ClassPathResource(bestand));
            def bijhoudingautorisatie = AutAutDSL.voerToegangBijhoudingautorisatieOp(regels);
            StringWriter writer = new StringWriter();
            bijhoudingautorisatie.toSQL(writer);

            String script = writer.getBuffer().toString()
            def resource = new ByteArrayResource(script.getBytes())
            JdbcTestUtils.executeSqlScript(new JdbcTemplate(dataSource), resource, false);
        }

        //refresh caches
        new JmxConnector().herlaadCaches()
    }

    private static List<String> vervangVandaag(final Resource resource) {
        List<String> regels = new ArrayList<>()

        final InputStream inputStream = resource.getInputStream()
        try {
            final List<String> lines = IOUtils.readLines(inputStream)
            for (String line : lines) {
                regels.add(DateSyntaxTranslatorUtil.parseString(String.valueOf(System.currentTimeMillis()), line))
            }
        } finally {
            inputStream.close();
        }

        return regels
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
     */
    @AsParameters
    public static class GegevensRegels {
        @Parameter(name = "veld")
        String field
        @Parameter(name = "waarde")
        String value
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in kern \$query de volgende gegevens: \$table")
    void executeKernQueryAndCheckTheResult(String query, ExamplesTable table) {
        def params = table.getRowsAsParameters(true)
        def gegevensRegels = new ArrayList<>(table.getRowCount());
        params.each {
            GegevensRegels gr = new GegevensRegels()
            gr.field = it.valueAs("veld", String.class)
            gr.value = it.valueAs("waarde", String.class)
            gegevensRegels.add(gr)
        }

        def row = new SqlProcessor(Database.KERN).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        gegevensRegels.each {
            gegevensRegel ->
                assertTupleField(gegevensRegel, row)
        }
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in ber \$query de volgende gegevens: \$gegevensRegels")
    void executeBerQueryAndCheckTheResult(String query, List<GegevensRegels> gegevensRegels) {
        GroovyRowResult row = new SqlProcessor(Database.BER).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        gegevensRegels.each {
            gegevensRegel ->
                assertTupleField(gegevensRegel, row)
        }
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in prot \$query de volgende gegevens: \$gegevensRegels")
    void executeLevQueryAndCheckTheResult(String query, List<GegevensRegels> gegevensRegels) {
        GroovyRowResult row = new SqlProcessor(Database.PROT).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        gegevensRegels.each {
            gegevensRegel ->
                assertTupleField(gegevensRegel, row)
        }
    }

    /**
     * Asser
     *
     * @param gegevensRegel
     */
    private void assertTupleField(GegevensRegels gegevensRegel, GroovyRowResult row) {
        assert StringUtils.equals(row.getProperty(gegevensRegel.field) as String,
                "NULL".equals(gegevensRegel.value) ? null : gegevensRegel.value)
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
     */
    @AsParameters
    public static class AanwezigheidsRegel {
        @Parameter(name = "veld")
        String field
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in kern \$query de aanwezige gegevens: \$aanwezigheidsRegels")
    void executeKernQueryAndCheckThePresence(String query, List<AanwezigheidsRegel> aanwezigheidsRegels) {
        GroovyRowResult row = new SqlProcessor(Database.KERN).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        logger.warn("validating that is not null {}", query)

        aanwezigheidsRegels.each {
            aanwezigheidsRegel ->
                assertTupleField(aanwezigheidsRegel, row)
        }
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in ber \$query de aanwezige gegevens: \$aanwezigheidsRegels")
    void executeBerQueryAndCheckThePresence(String query, List<AanwezigheidsRegel> aanwezigheidsRegels) {
        GroovyRowResult row = new SqlProcessor(Database.BER).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        logger.warn("validating that is not null {}", query)

        aanwezigheidsRegels.each {
            aanwezigheidsRegel ->
                assertTupleField(aanwezigheidsRegel, row)
        }
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     *
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in prot \$query de aanwezige gegevens: \$aanwezigheidsRegels")
    void executeLevQueryAndCheckThePresence(String query, List<AanwezigheidsRegel> aanwezigheidsRegels) {
        GroovyRowResult row = new SqlProcessor(Database.PROT).firstRow(query)

        if (row == null) {
            new AssertionMisluktError('geen database row teruggekregen')
        }

        logger.warn("validating that is not null {}", query)

        aanwezigheidsRegels.each {
            aanwezigheidsRegel ->
                assertTupleField(aanwezigheidsRegel, row)
        }
    }

    /**
     * Test dat er geprotocolleerd is voor een gegeven persoon, soortDienst en soortSynchronisatie
     *
     * @param bsn persoon bsn
     * @param soortDienst de dienst soort (bijv. Plaatsen afnemerindicatie)
     * @param soortSynchronisatie Volledigbericht / Mutatiebericht
     */
    @Then("is er geprotocolleerd voor persoon \$bsn en soortdienst \$soortDienst en soortSynchronisatie \$soortSynchronisatie")
    void isErGeprotocolleerdVoorPersoon(String bsn, String soortDienst, String soortSynchronisatie) {
        final DataSource dataSource = geefLezenSchrijvenDataSource();
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        final List list = template.queryForList("select 1\n" +
                "from \n" +
                "prot.levsaantek l,\n" +
                "prot.levsaantekpers lp\n" +
                "where \n" +
                "l.id = lp.levsaantek\n" +
                "and lp.pers = (select id from kern.pers where bsn = ?)\n" +
                "and l.dienst = (select l.dienst from autaut.dienst where id = l.dienst and srt = (select id from autaut.srtdienst where naam = ?))\n" +
                "and l.srtsynchronisatie = (select id from ber.srtsynchronisatie where naam = ?)", Integer.parseInt(bsn), soortDienst, soortSynchronisatie)
        if (list.isEmpty()) {
            throw new RuntimeException("Er is niet geprotolleerd");
        }
    }

    /**
     * Assert
     *
     * @param gegevensRegel
     */
    private void assertTupleField(AanwezigheidsRegel aanwezigheidsRegel, GroovyRowResult row) {
        logger.warn("validating that is not null {}", row.getProperty(aanwezigheidsRegel.field))
        assert StringUtils.isNotEmpty(row.getProperty(aanwezigheidsRegel.field))
    }

}
