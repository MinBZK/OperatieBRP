/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;

/**
 * MetaInformatieZoekCriteriumTest.
 */
public class MetaInformatieZoekCriteriumTest {

    @Test
    public void maakOfCriteria() {
        ZoekCriterium zoekCriterium = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT,
                3);
        ZoekCriterium zoekCriteriumOf1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT,
                "postcode2", zoekCriterium);
        ZoekCriterium zoekCriteriumOf2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT,
                "postcode3", zoekCriteriumOf1);
        ZoekCriterium zoekCriteriumOf3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_POSTCODE), Zoekoptie.EXACT,
                "postcode4", zoekCriteriumOf2);

        final MetaInformatieZoekCriterium
                metaInformatieZoekCriterium =
                MetaInformatieZoekCriterium.maak(zoekCriteriumOf3, true);

        Assert.assertEquals(3, metaInformatieZoekCriterium.getZoekCriteriaOrClauses().size());
    }
}
