/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanPersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.ElementBuilder;
import nl.bzk.brp.bijhouding.bericht.model.PersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link BijhoudingsplanContext}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsplanContextTest {

    @Test
    public void testMaakBijhoudingsplanElementVoorBijhoudingAntwoordBericht () {
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.StuurgegevensParameters stuurParams = new ElementBuilder.StuurgegevensParameters();
        stuurParams.zendendePartij("50301");
        stuurParams.zendendeSysteem("gemeente");
        stuurParams.referentienummer("refnr");
        stuurParams.tijdstipVerzending("2016-01-01T12:00:00.000+01:00");
        final StuurgegevensElement stuurgegevensElement = builder.maakStuurgegevensElement("stuurgegevens", stuurParams);
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevensElement);
        final BijhoudingsplanContext context = new BijhoudingsplanContext(bericht);

        final BijhoudingPersoon nieuwPersoon = new BijhoudingPersoon();
        final BijhoudingSituatie bijhoudingSituatie = BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN;
        context.addBijhoudingSituatieVoorPersoon(nieuwPersoon, bijhoudingSituatie);
        nieuwPersoon.setBijhoudingspartijVoorBijhoudingsplan(new Partij("partij", "000001"));
        final BijhoudingsplanElement bijhoudingsplanElement = context.maakBijhoudingsplanElementVoorBijhoudingAntwoordBericht();
        assertNotNull(bijhoudingsplanElement);
        final BijhoudingsplanPersoonElement bijhoudingsplanPersoonElement = bijhoudingsplanElement.getBijhoudingsplanPersonen().get(0);
        assertEquals(bijhoudingSituatie.getNaam(), bijhoudingsplanPersoonElement.getSituatieNaam().getWaarde());
        final PersoonElement persoonElement = bijhoudingsplanPersoonElement.getPersoon();
        assertNull(persoonElement.getAfgeleidAdministratief());
        assertNull(persoonElement.getIdentificatienummers());
    }
}
