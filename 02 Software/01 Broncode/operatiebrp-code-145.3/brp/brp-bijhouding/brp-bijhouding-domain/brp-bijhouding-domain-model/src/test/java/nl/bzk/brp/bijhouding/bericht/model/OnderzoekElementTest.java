/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Collections;
import org.junit.Test;

/**
 * Testen voor {@link OnderzoekElement}.
 */
public class OnderzoekElementTest {

    @Test
    public void testGetGegevensInOnderzoek() {
        //setup
        final nl.bzk.algemeenbrp.dal.domein.brp.enums.Element
                element =
                nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER;
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("CI_GIO_1", element.getNaam(), null, "2");
        final OnderzoekGroepElement onderzoekGroep = OnderzoekGroepElement.getInstance("ci_onderzoek_groep_1", 20170101, null, null, null);
        final OnderzoekElement
                onderzoekElement =
                OnderzoekElement.getInstance("ci_onderzoek_1", onderzoekGroep, Collections.singletonList(gegevenInOnderzoekElement));
        //execute & verify
        assertEquals(1, onderzoekElement.getGegevensInOnderzoek().size());
        assertSame(gegevenInOnderzoekElement, onderzoekElement.getGegevensInOnderzoek().iterator().next());
    }
}
