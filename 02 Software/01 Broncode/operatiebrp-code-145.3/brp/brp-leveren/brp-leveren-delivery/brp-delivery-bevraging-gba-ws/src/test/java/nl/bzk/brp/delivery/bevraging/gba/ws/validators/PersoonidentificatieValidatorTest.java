/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.VraagPLWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import org.junit.Test;

public class PersoonidentificatieValidatorTest {

    @Test
    public void geenPersoonidentificerendGegeven() {
        VraagPLWebserviceVraagBericht vraagBericht = new VraagPLWebserviceVraagBericht(Vragen.vraagPLVraag(param(10210, "Bla")));
        Antwoord result = new OpvragenPLidentificatieValidator().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(19, result.getResultaat().getCode());
        assertEquals("Geen correcte persoonsidentificatie", result.getResultaat().getOmschrijving());
    }

    @Test
    public void welPersoonidentificerendGegeven() {
        VraagPLWebserviceVraagBericht vraagBericht = new VraagPLWebserviceVraagBericht(Vragen.vraagPLVraag(param(10110, "123456789")));
        assertEquals(false, new OpvragenPLidentificatieValidator().apply(vraagBericht).isPresent());
    }
}
