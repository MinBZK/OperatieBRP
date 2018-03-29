/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.adresvraag;
import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AdresvraagIT extends AbstractIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void meerderePersonenOpPostcode() {
        Antwoord antwoord = request(
                adresvraag(10110,
                        param(81130, "a"),
                        param(81160, "1234AA")),
                "001801");
        assertANummers(antwoord, "5555555556", "5555555557");
    }

    @Test
    public void enkelePersoonOpPostcodeEnHuisnummer() {
        Antwoord antwoord = request(
                adresvraag(10110,
                        param(81160, "1234AA"),
                        param(81120, "1")),
                "001801");
        assertANummers(antwoord, "5555555555");
    }

    @Test
    public void enkelePersoonOpIdentificatiecodeVerblijfsplaats() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81190, "0626200010016003")),
                "001801");
        assertANummers(antwoord, "5555555558");
    }

    @Test
    public void medebewoners() {
        Antwoord antwoord = request(
                adresvraag(10110, param(10110, "5555555556")),
                "001801");
        assertANummers(antwoord, "5555555556", "5555555557");
    }

    @Test
    public void geenMedebewoners() {
        Antwoord antwoord = request(
                adresvraag(10110, param(10110, "5555555558")),
                "001801");
        assertANummers(antwoord, "5555555558");
    }

    @Test
    public void persoonZonderIndentificatieCodeNrAanduiding() {
        Antwoord antwoord = request(
                adresvraag(10110, param(10110, "5555555560")),
                "001801");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void nietBestaandeToegangLeveringsautorisatie() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81160, "1234AA")),
                "000099");
        assertAntwoord(antwoord, "X", 13);
    }

    @Test
    public void nietGeautoriseerdVoorGevraagdeRubriek() {
        Antwoord antwoord = request(
                adresvraag(Arrays.asList(10110, 81150), param(81160, "1234AA")),
                "001801");
        assertAntwoord(antwoord, "X", 13);
    }

    @Test
    public void nietGeautoriseerdVoorZoekRubriek() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81130, "3")),
                "001801");
        assertAntwoord(antwoord, "X", 20);
    }

    @Test
    public void nietUniekePersoonsidentificatie() {
        Antwoord antwoord = request(
                adresvraag(10110,
                        param(81160, "1234AA"),
                        param(10320, "0622")),
                "001801");
        assertAntwoord(antwoord, "P", 32);
    }

    @Test
    public void nietUniekeAdresidentificatie() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81160, "1234AA")), "001801");
        assertAntwoord(antwoord, "P", 32);
    }

    @Test
    public void alleGeenAdresidentificatie() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81160, "1234EE")), "001801");
        assertAntwoord(antwoord, "P", 32);
    }

    @Test
    public void slechtsEenGeenAdresidentificatie() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81160, "1234FF")), "001801");
        assertAntwoord(antwoord, "P", 32);
    }

    @Test
    public void geenPersoonGevonden() {
        Antwoord antwoord = request(
                adresvraag(10110, param(10110, "9999999999")), "001801");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void geenAdresGevonden() {
        Antwoord antwoord = request(
                adresvraag(10110, param(81160, "9999ZZ")), "001801");
        assertAntwoord(antwoord, "G", 33);
    }

    @Test
    public void geenActuelePersoonslijstOpAdres() {
        Antwoord antwoord = request(
                adresvraag(10110,
                        param(81160, "1234BB"),
                        param(81190, "0626200010016004")),
                "001801");
        assertAntwoord(antwoord, "Z", 37);
    }

    @Test
    public void geenActuelePersoonslijstOpPersoon() {
        Antwoord antwoord = request(
                adresvraag(10110, param(10110, "5555555559")), "001801");
        assertAntwoord(antwoord, "Z", 37);
    }

    @Test
    public void archiveren() {
        long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        request(adresvraag(10110, param(81190, "0626200010016003")), "001801");
        long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from ber.ber", Long.class);
        assertEquals(aantalBerichtenVoor + 2, aantalBerichtenNa);
    }

    @Test
    public void protocolleren() {
        long aantalBerichtenVoor = jdbcTemplate.queryForObject("select count(id) from prot.levsaantek", Long.class);
        request(adresvraag(10110, param(81190, "0626200010016003")), "001801");
        long aantalBerichtenNa = jdbcTemplate.queryForObject("select count(id) from prot.levsaantek", Long.class);
        assertEquals(aantalBerichtenVoor + 1, aantalBerichtenNa);
    }
}
