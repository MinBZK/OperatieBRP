/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jcabi.aspects.RetryOnFailure;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.service.datatoegang.QueryForListVerzoek;
import nl.bzk.brp.test.common.OnzekerResultaatExceptie;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.test.common.xml.XmlUtils;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.Then;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

/**
 * Testclient hulpklasse tbv archiveren.
 */
public class ArchiveringAsserts {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String SPECIAL_COL_BSN = "bsn";
    private static final String SPECIAL_COL_SOORT_DIENST = "soortDienst";
    private static final String SPECIAL_COL_BERICHTSOORT = "berichtSoort";
    private static final String SPECIAL_COL_LEVSAUTORISATIENAAM = "leveringsautorisatieNaam";
    private static final String COL_REFERENTIENR = "referentienr";
    private static final String COL_CROSSREFERENTIENR = "crossreferentienr";
    private static final String COL_SRT = "srt";
    private static final String XML_REFERENTIENUMMER = "<brp:referentienummer>";
    private static final String XML_REFERENTIENUMMER_CLOSE = "</brp:referentienummer>";
    private static final String RICHTING = "richting";

    private final DatabaseDocker archiveringDb;
    private final BrpOmgeving brpOmgeving;


    /**
     * Constructor.
     * @param brpOmgeving de omgeving
     */
    public ArchiveringAsserts(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
        archiveringDb = brpOmgeving.geefDocker(DockerNaam.ARCHIVERINGDB);
    }

    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met referentie en srt
     * @param referentie de referentie om te controleren
     * @param srt de srt om te controleren
     */
    public void assertGeenArchivering(final String referentie, final SoortBericht srt) {
        LOGGER.info("Assert geen archivering met ref {} en srt {}", referentie, srt);

        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put(COL_REFERENTIENR, referentie);
        filterMapParam.put(COL_SRT, String.valueOf(srt.getId()));

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
        verzoek.setForceerJoin(true);
        archiveringDb.template().readonly(verzoek);

        Assert.isTrue(verzoek.getRecords().isEmpty(), String.format("Er is wel gearchiveerd voor referentie [%s] en srt [%s]", referentie, srt));
    }

    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met crossreferentie en srt
     * @param crossreferentie de crossreferentie om te controleren
     * @param srt de srt om te controleren
     */
    public void assertGeenPersArchivering(final String crossreferentie, final SoortBericht srt) {
        LOGGER.info("Assert geen archivering voor persoon met crossref {} en srt {}", crossreferentie, srt);
        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put(COL_CROSSREFERENTIENR, crossreferentie);
        filterMapParam.put(COL_SRT, String.valueOf(srt.getId()));
        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
        verzoek.setForceerJoin(true);
        archiveringDb.template().readonly(verzoek);

        Assert.isTrue(verzoek.getRecords().isEmpty(), String.format("Er is wel gearchiveerd voor filter: %s", filterMapParam.toString()));
    }

