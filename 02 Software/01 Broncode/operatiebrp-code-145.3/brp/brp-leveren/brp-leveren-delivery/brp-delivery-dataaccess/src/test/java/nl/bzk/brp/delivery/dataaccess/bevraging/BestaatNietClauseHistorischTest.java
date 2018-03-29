/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.junit.Test;

public class BestaatNietClauseHistorischTest {

    private static final GroepElement GROEP_ELEMENT_STANDAARD =
            ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD);
    private static final GroepElement GROEP_ELEMENT_IDENTITEIT =
            ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT);

    @Test
    public void test() {
        final BestaatNietClauseHistorisch bestaatNietClauseHistorisch =
                new BestaatNietClauseHistorisch(GROEP_ELEMENT_STANDAARD);
        assertEquals(GROEP_ELEMENT_STANDAARD, bestaatNietClauseHistorisch.getGroepElement());
        assertTrue(bestaatNietClauseHistorisch.getZoekCriteria().isEmpty());
    }

    @Test
    public void testEquals(){
        final BestaatNietClauseHistorisch bestaatNietClauseHistorisch1 =
                new BestaatNietClauseHistorisch(GROEP_ELEMENT_STANDAARD);
        final BestaatNietClauseHistorisch bestaatNietClauseHistorisch2 =
                new BestaatNietClauseHistorisch(GROEP_ELEMENT_STANDAARD);
        final BestaatNietClauseHistorisch bestaatNietClauseHistorisch3 =
                new BestaatNietClauseHistorisch(GROEP_ELEMENT_IDENTITEIT);

        assertTrue(bestaatNietClauseHistorisch1.equals(bestaatNietClauseHistorisch1));
        assertTrue(bestaatNietClauseHistorisch1.equals(bestaatNietClauseHistorisch2));
        assertFalse(bestaatNietClauseHistorisch1.equals(bestaatNietClauseHistorisch3));
        assertFalse(bestaatNietClauseHistorisch1.equals(null));
        assertFalse(bestaatNietClauseHistorisch1.equals(GROEP_ELEMENT_IDENTITEIT));
    }
}
