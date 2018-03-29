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

public class NumeriekeZoekwaardenValidatorTest {
    @Test
    public void geenNumeriekeWaarde() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10120, "Aap")));
        Antwoord result = new NumeriekeZoekwaardenValidator<>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(22, result.getResultaat().getCode());
        assertEquals("Numeriek zoekcriterium 10120 bevat geen numerieke waarde", result.getResultaat().getOmschrijving());
    }

    @Test
    public void numeriekeWaarde() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10120, "1234567890")));
        assertEquals(false, new NumeriekeZoekwaardenValidator<>().apply(vraagBericht).isPresent());
    }
}