    /**
     * Stap om te controleren dat de tijdstip verzending in het bericht (zie data kolom ber.ber tabel) gelijk is aan tsverzending kolom in de ber.ber
     * tabel.
     */
    public void assertTijdstipVerzendingInBerichtIsGelijkAanTijdstipInArchivering() throws ParseException {
        LOGGER.info("Assert de tijdstip verzending in bericht is gelijk aan tijdstip in archief");

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(Maps.newHashMap());
        archiveringDb.template().readonly(verzoek);

        if (verzoek.getRecords().isEmpty()) {
            throw new OnzekerResultaatExceptie("Er is (nog) niet gearchiveerd...");
        }

        for (Map<String, Object> map : verzoek.getRecords()) {
            final String bericht = (String) map.get("data");
            final String tijdstipVerzending = XmlUtils.getNodeWaarde("//brp:stuurgegevens/brp:tijdstipVerzending", XmlUtils.stringToDocument(bericht));
            final Timestamp tsverzending = (Timestamp) map.get("tsverzending");
            if (tsverzending == null) {
                throw new TestclientExceptie("Geen tsverzending in ber.ber.");
            }
            if (tijdstipVerzending != null) {
                LOGGER.info(String.format("vergelijk tsverzending in database %s met tijdstipVerzending in bericht %s",
                        tsverzending.toString(), tijdstipVerzending));

                final String tsVerzendingDb = LocalDateTime.parse(tsverzending.toString().replace(' ', 'T')).atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneId.of("UTC")).toString();
                Assert.isTrue(ZonedDateTime.parse(tijdstipVerzending).withZoneSameInstant(ZoneId.of("UTC")).toString().equals(tsVerzendingDb));
            } else {
                throw new TestclientExceptie("Geen tijdstip in bericht.");
            }
        }
    }

    /**
     * Stap om te controleren dat de tijdstip ontvangst binnen redelijke grenzen actueel is.
     * Gezien de mogelijke tijdsverschillen op de servers hanteren we voorlopig een uur.
     */
    @Then("tijdstipontvangst is actueel")
    public void assertTijdstipOntvangsIsActueel() throws ParseException {
        LOGGER.info("Assert tijdstip ontvangst is actueel");

        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put(RICHTING, "1");
        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
        archiveringDb.template().readonly(verzoek);

        for (Map<String, Object> map : verzoek.getRecords()) {
            final Timestamp tsontv = (Timestamp) map.get("tsontv");
            final long nu = System.currentTimeMillis();
            final long verschil = Math.max(tsontv.getTime(), nu) - Math.min(tsontv.getTime(), nu);
            LOGGER.info(String.format("verschil tsontv = %d msec", verschil));
            if (verschil > 1000 * 60 * 60) {
                throw new TestclientExceptie("Tijdstip ontvangst niet actueel (verschil groter dan 1 uur!!)");
            }
        }
    }

    public void assertAdministratieveHandelingGearchiveerd(final String bsn) {
        LOGGER.info("Assert de administratievehandeling is correct gearchiveeerd");

        final PersHandelingVerzoek persAdmnhdVerzoek = new PersHandelingVerzoek(bsn);
        brpOmgeving.brpDatabase().template().readonly(persAdmnhdVerzoek);

        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put(SPECIAL_COL_BSN, bsn);
        filterMapParam.put("admhnd", String.valueOf(persAdmnhdVerzoek.getHandelingId()));
        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
        archiveringDb.template().readonly(verzoek);

        if (verzoek.getRecords().isEmpty()) {
            throw new OnzekerResultaatExceptie(
                    String.format("Er is nog niet gearchiveerd voor filter [%s]", filterMapParam.toString()));
        }
    }

    /**
     * Leegt de ber.ber tabellen.
     */
    public void reset() {
        LOGGER.info("Start leeg berichten (ber) tabellen");
        archiveringDb.template().readwrite(jdbcTemplate -> jdbcTemplate.batchUpdate("delete from ber.berpers", "delete from ber.ber"));
        LOGGER.info("Einde leeg berichten (ber) tabellen");
    }

    /**
     * Stap om te controleren dat de kruisreferentie in het bericht gelijk is aan de kruisreferentie in de ber.ber tabel
     */
    public void assertKruisreferentieGelijk() {
        LOGGER.info("Assert kruisreferentie is gelijk");

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(Maps.newHashMap());
        archiveringDb.template().readonly(verzoek);

        for (Map<String, Object> map : verzoek.getRecords()) {
            final String referentie = (String) map.get("referentienr");
            final String data = (String) map.get("data");
            final int startIndex = data.indexOf(XML_REFERENTIENUMMER);
            final int eindIndex = data.indexOf(XML_REFERENTIENUMMER_CLOSE);
            if (startIndex != -1 && eindIndex != -1) {
                final String referentieInBer = data.substring(startIndex + XML_REFERENTIENUMMER.length(), eindIndex);
                LOGGER.info(String.format("vergelijken referentie uit database %s met referentie in bericht %s", referentie, referentieInBer));
                Assert.isTrue(StringUtils.equals(referentie, referentieInBer));
            }
        }
    }

    @RetryOnFailure(delay = 2000L, types = OnzekerResultaatExceptie.class, randomize = false)
    public void assertDienstIdGelijk() {
        LOGGER.info("Assert dienstId is gelijk");

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(Maps.newHashMap());
        archiveringDb.template().readonly(verzoek);

        for (Map<String, Object> map : verzoek.getRecords()) {
            if (map.get("data") == null) {
                continue;
            }
            final Integer dienst = (Integer) map.get("dienst");
            final String data = (String) map.get("data");
            final String dienstInBericht = geefWaarde(data, "<brp:dienstIdentificatie>", "</brp:dienstIdentificatie>");
            if (dienstInBericht != null) {
                LOGGER.info(String.format("vergelijken dienstid uit database %s met dienstid in bericht %s", dienst, dienstInBericht));
                Assert.isTrue(dienst.equals(Integer.parseInt(dienstInBericht)));
            }
        }
    }

    public void assertLeveringautorisatieGelijk() {
        LOGGER.info("Assert leveringsautorisatie is gelijk");

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(Maps.newHashMap());
        archiveringDb.template().readonly(verzoek);

        for (Map<String, Object> map : verzoek.getRecords()) {
            if (map.get("data") == null) {
                continue;
            }
            final Integer levsautorisatie = (Integer) map.get("levsautorisatie");
            final String data = (String) map.get("data");

            final String levsautorisatieInBericht = geefWaarde(data, "<brp:leveringsautorisatieIdentificatie>",
                    "</brp:leveringsautorisatieIdentificatie>");
            if (levsautorisatieInBericht != null) {
                LOGGER.info(String.format("vergelijken leveringsautorisatie uit database %s met leveringsautorisatie in bericht %s", levsautorisatie,
                        levsautorisatieInBericht));
                Assert.isTrue(levsautorisatie.equals(Integer.parseInt(levsautorisatieInBericht)));
            }
        }
    }

    /**
     * Stap om te controleren dat het synchrone request en response bericht correct gearchiveerd zijn.
     */
    public void assertSynchroonGearchiveerd() {
        LOGGER.info("Assert het synchrone verzoek is correct gearchiveerd");

        //check request
        {
            final String request = brpOmgeving.verzoekService().getRequest();
            final String requestReferentie = geefWaarde(request, XML_REFERENTIENUMMER, XML_REFERENTIENUMMER_CLOSE);
            LOGGER.info("Controleer het request met referentie: " + requestReferentie);

            final Map<String, String> filterMapParam = Maps.newHashMap();
            filterMapParam.put(RICHTING, "1");
            filterMapParam.put(COL_REFERENTIENR, requestReferentie);

            final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
            archiveringDb.template().readonly(verzoek);

            if (verzoek.getRecords().isEmpty()) {
                throw new OnzekerResultaatExceptie("(Nog) geen request archivering");
            }
            org.junit.Assert.assertEquals(1, verzoek.getRecords().size());
            final String requestberichtUitArchief = (String) verzoek.getRecords().get(0).get("data");
            XmlUtils.assertGelijk("/", request, requestberichtUitArchief);
        }

        //check response
        {
            final String response = brpOmgeving.verzoekService().getResponse();
            final String responseReferentie = geefWaarde(response, XML_REFERENTIENUMMER, XML_REFERENTIENUMMER_CLOSE);
            LOGGER.info("Controleer het response met referentie: " + responseReferentie);

            final Map<String, String> filterMapParam = Maps.newHashMap();
            filterMapParam.put(RICHTING, "2");
            filterMapParam.put(COL_REFERENTIENR, responseReferentie);

            final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
            archiveringDb.template().readonly(verzoek);

            if (verzoek.getRecords().isEmpty()) {
                throw new OnzekerResultaatExceptie("(Nog) geen response archivering");
            }
            org.junit.Assert.assertEquals(1, verzoek.getRecords().size());
            final String requestberichtUitArchief = (String) verzoek.getRecords().get(0).get("data");
            XmlUtils.assertGelijk("/", response, requestberichtUitArchief);
        }

    }

    public void assertUitgaandGearchiveerdVoorPersoon(final String bsn) {
        LOGGER.info("Assert er is uitgaand gearchiveerd voor persoon met bsn {}", bsn);

        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put(SPECIAL_COL_BSN, bsn);
        filterMapParam.put(RICHTING, "2");

        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterMapParam);
        archiveringDb.template().readonly(verzoek);

        if (verzoek.getRecords().isEmpty()) {
            throw new OnzekerResultaatExceptie("(Nog) niet uitgaand geachiveerd");
        }
    }

    /**
     * Stap om te controleren dat er een uitgaand bericht bestaat als antwoord op het ingaande bericht.
     * @param referentie de referentie om te controleren
     */
    public void assertErBestaatEenAntwoordberichtVoorReferentie(final String referentie)  {
        LOGGER.info("Assert dat er een antwoordbericht bestaat voor referentie {}", referentie);

        final Map<String, String> filterMapParam = Maps.newHashMap();
        filterMapParam.put("crossreferentienr", referentie);
        filterMapParam.put(RICHTING, "2");

        final ArchiveringrecordVerzoek archiveringrecordVerzoek = new ArchiveringrecordVerzoek(filterMapParam);
        archiveringDb.template().readonly(archiveringrecordVerzoek);

        if (archiveringrecordVerzoek.getRecords().isEmpty()) {
            throw new OnzekerResultaatExceptie("(Nog) geen antwoord bericht gevonden voor referentie: " + referentie);
        }
    }

    /**
     * Assert dat er gearchiveerd is met de gegeven waarden.
     * @param filterwaarden map met filterwaarden
     */
    public void assertGearchiveerdMetDeVolgendeGegevens(final Map<String, String> filterwaarden) {
        LOGGER.info("Assert gearchiveerd met de volgende gegevens");
        final ArchiveringrecordVerzoek verzoek = new ArchiveringrecordVerzoek(filterwaarden);
        brpOmgeving.archiveringDatabase().template().readonly(verzoek);

        if (verzoek.getRecords().isEmpty()) {
            throw new OnzekerResultaatExceptie("(Nog) geen archiveringrecords gevonden");
        }
    }

    private final class ArchiveringrecordVerzoek implements Consumer<JdbcTemplate> {

        private static final String QUERY = "select * from ber.ber";
        private static final String QUERY_PERS = "select * from ber.ber b, ber.berpers bp where bp.ber = b.id";

        private final Map<String, String> filterMap;
        private List<Map<String, Object>> records;
        private boolean forceerJoin = false;

        private ArchiveringrecordVerzoek(final Map<String, String> filterMapParam) {

            filterMap = Maps.newLinkedHashMap(filterMapParam);

            if (filterMapParam.containsKey(SPECIAL_COL_BSN)) {
                this.filterMap.remove(SPECIAL_COL_BSN);

                final PersIdVerzoek persIdVerzoek = new PersIdVerzoek(filterMapParam.get(SPECIAL_COL_BSN));
                brpOmgeving.brpDatabase().template().readonly(persIdVerzoek);
                final int persId = persIdVerzoek.getPersId();
                this.filterMap.put("pers", String.valueOf(persId));
            }

            if (filterMapParam.containsKey(SPECIAL_COL_SOORT_DIENST)) {
                this.filterMap.remove(SPECIAL_COL_SOORT_DIENST);
                final String soortDienstNaam = filterMapParam.get(SPECIAL_COL_SOORT_DIENST);
                final SoortDienst gevondenDienst = Lists.newArrayList(SoortDienst.values()).stream()
                        .filter(soortDienst -> soortDienst.getNaam().equals(soortDienstNaam)).findFirst()
                        .orElseThrow(() -> new TestclientExceptie("Ongeldige soort dienst: " + soortDienstNaam));


                final List<Dienst> gevondenDiensten = brpOmgeving.autorisaties().geefDiensten(gevondenDienst);
                org.junit.Assert.assertFalse("Geen autorisatie gevonden voor dienst: " + gevondenDienst, gevondenDiensten.isEmpty());
                if (gevondenDiensten.size() > 1) {
                    LOGGER.warn("Meerdere diensten gevonden...eerste wordt gebruikt");
                }
                filterMap.put("dienst", gevondenDiensten.iterator().next().getId().toString());
            }
            if (filterMapParam.containsKey(SPECIAL_COL_BERICHTSOORT)) {
                this.filterMap.remove(SPECIAL_COL_BERICHTSOORT);
                final String soortBerichtValue = filterMapParam.get(SPECIAL_COL_BERICHTSOORT);
                final SoortBericht gevondenSoortBericht =
                        Lists.newArrayList(SoortBericht.values()).stream()
                                .filter(soortBericht -> soortBericht.getIdentifier().equals(soortBerichtValue))
                                .findFirst()
                                .orElseThrow(() -> new TestclientExceptie("SoortBericht niet gevonden: " + soortBerichtValue));
                this.filterMap.put("srt", String.valueOf(gevondenSoortBericht.getId()));
            }

            if (filterMapParam.containsKey(SPECIAL_COL_LEVSAUTORISATIENAAM)) {
                this.filterMap.remove(SPECIAL_COL_LEVSAUTORISATIENAAM);
                final String leveringsautorisatieNaam = filterMapParam.get(SPECIAL_COL_LEVSAUTORISATIENAAM);
                final QueryForListVerzoek verzoek = new QueryForListVerzoek(
                        String.format("select id from autaut.levsautorisatie where naam = '%s'", leveringsautorisatieNaam));
                brpOmgeving.brpDatabase().template().readonly(verzoek);
                Assert.isTrue(!verzoek.getRecords().isEmpty(), "Leveringsautorisatie niet gevonden met naam: " + leveringsautorisatieNaam);
                this.filterMap.put("levsautorisatie", String.valueOf(verzoek.getRecords().iterator().next().get("id")));
            }

        }

        @Override
        public void accept(final JdbcTemplate jdbcTemplate) {

            LOGGER.info("Filterwaarden: {}", filterMap);
            List<Map<String, Object>> tempRecords = jdbcTemplate.queryForList(forceerJoin || filterMap.containsKey("pers") ? QUERY_PERS : QUERY);

            // filter
            LOGGER.info("Totaal aantal berichtrecords: {}", tempRecords.size());
            for (Map<String, Object> record : tempRecords) {
                final Map<String, Object> printMap = Maps.newHashMap(record);
                printMap.replace("data", "<data>");
                LOGGER.info("record = {}", printMap.toString());
            }

            for (Map.Entry<String, String> filterwaarde : filterMap.entrySet()) {

                if (tempRecords.isEmpty()) {
                    break;
                }

                LOGGER.info("Filter op key={} value={}", filterwaarde.getKey(), filterwaarde.getValue());
                tempRecords = tempRecords.stream().filter(stringObjectMap -> {
                    if (!stringObjectMap.containsKey(filterwaarde.getKey())) {
                        return false;
                    }
                    final String dbWaarde = String.valueOf(stringObjectMap.get(filterwaarde.getKey()));
                    return filterwaarde.getValue().equalsIgnoreCase(dbWaarde);
                }).collect(Collectors.toList());

                LOGGER.info("Resultaat na filter = {} records", tempRecords.size());
            }
            records = tempRecords;

        }

        List<Map<String, Object>> getRecords() {
            return records;
        }

        void setForceerJoin(final boolean forceerJoin) {
            this.forceerJoin = forceerJoin;
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
}
