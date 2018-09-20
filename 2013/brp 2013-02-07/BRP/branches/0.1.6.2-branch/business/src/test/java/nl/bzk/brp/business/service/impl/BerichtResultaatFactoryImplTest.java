/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service.impl;

import java.util.Collection;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtResultaatFactory;
import nl.bzk.brp.business.service.BerichtResultaatFactoryImpl;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.junit.Test;
import org.mockito.Mockito;


public class BerichtResultaatFactoryImplTest {

    private final BerichtResultaatFactory berichtResultaatFactoryImpl = new BerichtResultaatFactoryImpl();

    @Test
    public void testCreeerBerichtResultaatBijhoudingsBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(Mockito.mock(AbstractBijhoudingsBericht.class),
                bouwBerichtContext()) instanceof BijhoudingResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatInschrijvingGeboorteBericht() {
        BerichtResultaat resultaat =
            berichtResultaatFactoryImpl.creeerBerichtResultaat(new InschrijvingGeboorteBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    @Test
    public void testCreeerBerichtResultaatVerhuizingBericht() {
        BerichtResultaat resultaat =
            berichtResultaatFactoryImpl.creeerBerichtResultaat(new VerhuizingBericht(), bouwBerichtContext());
        Assert.assertTrue(resultaat instanceof BijhoudingResultaat);
        Assert.assertNotNull(((BijhoudingResultaat) resultaat).getTijdstipRegistratie());
    }

    @Test
    public void testCreeerBerichtResultaatVraagDetailsPersoonBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(new VraagDetailsPersoonBericht(),
                bouwBerichtContext()) instanceof OpvragenPersoonResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVraagPersonenOpAdresInclusiefBetrokkenhedenBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(
            new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht(),
            bouwBerichtContext()) instanceof OpvragenPersoonResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVraagKandidaatVaderBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(new VraagKandidaatVaderBericht(),
                bouwBerichtContext()) instanceof OpvragenPersoonResultaat);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreeerBerichtResultaatOpvragenOnbekendBericht() {
        berichtResultaatFactoryImpl.creeerBerichtResultaat(new TestBericht(), bouwBerichtContext());
    }

    private class TestBericht implements BRPBericht {

        @Override
        public Integer getPartijId() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<String> getReadBsnLocks() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection<String> getWriteBsnLocks() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private BerichtContext bouwBerichtContext() {
        return new BerichtContext(new BerichtenIds(1L, 2L), 3, new Partij(), "refnr");
    }
}
