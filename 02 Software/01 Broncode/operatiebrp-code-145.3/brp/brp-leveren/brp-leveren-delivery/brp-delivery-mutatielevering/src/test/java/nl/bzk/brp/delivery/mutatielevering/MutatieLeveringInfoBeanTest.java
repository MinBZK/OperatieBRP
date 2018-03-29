/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MutatieLeveringInfoBeanTest {

    private MutatieLeveringInfoBean bean = new MutatieLeveringInfoBean();

    @Test
    public void testInfoBean() {
        Assert.assertEquals(0, bean.getAantal());
        Assert.assertEquals(0, bean.getAfnemerBericht());

        bean.incrementAfnemerBericht();
        Assert.assertEquals(1, bean.getAfnemerBericht());

        bean.incrementHandeling(100);
        bean.incrementHandeling(200);
        Assert.assertEquals(150, bean.getGemiddeldeTijdPerHandeling());
        Assert.assertTrue(bean.getAfnemerBerichtenPerSeconde() > 0);
        Assert.assertTrue(bean.getHandelingenPerSeconde() > 0);
    }
}
