/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.persoonsvraag;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class PersoonsvraagIT extends AbstractIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void happyFlow() {
        Vraag vraag = persoonsvraag(Arrays.asList(10110, 10120), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567890");
    }

    @Test
    public void happyFlowHistorisch() {
        Vraag vraag = persoonsvraag(Arrays.asList(10110, 10120), param(10110, "1234567899"));
        vraag.setIndicatieZoekenInHistorie((byte) 1);
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567890");
    }

    @Test
    public void archiveren() {
        final long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);

        Vraag vraag = persoonsvraag(Arrays.asList(10110, 10120), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);

        final long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        assertEquals(aantalBerichtenVoor + 2, aantalBerichtenNa);
    }

    @Test
    public void protocolleren() {
        final long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from prot.levsaantek", Long.class);

        Vraag vraag = persoonsvraag(Arrays.asList(10110, 10120), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);

        final long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from prot.levsaantek", Long.class);
        assertEquals(aantalBerichtenVoor + 1, aantalBerichtenNa);
    }

    @Test
    public void dubbeleMaskerWaarden() {
        Vraag vraag = persoonsvraag(Arrays.asList(10110, 10110), param(10240, "Aap"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_025.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_025.getCode());
        assertEquals("Dubbel rubrieknummer in masker niet toegestaan: 10110", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void dubbeleParameterRubrieken() {
        Vraag vraag = persoonsvraag(Collections.singletonList(10110), Arrays.asList(param(10240, "Aap"), param(10240, "Noot")));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_024.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_024.getCode());
        assertEquals("Dubbel rubrieknummer in zoekcriteria niet toegestaan: 10240", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void numeriekElementBevatGeenGetal() {
        Vraag vraag = persoonsvraag(10110, param(10120, "Aap"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_022.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_022.getCode());
        assertEquals("Numeriek zoekcriterium 10120 bevat geen numerieke waarde", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void nietToegestaneZoekParameter() {
        Vraag vraag = persoonsvraag(10110, param(46310, "301"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getCode());
        assertEquals("Niet toegestaan zoekcriterium gebruikt: 46310", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void nietToegestaneZoekParameterMeerdereParameters() {
        List<Integer> masker =
                Arrays.asList(40510, 46310, 46410, 46510, 47310, 48210, 48220, 48230, 48510, 48610, 540510, 546310, 546410, 546510, 548210, 548220, 548230,
                        548510, 548610);
        List<Zoekparameter>
                zoekparameters =
                Arrays.asList(param(10110, "2354507297"), param(40510, "0057"), param(46310, "301"), param(46410, "FR0057"), param(47310, "FR0057"));
        Vraag vraag = persoonsvraag(masker, zoekparameters);
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getCode());
        assertEquals("Niet toegestaan zoekcriterium gebruikt: 46310, 46410", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void zoekwaardeLangerDanToegestaan() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567890"), param(10230, "1234567890987654321"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_026.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_026.getCode());
        assertEquals("Onjuiste lengte voor rubriek: 10230=1234567890987654321", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void meerderePersoonslijsten() {
        Vraag vraag = persoonsvraag(10110, param(10240, "Aap"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 2 persoonslijst bevatten", 2, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567890", "1234567891");
    }

    @Test
    public void geenResultaat() {
        Vraag vraag = persoonsvraag(10110, param(10110, "9999999999"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void geenPartij() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567890"));
        Antwoord antwoord = request(vraag);
        assertAntwoord(antwoord, "X", 13);
    }

    @Test
    public void nietGeautoriseerdVoorAdhocVraag() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "002001");
        assertAntwoord(antwoord, "X", 13);
    }

    @Test
    public void nietGeldigePartij() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001901");
        assertAntwoord(antwoord, "X", 13);
    }


    @Test
    public void populatieBeperking() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "002101");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void geheimhouding() {
        Vraag vraag = persoonsvraag(10110, param(10110, "1234567895"));
        Antwoord antwoord = request(vraag, "000701");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void geenAutorisatieVoorGevraagdeRubriek() {
        Vraag vraag = persoonsvraag(10210, param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "X", 13);
    }

    @Test
    public void geenVerplichtePersoonsidentificerendeZoekRubriekAanwezig() {
        Vraag vraag = persoonsvraag(10110, param(10210, "Bla"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "X", 19);
    }

    @Test
    public void slimZoekenWildcard() {
        Vraag vraag = persoonsvraag(10110, param(10240, "aa*"), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567890");
    }

    @Test
    public void slimZoekenWildcardCaseSensitive() {
        Vraag vraag = persoonsvraag(10110, param(10240, "aA*"), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "G", 33);
        assertEquals("Antwoord moet leeg zijn", true, antwoord.getPersoonslijsten().isEmpty());
    }

    @Test
    public void slimZoekenCaseInsensitive() {
        Vraag vraag = persoonsvraag(10110, param(10240, "aap"), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567890");
    }

    @Test
    public void slimZoekenCaseSensitive() {
        Vraag vraag = persoonsvraag(10110, param(10240, "AaP"), param(10110, "1234567890"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "G", 33);
        assertEquals("Antwoord moet leeg zijn", true, antwoord.getPersoonslijsten().isEmpty());
    }

    @Test
    public void slimZoekenDiakriet() {
        Vraag vraag = persoonsvraag(10110, param(10240, "Héger"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void slimZoekenDiakrietEnWildcard() {
        Vraag vraag = persoonsvraag(10110, param(10240, "Hé*"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void slimZoekenZonderDiakriet() {
        Vraag vraag = persoonsvraag(10110, param(10240, "heger"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void slimZoekenDatum() {
        Vraag vraag = persoonsvraag(10110, param(10310, "1977*"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void verkeerdSlimZoekenDatum() {
        Vraag vraag = persoonsvraag(10110, param(10310, "19770*"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "X", 22);
    }

    @Test
    public void slimZoekenDatumMetMaand() {
        Vraag vraag = persoonsvraag(10110, param(10310, "197701*"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void slimZoekenDatumVerkeerdMetJaar() {
        Vraag vraag = persoonsvraag(10110, param(10310, "\\1977*"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "X", 22);
    }

    @Test
    public void slimZoekenDatumExacteDatum() {
        Vraag vraag = persoonsvraag(10110, param(10310, "\\19770103"), param(10110, "1234567892"));
        Antwoord antwoord = request(vraag, "001801");
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertANummers(antwoord, "1234567892");
    }

    @Test
    public void geenAutorisatieVoorZoekRubriek() {
        Vraag vraag = persoonsvraag(Collections.singletonList(10110), Arrays.asList(param(10110, "1234567890"), param(10210, "123456789")));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, "X", 13);
    }
}
