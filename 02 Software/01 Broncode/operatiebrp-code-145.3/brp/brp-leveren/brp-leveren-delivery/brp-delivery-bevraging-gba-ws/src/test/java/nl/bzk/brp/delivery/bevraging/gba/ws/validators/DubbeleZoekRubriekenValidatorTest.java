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

public class DubbeleZoekRubriekenValidatorTest {
    @Test
    public void bevatDubbele() {
        AdhocWebserviceVraagBericht vraagBericht =
                new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110,
                        param(10110, "123456789"),
                        param(10210, "Bla"),
                        param(10110, "123456789")));
        Antwoord result = new DubbeleZoekRubriekenValidator<>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(24, result.getResultaat().getCode());
        assertEquals("Dubbel rubrieknummer in zoekcriteria niet toegestaan: 10110", result.getResultaat().getOmschrijving());
    }

    @Test
    public void bevatGeenDubbele() {
        AdhocWebserviceVraagBericht vraagBericht =
                new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110,
                        param(10110, "123456789"),
                        param(10120, "9876543210")));
        assertEquals(false, new DubbeleZoekRubriekenValidator<>().apply(vraagBericht).isPresent());
    }
}
