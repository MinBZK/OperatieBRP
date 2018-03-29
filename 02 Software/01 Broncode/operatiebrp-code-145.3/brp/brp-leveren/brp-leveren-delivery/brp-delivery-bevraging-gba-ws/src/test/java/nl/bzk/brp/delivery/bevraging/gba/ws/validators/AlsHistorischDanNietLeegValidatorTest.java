/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.persoonsvraag;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import org.junit.Test;

public class AlsHistorischDanNietLeegValidatorTest {

    @Test
    public void ok() {
        Vraag persoonsvraag = persoonsvraag(10120, param(10120, "123456789"));
        persoonsvraag.setIndicatieZoekenInHistorie(Byte.valueOf("1"));
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(persoonsvraag);
        Optional<Antwoord> result = new AlsHistorischDanNietLeegValidator().apply(vraagBericht);
        assertEquals(false, result.isPresent());
    }

    @Test
    public void nok() {
        Vraag persoonsvraag = persoonsvraag(10120, param(10120, ""));
        persoonsvraag.setIndicatieZoekenInHistorie(Byte.valueOf("1"));
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(persoonsvraag);
        Optional<Antwoord> result = new AlsHistorischDanNietLeegValidator().apply(vraagBericht);
        assertEquals(true, result.isPresent());
        assertEquals("X", result.get().getResultaat().getLetter());
        assertEquals(18, result.get().getResultaat().getCode());
        assertEquals("Niet toegestaan leeg zoekcriterium gebruikt voor historische zoekvraag: 0120", result.get().getResultaat().getOmschrijving());
    }
}
