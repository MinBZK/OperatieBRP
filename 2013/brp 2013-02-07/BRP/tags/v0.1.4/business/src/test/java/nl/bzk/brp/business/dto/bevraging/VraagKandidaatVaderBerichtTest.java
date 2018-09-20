/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link VraagKandidaatVaderBericht} class.
 */
public class VraagKandidaatVaderBerichtTest {

    @Test
    public void testGetReadBsnLocks() {
        VraagKandidaatVaderBericht bericht = new VraagKandidaatVaderBericht();
        KandidaatVaderVraag vraag = new KandidaatVaderVraag();

        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());

        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());

        vraag.setBurgerservicenummer("123456789");
        Assert.assertFalse(bericht.getReadBsnLocks().isEmpty());
        Assert.assertEquals("123456789", bericht.getReadBsnLocks().iterator().next());

        vraag.setBurgerservicenummer("");
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());
    }

    @Test
    public void testGetWriteBsnLocks() {
        VraagKandidaatVaderBericht bericht = new VraagKandidaatVaderBericht();
        KandidaatVaderVraag vraag = new KandidaatVaderVraag();

        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        ReflectionTestUtils.setField(bericht, "vraag", vraag);
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        vraag.setBurgerservicenummer("123456789");
        Assert.assertTrue(bericht.getWriteBsnLocks().isEmpty());

        vraag.setBurgerservicenummer("");
        Assert.assertTrue(bericht.getReadBsnLocks().isEmpty());
    }
}
