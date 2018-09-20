/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.service.BerichtResultaatFactoryImpl;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.service.BerichtResultaatFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class BerichtResultaatFactoryImplTest {

    private final BerichtResultaatFactory berichtResultaatFactoryImpl = new BerichtResultaatFactoryImpl();

    @Test
    public void testCreeerBerichtResultaatBijhoudingsBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(Mockito.mock(BijhoudingsBericht.class),
                bouwBerichtContext()) instanceof BijhoudingResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatRegistreerGeboorteBericht() {
        final BerichtVerwerkingsResultaat resultaat =
            berichtResultaatFactoryImpl
                .creeerBerichtResultaat(new RegistreerGeboorteBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    @Test
    public void testCreeerBerichtResultaatRegistreerVerhuizingBericht() {
        final BerichtVerwerkingsResultaat resultaat =
            berichtResultaatFactoryImpl.creeerBerichtResultaat(new RegistreerVerhuizingBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    @Test
    public void testCreeerBerichtResultaatOverlijdenBericht() {
        final BerichtVerwerkingsResultaat resultaat =
                berichtResultaatFactoryImpl.creeerBerichtResultaat(new RegistreerOverlijdenBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    @Test
    public void testCreeerBerichtResultaatAdoptieBericht() {
        final BerichtVerwerkingsResultaat resultaat =
                berichtResultaatFactoryImpl
                        .creeerBerichtResultaat(new RegistreerAdoptieBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    private BijhoudingBerichtContext bouwBerichtContext() {
        return new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "refnr", null);
    }

    private class TestBericht extends BerichtBericht {

        protected TestBericht() {
            super(null);
        }
    }
}
