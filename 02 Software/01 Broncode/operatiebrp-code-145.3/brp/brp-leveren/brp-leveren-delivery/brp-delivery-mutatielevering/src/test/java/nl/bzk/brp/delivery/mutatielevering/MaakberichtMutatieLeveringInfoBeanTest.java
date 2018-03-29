/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * MaakberichtMutatieLeveringInfoBeanTest.
 */
public class MaakberichtMutatieLeveringInfoBeanTest {

    private MaakberichtMutatieLeveringInfoBean maakberichtMutatieLeveringInfoBean = new MaakberichtMutatieLeveringInfoBean();

    @Test
    public void testMaakberichtMutatieLeveringInfoBean() {
        assertEquals(0, maakberichtMutatieLeveringInfoBean.getAantal());
        maakberichtMutatieLeveringInfoBean.incrementHandeling(100);

        assertEquals(1, maakberichtMutatieLeveringInfoBean.getAantal());
        assertEquals(100, maakberichtMutatieLeveringInfoBean.getGemiddeldeTijdPerHandeling());

        maakberichtMutatieLeveringInfoBean.incrementAfnemerBericht();
        assertEquals(1, maakberichtMutatieLeveringInfoBean.getAfnemerBericht());

        maakberichtMutatieLeveringInfoBean.incrementHandeling(200);
        assertEquals(150, maakberichtMutatieLeveringInfoBean.getGemiddeldeTijdPerHandeling());
        assertTrue(maakberichtMutatieLeveringInfoBean.getHandelingenPerSeconde() > 0);
        assertTrue(maakberichtMutatieLeveringInfoBean.getAfnemerBerichtenPerSeconde() > 0);
    }
}
