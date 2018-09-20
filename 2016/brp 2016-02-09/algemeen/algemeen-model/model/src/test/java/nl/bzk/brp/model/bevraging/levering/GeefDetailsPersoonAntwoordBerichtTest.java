/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging.levering;

import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;


/**
 *
 */
@Ignore
public class GeefDetailsPersoonAntwoordBerichtTest {

    @Test
    public void testGetGevondenPersoonGeenPersonen() {
        GeefDetailsPersoonAntwoordBericht ber = new GeefDetailsPersoonAntwoordBericht();
        Assert.assertNull(ber.getGevondenPersoon());
        Assert.assertFalse(ber.isResultaatGevonden());
    }

    @Test
    public void testGetGevondenPersoonLeegPersonen() {
        GeefDetailsPersoonAntwoordBericht ber = new GeefDetailsPersoonAntwoordBericht();
        Assert.assertNull(ber.getGevondenPersoon());
        Assert.assertFalse(ber.isResultaatGevonden());
    }

    @Test
    public void testGetGevondenPersoon() {
        GeefDetailsPersoonAntwoordBericht ber = new GeefDetailsPersoonAntwoordBericht();
        final PersoonHisVolledigView persoonModel = Mockito.mock(PersoonHisVolledigView.class);
        ber.voegGevondenPersoonToe(persoonModel);
        Assert.assertEquals(persoonModel, ber.getGevondenPersoon());
        Assert.assertTrue(ber.isResultaatGevonden());
    }
}
