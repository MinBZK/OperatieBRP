/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import org.junit.Test;

public class DubbeleGevraagdeRubriekenValidatorTest {
    @Test
    public void bevatDubbele() {
        AdhocWebserviceVraagBericht vraagBericht =
                new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(Arrays.asList(10110, 10120, 10130, 10120, 10130), param(10210, "Bla")));
        Antwoord result = new DubbeleGevraagdeRubriekenValidator<>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(25, result.getResultaat().getCode());
        assertEquals("Dubbel rubrieknummer in masker niet toegestaan: 10120, 10130", result.getResultaat().getOmschrijving());
    }

    @Test
    public void bevatGeenDubbele() {
        AdhocWebserviceVraagBericht vraagBericht =
                new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(Arrays.asList(10110, 10120, 10130), param(10110, "123456789")));
        assertEquals(false, new DubbeleGevraagdeRubriekenValidator<>().apply(vraagBericht).isPresent());
    }
}
