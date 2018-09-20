/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.brp.model.beheer.autaut;

import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
@Ignore
public class HisDienstTest {

    private HisDienst hisDienst;

    /**
     * before.
     */
    @Before
    public final void init() {
        hisDienst = new HisDienst();
    }
    /**
     * Test of getFormeleHistorie method, of class HisDienst.
     */
    @Test
    public final void testGetFormeleHistorie() {
        Assert.assertEquals(hisDienst, hisDienst.getFormeleHistorie());
    }

    /**
     * Test of getVerwerkingssoort method, of class HisDienst.
     */
    @Test
    public final void testGetVerwerkingssoort() {
        Assert.assertNull(hisDienst.getVerwerkingssoort());
    }

    /**
     * Test of setVerwerkingssoort method, of class HisDienst.
     */
    @Test
    public final void testSetVerwerkingssoort() {
        hisDienst.setVerwerkingssoort(Verwerkingssoort.IDENTIFICATIE);
        Assert.assertNull(hisDienst.getVerwerkingssoort());
    }

    /**
     * Test of isMagGeleverdWorden method, of class HisDienst.
     */
    @Test
    public final void testIsMagGeleverdWorden() {
        Assert.assertFalse(hisDienst.isMagGeleverdWorden());
    }

    /**
     * test tijdstip registratie (methode naam klopt niet).
     */
    @Test
    public final void testGetTijdstipRegistratie() {
        final Date expectedDate = new Date();
        hisDienst.setDatumTijdRegistratie(new DatumTijdAttribuut(expectedDate));
        Assert.assertEquals(expectedDate, hisDienst.getTijdstipRegistratie().getWaarde());
    }

}
