/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import java.util.Map;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HisModelAttribuutAccessAdministratieImplTest {

    private HisModelAttribuutAccessAdministratie administratie;

    @Before
    public void before() {
        this.administratie = new HisModelAttribuutAccessAdministratieImpl();
    }

    @Test
    public void testIsActief() {
        Assert.assertFalse(this.administratie.isActief());

        this.administratie.activeer();
        Assert.assertTrue(this.administratie.isActief());

        this.administratie.deactiveer();
        Assert.assertFalse(this.administratie.isActief());
    }

    @Test(expected = IllegalStateException.class)
    public void testNietActiefExceptieVoorHuidigeRegel() {
        this.administratie.setHuidigeRegel(Regel.ALG0001);
    }

    @Test(expected = IllegalStateException.class)
    public void testNietActiefExceptieVoorRegistratie() {
        this.administratie.attribuutGeraakt(1, 2L, 3, new DatumEvtDeelsOnbekendAttribuut(20130101));
    }

    @Test(expected = IllegalStateException.class)
    public void testGeenHuidigeRegelExceptieVoorRegistratie() {
        this.administratie.activeer();
        this.administratie.attribuutGeraakt(1, 2L, 3, new DatumEvtDeelsOnbekendAttribuut(20130101));
    }

    @Test
    public void testRegistratie() {
        // Given
        this.administratie.activeer();
        this.administratie.setHuidigeRegel(Regel.ALG0001);
        this.administratie.attribuutGeraakt(1, 2L, 3, new DatumEvtDeelsOnbekendAttribuut(20130101));
        this.administratie.deactiveer();

        // When
        Map<Regel, Set<AttribuutAccess>> regelAttribuutAccess = this.administratie.getRegelAttribuutAccess();

        // Then
        Assert.assertEquals(1, regelAttribuutAccess.size());
        Assert.assertEquals(Regel.ALG0001, regelAttribuutAccess.keySet().iterator().next());

        Set<AttribuutAccess> attribuutAccessen = regelAttribuutAccess.values().iterator().next();
        AttribuutAccess attribuutAccess = attribuutAccessen.iterator().next();
        Assert.assertEquals(1, attribuutAccessen.size());
        Assert.assertEquals(1, attribuutAccess.getGroepDbObjectId());
        Assert.assertEquals(2L, attribuutAccess.getVoorkomenId().longValue());
        Assert.assertEquals(3, attribuutAccess.getAttribuutDbObjectId());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(20130101), attribuutAccess.getDatumAanvangGeldigheid());
    }

}
