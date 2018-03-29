/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.element.AttribuutElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * ZoekCriteriumTest.
 */
public class ZoekCriteriumTest {

    private static final AttribuutElement HUIS_NR = getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
    private static final AttribuutElement GEMEENTE_CODE = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
    private static final Zoekoptie ZOEK_OPTIE = Zoekoptie.EXACT;
    private static final String ZOEK_WAARDE = "3";

    @Test
    public void testOf() {
        final ZoekCriterium zoekCriterium = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);

        final ZoekCriterium zoekCriteriumHuisnummer1 = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);
        final ZoekCriterium zoekCriteriumHuisnummer2 = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);
    }

    @Test
    public void testZoekCriterium() {
        final ZoekCriterium zoekCriterium = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);
        Assert.assertEquals(HUIS_NR, zoekCriterium.getElement());
        Assert.assertEquals(ZOEK_OPTIE, zoekCriterium.getZoekOptie());
        Assert.assertEquals(ZOEK_WAARDE, zoekCriterium.getWaarde());
    }

    @Test
    public void testZoekCriteriumGelijkheid() {
        final ZoekCriterium zoekCriteriumA = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);
        final ZoekCriterium zoekCriteriumB = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, ZOEK_WAARDE);
        final ZoekCriterium zoekCriteriumC = new ZoekCriterium(GEMEENTE_CODE, ZOEK_OPTIE, ZOEK_WAARDE);
        final ZoekCriterium zoekCriteriumD = new ZoekCriterium(HUIS_NR, ZOEK_OPTIE, "4");
        final ZoekCriterium zoekCriteriumE = new ZoekCriterium(null, ZOEK_OPTIE, ZOEK_WAARDE);

        Assert.assertTrue(zoekCriteriumA.equals(zoekCriteriumA));
        Assert.assertTrue(zoekCriteriumA.equals(zoekCriteriumB));
        Assert.assertFalse(zoekCriteriumA.equals(zoekCriteriumC));
        Assert.assertTrue(zoekCriteriumA.equals(zoekCriteriumD));
        Assert.assertFalse(zoekCriteriumA.equals(zoekCriteriumE));
        Assert.assertFalse(zoekCriteriumA.equals(null));
        Assert.assertFalse(zoekCriteriumA.equals(HUIS_NR));
    }

    @Test
    public void hash() {
        Assert.assertEquals(0, new ZoekCriterium(null, ZOEK_OPTIE, ZOEK_WAARDE).hashCode());
    }
}
