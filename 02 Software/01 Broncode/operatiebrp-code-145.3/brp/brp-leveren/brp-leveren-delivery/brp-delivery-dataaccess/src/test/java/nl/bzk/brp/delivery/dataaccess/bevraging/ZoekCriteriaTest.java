/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import org.junit.Assert;
import org.junit.Test;

/**
 * ZoekCriteriaTest.
 */
public class ZoekCriteriaTest {

    @Test
    public void testZoekLeeg() {
        final Set<ZoekCriterium> zoekCriteriaSet = new HashSet<>();
        ZoekCriterium zoekCriteria1 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_SOORTNAAM), Zoekoptie.EXACT,
                SoortIndicatie.DERDE_HEEFT_GEZAG.getId());
        ZoekCriterium zoekCriteria2 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE), Zoekoptie.LEEG, null);
        zoekCriteria2.setAdditioneel(zoekCriteria1);
        ZoekCriterium zoekCriteria3 = new ZoekCriterium(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER), Zoekoptie.EXACT, 123);

        zoekCriteriaSet.add(zoekCriteria2);
        zoekCriteriaSet.add(zoekCriteria3);

        final ZoekCriteria zoekCriteria = new ZoekCriteria(zoekCriteriaSet, false);
        final Set<BestaatNietClauseActueel> notExists = zoekCriteria.getBestaatNietClausesActueel();
        //1 leeg plus 1 additioneel in 1 object levert 1 not exist op voor dat object met 1 zoekcriteria + additioneel
        Assert.assertEquals(1, notExists.size());
        final BestaatNietClauseActueel bestaatNietClause = notExists.stream().findAny().get();
        Assert.assertEquals(1, bestaatNietClause.getZoekCriteria().size());
        //plus 1 not exist voor de huisnummer vraag
        final Set<ZoekCriterium> zoekCriteriumBestaat = zoekCriteria.getZoekCriteria();
        Assert.assertEquals(1, zoekCriteriumBestaat.size());


    }
}
