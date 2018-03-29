/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import org.junit.Assert;
import org.junit.Test;

public class ZoekPersoonAntwoordBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void test() throws Exception {
        final ZoekPersoonAntwoordBericht subject = new ZoekPersoonAntwoordBericht();
        subject.setStatus(StatusType.OK);
        subject.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        subject.setPersoonId(123L);
        subject.setAnummer("1234567890");
        subject.setGemeente("1234");

        Assert.assertEquals(StatusType.OK, subject.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEVONDEN, subject.getResultaat());
        Assert.assertEquals(Long.valueOf(123), subject.getPersoonId());
        Assert.assertEquals("1234567890", subject.getAnummer());
        Assert.assertEquals("1234", subject.getGemeente());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }

}
