/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.persoonsvraag;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;
import org.junit.Test;

public class NietToegestaneRubriekenValidatorTest {
    @Test
    public void zoekOpNietToegestaneRubriek() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
                persoonsvraag(10110, param(46310, "301")));
        Antwoord result = new NietToegestaneRubriekenValidator<>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(18, result.getResultaat().getCode());
        assertEquals("Niet toegestaan zoekcriterium gebruikt: 46310", result.getResultaat().getOmschrijving());
    }

    @Test
    public void zoekOpNietToegestaneRubrieken() {
        List<Integer> masker =
                Arrays.asList(40510, 46310, 46410, 46510, 47310, 48210, 48220, 48230, 48510, 48610, 540510, 546310, 546410, 546510, 548210, 548220, 548230,
                        548510, 548610);
        List<Zoekparameter> zoekparameters =
                Arrays.asList(param(10110, "2354507297"), param(40510, "0057"), param(46310, "301"), param(46410, "FR0057"), param(47310, "FR0057"));
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(persoonsvraag(masker, zoekparameters));
        Antwoord result = new NietToegestaneRubriekenValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(18, result.getResultaat().getCode());
        assertEquals("Niet toegestaan zoekcriterium gebruikt: 46310, 46410", result.getResultaat().getOmschrijving());
    }

    @Test
    public void zoekOpToegestaneRubriek() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(persoonsvraag(10110, param(10110, "123456789")));
        assertEquals(false, new NietToegestaneRubriekenValidator<AdhocWebserviceVraagBericht>().apply(vraagBericht).isPresent());
    }
}
