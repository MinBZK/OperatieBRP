/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import java.util.Date;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de algemene methodes in de {@link BijhoudingAntwoordBericht}
 * klasse.
 */
public class AlgemeneAbstractBijhoudingAntwoordBerichtTest {

    @Test
    public void testGetterEnSetterTijdstipRegistratie() {
        BijhoudingAntwoordBericht bericht = new TestBericht();

        Assert.assertNull(bericht.getTijdstipRegistratie());

        final Date nu = new Date();
        bericht.setTijdstipRegistratie(nu.getTime());
        Assert.assertEquals(Long.valueOf(nu.getTime()), bericht.getTijdstipRegistratie());
    }

    @Test
    public void testTonenBijgehoudenPersonen() {
        BijhoudingAntwoordBericht bericht = new TestBericht();
        bericht.setResultaat(new BerichtResultaatGroepBericht());

        bericht.getResultaat().setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        Assert.assertTrue(bericht.tonenBijgehoudenPersonen());
        bericht.getResultaat().setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.FOUTIEF));
        Assert.assertFalse(bericht.tonenBijgehoudenPersonen());
    }

    private final class TestBericht extends BijhoudingAntwoordBericht {

        private TestBericht() {
            super(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE_R);
        }
    }
}
