/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link VraagDetailsPersoonBericht} class waarin de specifieke methodes van deze class worden
 * getest.
 */
public class VraagDetailsPersoonBerichtTest {

    @Test
    public void testStandaardGettersEnSetters() {
        VraagDetailsPersoonBericht bericht = new VraagDetailsPersoonBericht();
        Assert.assertNull(bericht.getBerichtStuurgegevens());
        Assert.assertNull(bericht.getVraag());

        BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
        DetailsPersoonVraag vraag = new DetailsPersoonVraag();
        bericht.setBerichtStuurgegevens(stuurgegevens);
        ReflectionTestUtils.setField(bericht, "vraag", vraag);

        Assert.assertSame(stuurgegevens, bericht.getBerichtStuurgegevens());
        Assert.assertSame(vraag, bericht.getVraag());
    }

    @Test
    public void testReadBsns() {
        VraagDetailsPersoonBericht vraagDetailsPersoonBericht = new VraagDetailsPersoonBericht();
        Assert.assertTrue(vraagDetailsPersoonBericht.getReadBsnLocks().isEmpty());

        DetailsPersoonVraag vraag = new DetailsPersoonVraag();
        vraag.setBurgerservicenummer("123456789");
        ReflectionTestUtils.setField(vraagDetailsPersoonBericht, "vraag", vraag);

        Assert.assertFalse(vraagDetailsPersoonBericht.getReadBsnLocks().isEmpty());
        Assert.assertEquals(1, vraagDetailsPersoonBericht.getReadBsnLocks().size());
        Assert.assertEquals("123456789", vraagDetailsPersoonBericht.getReadBsnLocks().iterator().next());
    }

    @Test
    public void testWriteBsnsAltijdLeeg() {
        VraagDetailsPersoonBericht vraagDetailsPersoonBericht = new VraagDetailsPersoonBericht();
        Assert.assertTrue(vraagDetailsPersoonBericht.getWriteBsnLocks().isEmpty());

        DetailsPersoonVraag vraag = new DetailsPersoonVraag();
        vraag.setBurgerservicenummer("123456789");
        ReflectionTestUtils.setField(vraagDetailsPersoonBericht, "vraag", vraag);

        Assert.assertTrue(vraagDetailsPersoonBericht.getWriteBsnLocks().isEmpty());
    }

    @Test
    public void testOphalenPartijId() {
        VraagDetailsPersoonBericht vraagDetailsPersoonBericht = new VraagDetailsPersoonBericht();
        Assert.assertNull(vraagDetailsPersoonBericht.getPartijId());

        BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
        vraagDetailsPersoonBericht.setBerichtStuurgegevens(stuurgegevens);
        Assert.assertNull(vraagDetailsPersoonBericht.getPartijId());

        stuurgegevens.setOrganisatie("123");
        Assert.assertEquals(Integer.valueOf(123), vraagDetailsPersoonBericht.getPartijId());
    }

    @Test
    public void testOphalenOngeldigePartijId() {
        VraagDetailsPersoonBericht vraagDetailsPersoonBericht = new VraagDetailsPersoonBericht();

        BerichtStuurgegevens stuurgegevens = new BerichtStuurgegevens();
        stuurgegevens.setOrganisatie("test");
        vraagDetailsPersoonBericht.setBerichtStuurgegevens(stuurgegevens);
        Assert.assertNull(vraagDetailsPersoonBericht.getPartijId());

        stuurgegevens.setOrganisatie("");
        Assert.assertNull(vraagDetailsPersoonBericht.getPartijId());
    }
}
