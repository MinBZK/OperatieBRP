/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import org.junit.Assert;
import org.junit.Test;

public class InitVullingAutorisatieTest {

    @Test
    public void test() {
        final InitVullingAutorisatie subject = new InitVullingAutorisatie();

        Assert.assertNull(subject.getAutorisatieId());
        subject.setAutorisatieId(123456L);
        Assert.assertEquals(Long.valueOf(123456L), subject.getAutorisatieId());

        Assert.assertNull(subject.getConversieResultaat());
        subject.setConversieResultaat("VIERKANT");
        Assert.assertEquals("VIERKANT", subject.getConversieResultaat());

        Assert.assertNull(subject.getConversieMelding());
        subject.setConversieMelding("LINKERKANT IS STUK");
        Assert.assertEquals("LINKERKANT IS STUK", subject.getConversieMelding());
    }
}
