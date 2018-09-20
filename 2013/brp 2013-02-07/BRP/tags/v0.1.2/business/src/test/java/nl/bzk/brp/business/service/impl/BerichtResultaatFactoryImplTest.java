/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service.impl;

import java.util.Collection;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.service.BerichtResultaatFactory;
import nl.bzk.brp.business.service.BerichtResultaatFactoryImpl;
import org.junit.Test;


public class BerichtResultaatFactoryImplTest {

    private final BerichtResultaatFactory berichtResultaatFactoryImpl = new BerichtResultaatFactoryImpl();

    @Test
    public void testCreeerBerichtResultaatBijhoudingsBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new BijhoudingsBericht()) instanceof BijhoudingResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatInschrijvingGeboorteBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new InschrijvingGeboorteBericht()) instanceof BijhoudingResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVerhuizingBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new VerhuizingBericht()) instanceof BijhoudingResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVraagDetailsPersoonBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new VraagDetailsPersoonBericht()) instanceof OpvragenPersoonResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVraagPersonenOpAdresInclusiefBetrokkenhedenBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new VraagPersonenOpAdresInclusiefBetrokkenhedenBericht()) instanceof OpvragenPersoonResultaat);
    }

    @Test
    public void testCreeerBerichtResultaatVraagKandidaatVaderBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(new VraagKandidaatVaderBericht()) instanceof OpvragenPersoonResultaat);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreeerBerichtResultaatOpvragenOnbekendBericht() {
        berichtResultaatFactoryImpl.creeerBerichtResultaat(new TestBericht());
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

}
