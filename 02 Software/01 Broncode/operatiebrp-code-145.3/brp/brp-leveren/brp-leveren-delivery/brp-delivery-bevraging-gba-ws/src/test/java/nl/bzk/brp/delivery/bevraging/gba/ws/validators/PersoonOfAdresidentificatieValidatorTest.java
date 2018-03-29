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

public class PersoonOfAdresidentificatieValidatorTest {
    @Test
    public void geenPersoonOfAdresidentificerendGegeven() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10210, "Bla")));
        Antwoord result = new PersoonOfAdresidentificatieValidator().apply(vraagBericht).get();
        assertEquals("X", result.getResultaat().getLetter());
        assertEquals(20, result.getResultaat().getCode());
        assertEquals("Geen correcte persoons- of adresidentificatie", result.getResultaat().getOmschrijving());
    }

    @Test
    public void welOpAlleenGeboorteDatumGegeven() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10310, "19701231")));
        assertEquals(false, new PersoonOfAdresidentificatieValidator().apply(vraagBericht).isPresent());
    }

    @Test
    public void welPersoonidentificerendGegeven() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.persoonsvraag(10110, param(10110, "123456789")));
        assertEquals(false, new PersoonOfAdresidentificatieValidator().apply(vraagBericht).isPresent());
    }

    @Test
    public void welAdresidentificerendGegeven() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.adresvraag(10110, param(81110, "Dorpstraat")));
        assertEquals(false, new PersoonOfAdresidentificatieValidator().apply(vraagBericht).isPresent());
    }

    @Test
    public void adresEnPersoonsidentificerendeGegevens() {
        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(Vragen.adresvraag(10110,
                param(10240, "Jansen"),
                param(81020, "1709"),
                param(81110, "Dorpsstraat"),
                param(81120, "666")));
        assertEquals(false, new PersoonOfAdresidentificatieValidator().apply(vraagBericht).isPresent());
    }
}
