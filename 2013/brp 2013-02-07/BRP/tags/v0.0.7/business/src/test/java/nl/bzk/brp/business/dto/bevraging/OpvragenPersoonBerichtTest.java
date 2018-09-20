/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.util.Date;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test class voor de {@link OpvragenPersoonBericht} class. */
public class OpvragenPersoonBerichtTest {

    @Test
    public void testPartijId() {
        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();

        Partij partij = null;
        ReflectionTestUtils.setField(bericht, "afzender", null);
        Assert.assertNull(bericht.getPartijId());

        partij = new Partij();
        partij.setId(Integer.valueOf(2));
        ReflectionTestUtils.setField(bericht, "afzender", partij);
        Assert.assertEquals(Integer.valueOf(2), bericht.getPartijId());
    }

    @Test
    public void testReadBsns() {
        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();
        ReflectionTestUtils.setField(bericht, "opvragenPersoonCriteria", bouwCriteriaVoorBsn("123456789"));

        Assert.assertFalse(bericht.getReadBsnLocks().isEmpty());
        Assert.assertEquals("123456789", bericht.getReadBsnLocks().iterator().next());
    }

    @Test
    public void testWriteBsns() {
        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();
        ReflectionTestUtils.setField(bericht, "opvragenPersoonCriteria", bouwCriteriaVoorBsn("123456789"));

        Assert.assertNull(bericht.getWriteBsnLocks());
    }

    @Test
    public void testZetDatum() {
        OpvragenPersoonBericht bericht = new OpvragenPersoonBericht();
        Assert.assertNull(bericht.getTijdstipVerzonden());
        Assert.assertNull(bericht.getTijdstipVerzondenDate());

        bericht.setTijdstipVerzondenDate(new Date());
        Assert.assertNotNull(bericht.getTijdstipVerzonden());
        Assert.assertNotNull(bericht.getTijdstipVerzondenDate());
        Assert.assertTrue(bericht.getTijdstipVerzondenDate().equals(bericht.getTijdstipVerzonden().getTime()));
    }

    private OpvragenPersoonCriteria bouwCriteriaVoorBsn(final String bsn) {
        OpvragenPersoonCriteria criteria = new OpvragenPersoonCriteria();
        ReflectionTestUtils.setField(criteria, "identificatienummers", new PersoonIdentificatienummers());
        criteria.getIdentificatienummers().setBurgerservicenummer(bsn);
        return criteria;
    }
}
