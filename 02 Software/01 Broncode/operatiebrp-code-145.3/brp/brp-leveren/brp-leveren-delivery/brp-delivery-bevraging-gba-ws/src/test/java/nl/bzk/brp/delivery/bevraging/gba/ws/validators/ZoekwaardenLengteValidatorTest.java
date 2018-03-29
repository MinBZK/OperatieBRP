/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import org.junit.Test;

public class ZoekwaardenLengteValidatorTest {
    @Test
    public void teLangeWaarde() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.persoonsvraag(10110, param(81010, "1234567890987654321")));
        Antwoord result = new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(26, result.getResultaat().getCode());
        assertEquals("Onjuiste lengte voor rubriek: 81010=1234567890987654321", result.getResultaat().getOmschrijving());
    }

    @Test
    public void teLangeNumeriekeWaarde() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.persoonsvraag(10110, param(10110, "12345678")));
        Antwoord result = new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(26, result.getResultaat().getCode());
        assertEquals("Onjuiste lengte voor rubriek: 10110=12345678", result.getResultaat().getOmschrijving());
    }

    @Test
    public void teKorteBsn() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.persoonsvraag(10110, param(10120, "12345678901")));
        Antwoord result = new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(26, result.getResultaat().getCode());
        assertEquals("Onjuiste lengte voor rubriek: 10120=12345678901", result.getResultaat().getOmschrijving());
    }

    @Test
    public void gedeeltelijkOnbekendeDatum() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.persoonsvraag(10110, param(10310, "199902")));
        assertEquals(false, new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).isPresent());
    }

    @Test
    public void gedeeltelijkOnbekendeDatumAlleenJaar() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.persoonsvraag(10110, param(10310, "1999")));
        assertEquals(false, new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).isPresent());
    }

    @Test
    public void meerdereTeLangeWaarden() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                Vragen.adresvraag(10110, param(81010, "12345678910"), param(81160, "1234567890987654321")));
        Antwoord result = new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(26, result.getResultaat().getCode());
        assertEquals("Onjuiste lengte voor rubriek: 81010=12345678910, 81160=1234567890987654321", result.getResultaat().getOmschrijving());
    }

    @Test
    public void teLangeExactZoekenWaarde() {
        AdhocWebserviceVraagBericht
                vraagBericht =
                new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10110, "1234567890"), param(10410, "\\MV")));
        Antwoord result = new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(26, result.getResultaat().getCode());
        assertEquals("Onjuiste lengte voor rubriek: 10410=\\MV", result.getResultaat().getOmschrijving());
    }

    @Test
    public void wildcardNietControleren() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10120, "1234*")));
        assertEquals(false, new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).isPresent());
    }

    @Test
    public void correcteWaarde() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10110, "1234567890")));
        assertEquals(false, new ZoekwaardenLengteValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).isPresent());
    }
}
