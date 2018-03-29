/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.vertaler;

import static nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat.NIET_GEVONDEN;
import static nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat.TECHNISCHE_FOUT_018;
import static nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat.TECHNISCHE_FOUT_020;
import static nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat.TECHNISCHE_FOUT_026;
import static nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat.TECHNISCHE_FOUT_032;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Test;

public class AntwoordBerichtResultaatTest {

    @Test
    public void ofElementMetÉénRegel() {
        assertEquals(Optional.of(NIET_GEVONDEN), AntwoordBerichtResultaat.of(Regel.R1403));
    }

    @Test
    public void ofElementMetMeerdereRegels() {
        assertEquals(Optional.of(TECHNISCHE_FOUT_020), AntwoordBerichtResultaat.of(Regel.R2375));
    }

    @Test
    public void ofNietGedefinieerdeRegel() {
        assertEquals(false, AntwoordBerichtResultaat.of(Regel.R1252).isPresent());
    }

    @Test
    public void omschrijving() {
        assertEquals("Te veel zoekresultaten", TECHNISCHE_FOUT_032.getOmschrijving());
    }

    @Test
    public void nullOmschrijving() {
        assertEquals("Te veel zoekresultaten", TECHNISCHE_FOUT_032.getOmschrijving());
    }

    @Test
    public void substitueerInOmschrijvingMetÉénArg() {
        assertEquals("Niet toegestaan zoekcriterium gebruikt: 010110", TECHNISCHE_FOUT_018.getOmschrijving("010110"));
    }

    @Test
    public void substitueerInOmschrijvingMetMeerdereArgs() {
        assertEquals("Onjuiste lengte voor rubriek: 010110=12345", TECHNISCHE_FOUT_026.getOmschrijving("010110=12345"));
    }
}
