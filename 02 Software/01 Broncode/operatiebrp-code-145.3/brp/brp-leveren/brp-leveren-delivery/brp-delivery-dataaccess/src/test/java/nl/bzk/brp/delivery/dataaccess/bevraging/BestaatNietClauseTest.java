/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Test;

public class BestaatNietClauseTest {

    @Test
    public void test() throws Exception {
        final BestaatNietClauseActueel clause = new BestaatNietClauseActueel(ElementHelper.getObjectElement(Element.PERSOON));
        final ZoekCriterium zoekCriterium = new ZoekCriterium(ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_NAAM),
                Zoekoptie.EXACT, null);
        clause.getZoekCriteria().add(zoekCriterium);

        assertEquals(ElementHelper.getObjectElement(Element.PERSOON), clause.getObjectElement());
        assertEquals(zoekCriterium, Iterables.getOnlyElement(clause.getZoekCriteria()));
    }

    @Test
    public void testEquals() throws Exception {
        final BestaatNietClauseActueel clause1 = new BestaatNietClauseActueel(ElementHelper.getObjectElement(Element.PERSOON));
        final BestaatNietClauseActueel clause2 = new BestaatNietClauseActueel(ElementHelper.getObjectElement(Element.PERSOON));
        final BestaatNietClauseActueel clause3 = new BestaatNietClauseActueel(ElementHelper.getObjectElement(Element.KIND));

        assertTrue(clause1.equals(clause1));
        assertTrue(clause1.equals(clause2));
        assertFalse(clause1.equals(clause3));
        assertFalse(clause1.equals(null));
        assertFalse(clause1.equals(new ZoekCriterium(ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_NAAM), Zoekoptie.EXACT, null)));
    }

    @Test
    public void testHashCode() throws Exception {
    }

}
