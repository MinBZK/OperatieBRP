/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.ontvanger.HttpLeveringOntvanger;
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLAssert;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xml.sax.SAXException;

/**
 * Stappen voor ber.ber.
 */
@Steps
public final class BerBerSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerBerSteps.class);

    @Autowired
    private DatabaseSteps databaseSteps;

    @Autowired
    private HttpLeveringOntvanger ontvanger;

    @Autowired
    private ScenarioRunContext runContext;


    /**
     * Leegt de ber.ber tabellen.
     */
    @BeforeStory
    public void leegBerichtenTabel() {
        LOGGER.info("Start leeg berichten (ber) tabellen");
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        template.update("truncate ber.ber cascade");
        LOGGER.info("Einde leeg berichten (ber) tabellen");
    }

    /**
     * Stap om te controleren dat de tijdstip verzending in het bericht (zie data kolom ber.ber tabel) gelijk is aan tsverzending kolom in de ber.ber
     * tabel.
     */
    @Then("tijdstipverzending in bericht is correct gearchiveerd")
    public void deTijdstipVerzendingInBerichtIsGelijkAanTijdstipInArchivering() throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List<Map<String, Object>> list = template.queryForList("select tsverzending, data from ber.ber");

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (final Map<String, Object> map : list) {
            final Timestamp tsverzending = (Timestamp) map.get("tsverzending");
            final String data = (String) map.get("data");
            final int startIndex = data.indexOf("<brp:tijdstipVerzending>");
            final int eindIndex = data.indexOf("</brp:tijdstipVerzending>");
            if (startIndex != -1 && eindIndex != -1) {
                String tijdInBericht = data.substring(startIndex + "<brp:tijdstipVerzending>".length(), eindIndex);
                tijdInBericht = tijdInBericht.replace("T", " ");
                tijdInBericht = tijdInBericht.substring(0, tijdInBericht.length() - 6);
                final Date parse = dateFormat.parse(tijdInBericht);
                tijdInBericht = dateFormat.format(parse);
                LOGGER.info(String.format("vergelijk tsverzending in database %s met tijdstipVerzending in bericht %s",
                    tsverzending.toString(), tijdInBericht));
                Assert.assertTrue(tijdInBericht.startsWith(tsverzending.toString()));
            }
//            throw new AssertionFailedError("Geen tijdstip in bericht");
        }
    }

    /**
     * Stap om te controleren dat de tijdstip ontvangst binnen redelijke grenzen actueel is. Gezien de mogelijke tijdsverschillen op de servers hanteren we
     * voorlopig een uur.
     */
    @Then("tijdstipontvangst is actueel")
    public void tijdstipOntvangsIsActueel() throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List<Map<String, Object>> list = template.queryForList("select tsontv from ber.ber where richting = 1");
        for (Map<String, Object> map : list) {
            final Timestamp tsontv = (Timestamp) map.get("tsontv");
            final long nu = System.currentTimeMillis();
            final long verschil = Math.max(tsontv.getTime(), nu) - Math.min(tsontv.getTime(), nu);
            LOGGER.info(String.format("verschil tsontv = %d msec", verschil));
            if (verschil > 1000 * 60 * 60) {
                throw new AssertionFailedError("Tijdstip ontvangst niet actueel (verschil groter dan 1 uur!!)");
            }
        }
    }

    /**
     * Stap om te controleren dat de kruisreferentie in het bericht gelijk is aan de kruisreferentie in de ber.ber tabel
     */
    @Then("referentienr is gelijk")
    public void kruisreferentieIsGelijk() throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List<Map<String, Object>> list = template.queryForList("select referentienr, data from ber.ber");

        for (final Map<String, Object> map : list) {
            final String referentie = (String) map.get("referentienr");
            final String data = (String) map.get("data");
            final int startIndex = data.indexOf("<brp:referentienummer>");
            final int eindIndex = data.indexOf("</brp:referentienummer>");
            if (startIndex != -1 && eindIndex != -1) {
                final String referentieInBer = data.substring(startIndex + "<brp:referentienummer>".length(), eindIndex);
                LOGGER.info(String.format("vergelijken referentie uit database %s met referentie in bericht %s", referentie, referentieInBer));
                Assert.assertEquals(referentie, referentieInBer);
            }
        }
    }

    /**
     * Stap om te controleren dat de leveringautorisatie
     */
    @Then("leveringautorisatie is gelijk in archief")
    public void leveringautorisatieIsGelijk() throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List<Map<String, Object>> list = template.queryForList("select levsautorisatie, data from ber.ber where data is not null");

        for (final Map<String, Object> map : list) {
            final Integer levsautorisatie = (Integer) map.get("levsautorisatie");
            final String data = (String) map.get("data");

            final String levsautorisatieInBericht = geefWaarde(data, "<brp:leveringsautorisatieIdentificatie>",
                "</brp:leveringsautorisatieIdentificatie>");
            if (levsautorisatieInBericht != null) {
                LOGGER.info(String.format("vergelijken leveringsautorisatie uit database %s met leveringsautorisatie in bericht %s", levsautorisatie,
                    levsautorisatieInBericht));
                Assert.assertEquals(levsautorisatie.intValue(), Integer.parseInt(levsautorisatieInBericht));
            }
        }
    }

    /**
     * Stap om te controleren dat de dienstid correct is gearchiveerd
     */
    @Then("dienstid is gelijk in archief")
    public void dienstIdIsGelijk() throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List<Map<String, Object>> list = template.queryForList("select dienst, data from ber.ber where data is not null");

        for (final Map<String, Object> map : list) {
            final Integer dienst = (Integer) map.get("dienst");
            final String data = (String) map.get("data");

            final String dienstInBericht = geefWaarde(data, "<brp:dienstIdentificatie>",
                "</brp:dienstIdentificatie>");
            if (dienstInBericht != null) {
                LOGGER.info(String.format("vergelijken dienstid uit database %s met dienstid in bericht %s", dienst, dienstInBericht));
                Assert.assertEquals(dienst.intValue(), Integer.parseInt(dienstInBericht));
            }
        }
    }


    /**
     * Stap om te controleren dat er een uitgaand bericht bestaat als antwoord op het ingaande bericht.
     */
    @Then("bestaat er een antwoordbericht voor referentie $referentie")
    public void bestaatErEenAntwoordberichtVoorReferentie(final String referentie) throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List list = template.queryForList("select 1 from ber.ber where richting = 2 and antwoordop in (select id from ber.ber where "
            + "richting = 1 and referentienr = ?)", referentie);
        LOGGER.info("Aantal antwoordberichten: " + list.size());
        if (list.isEmpty()) {
            throw new AssertionFailedError("Geen antwoord bericht gevonden voor referentie: " + referentie);
        }
    }

    /**
     * Stap om te controleren dat alle asynchroon ontvangen berichten correct gearchiveerd zijn.
     */
    @Then("controleer dat alle asynchroon ontvangen berichten correct gearchiveerd zijn")
    public void controleerDatAlleOntvangenBerichtenGearchiveerdZijn() throws IOException, SAXException {
        final List<String> ontvangenBerichten = ontvanger.getMessages();
        LOGGER.info("Aantal ontvangen berichten om te controleren:" + ontvangenBerichten.size());
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        for (final String bericht : ontvangenBerichten) {
            final String referentie = geefWaarde(bericht, "<brp:referentienummer>", "</brp:referentienummer>");
            final List<Map<String, Object>> archiefBerichten = template.queryForList("select data from ber.ber where richting = 2 and referentienr = ?",
                referentie);
            Assert.assertEquals(archiefBerichten.size(), 1);

            String berichtUitArchief = (String) archiefBerichten.get(0).get("data");
            checkXmlGelijk(bericht, berichtUitArchief);
        }
    }

    /**
     * Stap om te controleren dat het synchrone request en response bericht correct gearchiveerd zijn.
     */
    @Then("is het synchrone verzoek correct gearchiveerd")
    public void isHetSynchroneVerzoekCorrectGearchiveerd() throws IOException, SAXException {

        final StepResult laatsteVerzoek = runContext.geefLaatsteVerzoek();
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());

        //check request archivering
        {
            final String request = laatsteVerzoek.getRequest().toString();
            final String requestReferentie = geefWaarde(request, "<brp:referentienummer>", "</brp:referentienummer>");
            LOGGER.info("Controleer het request met referentie: " + requestReferentie);
            final List<Map<String, Object>> archiefBerichten = template.queryForList("select data from ber.ber where richting = 1 and referentienr = ?",
                requestReferentie);
            Assert.assertEquals(archiefBerichten.size(), 1);
            String requestberichtUitArchief = (String) archiefBerichten.get(0).get("data");
            checkXmlGelijk(request, requestberichtUitArchief);
        }
        //check response archivering
        {
            final String response = laatsteVerzoek.getResponse().toString();
            final String responseReferentie = geefWaarde(response, "<brp:referentienummer>", "</brp:referentienummer>");
            LOGGER.info("Controleer het response met referentie: " + responseReferentie);
            final List<Map<String, Object>> archiefBerichten = template.queryForList("select data from ber.ber where richting = 2 and referentienr = ?",
                responseReferentie);
            Assert.assertEquals(archiefBerichten.size(), 1);
            String requestberichtUitArchief = (String) archiefBerichten.get(0).get("data");
            checkXmlGelijk(response, requestberichtUitArchief);
        }
    }


    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met crossreferentie en srt
     */
    @Then("bestaat er geen voorkomen in berpers tabel voor crossreferentie $crossreferentie en srt $srt")
    public void bestaatErGeenVoorkomenInBerPersVoorCrossReferentieEnSrt(final String crossreferentie, final String srt) throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List list = template.queryForList("select 1 from ber.berpers "
            + "where berpers.ber = (select id from ber.ber where crossreferentienr = ? and srt= ?)", crossreferentie, Integer.parseInt(srt));
        LOGGER.info("Aantal records in ber.pers met refnr en srt: " + list.size());
        if (!list.isEmpty()) {
            throw new AssertionFailedError("Id van ber.ber bericht komt voor in de ber.pers tabel: " + crossreferentie + srt);
        }
    }

    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met referentie en srt
     */
    @Then("bestaat er geen voorkomen in berpers tabel voor referentie $referentie en srt $srt")
    public void bestaatErGeenVoorkomenInBerPersVoorReferentieEnSrt(final String referentie, final String srt) throws ParseException {
        final JdbcTemplate template = new JdbcTemplate(geefBerLezenSchrijvenDataSource());
        final List list = template.queryForList("select 1 from ber.berpers "
            + "where berpers.ber = (select id from ber.ber where referentienr = ? and srt= ?)", referentie, Integer.parseInt(srt));
        LOGGER.info("Aantal records in ber.pers met refnr en srt: " + list.size());
        if (!list.isEmpty()) {
            throw new AssertionFailedError("Id van ber.ber bericht komt voor in de ber.pers tabel: " + referentie + srt);
        }
    }

    private String geefWaarde(final String input, final String van, final String tot) {
        final int startIndex = input.indexOf(van);
        final int eindIndex = input.indexOf(tot);
        if (startIndex != -1 && eindIndex != -1) {
            return input.substring(startIndex + van.length(), eindIndex);
        }
        return null;
    }

    private void checkXmlGelijk(final String berichtA, String berichtB) throws SAXException, IOException {
        final Diff myDiff = new Diff(stripSoap(berichtA), stripSoap(berichtB));
        try {
            XMLAssert.assertXMLEqual(myDiff, true);
        } catch (AssertionFailedError e) {
            //AssertionFailedError is geen subklasse van Exceptie, daarom wrappen we hier
            throw new AssertionMisluktError("XML niet gelijk op xpath: $xpath", e);
        }
    }

    private String stripSoap(String input) {
        input = input.substring(input.indexOf("<brp:"), input.length());
        if (input.contains("</soap")) {
            input = input.substring(0, input.indexOf("</soap"));
        } else if (input.contains("</SOAP")) {
            input = input.substring(0, input.indexOf("</SOAP"));
        }
        return input;
    }

    private DataSource geefBerLezenSchrijvenDataSource() {
        return (DataSource) databaseSteps.getApplicationContext().getBean("berlezenSchrijvenDataSource");
    }
}
